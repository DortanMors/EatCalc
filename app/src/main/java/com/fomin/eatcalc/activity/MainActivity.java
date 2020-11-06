package com.fomin.eatcalc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.fomin.eatcalc.R;
import com.fomin.eatcalc.activity.navigator.Navigator;

public class MainActivity extends AppCompatActivity {

    View buttonToIngredients, buttonToRecipes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Navigator navigator = new Navigator();
        buttonToIngredients = findViewById(R.id.prices);
        buttonToRecipes     = findViewById(R.id.recipes);
        buttonToIngredients.setOnClickListener(navigator);
        buttonToRecipes.setOnClickListener(navigator);
    }
}