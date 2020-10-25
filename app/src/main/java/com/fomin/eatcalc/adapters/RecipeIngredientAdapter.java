package com.fomin.eatcalc.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fomin.eatcalc.R;
import com.fomin.eatcalc.datastorage.RecipeIngredient;

import java.util.List;

public class RecipeIngredientAdapter extends RecyclerView.Adapter<RecipeIngredientAdapter.RecipeIngredientViewHolder>{

    private LayoutInflater inflater;
    private List<RecipeIngredient> ingredients;
    public final int countIndex = 0, unitsIndex = 1, nameIndex = 2;

    public RecipeIngredientAdapter(Context context, List<RecipeIngredient> ingredients) {
        this.inflater = LayoutInflater.from(context);
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public RecipeIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recipe_ingredients_list_item, parent,false);
        return new RecipeIngredientViewHolder(
                itemView,
                new EditTextListener(countIndex),
                new EditTextListener(unitsIndex),
                new EditTextListener(nameIndex)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientViewHolder holder, int position) {
        holder.countListener.updatePosition(position);
        holder.unitsListener.updatePosition(position);
        holder.nameListener.updatePosition(position);

        holder.count.setText(ingredients.get(position).params[countIndex]);
        holder.units.setText(ingredients.get(position).params[unitsIndex]);
        holder.name.setText(ingredients.get(position).params[nameIndex]);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class RecipeIngredientViewHolder extends RecyclerView.ViewHolder {

        public EditText count, units, name;
        public EditTextListener countListener, unitsListener, nameListener;

        public RecipeIngredientViewHolder(
                @NonNull View itemView,
                EditTextListener countListener,
                EditTextListener unitsListener,
                EditTextListener nameListener
        ) {
            super(itemView);
            this.countListener = countListener;
            this.unitsListener = unitsListener;
            this.nameListener = nameListener;

            count = itemView.findViewById(R.id.new_recipe_ingredient_count);
            units = itemView.findViewById(R.id.new_recipe_ingredient_units);
            name = itemView.findViewById(R.id.new_recipe_ingredient_name);

            count.addTextChangedListener(countListener);
            units.addTextChangedListener(unitsListener);
            name.addTextChangedListener(nameListener);
        }
    }

    private class EditTextListener implements TextWatcher {

        private int listPosition;
        private int viewPosition;

        public EditTextListener(int viewPosition) {
            this.viewPosition = viewPosition;
        }

        public void updatePosition(int position) {
            this.listPosition = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ingredients.get(listPosition).params[viewPosition] = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
