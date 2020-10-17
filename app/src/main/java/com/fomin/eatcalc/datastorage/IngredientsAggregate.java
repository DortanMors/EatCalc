package com.fomin.eatcalc.datastorage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class IngredientsAggregate {
    private IngredientDao mIngredientDao;
    private LiveData<List<Ingredient>> mAllIngredients;

    public IngredientsAggregate(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mIngredientDao = db.ingredientDao();
        mAllIngredients = mIngredientDao.getAll();
    }

    // to execute in separate threads

    public LiveData<List<Ingredient>> getAll() {
        return mAllIngredients;
    }

    public void insert(Ingredient ingredient) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mIngredientDao.insert(ingredient);
        });
    }
}
