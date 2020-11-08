package com.fomin.eatcalc.validation;

import java.util.List;

public class ValidatorsComposer<T> implements Validator<T> {

    private List<Validator<T>> validators;
    private String message;

    @Override
    public boolean isValid(T value) {
        for(Validator<T> validator : validators) {
            if(!validator.isValid(value)) {
                this.message = validator.getMessage();
                return false;
            }
        }
        return true;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
