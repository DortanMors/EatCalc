package com.fomin.eatcalc.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fomin.eatcalc.R;
import com.fomin.eatcalc.activity.navigator.Navigator;
import com.fomin.eatcalc.adapters.IngredientAdapter;
import com.fomin.eatcalc.datastorage.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientsListActivity extends AppCompatActivity {

    Button buttonAddIngredient;
    RecyclerView ingredientsList;
    List<Ingredient> ingredients;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_list);

        ingredients = new ArrayList<Ingredient>();
        Ingredient i = new Ingredient();
        i.currency_id=1;
        i.id=1;
        i.name="1";
        i.price=1d;
        i.unit_id=1;
        ingredients.add(i);
        ingredientsList = (RecyclerView) findViewById(R.id.ingredients_list);
        IngredientAdapter adapter = new IngredientAdapter(this, ingredients);
        ingredientsList.setAdapter(adapter);

        Navigator navigator = new Navigator();
        View buttonAddIngredient = (View) findViewById(R.id.add_ingredient_price);
        buttonAddIngredient.setOnClickListener(navigator);
    }
}