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

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
   private LayoutInflater inflater;
   private List<Ingredient> ingredients;

   public IngredientAdapter(Context context){
      this.inflater = LayoutInflater.from(context);
   }

   @NonNull
   @Override
   public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View itemView = inflater.inflate(R.layout.ingredients_list_item, parent, false);
      return new IngredientViewHolder(itemView);
   }

   @Override
   public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
      if(ingredients != null) {
         Ingredient ingredient = ingredients.get(position);
         holder.countView.setText(String.valueOf(1)); // price is always for 1 unit
         holder.unitView.setText(ingredient.getUnit());
         holder.nameView.setText(ingredient.getName());
         holder.priceView.setText(String.format(Locale.US, "%.2f", ingredient.getPrice()));
         holder.currencyView.setText(ingredient.getCurrency());
      } else {
         holder.nameView.setText(R.string.no_ingredients_here);
      }
   }

   @Override
   public int getItemCount() {
      if(ingredients != null)
         return ingredients.size();
      else
         return 0;
   }

   public Ingredient getItem(int position) {
      return ingredients.get(position);
   }

   public void setIngredients(List<Ingredient> ingredients) {
      this.ingredients = ingredients;
      notifyDataSetChanged();
   }

   public class IngredientViewHolder extends RecyclerView.ViewHolder{

      final TextView countView, unitView, nameView, priceView, currencyView;

      public IngredientViewHolder(@NonNull View itemView) {
         super(itemView);
         countView    = (TextView) itemView.findViewById(R.id.item_count);
         unitView     = (TextView) itemView.findViewById(R.id.item_unit);
         nameView     = (TextView) itemView.findViewById(R.id.item_name);
         priceView    = (TextView) itemView.findViewById(R.id.item_price);
         currencyView = (TextView) itemView.findViewById(R.id.item_currency);
      }
   }
}
