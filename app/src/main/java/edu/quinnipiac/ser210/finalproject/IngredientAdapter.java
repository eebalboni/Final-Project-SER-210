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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder>{
    private ArrayList<Ingredient> mIngredientData;
    private Context mContext;
    private IngredientDataSource dataSource;

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mNameText;
        private TextView mExpirationDateText;
        private TextView mNutritionText;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            mNameText = cardView.findViewById(R.id.ingName);
            mExpirationDateText = cardView.findViewById(R.id.date);
            mNutritionText = cardView.findViewById(R.id.nutrition);
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
        dataSource = new IngredientDataSource(context);
    }

    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.ingredient_list_item, parent, false));
    }

    //used stack overflow as a reference:
    // https://stackoverflow.com/questions/52404324/how-to-delete-or-remove-cardview-from-recyclerview-android-studio
    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {
                dataSource.open();
                Ingredient currentIngredient = mIngredientData.get(position);
                CheckBox ch = holder.cardView.findViewById(R.id.check);
                ch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dataSource.deleteIngredient(currentIngredient);
                        mIngredientData.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                        notifyItemRemoved(holder.getAdapterPosition());
                    }
                });

        try {
            holder.bindTo(currentIngredient);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
