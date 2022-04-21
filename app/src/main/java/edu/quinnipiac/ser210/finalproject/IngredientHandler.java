package edu.quinnipiac.ser210.finalproject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IngredientHandler {

    public String getNutrition(String nutritionJsonStr) throws JSONException{
        JSONObject listJSONObj = new JSONObject(nutritionJsonStr);
        JSONArray nutritionJSONArray = listJSONObj.getJSONArray("items");
        JSONObject nutritionJSONOBJ = nutritionJSONArray.getJSONObject(0);

        double calories = nutritionJSONOBJ.getDouble("calories");
        double protein = nutritionJSONOBJ.getDouble("protein_g");
        double carbohydrates = nutritionJSONOBJ.getDouble("carbohydrates_total_g");
        double fat = nutritionJSONOBJ.getDouble("fat_total_g");
        double sugar = nutritionJSONOBJ.getDouble("sugar_g");

        String result = "Calories: " + calories +"\nProtein: " + protein + "\nCarbohydrates: " + carbohydrates + "\nFat: " + fat + "\nSugar: " + sugar;
        return result;
    }

}
