/*
SER 210 Final Project
Professor Ruby
Be prePEARed app, meant to help users keep track of food and find recipes easily!
By: Jonathan Mason, Emily Balboni, and Amber Kusma
 */

/*
Find recipe fragment lets the user search for a recipe.
 */
package edu.quinnipiac.ser210.finalproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class FindRecipeFragment extends Fragment implements View.OnClickListener{
    private String recipe;
    NavController navController = null;
    public FindRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_recipe, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        view.findViewById(R.id.submitRButton).setOnClickListener(this);
    }

    /*
    Setting on click listener for the submit button, saving the recipe name
    as a string in the bundle. nav graph moves to next fragment to display
     */
    @Override
    public void onClick(View viw){
        Log.d("Reach","I think ketchup goes well with fries");
        Bundle bundle = new Bundle();
        EditText recipeName = getView().findViewById(R.id.enterRecipe);
        recipe = recipeName.getText().toString();
        bundle.putString("recipe",recipe);
        navController.navigate(R.id.action_findRecipeFragment_to_recipeListFragment);
    }
}