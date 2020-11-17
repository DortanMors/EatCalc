package com.fomin.eatcalc.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.widget.Toast;

import com.fomin.eatcalc.R;
import com.fomin.eatcalc.swipe.SwipeController;
import com.fomin.eatcalc.swipe.SwipeControllerActions;
import com.fomin.eatcalc.adapters.IngredientAdapter;
import com.fomin.eatcalc.datastorage.Ingredient;
import com.fomin.eatcalc.viewmodels.IngredientViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        ingredientViewModel.getAllIngredients().observe(this, adapter::setIngredients);

        SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {

            }

            @Override
            public void onRightClicked(int position) {
                Ingredient toDelete = adapter.getItem(position);
                ingredientViewModel.delete(toDelete);
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

        if(requestCode == ADD_INGREDIENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // TODO: извлечь строки в переменные, получать их из AddIngredientActivity
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