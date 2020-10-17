package com.fomin.eatcalc.datastorage;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;

    public double price;

    public String unit_id;

    public String currency_id;

    public Ingredient(String name, double price, String unit_id, String currency_id) {
        this.name = name;
        this.price = price;
        this.unit_id = unit_id;
        this.currency_id = currency_id;
    }

    public String getCurrency() { // TODO: механизм получения валюты из id в string
        return String.valueOf(currency_id);
    }

    public String getUnit() { // TODO: механизм получения единицы измерения из id в string
        return String.valueOf(unit_id);
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
