package com.fomin.eatcalc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.fomin.eatcalc.R;
import com.fomin.eatcalc.validation.EmptyValidator;
import com.fomin.eatcalc.validation.ValidatorsComposer;
import com.google.android.material.textfield.TextInputEditText;

public class AddIngredientActivity extends AppCompatActivity {

    private boolean isValid = false;
    private boolean isError = false;
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
        Context context = this;
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                isError = false;
                double count;
                try {
                    count = Double.parseDouble(countEditText.getText().toString());
                } catch (Exception e) {
                    count = 0;
                    isError = true;
                    countEditText.setError(context.getString(R.string.input_number));
                }
                String units = unitsEditText.getText().toString();
                String name = nameEditText.getText().toString();
                double price;
                try {
                    price = Double.parseDouble(priceEditText.getText().toString());
                } catch (Exception e) {
                    price = 0;
                    isError = true;
                    priceEditText.setError(context.getString(R.string.input_number));
                }
                String currency = currencyEditText.getText().toString();

                ValidatorsComposer<String> validation = new ValidatorsComposer<>(new EmptyValidator(context));
                if (!validation.isValid(units)) {
                    unitsEditText.setError(validation.getMessage());
                } else if(!validation.isValid(name)) {
                    nameEditText.setError(validation.getMessage());
                } else if(!validation.isValid(currency)) {
                    currencyEditText.setError(validation.getMessage());
                } else  { // TODO: добавить проверку ввода

                    // TODO: извлечь строки в переменные
                    replyIntent.putExtra("count", count);
                    replyIntent.putExtra("units", units);
                    replyIntent.putExtra("name", name);
                    replyIntent.putExtra("price", price);
                    replyIntent.putExtra("currency", currency);
                    // TODO: если все данные в порядке
                    setResult(RESULT_OK, replyIntent);
                    isValid = true;
                    finish();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isValid) {
            setResult(RESULT_CANCELED, new Intent());
        }
    }
}