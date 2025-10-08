package edu.rit.swen262.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Emily Santillanes
 */
public class Recipe implements Food, Serializable {
    @SuppressWarnings("unused")
    private static final long serial = 1L; 
    
    private String name;
    private Map<Ingredient, Integer> ingredients;
    private String instructions;

    //needed for JSON imports
    private float carbs;
    private int calories;
    private float protein;
    private float fiber;
    private float fat;

    /**
     * Creates a recipe with its name, ingredients, and preparation instructions
     * @param name The name of the recipe
     * @param ingredients The ingredients included in the recipe
     * @param instructions The preparation instructions for the recipe
     */
    public Recipe(String name, Map<Ingredient, Integer> ingredients, String instructions){
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    
    //defult constructor
    public Recipe() {
        this.name = "";
        this.ingredients = null;  
        this.instructions = "";
    }


    /**
     * Adds an ingredient and its amount needed to the recipe
     * @param ingredient The ingredient being added
     * @param amountNeeded How much of the ingredient is needed for the recipe
     */
    public void addChild(Ingredient ingredient, int amountNeeded){
        this.ingredients.put(ingredient, amountNeeded);
    }

    /**
     * Removes an ingredient from the recipe
     * @param ingredient The ingredient being removed from the recipe
     */
    public void removeChild(Ingredient ingredient){
        this.ingredients.remove(ingredient);
    }

    /**
     * Gets the list of ingredients that make up the recipe
     * @return A list of ingredients
     */
    public List<Ingredient> children() {
        List<Ingredient> children = new ArrayList<>();
        Set<Ingredient> keys = ingredients.keySet();
        for(Ingredient ingredient: keys){
            children.add(ingredient);
        }
        return children;
    }

    public String getInstructions(){
        return this.instructions;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public int getCalories(){
        int calories = 0;
        Set<Ingredient> keys = ingredients.keySet();
        for(Ingredient ingredient: keys){
            calories+=(ingredient.getCalories()*ingredients.get(ingredient));
        }
        return calories;
    }

    @Override
    public float getFat(){
        float fat = 0;
        Set<Ingredient> keys = ingredients.keySet();
        for(Ingredient ingredient: keys){
            fat+=(ingredient.getFat()*ingredients.get(ingredient));
        }
        return fat;
    }

    @Override
    public float getProtein(){
        float protein = 0;
        Set<Ingredient> keys = ingredients.keySet();
        for(Ingredient ingredient: keys){
            protein+=(ingredient.getProtein()*ingredients.get(ingredient));
        }
        return protein;
    }

    @Override
    public float getFiber(){
        float fiber=0;
        Set<Ingredient> keys = ingredients.keySet();
        for(Ingredient ingredient: keys){
            fiber+=(ingredient.getFiber()*ingredients.get(ingredient));
        }
        return fiber;
    }

    @Override
    public float getCarbs(){
        float carbs=0;
        Set<Ingredient> keys = ingredients.keySet();
        for(Ingredient ingredient: keys){
            carbs+=(ingredient.getCarbs()*ingredients.get(ingredient));
        }
        return carbs;
    }

    public void accept(FoodVisitor visitor){
        for (Ingredient ingredient : ingredients.keySet()) {
            for (int i=0;i<ingredients.get(ingredient);i++) {
                ingredient.accept(visitor);
            }
        }
    }
}
