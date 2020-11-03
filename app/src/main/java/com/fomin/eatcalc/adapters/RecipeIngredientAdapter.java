package com.fomin.eatcalc.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fomin.eatcalc.R;
import com.fomin.eatcalc.datastorage.Ingredient;

import java.util.HashMap;
import java.util.List;

public class RecipeIngredientAdapter extends RecyclerView.Adapter<RecipeIngredientAdapter.RecipeIngredientViewHolder>{

    private final LayoutInflater inflater;
    private List<Ingredient> ingredients;
    private final HashMap<Long, Double> counts;

    public RecipeIngredientAdapter(Context context, HashMap<Long, Double> counts) {
        this.inflater = LayoutInflater.from(context);
        this.counts = counts;
    }

    @NonNull
    @Override
    public RecipeIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recipe_ingredients_list_item, parent,false);
        return new RecipeIngredientViewHolder(
                itemView,
                new EditTextListener()
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientViewHolder holder, int position) {
        if(ingredients!=null) {
            holder.countListener.updatePosition(position);
            Ingredient ingredient = ingredients.get(position);
            Double count = counts.get(ingredient.id);
            if (count==null)
                count = 0d;
            holder.count.setText(String.valueOf(count));
            holder.units.setText(ingredient.unit_id);
            holder.name.setText(ingredient.name);
        } else {
            holder.name.setText(R.string.no_ingredients_here);
        }
    }

    @Override
    public int getItemCount() {
        if(ingredients!=null)
            return ingredients.size();
        else
            return 0;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    public HashMap<Long, Double> getCounts() {
        return counts;
    }

    public class RecipeIngredientViewHolder extends RecyclerView.ViewHolder {

        public EditText count;
        public TextView units, name;
        public EditTextListener countListener;

        public RecipeIngredientViewHolder(
                @NonNull View itemView,
                EditTextListener countListener
        ) {
            super(itemView);
            this.countListener = countListener;

            count = itemView.findViewById(R.id.new_recipe_ingredient_count);
            units = itemView.findViewById(R.id.new_recipe_ingredient_units);
            name = itemView.findViewById(R.id.new_recipe_ingredient_name);

            count.addTextChangedListener(countListener);
        }
    }

    private class EditTextListener implements TextWatcher {

        private int listPosition;

        public void updatePosition(int position) {
            this.listPosition = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(ingredients != null) {
                Ingredient ingredient = ingredients.get(listPosition);
                counts.put(ingredient.id, Double.parseDouble(s.toString()));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
