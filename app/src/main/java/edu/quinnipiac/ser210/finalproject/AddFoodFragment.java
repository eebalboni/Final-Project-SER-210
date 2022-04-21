package edu.quinnipiac.ser210.finalproject;

import static edu.quinnipiac.ser210.finalproject.R.layout.fragment_add_food;
import static edu.quinnipiac.ser210.finalproject.R.layout.fragment_find_food;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class AddFoodFragment extends Fragment {
    String item,dateTwo;
    String location;
    NavController navController;
    IngredientDataSource dataSource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = getArguments().getString("item");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        view.findViewById(R.id.pantry).setOnClickListener(this::onClickPn);
        view.findViewById(R.id.refrigerator).setOnClickListener(this::onClickPn);
//        navController.navigate(R.id.action_addFoodFragment_to_findFoodFragment);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(fragment_add_food,container,false);

        return layout;
    }

    private void onClickPn(View view) {
        EditText date = (EditText) view.findViewById(R.id.datepicker) ;
        String d = date.getText().toString();
        callDataBase(d);
        location = "pantry";
    }

    private void onClickRf(View view) {
        EditText date = (EditText) view.findViewById(R.id.datepicker) ;
        String d = date.getText().toString();
        callDataBase(d);
        location = "refrigerator";
    }

    public void callDataBase(String date){
        dataSource.createIngredient(item,date,location);
    }
}