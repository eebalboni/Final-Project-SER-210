package edu.quinnipiac.ser210.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class RecipeDetailFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Recipe> mRecipeData;
    private RecipeAdapter mRecipeAdapter;


    private String mParam1;
    private String mParam2;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }



    public static RecipeDetailFragment newInstance(String param1, String param2) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        // Inflate the layout for this fragment

        mRecyclerView.setLayoutManager(linearLayoutManager);
        if(mRecipeData == null){
            mRecipeData = new ArrayList<Recipe>();
        }
        mRecipeAdapter = new RecipeAdapter(mRecipeData, this.getActivity());
        mRecyclerView.setAdapter(mRecipeAdapter);
        return view;
    }
    public void setRecipeData(ArrayList<Recipe> recipeData) {
        mRecipeData = recipeData;
    }

    public void replaceRecipeData(ArrayList<Recipe> recipeData){
        mRecipeData = recipeData;
        mRecipeAdapter.updateList(mRecipeData);
    }

}
