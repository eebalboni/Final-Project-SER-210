package edu.quinnipiac.ser210.finalproject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder>{
    private ArrayList<Ingredient> mIngredientData;
    private Context mContext;

    public IngredientAdapter(ArrayList<Ingredient> ingredients, Context context){
        mIngredientData = ingredients;
        mContext = context;
    }

    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.ingredient_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {
        Ingredient currentIngredient = mIngredientData.get(position);

        holder.bindTo(currentIngredient);
    }

    @Override
    public int getItemCount() {
        return mIngredientData.size();
    }

    public void updateList(ArrayList<Ingredient> newList){
        mIngredientData = newList;
        notifyDataSetChanged();
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mNameText;
        private TextView mExpirationDateText;
        private TextView mNutritionText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mNameText = itemView.findViewById(R.id.ingName);
            mExpirationDateText = itemView.findViewById(R.id.date);
            mNutritionText = itemView.findViewById(R.id.nutrition);

        }

        public void bindTo(Ingredient currentIngredient) {
            mNameText.setText(currentIngredient.getName());
            mExpirationDateText.setText(currentIngredient.getExpirationDate());
            mNutritionText.setText(currentIngredient.getNutrition());
        }
    }
}
