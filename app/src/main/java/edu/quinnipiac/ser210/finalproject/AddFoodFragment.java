/*
SER 210 Final Project
Professor Ruby
Be prePEARed app, meant to help users keep track of food and find recipes easily!
By: Jonathan Mason, Emily Balboni, and Amber Kusma
 */

/*
The AddFoodFragment lets the user add a food item to their pantry or refrigerator
This stores the information into a database
 */
package edu.quinnipiac.ser210.finalproject;

import static edu.quinnipiac.ser210.finalproject.R.layout.fragment_add_food;
import static edu.quinnipiac.ser210.finalproject.R.layout.fragment_find_food;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddFoodFragment extends Fragment  {
    String item,dateTwo;
    String location;
    NavController navController;
    IngredientDataSource dataSource;
    Button pantryButton;
    String expDate;
    CalendarView calendarView;
    SimpleDateFormat simpleDateFormat;




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
        view.findViewById(R.id.refrigerator).setOnClickListener(this::onClickRf);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(fragment_add_food,container,false);
        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

        calendarView = layout.findViewById(R.id.datepicker);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(i, i1, i2);
                expDate = simpleDateFormat.format(calendar.getTimeInMillis());
            }
        });


        dataSource = new IngredientDataSource(layout.getContext());
        dataSource.open();

        return layout;
    }


    private void onClickPn(View view) {
       // EditText date =  getView().findViewById(R.id.datepicker) ;
        Log.d("Date", expDate);
        String d = expDate;

        location = "pantry";
        Log.d("Items", "Item: " + item + "Date: " + d + "location" + location);
        callDataBase(d);
        Toast toast = Toast.makeText(getContext(), "Item added to your pantry", Toast.LENGTH_LONG);
        toast.show();
    }

    private void onClickRf(View view) {
       //EditText date = getView().findViewById(R.id.datepicker) ;
        String d = expDate;
        location = "refrigerator";
        callDataBase(d);
        Toast toast = Toast.makeText(getContext(), "Item added to your refrigerator", Toast.LENGTH_LONG);
        toast.show();


    }

    public void callDataBase(String date){
        dataSource.createIngredient(item,date,location);
    }
}