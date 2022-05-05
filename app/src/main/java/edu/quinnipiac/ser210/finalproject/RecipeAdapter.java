/*
SER 210 Final Project
Professor Ruby
Be prePEARed app, meant to help users keep track of food and find recipes easily!
By: Jonathan Mason, Emily Balboni, and Amber Kusma
 */

/*
RecipeAdapter class. Binds recipe data to cards.
Author: Jonathan Mason and Emily Balboni
 */
package edu.quinnipiac.ser210.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{
    private ArrayList<Recipe> mRecipeData;
    private RecipeListFragment recipeListFrag= new RecipeListFragment();
    private Context mContext;
    private NavController navController;
    private Bundle mBundle;
    private RecyclerViewOnClickListener listener;
    private RecipeDataSource dataSource;

    public RecipeAdapter(ArrayList<Recipe> recipeData, Context context, Bundle bundle,RecyclerViewOnClickListener listener){
        dataSource = new RecipeDataSource(context);
        mRecipeData = recipeData;
        mContext = context;
        recipeListFrag.setRecipeData(mRecipeData);
        mBundle = bundle;
        this.listener = listener;

    }


    public interface RecyclerViewOnClickListener{
        void recyclerViewClick(int position);
    }


    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recipe_list_item, parent, false),this.listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe currentRecipe = mRecipeData.get(position);
        holder.bindTo(currentRecipe);
        dataSource.open();
        CheckBox checkBox = holder.cardView.findViewById(R.id.star);
        String name = currentRecipe.getName();
        String serving = currentRecipe.getServings();
        String ingredient = currentRecipe.getIngredients();
        String instruction = currentRecipe.getInstructions();
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update recipe info here
               dataSource.createRecipe(name,serving,ingredient,instruction);
            }
        });
    }

    public void setRecipeData(ArrayList<Recipe>recipeData){
        if(recipeData!=null){
            mRecipeData = recipeData;
            recipeListFrag.replaceRecipeData(mRecipeData);
        }

    }

    @Override
    public int getItemCount() {
        return mRecipeData.size();
    }

    public void updateList(ArrayList<Recipe> newData){
        mRecipeData = newData;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameText;
        private RecyclerViewOnClickListener mListener;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView, RecyclerViewOnClickListener listener) {
            super(itemView);
            this.mListener = listener;
            cardView = (CardView) itemView;
            this.mNameText = itemView.findViewById(R.id.recipeName);
            CardView cardView = (CardView) itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.recyclerViewClick(getAdapterPosition());
                }
            });
        }
        public void bindTo(Recipe currentRecipe){ mNameText.setText(currentRecipe.getName());}
    }

}
