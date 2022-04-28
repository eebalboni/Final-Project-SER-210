/*
SER 210 Final Project
Professor Ruby
Be prePEARed app, meant to help users keep track of food and find recipes easily!
By: Jonathan Mason, Emily Balboni, and Amber Kusma
 */
package edu.quinnipiac.ser210.finalproject;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class RecipeListFragment extends Fragment {

    private RecipeDataSource dataSource;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Recipe> mRecipeData;
    private RecipeAdapter mRecipeAdapter;
    private RecipeHandler mRecipeHandler = new RecipeHandler();

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mRecipeData = (ArrayList<Recipe>) getArguments().getSerializable("recipes");
        }
        //not sure if this line will be any help
       // String recipe = savedInstanceState.getString("recipe");
        //favorites
//        cursor = readDatabase();
//        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, columns,to, 0);
//        setListAdapter(listAdapter);
    }

//    private Cursor readDatabase(){
//        //RecipeSQLiteHelper recipeHelper = new RecipeSQLiteHelper(this, RecipeSQLiteHelper.COLUMN_RECIPE, null, RecipeSQLiteHelper.DA)
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        //i stored the recipe title

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

        mRecipeAdapter = new RecipeAdapter(mRecipeData, this.getActivity());
        mRecyclerView.setAdapter(mRecipeAdapter);
        return view;
    }

    public void setRecipeData(ArrayList<Recipe> newData){
        mRecipeData = newData;
    }

    public void replaceRecipeData(ArrayList<Recipe> newData){
        mRecipeData = newData;
        mRecipeAdapter.updateList(mRecipeData);
    }
}





