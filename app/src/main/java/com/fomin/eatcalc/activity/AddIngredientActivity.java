package com.fomin.eatcalc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.fomin.eatcalc.R;
import com.google.android.material.textfield.TextInputEditText;

public class AddIngredientActivity extends AppCompatActivity {

    private TextInputEditText countEditText;
    private TextInputEditText unitsEditText;
    private TextInputEditText nameEditText;
    private TextInputEditText priceEditText;
    private TextInputEditText currencyEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_add_ingredient);
        countEditText    = findViewById(R.id.add_ingredient_count);
        unitsEditText    = findViewById(R.id.add_ingredient_units);
        nameEditText     = findViewById(R.id.add_ingredient_name);
        priceEditText    = findViewById(R.id.add_ingredient_edit_price);
        currencyEditText = findViewById(R.id.add_ingredient_currency);

        final Button button_submit = findViewById(R.id.save_new_ingredient);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(nameEditText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                }else { // TODO: добавить проверку ввода
                    String units = unitsEditText.getText().toString();
                    String name = nameEditText.getText().toString();
                    double price = Double.parseDouble(priceEditText.getText().toString());
                    String currency = currencyEditText.getText().toString();
                double count = Double.parseDouble(countEditText.getText().toString());
                    // TODO: извлечь строки в переменные
                    replyIntent.putExtra("count", count);
                    replyIntent.putExtra("units", units);
                    replyIntent.putExtra("name", name);
                    replyIntent.putExtra("price", price);
                    replyIntent.putExtra("currency", currency);
                    // TODO: если все данные в порядке
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

}