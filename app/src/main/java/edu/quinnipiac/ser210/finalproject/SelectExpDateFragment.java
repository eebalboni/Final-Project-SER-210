package edu.quinnipiac.ser210.finalproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;


public class SelectExpDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        final Calendar calendar= Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public SelectExpDateFragment() {
        // Required empty public constructor
    }


    @Override
    public void onDateSet(DatePicker datePicker, int day, int month, int year) {
        populateSetDate(year, month, day);
    }
    public void populateSetDate(int year, int month, int day) {

        TextView dateText = (TextView) getActivity().findViewById(R.id.datepicker);
        dateText.setText(month+"/"+day+"/"+year);
    }
}
