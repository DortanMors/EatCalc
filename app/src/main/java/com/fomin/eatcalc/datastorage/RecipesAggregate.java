package com.fomin.eatcalc.datastorage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RecipesAggregate {
    private RecipeDao recipeDao;
    private LiveData<List<Recipe>> allRecipes;

    public RecipesAggregate(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        recipeDao = db.recipeDao();
        allRecipes = recipeDao.getAll();
    }

    // to execute in separated threads

    public LiveData<List<Recipe>> getAll() {
        return allRecipes;
    }

    public void insert(Recipe recipe) {
        AppDatabase.databaseWriteExecutor.execute(()-> {
            recipeDao.insert(recipe);
        });
    }
}
