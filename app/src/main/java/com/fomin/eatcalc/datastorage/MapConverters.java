package com.fomin.eatcalc.datastorage;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

public class MapConverters {
    @TypeConverter
    public String MapToString(HashMap<Long, Double> smap) {
        return new Gson().toJson(smap);
    }

    @TypeConverter
    public HashMap<Long, Double> StringToMap(String jmap) {
        return new Gson().fromJson(jmap, new TypeToken<HashMap<Long, Double>>() {}.getType());
    }
}
