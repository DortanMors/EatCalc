package com.fomin.eatcalc.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fomin.eatcalc.R;
import com.fomin.eatcalc.adapters.IngredientAdapter;
import com.fomin.eatcalc.datastorage.Ingredient;
import com.fomin.eatcalc.viewmodels.IngredientViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class IngredientsListActivity extends AppCompatActivity {
    public static final int ADD_INGREDIENT_ACTIVITY_REQUEST_CODE = 1;
    private IngredientViewModel ingredientViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_list);
        
        RecyclerView ingredientsList = findViewById(R.id.ingredients_list);
        final IngredientAdapter adapter = new IngredientAdapter(this);
        ingredientsList.setAdapter(adapter);
        ingredientsList.setLayoutManager(new LinearLayoutManager(this));

        ingredientViewModel = new ViewModelProvider(this).get(IngredientViewModel.class);
        ingredientViewModel.getAllIngredients().observe(this, new Observer<List<Ingredient>>() {

            @Override
            public void onChanged(@Nullable final List<Ingredient> ingredients) {
                adapter.setIngredients(ingredients);
            }
        });

        FloatingActionButton button_add = findViewById(R.id.add_ingredient_price);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IngredientsListActivity.this, AddIngredientActivity.class);
                startActivityForResult(intent, ADD_INGREDIENT_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_INGREDIENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // TODO: извлечь строки в переменные, получать их из AddIngredientActivity
            int edit_count = data.getIntExtra("count", 1);
            String name = data.getStringExtra("name");
            String units = data.getStringExtra("units");
            String currency = data.getStringExtra("currency");
            double price = data.getDoubleExtra("price",0) / edit_count;

            Ingredient ingredient = new Ingredient(name, price, units, currency);
            ingredientViewModel.insert(ingredient);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Ingredient not saved because it is uncomplete",
                    Toast.LENGTH_LONG
                ).show();
        }
    }
}