package com.fomin.eatcalc.datastorage;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Unit {

    @PrimaryKey
    public String name;

    public long type;

    public Unit(String name, long type) {
        this.name = name;
        this.type = type;
    }
}
