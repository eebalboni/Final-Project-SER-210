/*
SER 210 Final Project
Professor Ruby
Be prePEARed app, meant to help users keep track of food and find recipes easily!
By: Jonathan Mason, Emily Balboni, and Amber Kusma
 */
package edu.quinnipiac.ser210.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{
    private ArrayList<Recipe> mRecipeData;
    private RecipeListFragment recipeListFrag= new RecipeListFragment();
    private Context mContext;
    private NavController navController;
    private Bundle mBundle;

    public RecipeAdapter(ArrayList<Recipe> recipeData, Context context, Bundle bundle){
        mRecipeData = recipeData;
        mContext = context;
        recipeListFrag.setRecipeData(mRecipeData);
        mBundle = bundle;

    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recipe_list_item, parent, false));
    }

    public void setRecipeData(ArrayList<Recipe>recipeData){
        if(recipeData!=null){
            mRecipeData = recipeData;
            recipeListFrag.replaceRecipeData(mRecipeData);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
        Recipe currentRecipe = mRecipeData.get(position);
        holder.bindTo(currentRecipe);
       // mBundle.putString("name", mRecipeData.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                navController = new NavController(mContext);
                navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_recipeListFragment_to_recipeDetailFragment, mBundle);
            }
        });

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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mNameText = itemView.findViewById(R.id.recipeName);
        }

        public void bindTo(Recipe currentRecipe){
            mNameText.setText(currentRecipe.getName());
        }
    }

}
