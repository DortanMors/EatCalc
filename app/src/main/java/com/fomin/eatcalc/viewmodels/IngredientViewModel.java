package com.fomin.eatcalc.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fomin.eatcalc.datastorage.Ingredient;
import com.fomin.eatcalc.datastorage.IngredientsAggregate;

import java.util.List;

public class IngredientViewModel extends AndroidViewModel {
    private IngredientsAggregate ingredientsAggregate;
    private LiveData<List<Ingredient>> allIngredients;

    public IngredientViewModel(@NonNull Application application) {
        super(application);
        ingredientsAggregate = new IngredientsAggregate(application);
        allIngredients = ingredientsAggregate.getAll();
    }

    public LiveData<List<Ingredient>> getAllIngredients() {
        return allIngredients;
    }

    public void insert(Ingredient ingredient) {
        ingredientsAggregate.insert(ingredient);
    }
}
