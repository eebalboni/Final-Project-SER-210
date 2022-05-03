package edu.quinnipiac.ser210.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecipeDetailFragment extends Fragment {
    private static String name;
    private Toolbar mToolbar;
    private ShareActionProvider mShareActionProvider;
    private ArrayList<Recipe> mRecipe;
    private Recipe recipe;
    private Bundle recipeBundle;

    public RecipeDetailFragment() {

    }

    public static RecipeDetailFragment newInstance(){
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle recipeBundle = fragment.getArguments();
        name = (String) recipeBundle.get("name");
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_recipe_detail,container,false);
        mToolbar = (Toolbar) layout.findViewById(R.id.toolbar);

        //this is where i set the data


        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        return layout;
    }

    public void onAttach(@NonNull Context context){

        super.onAttach(context);
    }

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.main_menu,menu);

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.action_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                // arraylist
                
                return true;
        }
        return false;
    }


}