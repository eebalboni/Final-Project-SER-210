/*
SER 210 Final Project
Professor Ruby
Be prePEARed app, meant to help users keep track of food and find recipes easily!
By: Jonathan Mason, Emily Balboni, and Amber Kusma
 */
package edu.quinnipiac.ser210.finalproject;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.Navigator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class RecipeListFragment extends Fragment implements RecipeAdapter.RecyclerViewOnClickListener{

    private RecipeDataSource dataSource;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Recipe> mRecipeData;
    private RecipeAdapter mRecipeAdapter;
    private RecipeHandler mRecipeHandler = new RecipeHandler();
    private NavController navController;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public RecipeListFragment(){}

    public static RecipeListFragment newInstance(String param1, String param2) {
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mRecipeData = (ArrayList<Recipe>) getArguments().getSerializable("recipes");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        mRecyclerView = view.findViewById(R.id.recipeRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(view.getContext());

        mRecyclerView.setLayoutManager(linearLayoutManager);
        if(mRecipeData == null){
            dataSource = new RecipeDataSource(view.getContext());
            dataSource.open();
            mRecipeData = (ArrayList<Recipe>) dataSource.getAllRecipes();
            dataSource.close();
        }

        mRecipeAdapter = new RecipeAdapter(mRecipeData, this.getActivity(),savedInstanceState,this);
        mRecyclerView.setAdapter(mRecipeAdapter);
        String name = mRecipeData.get(1).toString();


        return view;
    }



    public void setRecipeData(ArrayList<Recipe> newData){
        mRecipeData = newData;
    }

    public void replaceRecipeData(ArrayList<Recipe> newData){
        mRecipeData = newData;
        mRecipeAdapter.updateList(mRecipeData);
    }

    //reference code : https://stackoverflow.com/questions/52146732/how-to-do-you-implement-a-recyclerview-onclick-in-a-fragment
    @Override
    public void recyclerViewClick(int position) {
        String recipeName = mRecipeData.get(position).getName();
        String recipeServing = mRecipeData.get(position).getServings();
        String recipeIngredient = mRecipeData.get(position).getIngredients();
        String recipeInstruction = mRecipeData.get(position).getInstructions();
        Bundle b = new Bundle();
        b.putString("name",recipeName);
        b.putString("serving",recipeServing);
        b.putString("ingredient",recipeIngredient);
        b.putString("instruction",recipeInstruction);
        navController.navigate(R.id.action_recipeListFragment_to_recipeDetailFragment,b);
    }
}





