package com.fomin.eatcalc.validation;

public interface Validator<T> {

    boolean isValid(T value);

    String getMessage();

}