package edu.quinnipiac.ser210.finalproject;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class FindFoodFragment extends Fragment implements View.OnClickListener{
        private static String item;
        NavController navController = null;


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
        view.findViewById(R.id.enterFood).setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view){
        Bundle bundle = new Bundle();
        EditText foodItem = (EditText)view.findViewById(R.id.enterFood);
        item = foodItem.getText().toString();
        bundle.putString("item",item);
        navController.navigate(R.id.addFoodFragment);
    }

}