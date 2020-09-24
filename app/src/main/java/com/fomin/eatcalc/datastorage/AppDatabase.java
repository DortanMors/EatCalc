package com.fomin.eatcalc.datastorage;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {Ingredient.class, Recipe.class, Unit.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract IngredientDao ingredientDao();
    public abstract RecipeDao recipeDao();
    public abstract UnitDao unitDao();
}
