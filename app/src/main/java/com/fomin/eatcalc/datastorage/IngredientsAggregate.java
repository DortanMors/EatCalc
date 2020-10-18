package com.fomin.eatcalc.datastorage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class IngredientsAggregate {
    private IngredientDao ingredientDao;
    private LiveData<List<Ingredient>> allIngredients;

    public IngredientsAggregate(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        ingredientDao = db.ingredientDao();
        allIngredients = ingredientDao.getAll();
    }

    // to execute in separate threads

    public LiveData<List<Ingredient>> getAll() {
        return allIngredients;
    }

    public void insert(Ingredient ingredient) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            ingredientDao.insert(ingredient);
        });
    }
}
