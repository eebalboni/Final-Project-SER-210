package edu.quinnipiac.ser210.finalproject;

import java.sql.Date;

public class Ingredient {
    private String name;
    private String expirationDate;
    private String location;
    private String nutrition;
    private long id;

    public Ingredient(String name, String expirationDate){
        this.name = name;
        this.expirationDate = expirationDate;
    }
    public Ingredient(){

    }

    public String getName() {
        return name;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getLocation() {
        return location;
    }

    public long getId() {
        return id;
    }

    public String getNutrition(){
        return nutrition;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNutrition(String nutrition){
        this.nutrition = nutrition;
    }
}
