package com.fomin.eatcalc.activity.navigator;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.fomin.eatcalc.R;
import com.fomin.eatcalc.activity.AddIngredientActivity;
import com.fomin.eatcalc.activity.AddRecipeActivity;
import com.fomin.eatcalc.activity.IngredientsListActivity;
import com.fomin.eatcalc.activity.RecipesListActivity;

public class Navigator implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        Context contextActivity = view.getContext();
        if(viewId== R.id.prices){
            Intent intent = new Intent(contextActivity, IngredientsListActivity.class);
            contextActivity.startActivity(intent);
        } else if(viewId == R.id.recipes) {
            Intent intent = new Intent(contextActivity, RecipesListActivity.class);
            contextActivity.startActivity(intent);
        } else if(viewId == R.id.add_new_recipe) {
            Intent intent = new Intent(contextActivity, AddRecipeActivity.class);
            contextActivity.startActivity(intent);
        } else if(viewId==R.id.add_ingredient_price){
            Intent intent = new Intent(contextActivity, AddIngredientActivity.class);
            contextActivity.startActivity(intent);
        } else if(viewId == R.id.save_new_ingredient){
            Intent intent = new Intent(contextActivity, IngredientsListActivity.class);
            contextActivity.startActivity(intent);
        }
    }
}
