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

class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
   private LayoutInflater inflater;
   private List<Ingredient> ingredients;

   IngredientAdapter(Context context, List<Ingredient> ingredients){
      this.inflater = LayoutInflater.from(context);
      this.ingredients = ingredients;
   }

   @NonNull
   @Override
   public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = inflater.inflate(R.layout.ingredients_list_item, parent, false);
      return new ViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {
      Ingredient ingredient = ingredients.get(position);
      holder.countView.setText(String.valueOf(1)); // price is always for 1 unit
      holder.unitView.setText(ingredient.getUnit());
      holder.nameView.setText(ingredient.getName());
      holder.priceView.setText(String.valueOf(ingredient.getPrice()));
      holder.currencyView.setText(ingredient.getCurrency());
   }

   @Override
   public int getItemCount() {
      return ingredients.size();
   }


   public class ViewHolder extends RecyclerView.ViewHolder{

      final TextView countView, unitView, nameView, priceView, currencyView;

      public ViewHolder(@NonNull View itemView) {
         super(itemView);
         countView    = (TextView) itemView.findViewById(R.id.item_count);
         unitView     = (TextView) itemView.findViewById(R.id.item_unit);
         nameView     = (TextView) itemView.findViewById(R.id.item_name);
         priceView    = (TextView) itemView.findViewById(R.id.item_price);
         currencyView = (TextView) itemView.findViewById(R.id.item_currency);
      }
   }
}
