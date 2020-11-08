package com.fomin.eatcalc.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fomin.eatcalc.R;
import com.fomin.eatcalc.adapters.RecipeIngredientsAdapter;
import com.fomin.eatcalc.datastorage.Ingredient;
import com.fomin.eatcalc.datastorage.Recipe;
import com.fomin.eatcalc.viewmodels.IngredientViewModel;
import com.fomin.eatcalc.viewmodels.RecipeViewModel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RecipeActivity extends AppCompatActivity {

    private TextView name, portionsNum, price, currency, cookingMethod;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        name = findViewById(R.id.recipe_name);
        portionsNum = findViewById(R.id.portions_num);
        price = findViewById(R.id.recipe_price);
        currency = findViewById(R.id.recipe_currency);
        cookingMethod = findViewById(R.id.cooking_method);

        List<Ingredient> ingredients = new LinkedList<>();
        RecyclerView ingredientsView = findViewById(R.id.recipe_ingredients);
        RecipeIngredientsAdapter adapter = new RecipeIngredientsAdapter(this, ingredients);
        ingredientsView.setAdapter(adapter);
        ingredientsView.setLayoutManager(new LinearLayoutManager(this));

        long recipeId = getIntent().getLongExtra("recipeId", 1);

        ViewModelStoreOwner owner = this;
        Thread calculating = new Thread(() -> {
            ViewModelProvider viewModelProvider = new ViewModelProvider(owner);
            IngredientViewModel ingredientViewModel = viewModelProvider.get(IngredientViewModel.class);
            RecipeViewModel recipeViewModel = viewModelProvider.get(RecipeViewModel.class);

            Recipe recipe = recipeViewModel.getById(recipeId);
            name.setText(recipe.name);
            portionsNum.setText(String.valueOf(recipe.portions));
            price.setText(String.format(Locale.US, "%.2f", recipe.price/recipe.portions));
            currency.setText(R.string.rub); // TODO: добавить поддержку пересчёта валюты
            cookingMethod.setText(recipe.method);

            HashMap<Long, Double> counts = recipe.ingredients;
            for(Map.Entry<Long, Double> entry : counts.entrySet()) {
                Ingredient ingredient = ingredientViewModel.getById(entry.getKey());
                double count = entry.getValue();
                if(count!=0) {
                    ingredient.count = count;
                    ingredient.price *= count;
                    ingredients.add(ingredient);

                    runOnUiThread(adapter::notifyDataSetChanged);
                }
            }
        });
        calculating.start();
    }
}
