package com.fomin.eatcalc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fomin.eatcalc.R;
import com.fomin.eatcalc.adapters.EditIngredientsAdapter;
import com.fomin.eatcalc.viewmodels.IngredientViewModel;

import java.util.HashMap;

public class EditIngredientsActivity extends AppCompatActivity {
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

        Button button_submit = findViewById(R.id.recipe_ingredients_save);
        button_submit.setOnClickListener(v -> {
            Intent result = new Intent();
            counts = adapter.getCounts();
            result.putExtra("resultCounts", counts);

            setResult(RESULT_OK, result);
            finish();
        });
    }
}
