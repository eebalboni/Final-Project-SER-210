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

public class FindRecipeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String recipe;
    private String mParam1;
    private String mParam2;

    private RecipeDataSource dataSource;
    private ArrayList<Recipe> mRecipe;

    private TabsAdapter tabsAdapter;

    private String urlString = "https://rapidapi.com/apininjas/api/recipe-by-api-ninjas/ ";
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
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        dataSource = new RecipeDataSource(view.getContext());
        new FindRecipeFragment.FetchRecipes().execute();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        view.findViewById(R.id.submitRButton).setOnClickListener((View.OnClickListener) this);
    }


    public void onClick(View view) {
        Log.d("Reach", "I think ketchup goes well with fries");
        Bundle bundle= new Bundle();
        EditText recipeName = getView().findViewById(R.id.enterRecipe);
        recipe = recipeName.getText().toString();
        bundle.putString("recipe",recipe);
        navController.navigate(R.id.action_findRecipeFragment_to_recipeListFragment);
    }
    public static FindRecipeFragment newInstance(String param1, String param2) {
        FindRecipeFragment fragment = new FindRecipeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    class FetchRecipe extends AsyncTask<String, Void, ArrayList<Recipe>> {

        @Override
        protected ArrayList<Recipe> doInBackground(String... strings) {
            dataSource.open();
            List<Recipe> allIngredients = dataSource.getAllRecipes();
            return (ArrayList<Recipe>) mRecipe;
        }


        @Override
        protected void onPostExecute(ArrayList<Recipe> recipe) {
            mRecipe = recipe;
            Log.d(LOG_TAG, mRecipe.size()+"");
            for(int i = 0; i < mRecipe.size(); i++){
                new FetchRecipe().execute(mRecipe.get(i).getName());
            }
            dataSource.close();
        }
    }

    class FetchRecipes extends AsyncTask<String, Void, String>{

        String recipeName;
        @Override
        protected String doInBackground(String... strings) {
            recipeName = strings[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String recipe = null;
            String query = strings[0].replaceAll("\\s", "%20");
            try{
                URL url= new URL(urlString+ query);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("x-rapidapi-key", "906083b443msh7aa7c234551c004p11e");
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

    }

    private String getRecipeFromBuffer(BufferedReader bufferedReader){
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


