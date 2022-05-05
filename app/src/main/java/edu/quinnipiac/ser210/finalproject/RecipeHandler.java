/*
SER 210 Final Project
Professor Ruby
Be prePEARed app, meant to help users keep track of food and find recipes easily!
By: Jonathan Mason, Emily Balboni, and Amber Kusma
 */
/*
RecipeHandler class. Converts string buffer into a JSONArray that can then be used for obtaining data from the Recipe API.
Author: Jonathan Mason, Amber Kusma
 */

package edu.quinnipiac.ser210.finalproject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

            result.add(new Recipe(name, ingredients, servings, instructions,"false"));
            //String result = "Recipe " + name +"\nIngredients " + ingredients + "\nServings: " + servings + "\nInstructions " + instructions ;
        }

        return result;
    }

}
