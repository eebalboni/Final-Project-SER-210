package edu.quinnipiac.ser210.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // retrieve expiration date from database
        //cursor.readDatabase():
        //String[]columns (FoodDB.NAMW, FoodDB.EXPDATE);
        //int[]
    }
}