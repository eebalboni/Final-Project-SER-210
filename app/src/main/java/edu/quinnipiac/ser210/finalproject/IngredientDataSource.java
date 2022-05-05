/*
SER 210 Final Project
Professor Ruby
Be prePEARed app, meant to help users keep track of food and find recipes easily!
By: Jonathan Mason, Emily Balboni, and Amber Kusma
 */
/*
IngredientDataSource class. Handles logic for adding and retrieving data from the Ingredients database.
 */
package edu.quinnipiac.ser210.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class IngredientDataSource {
    private SQLiteDatabase database;
    private IngredientSQLiteHelper dbHelper;
    private String[] allColumns = {IngredientSQLiteHelper.COLUMN_ID, IngredientSQLiteHelper.COLUMN_INGREDIENT, IngredientSQLiteHelper.COLUMN_EXPIRATION, IngredientSQLiteHelper.COLUMN_LOCATION};

    public IngredientDataSource(Context context){
        dbHelper = new IngredientSQLiteHelper(context);
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

    //create ingredient
    public Ingredient createIngredient(String name, String expiration, String location){
        ContentValues values = new ContentValues();
        values.put(IngredientSQLiteHelper.COLUMN_INGREDIENT, name);
        values.put(IngredientSQLiteHelper.COLUMN_EXPIRATION, expiration);
        values.put(IngredientSQLiteHelper.COLUMN_LOCATION, location);

        long insertId = database.insert(IngredientSQLiteHelper.TABLE_INGREDIENT, null, values);
        Cursor cursor = database.query(IngredientSQLiteHelper.TABLE_INGREDIENT, allColumns, IngredientSQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Ingredient newIngredient = cursorToIngredient(cursor);
        cursor.close();

        return newIngredient;

    }

    public void deleteIngredient(Ingredient ingredient){
        long id = ingredient.getId();
        database.delete(IngredientSQLiteHelper.TABLE_INGREDIENT, IngredientSQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Ingredient> getAllIngredients(){
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        Cursor cursor = database.query(IngredientSQLiteHelper.TABLE_INGREDIENT, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Ingredient ingredient = cursorToIngredient(cursor);
            ingredients.add(ingredient);
            cursor.moveToNext();
        }
        cursor.close();
        return ingredients;
    }

    private Ingredient cursorToIngredient(Cursor cursor){
        Ingredient ingredient = new Ingredient();
        ingredient.setId(cursor.getLong(0));
        ingredient.setName(cursor.getString(1));
        ingredient.setExpirationDate(cursor.getString(2));
        ingredient.setLocation(cursor.getString(3));
        return ingredient;
    }
}
