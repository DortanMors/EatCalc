package com.fomin.eatcalc.datastorage;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public String method;
    // JsonArrays
    public String ingredients;
    public String counts;
    public String units;
}
