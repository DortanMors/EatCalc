package com.fomin.eatcalc.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.widget.Toast;

import com.fomin.eatcalc.R;
import com.fomin.eatcalc.datastorage.Recipe;
import com.fomin.eatcalc.swipe.SwipeController;
import com.fomin.eatcalc.swipe.SwipeControllerActions;
import com.fomin.eatcalc.adapters.IngredientAdapter;
import com.fomin.eatcalc.datastorage.Ingredient;
import com.fomin.eatcalc.viewmodels.IngredientViewModel;
import com.fomin.eatcalc.viewmodels.RecipeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

public class IngredientsListActivity extends AppCompatActivity {
    public static final int ADD_INGREDIENT_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_INGREDIENT_ACTIVITY_REQUEST_CODE = 3;
    private IngredientViewModel ingredientViewModel;
    private final ViewModelStoreOwner viewModelStoreOwner = this;
    private final LifecycleOwner lifecycleOwner = this;
    long toDeleteId = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_list);
        
        RecyclerView ingredientsList = findViewById(R.id.ingredients_list);
        final IngredientAdapter adapter = new IngredientAdapter(this);
        ingredientsList.setAdapter(adapter);
        ingredientsList.setLayoutManager(new LinearLayoutManager(this));

        ingredientViewModel = new ViewModelProvider(this).get(IngredientViewModel.class);
        ingredientViewModel.getAllIngredients().observe(this, adapter::setIngredients);


        ViewModelProvider viewModelProvider = new ViewModelProvider(viewModelStoreOwner);
        IngredientViewModel ingredientViewModel = viewModelProvider.get(IngredientViewModel.class);
        RecipeViewModel recipeViewModel = viewModelProvider.get(RecipeViewModel.class);
        recipeViewModel.getAllRecipes().observe(lifecycleOwner, recipes -> { // TODO: переделать
            for(Recipe recipe : recipes) {
                new Thread(()-> {
                    double price = 0;
                    double mass = 0;
                    HashMap<Long, Double> counts = recipe.ingredients;
                    counts.remove(toDeleteId);
                    for (Map.Entry<Long, Double> entry : counts.entrySet()) {
                        Ingredient ingredient = ingredientViewModel.getById(entry.getKey());
                        double count = entry.getValue();
                        if (count != 0 && ingredient!=null) {
                            ingredient.price *= count;
                            ingredient.count *= count;
                            price += ingredient.price;
                            mass += ingredient.count;
                        }
                    }
                    recipe.ingredients = counts;
                    recipe.mass = mass;
                    recipe.price = price;
                    recipeViewModel.update(recipe);
                }).start();
            }
        });


        SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                Ingredient toUpdate = adapter.getItem(position);
                Intent intent = new Intent(IngredientsListActivity.this, AddIngredientActivity.class);
                intent.putExtra("requestCode", UPDATE_INGREDIENT_ACTIVITY_REQUEST_CODE);
                intent.putExtra("id", toUpdate.id);
                intent.putExtra("count",toUpdate.count);
                intent.putExtra("units",toUpdate.unit_id);
                intent.putExtra("name",toUpdate.name);
                intent.putExtra("price",toUpdate.price);
                intent.putExtra("currency",toUpdate.currency_id);
                startActivityForResult(intent, UPDATE_INGREDIENT_ACTIVITY_REQUEST_CODE);
            }

            @Override
            public void onRightClicked(int position) {
                Ingredient toDelete = adapter.getItem(position);
                ingredientViewModel.delete(toDelete);
                toDeleteId = toDelete.id;
                // TODO: пересчитать все рецепты нафиг
            }
        });
        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeController);
        touchHelper.attachToRecyclerView(ingredientsList);
        ingredientsList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

        FloatingActionButton button_add = findViewById(R.id.add_ingredient_price);
        button_add.setOnClickListener(v -> {
            Intent intent = new Intent(IngredientsListActivity.this, AddIngredientActivity.class);
            startActivityForResult(intent, ADD_INGREDIENT_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            // TODO: извлечь строки в переменные, получать их из AddIngredientActivity
            double edit_count = data.getDoubleExtra("count", 1);
            String name = data.getStringExtra("name");
            String units = data.getStringExtra("units");
            String currency = data.getStringExtra("currency");
            double price = data.getDoubleExtra("price",0) / edit_count;

            if(requestCode == ADD_INGREDIENT_ACTIVITY_REQUEST_CODE) {
                Ingredient ingredient = new Ingredient(name, price, units, currency);
                ingredientViewModel.insert(ingredient);
            } else if(requestCode == UPDATE_INGREDIENT_ACTIVITY_REQUEST_CODE) {
                Ingredient ingredient = new Ingredient(name, price, units, currency);
                ingredient.id = data.getLongExtra("id",-1);
                ingredientViewModel.update(ingredient);
                // TODO: пересчитать всё по-нормальному
            }
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    getString(R.string.Ingredient_not_saved_because_it_is_uncompleted),
                    Toast.LENGTH_LONG
                ).show();
        }
    }
}