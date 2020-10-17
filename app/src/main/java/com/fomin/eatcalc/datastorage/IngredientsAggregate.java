package com.fomin.eatcalc.datastorage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class IngredientsAggregate {
    private IngredientDao mIngredientDao;
    private LiveData<List<Ingredient>> mAllIngredients;

    IngredientsAggregate(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mIngredientDao = db.ingredientDao();
        mAllIngredients = mIngredientDao.getAll();
    }

    // to execute in separate threads

    LiveData<List<Ingredient>> getAll() {
        return mAllIngredients;
    }

    void insert(Ingredient ingredient) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mIngredientDao.insert(ingredient);
        });
    }
}
