package com.fomin.eatcalc.datastorage;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UnitDao {
    @Query("SELECT * FROM unit")
    List<Unit> getAll();

    @Query("SELECT * FROM unit WHERE name = :name")
    Unit getById(String name);

    @Insert
    void insert(Unit unit);

    @Delete
    void delete(Unit unit);

    @Update
    void update(Unit unit);
}
