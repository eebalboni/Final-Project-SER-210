package edu.quinnipiac.ser210.finalproject;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecipeHandler {

    public static String getRecipe(String recipeJsonStr) throws JSONException{
        JSONObject listJSONObj = new JSONObject(recipeJsonStr);
        JSONArray recipeJSONArray = listJSONObj.getJSONArray("items");
        JSONObject recipeJSONOBJ = recipeJSONArray.getJSONObject(0);

        String name = recipeJSONOBJ.getString("title");
        String ingredients = recipeJSONOBJ.getString("ingredients");
        String servings = recipeJSONOBJ.getString("servings");
        String instructions = recipeJSONOBJ.getString("instructions");


        String result = "Recipe " + name +"\nIngredients " + ingredients + "\nServings: " + servings + "\nInstructions " + instructions ;
        return result;
    }

}
