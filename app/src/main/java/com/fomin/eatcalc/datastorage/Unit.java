package com.fomin.eatcalc.datastorage;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Unit {

    @PrimaryKey(autoGenerate = true)
    public  long id;

    public String name;

    public long type;
}
