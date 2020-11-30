package com.fomin.eatcalc.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fomin.eatcalc.adapters.RecipeIngredientsAdapter;
import com.fomin.eatcalc.datastorage.Ingredient;
import com.fomin.eatcalc.datastorage.Recipe;
import com.fomin.eatcalc.validation.EmptyValidator;
import com.fomin.eatcalc.validation.ValidatorsComposer;
import com.fomin.eatcalc.viewmodels.IngredientViewModel;
import com.fomin.eatcalc.viewmodels.RecipeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import com.fomin.eatcalc.R;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AddRecipeActivity extends AppCompatActivity {

    public static final int EDIT_RECIPE_INGREDIENTS_REQUEST_CODE = 4;
    private TextInputEditText name;
    private TextInputEditText portionsNum;
    private TextInputEditText method;
    private HashMap<Long, Double> counts;
    private double mass = 0;
    private double price = 0;
    private boolean isValid = false;
    private boolean isError = false;
    private final Context context = this;
    private final List<Ingredient> ingredients = new LinkedList<>();
    private RecipeIngredientsAdapter adapter;
    private long id = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        adapter = new RecipeIngredientsAdapter(this, ingredients);
        counts = new HashMap<>();

        name = findViewById(R.id.new_recipe_name);
        portionsNum = findViewById(R.id.new_recipe_portions_num);
        method = findViewById(R.id.new_recipe_method);

        RecyclerView ingredientsView = findViewById(R.id.add_recipe_ingredients);
        ingredientsView.setAdapter(adapter);
        ingredientsView.setLayoutManager(new LinearLayoutManager(this));

        Intent curIntent = getIntent();
        int editRecipePortions = curIntent.getIntExtra("portions", 0);
        String editRecipeName = curIntent.getStringExtra("name");
        String editRecipeMethod = curIntent.getStringExtra("method");
        name.setText(editRecipeName);
        portionsNum.setText(String.valueOf(editRecipePortions));
        method.setText(editRecipeMethod);


        if(curIntent.getIntExtra("requestCode", 2) == RecipesListActivity.UPDATE_RECIPE_ACTIVITY_REQUEST_CODE) {
            new Thread(()-> {
                RecipeViewModel recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
                long curId = curIntent.getLongExtra("id",1);
                id = curId;
                Recipe recipe = recipeViewModel.getById(curId);
                for(Map.Entry<Long, Double> entry : recipe.ingredients.entrySet()) {
                    counts.put(entry.getKey(), entry.getValue());
                }

                // TODO: воспользоваться calculating
                // TODO: count entry бывает нулевой
                IngredientViewModel viewModel = new ViewModelProvider(this).get(IngredientViewModel.class);
                ingredients.clear();
                for(Map.Entry<Long, Double> entry: counts.entrySet()) {
                    Ingredient ingredient = viewModel.getById(entry.getKey());
                    double count = entry.getValue();
                    if(count!=0 && ingredient!=null) {
                        ingredient.price *= count;
                        ingredient.count *= count;
                        price += ingredient.price;
                        mass += ingredient.count;
                        ingredients.add(ingredient); // TODO: воспользоваться конвертером единиц, чтобы найти массу рецепта

                        runOnUiThread(adapter::notifyDataSetChanged);
                    }
                }
            }).start();
        }

        FloatingActionButton button_add_ingredient = findViewById(R.id.add_recipe_ingredient);
        button_add_ingredient.setOnClickListener(v -> {
            Intent editIngredients = new Intent(AddRecipeActivity.this, EditIngredientsActivity.class);
            editIngredients.putExtra("counts", counts);
            startActivityForResult(editIngredients, EDIT_RECIPE_INGREDIENTS_REQUEST_CODE);
        });

        Button button_submit = findViewById(R.id.new_recipe_save);
        button_submit.setOnClickListener(v -> {
            Intent addRecipe = new Intent();
            isError = false;

            int portionsCount;
            try {
                portionsCount = Integer.parseInt(portionsNum.getText().toString());
            } catch (Exception e) {
                portionsCount = 0;
                portionsNum.setError(context.getString(R.string.input_number));
                isError = true;
            }
            String recipeName = name.getText().toString();
            String cookingMethod = method.getText().toString();

            ValidatorsComposer<String> validation = new ValidatorsComposer<>(new EmptyValidator(context));
            if(!validation.isValid(recipeName)) {
                name.setError(validation.getMessage());
                isError = true;
            }
            if(!validation.isValid(cookingMethod)) {
                method.setError(validation.getMessage());
                isError = true;
            }
            if(counts.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        context.getString(R.string.recipe_has_no_ingredients),
                        Toast.LENGTH_LONG
                ).show();
                isError= true;
            }
            if(!isError){
                // TODO: извлечь строки в переменные
                addRecipe.putExtra("portionsCount", portionsCount);
                addRecipe.putExtra("name", recipeName);
                addRecipe.putExtra("cookingMethod", cookingMethod);

                addRecipe.putExtra("resultCounts", counts);
                addRecipe.putExtra("price", price);
                addRecipe.putExtra("mass", mass);

                addRecipe.putExtra("id", id);

                setResult(RESULT_OK, addRecipe);
                isValid = true;
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if((requestCode == EDIT_RECIPE_INGREDIENTS_REQUEST_CODE) && (resultCode == RESULT_OK)) {
            counts = (HashMap<Long, Double>) data.getSerializableExtra("resultCounts");

            if(counts.isEmpty()) { // TODO: одинаковые тосты в функции
                Toast.makeText(
                        getApplicationContext(),
                        context.getString(R.string.recipe_has_no_ingredients),
                        Toast.LENGTH_LONG
                ).show();
                isError= true;
            }

            price = 0;
            mass = 0;

            Thread calculating = new Thread(() -> {
                IngredientViewModel viewModel = new ViewModelProvider(this).get(IngredientViewModel.class);
                ingredients.clear();
                for(Map.Entry<Long, Double> entry: counts.entrySet()) {
                    Ingredient ingredient = viewModel.getById(entry.getKey());
                    double count = entry.getValue();
                    if(count!=0) {
                        ingredient.price *= count;
                        ingredient.count *= count;
                        price += ingredient.price;
                        mass += ingredient.count;
                        ingredients.add(ingredient); // TODO: воспользоваться конвертером единиц, чтобы найти массу рецепта

                        runOnUiThread(adapter::notifyDataSetChanged);
                    }
                }
            });
            calculating.start();
            try {
                calculating.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            if(counts.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        context.getString(R.string.recipe_has_no_ingredients),
                        Toast.LENGTH_LONG
                ).show();
                isError= true;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!isValid) {
            setResult(RESULT_CANCELED, new Intent());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
