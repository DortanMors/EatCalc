package com.fomin.eatcalc.datastorage;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Ingredient.class, Recipe.class, Unit.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract IngredientDao ingredientDao();
    public abstract RecipeDao recipeDao();
    public abstract UnitDao unitDao();

    private static volatile AppDatabase instance;
    private static final int number_of_threads = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(number_of_threads);

    static AppDatabase getDatabase(final Context context) {
        if(instance == null) {
            synchronized (AppDatabase.class) {
                if(instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                                    AppDatabase.class,
                                                    "eatcalc_database")
                        .addCallback(sRoomDatabaseCallback)
                        .build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

        }
    };
}
