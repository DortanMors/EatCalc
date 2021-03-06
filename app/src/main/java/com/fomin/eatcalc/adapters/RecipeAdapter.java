package com.fomin.eatcalc.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fomin.eatcalc.R;
import com.fomin.eatcalc.activity.RecipeActivity;
import com.fomin.eatcalc.datastorage.Recipe;

import java.util.List;
import java.util.Locale;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private List<Recipe> recipes;

    public RecipeAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recipes_list_item, parent, false);
        RecipeViewHolder holder = new RecipeViewHolder(itemView);
        holder.itemView.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            if(position != RecyclerView.NO_POSITION) {
                long recipeId = recipes.get(position).id;
                Intent toRecipe = new Intent(context, RecipeActivity.class);
                toRecipe.putExtra("recipeId", recipeId);
                context.startActivity(toRecipe);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        if(recipes != null) {
            Recipe recipe = recipes.get(position);
            holder.recipeType.setImageResource(R.drawable.soup);
            holder.name.setText(recipe.name);
            holder.price.setText(String.format(Locale.US, "%.2f",recipe.price));
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
