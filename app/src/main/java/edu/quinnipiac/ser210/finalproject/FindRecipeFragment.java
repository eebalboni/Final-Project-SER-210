/*
SER 210 Final Project
Professor Ruby
Be prePEARed app, meant to help users keep track of food and find recipes easily!
By: Jonathan Mason, Emily Balboni, and Amber Kusma
 */

/*
Find recipe fragment lets the user search for a recipe.
 */
package edu.quinnipiac.ser210.finalproject;

import android.os.AsyncTask;
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
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FindRecipeFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String recipe;
    private String mParam1;
    private String mParam2;

    private RecipeDataSource dataSource;
    private ArrayList<Recipe> mRecipe;

    private TabsAdapter tabsAdapter;

    private String urlString = "https://recipe-by-api-ninjas.p.rapidapi.com/v1/recipe?query=";
    private String LOG_TAG = this.getClass().getSimpleName();

    private RecipeHandler recipeHandler = new RecipeHandler();
    NavController navController = null;
    public FindRecipeFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_find_recipe, container, false);

        dataSource = new RecipeDataSource(view.getContext());

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        view.findViewById(R.id.submitRButton).setOnClickListener(this);
    }


    /*
    Setting on click listener for the submit button, saving the recipe name
    as a string in the bundle. nav graph moves to next fragment to display
     */
    @Override
    public void onClick(View viw){
        Log.d("Reach","I think ketchup goes well with fries");
        Bundle bundle = new Bundle();
        EditText recipeName = getView().findViewById(R.id.enterRecipe);
        //this line needs to change
        new FetchRecipes().execute(recipeName.getText().toString());
    }


    public static FindRecipeFragment newInstance(String param1, String param2) {
        FindRecipeFragment fragment = new FindRecipeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    class FetchRecipes extends AsyncTask<String, Void, ArrayList<Recipe>>{

        String recipeName;
        @Override
        protected ArrayList<Recipe> doInBackground(String... strings) {
            recipeName = strings[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            ArrayList<Recipe> recipe = null;
            String query = strings[0].replaceAll("\\s", "%20");
            try{
                URL url= new URL(urlString+ query);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("x-rapidapi-key", "61d3d3d5e3mshab3a9610c39fe9ap1d574djsn973568c7e356");
                urlConnection.connect();

                InputStream in = urlConnection.getInputStream();
                if(in == null)
                    return null;

                reader = new BufferedReader(new InputStreamReader(in));
                recipe = getRecipeFromBuffer(reader);


            }catch(Exception e){
                Log.e(LOG_TAG, "Error" + e.getMessage());
                return null;
            }finally{
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
                if (reader != null){
                    try{
                        reader.close();
                    }catch (IOException e){
                        Log.e(LOG_TAG,"Error" + e.getMessage());
                        return null;
                    }
                }
            }
            return recipe;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("recipes", recipes);
            Log.d("Reach", recipes.toString());
            navController.navigate(R.id.action_findRecipeFragment_to_recipeListFragment, bundle);
        }
    }

    private ArrayList<Recipe> getRecipeFromBuffer(BufferedReader bufferedReader){
        StringBuffer buffer = new StringBuffer();
        String line;

        if (bufferedReader != null) {
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line + '\n');
                }
                bufferedReader.close();
                return RecipeHandler.getRecipe(buffer.toString());
            } catch (Exception e) {
                Log.e("MainActivity", "Error" + e.getMessage());
                return null;
            } finally {

            }
        }
        return null;
    }
}


