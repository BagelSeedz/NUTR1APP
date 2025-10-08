package edu.rit.swen262.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.rit.swen262.model.DailyHistoryModel;
import edu.rit.swen262.model.Meal;
import edu.rit.swen262.model.Recipe;
import edu.rit.swen262.model.StockAddVisitor;
import edu.rit.swen262.model.StockDeductorVisitor;
import edu.rit.swen262.model.UndoClasses.StockMemento;
import edu.rit.swen262.model.Ingredient;

/**
 * @author Emily Santillanes
 */

public class FoodController implements Originator {
    private PersonalHistoryController personalHistoryController;
    private UserController userController;

    public FoodController(PersonalHistoryController personalHistoryController, UserController userController) {
        this.personalHistoryController = personalHistoryController;
        this.userController = userController;
    }
//a controller is supposed to be a single object
//either update user every time or throw in a user 

    /**
     * we have to overloaded the method to create a Meal - when the user would want to say how many calories it was: user for importing 
     * since the meal that the user imports won't be calculated based on recipes or ingredients. 
     * @param name
     * @param recipes
     * @param manualCalories
     * @return
     */
    public Meal makeMeal(String name, List<Recipe> recipes, int manualCalories) {
        Meal meal = new Meal(name, recipes, manualCalories);
        return meal;
    }
    
    /**
     * Method #2 -> used when normally executing the program, meals are based on recipeis and ingredients form the db: calories are calculated from here
     * @param name
     * @param recipes
     * @return
     */
    public Meal makeMeal(String name, List<Recipe> recipes) {
        return new Meal(name, recipes, 0); //manaual calories will be 0 in here
    }

    /**
     * Adds a meal to the user's personal history if there's no warnings
     * @param meal The meal the user wants to eat
     * @return A list of warnings
     */
    public List<String> addMeal(Meal meal){
        DailyHistoryModel currentDay = personalHistoryController.getCurrentPersonalHistoryModel().getCurrentDay();

        // The user is warned if they will exceed their daily target of calories by consuming the meal.
        ArrayList<String> warnings = new ArrayList<>();
        boolean calorieSuccess = currentDay.addMeal(meal);
        if (!calorieSuccess) {
            warnings.add("calorie");
        }

        // Ingredients are automatically deducted from the current stock.
        // The user is warned if they try to prepare a meal but the ingredients are not in stock.
        StockDeductorVisitor stockDV = new StockDeductorVisitor(userController.getCurrentUser());
        meal.accept(stockDV);
        if (stockDV.getDeductStatus().equals("warning")) {
            warnings.add("stock");
        }

        return warnings;
    }

    /**
     * Removes a meal from the user's personal history if they don't have the stock of ingredients to prepare it
     * @param meal the meal the user wanted to eat
     */
    public void unaddMeal(Meal meal) {
        DailyHistoryModel currentDay = personalHistoryController.getCurrentPersonalHistoryModel().getCurrentDay();
        currentDay.unaddMeal(meal);
        StockAddVisitor stockAV = new StockAddVisitor(userController.getCurrentUser().getStock());
        meal.accept(stockAV);
    }

    /**
     * Creates a recipe with its name, ingredients, and preparation instructions and adds the recipe to the current user's list
     * @param name The recipe's name
     * @param ingredients The ingredients and amount of each for the recipe
     * @param instructions The preparation instructions for the recipe
     * @return a created recipe
     */
    public Recipe addRecipe(String name, Map<Ingredient, Integer> ingredients, String instructions){
        Recipe recipe = new Recipe(name, ingredients, instructions);
        if (personalHistoryController.getCurrentPersonalHistoryModel() != null) {
            personalHistoryController.getCurrentPersonalHistoryModel().getUser().addRecipe(recipe);
        } else {
            System.out.println("No personal history model");
        }
        return recipe;
    }

    @Override
    public StockMemento createMemento() {
        return new StockMemento(userController.getCurrentUser().getStock().getStock());
    }

    @Override
    public void restoreMemento(Object m) {
        StockMemento memento = (StockMemento) m;
        userController.getCurrentUser().getStock().restore(memento);
    }
}

