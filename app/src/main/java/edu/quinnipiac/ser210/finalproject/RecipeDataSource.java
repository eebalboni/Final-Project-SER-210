/*
SER 210 Final Project
Professor Ruby
Be prePEARed app, meant to help users keep track of food and find recipes easily!
By: Jonathan Mason, Emily Balboni, and Amber Kusma
 */
package edu.quinnipiac.ser210.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class RecipeDataSource {
    private SQLiteDatabase database;
    private RecipeSQLiteHelper dbHelper;
    private String[] allColumns = {RecipeSQLiteHelper.COLUMN_ID, RecipeSQLiteHelper.COLUMN_RECIPE, RecipeSQLiteHelper.COLUMN_INGREDIENTS, RecipeSQLiteHelper.COLUMN_SERVINGS, RecipeSQLiteHelper.COLUMN_INSTRUCTIONS};

    public RecipeDataSource(Context context){
        dbHelper = new RecipeSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase getDatabase(){
        return database;
    }

    public void close(){
        dbHelper.close();
    }

    public Recipe createRecipe(String name, String ingredients, String servings, String instructions){
        ContentValues values = new ContentValues();
        values.put(RecipeSQLiteHelper.COLUMN_RECIPE, name);
        values.put(RecipeSQLiteHelper.COLUMN_INGREDIENTS, ingredients);
        values.put(RecipeSQLiteHelper.COLUMN_SERVINGS, servings);
        values.put(RecipeSQLiteHelper.COLUMN_INSTRUCTIONS, instructions);

        long insertId = database.insert(RecipeSQLiteHelper.TABLE_RECIPE, null, values);
        Cursor cursor = database.query(RecipeSQLiteHelper.TABLE_RECIPE, allColumns, RecipeSQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Recipe newRecipe = cursorToRecipe(cursor);
        cursor.close();

        return newRecipe;

    }

    public void deleteRecipe(Recipe recipe){
        long id = recipe.getId();
        database.delete(RecipeSQLiteHelper.TABLE_RECIPE, RecipeSQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Recipe> getAllRecipes(){
        List<Recipe> recipes = new ArrayList<Recipe>();
        Cursor cursor = database.query(RecipeSQLiteHelper.TABLE_RECIPE, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Recipe recipe = cursorToRecipe(cursor);
            recipes.add(recipe);
            cursor.moveToNext();
        }
        cursor.close();
        return recipes;
    }

    //stack overflow for reference
    public Recipe cursorToRecipe(Cursor cursor){
        Recipe recipe = new Recipe();
        recipe.setId(cursor.getLong(0));
        recipe.setName(cursor.getString(1));
        recipe.setIngredients(cursor.getString(2));
        recipe.setServings(cursor.getString(3));
        recipe.setInstructions(cursor.getString(4));
        return recipe;
    }
}
