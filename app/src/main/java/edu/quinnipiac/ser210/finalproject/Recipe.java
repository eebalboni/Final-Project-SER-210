package edu.quinnipiac.ser210.finalproject;

public class Recipe {
    private String name;
    //I don't think we should have an array of type ingredients as parsing the string from the API doesn't seem to be the best idea;
    private String ingredients;
    //Could store as an integer, but there's 0 case where we need to compare serving sizes or anything
    private String servings;
    private String instructions;
    private long id;

    public Recipe(){

    }

    public Recipe(String name, String ingredients, String servings, String instructions){
        this.name = name;
        this.ingredients = ingredients;
        this.servings = servings;
        this.instructions = instructions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getIngredients(){
        return ingredients;
    }

    public String getServings() {
        return servings;
    }

    public String getInstructions() {
        return instructions;
    }

    public long getId(){
        return id;
    }
}
