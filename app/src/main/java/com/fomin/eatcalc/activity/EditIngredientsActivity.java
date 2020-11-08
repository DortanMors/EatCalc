package com.fomin.eatcalc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fomin.eatcalc.R;
import com.fomin.eatcalc.adapters.EditIngredientsAdapter;
import com.fomin.eatcalc.datastorage.Ingredient;
import com.fomin.eatcalc.viewmodels.IngredientViewModel;

import java.util.HashMap;

public class EditIngredientsActivity extends AppCompatActivity {
    public static final int ADD_INGREDIENT_ACTIVITY_REQUEST_CODE = 1;
    private HashMap<Long, Double> counts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredients);

        RecyclerView ingredientsList = findViewById(R.id.edit_ingredients_list);

        counts = (HashMap<Long, Double>) getIntent().getSerializableExtra("counts");
        if(counts==null) counts = new HashMap<>();

        EditIngredientsAdapter adapter = new EditIngredientsAdapter(this, counts);
        ingredientsList.setAdapter(adapter);
        ingredientsList.setLayoutManager(new LinearLayoutManager(this));

        IngredientViewModel viewModel = new ViewModelProvider(this).get(IngredientViewModel.class);
        viewModel.getAllIngredients().observe(this, adapter::setIngredients);

        Button button_add_ingredient = findViewById(R.id.add_recipe_ingredient);
        button_add_ingredient.setOnClickListener(v -> {
            Intent intent = new Intent(EditIngredientsActivity.this, AddIngredientActivity.class);
            startActivityForResult(intent, ADD_INGREDIENT_ACTIVITY_REQUEST_CODE);
        });

        Button button_submit = findViewById(R.id.recipe_ingredients_save);
        button_submit.setOnClickListener(v -> {
            Intent result = new Intent();
            counts = adapter.getCounts();
            result.putExtra("resultCounts", counts);

            setResult(RESULT_OK, result);
            finish();
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_INGREDIENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // TODO: извлечь строки в переменные, получать их из AddIngredientActivity
            IngredientViewModel ingredientViewModel = new ViewModelProvider(this).get(IngredientViewModel.class);
            double edit_count = data.getDoubleExtra("count", 1);
            String name = data.getStringExtra("name");
            String units = data.getStringExtra("units");
            String currency = data.getStringExtra("currency");
            double price = data.getDoubleExtra("price",0) / edit_count;

            Ingredient ingredient = new Ingredient(name, price, units, currency);
            ingredientViewModel.insert(ingredient);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    getString(R.string.Ingredient_not_saved_because_it_is_uncompleted),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

}
