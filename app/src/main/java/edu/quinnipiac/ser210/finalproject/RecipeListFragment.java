/*
SER 210 Final Project
Professor Ruby
Be prePEARed app, meant to help users keep track of food and find recipes easily!
By: Jonathan Mason, Emily Balboni, and Amber Kusma
 */
package edu.quinnipiac.ser210.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.Navigator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeListFragment extends Fragment implements RecipeAdapter.RecyclerViewOnClickListener{

    private RecipeDataSource dataSource;
    private IngredientDataSource ingredientDataSource;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Recipe> mRecipeData;
    private RecipeAdapter mRecipeAdapter;
    private RecipeHandler mRecipeHandler = new RecipeHandler();
    private NavController navController;
    private Toolbar mToolbar;
    private ArrayList<Recipe> updatedRecipeList;
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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ingredientDataSource = new IngredientDataSource(view.getContext());
        ingredientDataSource.open();

        //toolbar
        mToolbar = (Toolbar) view.findViewById(R.id.recipeToolbar);
        mToolbar.inflateMenu(R.menu.recipe_menu);
        setHasOptionsMenu(true);

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

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater){
        inflater.inflate(R.menu.recipe_menu,menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        Log.d("Reach","you made it!");

        switch (item.getItemId()){
            case R.id.action_filter:
                updatedRecipeList = new ArrayList<Recipe> ();
                ArrayList<Ingredient> allIngredients = (ArrayList<Ingredient>) ingredientDataSource.getAllIngredients();
                int size = mRecipeData.size();
                for(int i = 0; i < size; i++) {
                   String currentIng =  mRecipeData.get(i).getServings().toLowerCase();
                   Log.d("Reach",currentIng);
                   for(int j = 0; j < allIngredients.size();j++){
                       Log.d("something", allIngredients.get(j).getName().toLowerCase());
                       if(currentIng.contains(allIngredients.get(j).getName().toLowerCase())){
                           updatedRecipeList.add(mRecipeData.get(i));
                           Log.d("cur contains", currentIng.contains(allIngredients.get(j).getName().toLowerCase()) + "");
                           break;
                       }
                   }
                }
                if(updatedRecipeList != null){
                    replaceRecipeData(updatedRecipeList);
                } //else
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}





