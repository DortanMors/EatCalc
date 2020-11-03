package com.fomin.eatcalc.datastorage;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;

@Entity
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public String method;
    public int portions;
    public double price;
    public double mass;

    public HashMap<Long, Double> ingredients;

    public Recipe(String name, String method, int portions, double price, double mass, HashMap<Long, Double> ingredients) {
        this.name = name;
        this.method = method;
        this.portions = portions;
        this.price = price;
        this.mass = mass;
        this.ingredients = ingredients;
    }
}
