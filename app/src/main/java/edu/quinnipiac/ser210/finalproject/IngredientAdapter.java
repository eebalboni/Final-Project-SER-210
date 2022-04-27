/*
SER 210 Final Project
Professor Ruby
Be prePEARed app, meant to help users keep track of food and find recipes easily!
By: Jonathan Mason, Emily Balboni, and Amber Kusma
 */
/*
Ingredient class
 */
package edu.quinnipiac.ser210.finalproject;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder>{
    private ArrayList<Ingredient> mIngredientData;
    private Context mContext;
    private IngredientDataSource dataSource;

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mNameText;
        private TextView mExpirationDateText;
        private TextView mNutritionText;
        private CardView cardView;
        private ImageButton bn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            mNameText = cardView.findViewById(R.id.ingName);
            mExpirationDateText = cardView.findViewById(R.id.date);
            mNutritionText = cardView.findViewById(R.id.nutrition);

            //something is wrong
            bn = cardView.findViewById(R.id.delete_button);
        }


        @SuppressLint("ResourceAsColor")
        public void bindTo(Ingredient currentIngredient) throws ParseException {
            mNameText.setText(currentIngredient.getName());
            mExpirationDateText.setText(currentIngredient.getExpirationDate());
            mNutritionText.setText(currentIngredient.getNutrition());

            Date expDate = new SimpleDateFormat("dd/MM/yyyy").parse(currentIngredient.getExpirationDate());
            Date todayDate = new Date();
            double compare = expDate.compareTo(todayDate);
            if(compare < 0){
                mExpirationDateText.setTextColor(R.color.red);
            }
        }


    }

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
                IngredientDataSource i = new IngredientDataSource(mContext);
                i.open();
                CardView cardView = holder.cardView;
                ImageButton bt = (ImageButton) cardView.findViewById(R.id.delete_button);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //removing from list
                        mIngredientData.remove(holder.getAdapterPosition());
                        //removing from database
                        i.deleteIngredient(mIngredientData.get(position));
                        //updating
                        notifyDataSetChanged();
                    }
                });
        Ingredient currentIngredient = mIngredientData.get(position);
        try {
            holder.bindTo(currentIngredient);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        i.close();
    }

    @Override
    public int getItemCount() {
        return mIngredientData.size();
    }

    public void updateList(ArrayList<Ingredient> newList){
        mIngredientData = newList;
        notifyDataSetChanged();
    }


}
