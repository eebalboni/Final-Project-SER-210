package edu.quinnipiac.ser210.finalproject;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import junit.framework.TestCase;

public class IngredientDataSourceTest extends TestCase {
    private IngredientDataSource d;
    public void setUp() throws Exception{
        super.setUp();
        Context context = ApplicationProvider.getApplicationContext();
        d = new IngredientDataSource(context);
        d.open();
    }

    public void testGetDatabase() {
    }

    public void testCreateIngredient() {
        Ingredient ing = new Ingredient("Banana","02/02/2022");
        Ingredient newIng = d.createIngredient("Banana","02/02/2022","pantry");
        assertEquals(ing,newIng);
    }

    public void testDeleteIngredient() {
    }

    public void testGetAllIngredients() {
    }
}