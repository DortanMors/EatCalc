package com.fomin.eatcalc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fomin.eatcalc.R;
import com.fomin.eatcalc.adapters.EditIngredientsAdapter;
import com.fomin.eatcalc.datastorage.Ingredient;
import com.fomin.eatcalc.viewmodels.IngredientViewModel;

import java.util.HashMap;
import java.util.List;

public class EditIngredientsActivity extends AppCompatActivity {
    private HashMap<Long, Double> counts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredients);

        RecyclerView ingredientsList = findViewById(R.id.edit_ingredients_list);
        counts = (HashMap<Long, Double>) getIntent().getSerializableExtra("counts");
        if(counts==null)
            counts = new HashMap<Long, Double>();
        EditIngredientsAdapter adapter = new EditIngredientsAdapter(this, counts);
        ingredientsList.setAdapter(adapter);
        ingredientsList.setLayoutManager(new LinearLayoutManager(this));

        IngredientViewModel viewModel = new ViewModelProvider(this).get(IngredientViewModel.class);
        viewModel.getAllIngredients().observe(this, new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(List<Ingredient> ingredients) {
                adapter.setIngredients(ingredients);
            }
        });

        Button button_submit = findViewById(R.id.recipe_ingredients_save);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                counts = adapter.getCounts();
                result.putExtra("resultCounts", counts);

                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
}
