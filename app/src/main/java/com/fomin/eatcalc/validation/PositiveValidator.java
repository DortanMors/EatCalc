package com.fomin.eatcalc.validation;

import android.content.Context;
import java.lang.Double;

import com.fomin.eatcalc.R;

public class PositiveValidator implements Validator<Double> {

    Context context;

    public PositiveValidator(Context context) {
        this.context = context;
    }

    @Override
    public boolean isValid(Double value) {
        return value > 0;
    }

    @Override
    public String getMessage() {
        return context.getString(R.string.must_be_positive);
    }
}
