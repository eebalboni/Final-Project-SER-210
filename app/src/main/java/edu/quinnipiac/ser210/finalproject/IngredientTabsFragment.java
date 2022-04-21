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
public class IngredientTabsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private IngredientDataSource dataSource;
    private ArrayList<Ingredient> mIngredientsData;
    private ArrayList<Ingredient> mRefrigeratorData = new ArrayList<Ingredient>();
    private ArrayList<Ingredient> mPantryData = new ArrayList<Ingredient>();
    private TabsAdapter tabsAdapter;

    private String urlString = "https://calorieninjas.p.rapidapi.com/v1/nutrition?query=";
    private String LOG_TAG = this.getClass().getSimpleName();

    private IngredientHandler ingredientHandler = new IngredientHandler();

    public IngredientTabsFragment() {
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
    public static IngredientTabsFragment newInstance(String param1, String param2) {
        IngredientTabsFragment fragment = new IngredientTabsFragment();
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
        View view = inflater.inflate(R.layout.fragment_ingredient_tabs, container, false);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        tabsAdapter = new TabsAdapter(getParentFragmentManager(), 2, view.getContext());
        viewPager.setAdapter(tabsAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.refrigerator_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.pantry_tab));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Instantiate data source
        dataSource = new IngredientDataSource(view.getContext());
        new FetchIngredients().execute();

        return view;
    }

    class FetchIngredients extends AsyncTask<String, Void, ArrayList<Ingredient>> {

        @Override
        protected ArrayList<Ingredient> doInBackground(String... strings) {
            dataSource.open();
            List<Ingredient> allIngredients = dataSource.getAllIngredients();
            return (ArrayList<Ingredient>) allIngredients;
        }

        @Override
        protected void onPostExecute(ArrayList<Ingredient> ingredients) {
            mIngredientsData = ingredients;
            Log.d(LOG_TAG, mIngredientsData.size()+"");
            for(int i = 0; i < mIngredientsData.size(); i++){
                new FetchNutrition().execute(mIngredientsData.get(i).getName());
            }
            dataSource.close();
        }
    }

    class FetchNutrition extends AsyncTask<String, Void, String>{

        String ingredientName;
        @Override
        protected String doInBackground(String... strings) {
            ingredientName = strings[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String nutrition = null;
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
                nutrition = getNutritionFromBuffer(reader);


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
            return nutrition;
        }

        @Override
        protected void onPostExecute(String s) {
            if(mIngredientsData != null){
                for(int i = 0; i < mIngredientsData.size(); i++){
                    if(ingredientName == mIngredientsData.get(i).getName()){
                        mIngredientsData.get(i).setNutrition(s);
                        switch(mIngredientsData.get(i).getLocation()){
                            case "refrigerator":{
                                mRefrigeratorData.add(mIngredientsData.get(i));
                                break;
                            }
                            case "pantry":{
                                mPantryData.add(mIngredientsData.get(i));
                                break;
                            }
                        }
                    }
                }
                tabsAdapter.setRefrigeratorData(mRefrigeratorData);
                tabsAdapter.setPantryData(mPantryData);
            }
        }
    }

    private String getNutritionFromBuffer(BufferedReader bufferedReader){
        StringBuffer buffer = new StringBuffer();
        String line;

        if (bufferedReader != null) {
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line + '\n');
                }
                bufferedReader.close();
                return ingredientHandler.getNutrition(buffer.toString());
            } catch (Exception e) {
                Log.e("MainActivity", "Error" + e.getMessage());
                return null;
            } finally {

            }
        }
        return null;
    }
}