package com.fomin.eatcalc.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fomin.eatcalc.datastorage.Ingredient;
import com.fomin.eatcalc.datastorage.IngredientsAggregate;

import java.util.List;

public class IngredientViewModel extends AndroidViewModel {
    private IngredientsAggregate mIngredientsAggregate;
    private LiveData<List<Ingredient>> mAllIngredients;

    public IngredientViewModel(@NonNull Application application) {
        super(application);
        mIngredientsAggregate = new IngredientsAggregate(application);
        mAllIngredients = mIngredientsAggregate.getAll();
    }

    public LiveData<List<Ingredient>> getAllIngredients() {
        return mAllIngredients;
    }

    public void insert(Ingredient ingredient) {
        mIngredientsAggregate.insert(ingredient);
    }
}
