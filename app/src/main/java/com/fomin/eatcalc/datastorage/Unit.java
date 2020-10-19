package com.fomin.eatcalc.datastorage;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Unit {

    @PrimaryKey
    @NonNull
    public String name;

    public long type;

    public Unit(String name, long type) {
        this.name = name;
        this.type = type;
    }
}
