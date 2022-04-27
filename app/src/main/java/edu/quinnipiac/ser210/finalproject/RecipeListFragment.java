package edu.quinnipiac.ser210.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RecipeListFragment extends Fragment {

    private RecipeDataSource dataSource;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Recipe> mRecipeData;
    private RecipeAdapter mAdapter;

    public RecipeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //not sure if this line will be any help
       // String recipe = savedInstanceState.getString("recipe");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        //i stored the recipe title
        mRecyclerView = view.findViewById(R.id.recipeRecyclerView);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        if(mRecipeData == null){
            dataSource = new RecipeDataSource(view.getContext());
            dataSource.open();
            mRecipeData = (ArrayList<Recipe>) dataSource.getAllRecipes();
            dataSource.close();
        }

        mAdapter = new RecipeAdapter(mRecipeData, this.getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    public void setRecipeData(ArrayList<Recipe> newData){
        mRecipeData = newData;
    }

    public void replaceRecipeData(ArrayList<Recipe> newData){
        mRecipeData = newData;
        mAdapter.updateList(mRecipeData);
    }
}