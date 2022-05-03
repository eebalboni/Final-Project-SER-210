/*
SER 210 Final Project
Professor Ruby
Be prePEARed app, meant to help users keep track of food and find recipes easily!
By: Jonathan Mason, Emily Balboni, and Amber Kusma
 */
/*
IngredientListFragment class. Creates recycle view containing ingredient data.
Author: Jonathan Mason
 */
package edu.quinnipiac.ser210.finalproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Ingredient> mIngredientData;
    private IngredientAdapter mIngredientAdapter;
    private IngredientDataSource ingredientDataSource;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IngredientListFragment() {

    }


    public static IngredientListFragment newInstance(String param1, String param2) {
        IngredientListFragment fragment = new IngredientListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);
        //code for button
       // ingredientDataSource = new IngredientDataSource(view.getContext());
        //ingredientDataSource.open();


        mRecyclerView = view.findViewById(R.id.recyclerView);

        mRecyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(view.getContext());

        mRecyclerView.setLayoutManager(linearLayoutManager);
        if(mIngredientData == null){
            mIngredientData = new ArrayList<Ingredient>();
            mIngredientData.add(new Ingredient("Loading", "Loading"));
        }
        mIngredientAdapter = new IngredientAdapter(mIngredientData, this.getActivity());
        mRecyclerView.setAdapter(mIngredientAdapter);

       // ingredientDataSource.close();
        return view;
    }


    public void setIngredientData(ArrayList<Ingredient> ingredientData) {
        mIngredientData = ingredientData;
    }

    public void replaceIngredientData(ArrayList<Ingredient> ingredientData){
        mIngredientData = ingredientData;
        mIngredientAdapter.updateList(mIngredientData);
    }

}