package com.fomin.eatcalc.datastorage;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Recipe {

    @PrimaryKey
    public long id;

    public String name;
    // JsonArrays
    public String ingredients;
    public String counts;
    public String unit_ids;
}
