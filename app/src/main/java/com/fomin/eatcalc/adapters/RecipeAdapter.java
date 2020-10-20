package com.fomin.eatcalc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fomin.eatcalc.R;
import com.fomin.eatcalc.datastorage.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private LayoutInflater inflater;
    private List<Recipe> recipes;

    public RecipeAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recipes_list_item, parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        if(recipes != null) {
            Recipe recipe = recipes.get(position);
            holder.recipeType.setImageResource(R.drawable.soup);
            holder.name.setText(recipe.name);
            holder.price.setText(String.valueOf(recipe.price));
        } else {
            holder.name.setText(R.string.no_recipes_here);
        }
    }

    @Override
    public int getItemCount() {
        if(recipes != null)
            return recipes.size();
        else
            return 0;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        ImageView recipeType;
        TextView name, mass, price;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            name       = itemView.findViewById(R.id.recipe_item_name);
            mass       = itemView.findViewById(R.id.recipe_item_mass);
            price      = itemView.findViewById(R.id.recipe_item_price);
            recipeType = itemView.findViewById(R.id.recipe_item_image);
        }
    }
}
