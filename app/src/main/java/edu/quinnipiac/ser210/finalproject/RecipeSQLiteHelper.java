package edu.quinnipiac.ser210.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RecipeSQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_RECIPE = "recipes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_RECIPE = "name";
    public static final String COLUMN_INGREDIENTS = "ingredients";
    public static final String COLUMN_SERVINGS = "servings";
    public static final String COLUMN_INSTRUCTIONS = "instructions";

    public static final String DATABASE_NAME = "ingredients.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_RECIPE + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_RECIPE
            + " text not null, " + COLUMN_INGREDIENTS + " text not null, " + COLUMN_SERVINGS + " text not null, " + COLUMN_INSTRUCTIONS + " text not null);";

    public RecipeSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(IngredientSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        onCreate(db);
    }

}
