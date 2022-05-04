package edu.quinnipiac.ser210.finalproject;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class RecipeHandler {

    public static ArrayList<Recipe> getRecipe(String recipeJsonStr) throws JSONException{
        JSONArray listJSONArray = new JSONArray(recipeJsonStr);
        ArrayList<Recipe> result = new ArrayList<Recipe>();

        for(int i = 0; i < listJSONArray.length(); i++){
            JSONObject curObject = listJSONArray.getJSONObject(i);

            String name = curObject.getString("title");
            String ingredients = curObject.getString("ingredients");
            String servings = curObject.getString("servings");
            String instructions = curObject.getString("instructions");

            result.add(new Recipe(name, ingredients, servings, instructions));
            //String result = "Recipe " + name +"\nIngredients " + ingredients + "\nServings: " + servings + "\nInstructions " + instructions ;
        }

        return result;
    }

}
