package com.fomin.eatcalc.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fomin.eatcalc.R;
import com.fomin.eatcalc.datastorage.Ingredient;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.List;

public class EditIngredientsAdapter extends RecyclerView.Adapter<EditIngredientsAdapter.RecipeIngredientViewHolder>{

    private final LayoutInflater inflater;
    private List<Ingredient> ingredients;
    private final HashMap<Long, Double> counts;

    public EditIngredientsAdapter(Context context, HashMap<Long, Double> counts) {
        this.inflater = LayoutInflater.from(context);
        this.counts = counts;
    }

    @NonNull
    @Override
    public RecipeIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recipe_ingredients_list_item, parent,false);
        return new RecipeIngredientViewHolder(
                itemView
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

        public TextInputEditText count;
        public TextView units, name;
        public EditTextListener countListener;

        public RecipeIngredientViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);
            name =  itemView.findViewById(R.id.new_recipe_ingredient_name);
            units = itemView.findViewById(R.id.new_recipe_ingredient_units);
            count = itemView.findViewById(R.id.new_recipe_ingredient_count);
            this.countListener = new EditTextListener(count);

            count.addTextChangedListener(countListener);
        }
    }

    private class EditTextListener implements TextWatcher {

        private TextInputEditText countEdit;
        private int listPosition;

        public EditTextListener(TextInputEditText countEdit) {
            this.countEdit = countEdit;
        }

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
                double n_count;
                try {
                    n_count = Double.parseDouble(s.toString());
                } catch (Exception e) {
                    countEdit.setError(countEdit.getContext().getString(R.string.input_number));
                    n_count = 0;
                }

                if(n_count != 0) {
                    counts.put(ingredient.id, n_count);
                } else {
                    counts.remove(ingredient.id);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
