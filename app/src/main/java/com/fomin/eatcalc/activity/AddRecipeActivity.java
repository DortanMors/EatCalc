package com.fomin.eatcalc.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.fomin.eatcalc.datastorage.Ingredient;
import com.fomin.eatcalc.viewmodels.IngredientViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import com.fomin.eatcalc.R;

import java.util.HashMap;
import java.util.Map;

public class AddRecipeActivity extends AppCompatActivity {

    public static final int EDIT_RECIPE_INGREDIENTS_REQUEST_CODE = 4;
    private TextInputEditText name;
    private TextInputEditText portionsNum;
    private TextInputEditText method;
    private HashMap<Long, Double> counts;
    private double mass = 0;
    private double price = 0;
    private List<Ingredient> ingredients = new LinkedList<>();
    private RecipeIngredientsAdapter adapter;

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

        FloatingActionButton button_add_ingredient = findViewById(R.id.add_recipe_ingredient);
        button_add_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIngredients = new Intent(AddRecipeActivity.this, EditIngredientsActivity.class);
                editIngredients.putExtra("counts", counts);
                startActivityForResult(editIngredients, EDIT_RECIPE_INGREDIENTS_REQUEST_CODE);
            }
        });

        Button button_submit = findViewById(R.id.new_recipe_save);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addRecipe = new Intent();
                if(TextUtils.isEmpty(name.getText())) {
                    setResult(RESULT_CANCELED, addRecipe);
                } else { // TODO: добавить валидацию всех инпутов
                    int portionsCount = Integer.parseInt(portionsNum.getText().toString());
                    String recipeName = name.getText().toString();
                    String cookingMethod = method.getText().toString();
                    // TODO: извлечь строки в переменные
                    addRecipe.putExtra("portionsCount", portionsCount);
                    addRecipe.putExtra("name", recipeName);
                    addRecipe.putExtra("cookingMethod", cookingMethod);

                    addRecipe.putExtra("resultCounts", counts);
                    addRecipe.putExtra("price", price);
                    addRecipe.putExtra("mass", mass);
                    // TODO: если все данные в порядке
                    setResult(RESULT_OK, addRecipe);
                }
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if((requestCode == EDIT_RECIPE_INGREDIENTS_REQUEST_CODE) && (resultCode == RESULT_OK)) {
            counts = (HashMap<Long, Double>) data.getSerializableExtra("resultCounts");
            price = 0;
            mass = 0;

            ViewModelStoreOwner context = this;
            Thread calculating = new Thread(() -> {
                IngredientViewModel viewModel = new ViewModelProvider(context).get(IngredientViewModel.class);
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
            Toast.makeText(
                    getApplicationContext(),
                    getString(R.string.recipe_not_saved),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
