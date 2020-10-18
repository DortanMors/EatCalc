package com.fomin.eatcalc.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fomin.eatcalc.datastorage.Recipe;
import com.fomin.eatcalc.datastorage.RecipesAggregate;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {
    private RecipesAggregate recipesAggregate;
    private LiveData<List<Recipe>> allRecipes;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        recipesAggregate = new RecipesAggregate(application);
        allRecipes = recipesAggregate.getAll();
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return allRecipes;
    }

    public void insert(Recipe recipe) {
        recipesAggregate.insert(recipe);
    }
}
