package com.fomin.eatcalc.validation;

import android.content.Context;
import android.text.TextUtils;

import com.fomin.eatcalc.R;

public class EmptyValidator implements Validator<String> {

    Context context;

    public EmptyValidator(Context context) {
        this.context = context;
    }

    @Override
    public boolean isValid(String value) {
        return !TextUtils.isEmpty(value);
    }

    @Override
    public String getMessage() {
        return context.getString(R.string.field_must_not_be_empty);
    }
}