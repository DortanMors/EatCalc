package com.fomin.eatcalc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import com.fomin.eatcalc.R;

public class AddRecipeActivity extends AppCompatActivity {

    TextInputEditText name;
    TextInputEditText portionsNum;
    TextInputEditText method;
    // TODO: сделать добавление игнгредиентов
    TextInputEditText ingredient1count;
    TextInputEditText ingredient1unit;
    TextInputEditText ingredient1name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        name = findViewById(R.id.new_recipe_name);
        portionsNum = findViewById(R.id.new_recipe_portions_num);
        method = findViewById(R.id.new_recipe_method);

        ingredient1count = findViewById(R.id.deprecated_new_recipe_count);
        ingredient1name = findViewById(R.id.deprecated_new_name);
        ingredient1unit = findViewById(R.id.deprecated_new_units);

        Button button_submit = findViewById(R.id.new_recipe_save);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if(TextUtils.isEmpty(name.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else { // TODO: добавить валидацию всех инпутов
                    int portionsCount = Integer.parseInt(portionsNum.getText().toString());
                    String recipeName = name.getText().toString();
                    String cookingMethod = method.getText().toString();
                    // TODO: переделать под список ингредиентов
                    long ing1count = Long.parseLong(ingredient1count.getText().toString());
                    String ing1name = ingredient1name.getText().toString();
                    String ing1unit = ingredient1unit.getText().toString();
                    // TODO: извлечь строки в переменные
                    replyIntent.putExtra("portionsCount", portionsCount);
                    replyIntent.putExtra("name", recipeName);
                    replyIntent.putExtra("cookingMethod", cookingMethod);

                    replyIntent.putExtra("ing1count", ing1count);
                    replyIntent.putExtra("ing1name", ing1name);
                    replyIntent.putExtra("ing1unit", ing1unit);
                    // TODO: если все данные в порядке
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
