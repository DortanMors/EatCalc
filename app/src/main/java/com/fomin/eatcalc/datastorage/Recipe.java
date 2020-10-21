package com.fomin.eatcalc.datastorage;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public String method;
    public double price;
    // JsonArrays
    public String ingredients;
    public String counts;
    public String units;

    public Recipe(String name, String method, double price, String ingredients, String counts, String units) {
        this.name = name;
        this.method = method;
        this.price = price;
        this.ingredients = ingredients;
        this.counts = counts;
        this.units = units;
    }
}
