package edu.rit.swen262.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


/**
 * changes made: changed from iterator to for loop w null execption check so exports work w/out errors
 */


@JsonIgnoreProperties(ignoreUnknown = true) //needed for importing meals since we are creating our own field called calories

/**
 * @author Emily Santillanes
 */
public class Meal implements Food, Serializable {
    private static final long serialVersionUID = 1L; 

    private String name;
    private List<Recipe> recipeList;

    //storing the calories manually - user will enter when importing (will be used) else - calories will be computed from the recipies
    private int manualCalories;

    // Needed for deserialization by Jackson
    public Meal() { }

    /**
     * Creates a meal with its name and included recipes
     * @param name The name of the meal
     * @param recipes The recipes that make up the meal
     */
    public Meal(String name, List<Recipe> recipes, int manualCalories) {
        this.name = name;
        this.recipeList = recipes;
        this.manualCalories = manualCalories;
    }

    /**
     * Adds a recipe to the recipe list for the meal
     * @param recipe The recipe being added to the meal
     */
    public void addChild(Recipe recipe) {
        this.recipeList.add(recipe);
    }

    /**
     * Removes a specific recipe from the meal
     * @param recipe The recipe being removed
     */
    public void removeChild(Recipe recipe){
        this.recipeList.remove(this.recipeList.indexOf(recipe));
    }

    /**
     * Gets the recipes that make up the meal
     * @return a list of recipes
     */
    
    public List<Recipe> children() {
        return this.recipeList;
    }

    
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Before - always calculated calories based on the recipe
     * Changed - if manualCalories is set - return that instead --> from the import
     * JsonProperty - when serializing to JSON, Jackson will map the methods return value to the JSON property named: "calories"
     */
    @JsonProperty("calories")
    @Override
    public int getCalories() {
        if (manualCalories != 0) {
            return manualCalories;
        }
        int computedCalories = 0;
        if (recipeList != null) {
            for (Recipe recipe : recipeList) {
                computedCalories += recipe.getCalories();
            }
        }
        return computedCalories;
    }

    public void setManualCalories(int manualCalories) {
        this.manualCalories = manualCalories;
    }

    public int getManualCalories() {
        return manualCalories;
    }


    /**
     * only allows JSON to read but not write the value 
     * included when Java->JSON but not for importing since value of fat won't be set
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Override
    public float getFat(){
        float totalFat=0;
        if(recipeList != null) {
            for (Recipe recipe : recipeList) {
                totalFat += recipe.getFat();
            }
        }
        return totalFat;
    }

    /**
     * only allows JSON to read but not write the value 
     * included when Java->JSON but not for importing since value of protein won't be set
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Override
    public float getProtein(){
        float totalProtein=0;
        if(recipeList != null) {
            for (Recipe recipe : recipeList) {
                totalProtein += recipe.getProtein();
            }
        }
        return totalProtein;
    }

    /**
     * only allows JSON to read but not write the value 
     * included when Java->JSON but not for importing since value of fiber won't be set
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Override
    public float getFiber(){
        float totalFiber=0;
        if(recipeList != null) {
            for (Recipe recipe : recipeList) {
                totalFiber += recipe.getFiber();
            }
        }
        return totalFiber;
    }

    /**
     * only allows JSON to read but not write the value 
     * included when Java->JSON but not for importing since value of carbs won't be set
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Override
    public float getCarbs(){
        float totalCarbs=0;
        if(recipeList != null) {
            for (Recipe recipe : recipeList) {
                totalCarbs += recipe.getCarbs();
            }
        }
        return totalCarbs;
    }


    
    public void accept(FoodVisitor visitor){
        if(recipeList != null) {
            for (Recipe recipe : recipeList) {
                recipe.accept(visitor);
            }
        }
    }

    @Override
    public String toString() {
        return name; 
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Meal meal = (Meal) obj;
        return name.equals(meal.name) && Objects.equals(recipeList, meal.recipeList); //won't throw a null pointer using Objects 
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, recipeList);
    }
}
