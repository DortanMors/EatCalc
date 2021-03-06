package com.fomin.eatcalc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fomin.eatcalc.R;
import com.fomin.eatcalc.adapters.RecipeAdapter;
import com.fomin.eatcalc.datastorage.Recipe;
import com.fomin.eatcalc.viewmodels.RecipeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;

public class RecipesListActivity extends AppCompatActivity {
    public static final int ADD_RECIPE_ACTIVITY_REQUEST_CODE = 2;
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
        recipeViewModel.getAllRecipes().observe(this, new Observer<List<Recipe>>(){

            @Override
            public void onChanged(List<Recipe> recipes) {
                recipeAdapter.setRecipes(recipes);
            }
        });

        FloatingActionButton button_add = findViewById(R.id.add_new_recipe);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesListActivity.this, AddRecipeActivity.class);
                startActivityForResult(intent, ADD_RECIPE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_RECIPE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // TODO: извлечь строки в ресурсы/переменные
            String name = data.getStringExtra("name");
            String method = data.getStringExtra("cookingMethod");
            int portions = data.getIntExtra("portionsCount",0);
            double price = data.getDoubleExtra("price", 0);
            double mass = data.getDoubleExtra("mass", 0);
            HashMap<Long, Double> ingredients = (HashMap<Long, Double>) data.getSerializableExtra("resultCounts");

            Recipe recipe = new Recipe(name, method, portions, price, mass, ingredients);
            recipeViewModel.insert(recipe);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    getString(R.string.recipe_not_saved),
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
