package com.fomin.eatcalc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    private final Context context = this;
    private long id = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_add_ingredient);
        countEditText    = findViewById(R.id.add_ingredient_count);
        unitsEditText    = findViewById(R.id.add_ingredient_units);
        nameEditText     = findViewById(R.id.add_ingredient_name);
        priceEditText    = findViewById(R.id.add_ingredient_edit_price);
        currencyEditText = findViewById(R.id.add_ingredient_currency);

        Intent curIntent = getIntent();
        int requestCode = curIntent.getIntExtra("requestCode", 1);
        if(requestCode == IngredientsListActivity.UPDATE_INGREDIENT_ACTIVITY_REQUEST_CODE) {
            countEditText.setText(String.valueOf(curIntent.getDoubleExtra("count",0)));
            unitsEditText.setText(curIntent.getStringExtra("units"));
            nameEditText.setText(curIntent.getStringExtra("name"));
            priceEditText.setText(String.valueOf(curIntent.getDoubleExtra("price",0)));
            currencyEditText.setText(curIntent.getStringExtra("currency"));
            id = curIntent.getLongExtra("id", -1);
        }

        final Button button_submit = findViewById(R.id.save_new_ingredient);
        button_submit.setOnClickListener(v -> {
            Intent replyIntent = new Intent();
            isError = false;

            double count;
            double price;
            String units = unitsEditText.getText().toString();
            String name = nameEditText.getText().toString();
            String currency = currencyEditText.getText().toString();

            try {
                count = Double.parseDouble(countEditText.getText().toString());
            } catch (Exception e) {
                count = 0;
                isError = true;
                countEditText.setError(context.getString(R.string.input_number));
            }
            try {
                price = Double.parseDouble(priceEditText.getText().toString());
            } catch (Exception e) {
                price = 0;
                isError = true;
                priceEditText.setError(context.getString(R.string.input_number));
            }

            ValidatorsComposer<String> validation = new ValidatorsComposer<>(new EmptyValidator(context));
            if (!validation.isValid(units)) {
                unitsEditText.setError(validation.getMessage());
                isError = true;
            }
            if(!validation.isValid(name)) {
                nameEditText.setError(validation.getMessage());
                isError = true;
            }
            if(!validation.isValid(currency)) {
                currencyEditText.setError(validation.getMessage());
                isError = true;
            }
            if(!isError) {
                // TODO: извлечь строки в переменные
                replyIntent.putExtra("id", id);
                replyIntent.putExtra("count", count);
                replyIntent.putExtra("units", units);
                replyIntent.putExtra("name", name);
                replyIntent.putExtra("price", price);
                replyIntent.putExtra("currency", currency);

                setResult(RESULT_OK, replyIntent);
                isValid = true;
                finish();
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