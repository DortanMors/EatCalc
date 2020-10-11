package com.fomin.eatcalc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.fomin.eatcalc.R;
import com.fomin.eatcalc.activity.navigator.Navigator;

public class AddIngredientActivity extends AppCompatActivity {

    View buttonSubmit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_add_ingredient);

        Navigator navigator = new Navigator();
        buttonSubmit = (View) findViewById(R.id.save_new_ingredient);
        buttonSubmit.setOnClickListener(navigator);
    }

}