package com.fomin.eatcalc.activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fomin.eatcalc.R;
import com.fomin.eatcalc.adapters.RecipeAdapter;
import com.fomin.eatcalc.datastorage.Recipe;
import com.fomin.eatcalc.swipe.SwipeController;
import com.fomin.eatcalc.swipe.SwipeControllerActions;
import com.fomin.eatcalc.viewmodels.RecipeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;

public class RecipesListActivity extends AppCompatActivity {
    public static final int ADD_RECIPE_ACTIVITY_REQUEST_CODE = 2;
    public static final int UPDATE_RECIPE_ACTIVITY_REQUEST_CODE = 4;
    private RecipeViewModel recipeViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);

        RecyclerView recipesList = findViewById(R.id.recipes_list);
        final RecipeAdapter recipeAdapter = new RecipeAdapter(this);
        recipesList.setAdapter(recipeAdapter);
        recipesList.setLayoutManager(new LinearLayoutManager(this));

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        recipeViewModel.getAllRecipes().observe(this, recipeAdapter::setRecipes);

        SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                Recipe toUpdate = recipeAdapter.getItem(position);
                Intent intent = new Intent(RecipesListActivity.this, AddRecipeActivity.class);
                intent.putExtra("requestCode", UPDATE_RECIPE_ACTIVITY_REQUEST_CODE);
                intent.putExtra("id", toUpdate.name);
                intent.putExtra("name", toUpdate.name);
                intent.putExtra("portions", toUpdate.portions);
                intent.putExtra("method", toUpdate.method);
                startActivityForResult(intent, UPDATE_RECIPE_ACTIVITY_REQUEST_CODE);
            }

            @Override
            public void onRightClicked(int position) {
                Recipe toDelete = recipeAdapter.getItem(position);
                recipeViewModel.delete(toDelete);
            }
        });
        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeController);
        touchHelper.attachToRecyclerView(recipesList);
        recipesList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

        FloatingActionButton button_add = findViewById(R.id.add_new_recipe);
        button_add.setOnClickListener(v -> {
            Intent intent = new Intent(RecipesListActivity.this, AddRecipeActivity.class);
            startActivityForResult(intent, ADD_RECIPE_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            // TODO: извлечь строки в ресурсы/переменные
            String name = data.getStringExtra("name");
            String method = data.getStringExtra("cookingMethod");
            int portions = data.getIntExtra("portionsCount",0);
            double price = data.getDoubleExtra("price", 0);
            double mass = data.getDoubleExtra("mass", 0);
            HashMap<Long, Double> ingredients = (HashMap<Long, Double>) data.getSerializableExtra("resultCounts");

            if(requestCode == ADD_RECIPE_ACTIVITY_REQUEST_CODE) {
                Recipe recipe = new Recipe(name, method, portions, price, mass, ingredients);
                recipeViewModel.insert(recipe);
            } else if(requestCode == UPDATE_RECIPE_ACTIVITY_REQUEST_CODE) {
                Recipe recipe = new Recipe(name, method, portions, price, mass, ingredients);
                recipe.id = data.getLongExtra("id",-1);
                recipeViewModel.update(recipe);
            }
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    getString(R.string.recipe_not_saved),
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
