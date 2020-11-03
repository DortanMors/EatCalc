package com.fomin.eatcalc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import com.fomin.eatcalc.R;

import java.util.HashMap;

public class AddRecipeActivity extends AppCompatActivity {

    public static final int EDIT_RECIPE_INGREDIENTS_REQUEST_CODE = 4;
    TextInputEditText name;
    TextInputEditText portionsNum;
    TextInputEditText method;
    // TODO: сделать добавление игнгредиентов
    TextInputEditText ingredient1count;
    TextInputEditText ingredient1unit;
    TextInputEditText ingredient1name;
    private HashMap<Long, Double> counts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        counts = new HashMap<Long, Double>();

        name = findViewById(R.id.new_recipe_name);
        portionsNum = findViewById(R.id.new_recipe_portions_num);
        method = findViewById(R.id.new_recipe_method);

        FloatingActionButton button_add_ingredient = findViewById(R.id.add_recipe_ingredient);
        button_add_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIngredients = new Intent(AddRecipeActivity.this, EditIngredientsActivity.class);
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
                    // TODO: переделать под список ингредиентов
                    long ing1count = Long.parseLong(ingredient1count.getText().toString());
                    String ing1name = ingredient1name.getText().toString();
                    String ing1unit = ingredient1unit.getText().toString();
                    // TODO: извлечь строки в переменные
                    addRecipe.putExtra("portionsCount", portionsCount);
                    addRecipe.putExtra("name", recipeName);
                    addRecipe.putExtra("cookingMethod", cookingMethod);

                    addRecipe.putExtra("ing1count", ing1count);
                    addRecipe.putExtra("ing1name", ing1name);
                    addRecipe.putExtra("ing1unit", ing1unit);
                    // TODO: если все данные в порядке
                    setResult(RESULT_OK, addRecipe);
                }
                finish();
            }
        });
    }
}
