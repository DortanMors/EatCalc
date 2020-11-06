package com.fomin.eatcalc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fomin.eatcalc.R;
import com.fomin.eatcalc.datastorage.Ingredient;

import java.util.List;
import java.util.Locale;

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredients;
    private LayoutInflater inflater;

    public RecipeIngredientsAdapter(Context context, List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recipe_ingredient_item, parent, false);
        return new IngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.count.setText(String.valueOf(ingredient.count));
        holder.units.setText(ingredient.unit_id);
        holder.name.setText(ingredient.name);
        holder.price.setText(String.format(Locale.US, "%.2f", ingredient.price));
        holder.currency.setText(ingredient.currency_id);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView count, units, name, price, currency;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            count = itemView.findViewById(R.id.ingredient_count);
            units = itemView.findViewById(R.id.ingredient_units);
            price = itemView.findViewById(R.id.ingredient_price);
            name  = itemView.findViewById(R.id.ingredient_name);
            currency = itemView.findViewById(R.id.ingredient_currency);
        }
    }
}
