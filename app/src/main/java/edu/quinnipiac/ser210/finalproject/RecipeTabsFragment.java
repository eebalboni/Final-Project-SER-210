package edu.quinnipiac.ser210.finalproject;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientTabsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeTabsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecipeDataSource dataSource;
    private ArrayList<Recipe> mRecipe;

    private TabsAdapter tabsAdapter;

    private String urlString = "https://rapidapi.com/apininjas/api/recipe-by-api-ninjas/ ";
    private String LOG_TAG = this.getClass().getSimpleName();

    private RecipeHandler recipeHandler = new RecipeHandler();

    public RecipeTabsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IngredientTabsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeTabsFragment newInstance(String param1, String param2) {
        RecipeTabsFragment fragment = new RecipeTabsFragment();
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
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);





        //Instantiate data source
        dataSource = new RecipeDataSource(view.getContext());
        new FetchIngredients().execute();

        return view;
    }

    class FetchIngredients extends AsyncTask<String, Void, ArrayList<Recipe>> {

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

    class FetchRecipe extends AsyncTask<String, Void, String>{

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

//        @Override
//        protected void onPostExecute(String s) {
//            if(mIngredientsData != null){
//                for(int i = 0; i < mIngredientsData.size(); i++){
//                    if(ingredientName == mIngredientsData.get(i).getName()){
//                        mIngredientsData.get(i).setNutrition(s);
//                        switch(mIngredientsData.get(i).getLocation()){
//                            case "refrigerator":{
//                                mRefrigeratorData.add(mIngredientsData.get(i));
//                                break;
//                            }
//                            case "pantry":{
//                                mPantryData.add(mIngredientsData.get(i));
//                                break;
//                            }
//                        }
//                    }
//                }
//                tabsAdapter.setRefrigeratorData(mRefrigeratorData);
//                tabsAdapter.setPantryData(mPantryData);
//            }
//        }
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