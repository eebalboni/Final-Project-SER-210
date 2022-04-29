package edu.quinnipiac.ser210.finalproject;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.*;

import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class IngredientDataSourceTest {
    private IngredientDataSource ingredientDataSource;

    @Before
    public void setUp() throws Exception {
        getTargetContext().deleteDatabase(IngredientSQLiteHelper.DATABASE_NAME);
        ingredientDataSource = new IngredientDataSource(getTargetContext());
        ingredientDataSource.open();
    }

    @After
    public void tearDown() throws Exception {
        ingredientDataSource.close();
    }

    @Test
    public void createIngredient() {
        ingredientDataSource.createIngredient("Banana","02/02/2022","pantry");
        List<Ingredient> l = ingredientDataSource.getAllIngredients();
        assertTrue(l.get(0).getName().equals("Banana"));
        assertTrue(l.get(0).getExpirationDate().equals("02/02/2022"));
        assertTrue(l.get(0).getLocation().equals("pantry"));
    }

    @Test
    public void deleteIngredient() {
        Ingredient ing = ingredientDataSource.createIngredient("Apple","02/04/2023","pantry");
        ingredientDataSource.deleteIngredient(ing);
        List<Ingredient> ingredients = ingredientDataSource.getAllIngredients();
        assertEquals(ingredients.size(),0);
    }
}