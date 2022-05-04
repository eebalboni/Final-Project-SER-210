package edu.quinnipiac.ser210.finalproject;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class IngredientTest {
    private Ingredient ing = new Ingredient();
    @Before
    public void setUp() throws Exception {
        ing = new Ingredient();
    }

    @Test
    public void getName() {
        ing.setName("Banana");
        String name = ing.getName();
        assertEquals("Banana",name);
    }

    @Test
    public void getExpirationDate() {
        ing.setExpirationDate("02/02/2022");
        String exp = ing.getExpirationDate();
        assertEquals("02/02/2022",exp);
    }

    @Test
    public void getLocation() {
        ing.setLocation("pantry");
        String loc = ing.getLocation();
        assertEquals("pantry",loc);
    }

    @Test
    public void getId() {
        ing.setId(202);
        long id = ing.getId();
        assertEquals(202,id);
    }

    @Test
    public void getNutrition() {
        ing.setNutrition("calories");
        String nut = ing.getNutrition();
        assertEquals("calories",nut);
    }
}