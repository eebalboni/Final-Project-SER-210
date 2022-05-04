/*
SER 210 Final Project
Professor Ruby
Be prePEARed app, meant to help users keep track of food and find recipes easily!
By: Jonathan Mason, Emily Balboni, and Amber Kusma
 */

/*
The find food fragment lets the user search for a food item. When they press enter
the navigation controller takes the user to the next screen
We store the food item being searched for in the bundle
 */
package edu.quinnipiac.ser210.finalproject;

import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;


public class FindFoodFragment extends Fragment implements View.OnClickListener{
        private static String item;
        NavController navController = null;
        public FindFoodFragment(){
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_find_food,container,false);
        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        view.findViewById(R.id.submitButton).setOnClickListener(this);
    }

    //onClickListener for the submit button
    //when its clicked navigation graph takes us to next screen
    @Override
    public void onClick(View view){
        Log.d("Reach", "I think ketchup goes well with fries");
        Bundle bundle = new Bundle();
        EditText foodItem = getView().findViewById(R.id.enterFood);
        item = foodItem.getText().toString();
        bundle.putString("item",item);
        navController.navigate(R.id.action_findFoodFragment_to_addFoodFragment, bundle);
    }
}