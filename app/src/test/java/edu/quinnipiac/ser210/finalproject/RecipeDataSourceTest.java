package edu.quinnipiac.ser210.finalproject;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.*;

import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;


public class RecipeDataSourceTest {
    RecipeDataSource recipeDataSource;
    @Before
    public void setUp() throws Exception {
        getTargetContext().deleteDatabase(RecipeSQLiteHelper.DATABASE_NAME);
        recipeDataSource = new RecipeDataSource(getTargetContext());
        recipeDataSource.open();
    }

    @After
    public void tearDown() throws Exception {
        recipeDataSource.close();
    }

    @Test
    public void testCreateRecipe() {
        recipeDataSource.createRecipe("Bread","bananas","10","Bake");
        List<Recipe> l = recipeDataSource.getAllRecipes();
        assertTrue(l.get(0).getName().equals("Bread"));
        assertTrue(l.get(0).getIngredients().equals("bananas"));
        assertTrue(l.get(0).getInstructions().equals("Bake"));
        assertTrue(l.get(0).getServings().equals("10"));
    }

    @Test
    public void testDeleteRecipe() {
        Recipe recipe = recipeDataSource.createRecipe("Apple","fruit","1","get");
        recipeDataSource.deleteRecipe(recipe);
        List<Recipe> recipes = recipeDataSource.getAllRecipes();
        assertEquals(recipes.size(),0);
    }
}