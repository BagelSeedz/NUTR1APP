package edu.rit.swen262.ui;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import edu.rit.swen262.model.IngredientDatabase;
import edu.rit.swen262.model.Meal;
import edu.rit.swen262.controller.FoodController;
import edu.rit.swen262.controller.UndoCaretaker;
import edu.rit.swen262.controller.UserController;
import edu.rit.swen262.model.Ingredient;
import edu.rit.swen262.model.Recipe;

/**
 * @author Emily Santillanes 
 * 
 * NOTE: This does not support multiple users if you are using a single user in the 
 * constructor. Use User controller everytime you need to modify a user's data to 
 * support multiple and different users being logged in to the system. 
 */
public class FoodView {
    private FoodController foodController;
    private UndoCaretaker undoCaretaker;
    private final Scanner scanner;
    private final IngredientDatabase ingredientDatabase;
    private final UserController userController;

    /**
     * Creates a food view with the ingredient database, the food controller, and the user controller
     * @param ingredientDatabase
     * @param foodController
     * @param userController
     */
    public FoodView(IngredientDatabase ingredientDatabase, FoodController foodController, UserController userController, UndoCaretaker undoCaretaker) {
        this.ingredientDatabase = ingredientDatabase;
        this.undoCaretaker = undoCaretaker;
        this.foodController = foodController;
        this.userController = userController;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Shows a menu for the food view
     * Users have the option to add a meal, use an existing meal, or exit
     */
    public void showMenu(){
        while(true){
            System.out.println("\nFood Organizer");
            System.out.println("1. Add new meal");
            System.out.println("2. Use an existing meal");
            System.out.println("3. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1:
                    addMeal();
                    break;
                case 2:
                    useExistingMeal();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Enter a correct choice");
            }
        }
    }

    /**
     * Users have the option to use an existing meal from their history
     * This method lets the users look through a list of their previous meals and choose one to prepare
     */
    public void useExistingMeal(){
        System.out.println("Here is a list of your previous meals:");
        List<Meal> previousMeals = userController.getCurrentUser().getMeals();
        for(int x = 1; x<previousMeals.size(); x++){
            System.out.println(x + " " + previousMeals.get(x-1).getName());
        }
        System.out.println("Enter the number associated with the meal or \"exit\" to return");
        System.out.println("Enter your choice: ");
        String choice = scanner.nextLine();
    
        if(choice.equalsIgnoreCase("exit")){
            return;
        }

        Meal chosenMeal = previousMeals.get(Integer.valueOf(choice)-1);
        System.out.println("You have chosen " + chosenMeal.getName());

        System.out.println("1. Begin Preparation");
        System.out.println("2. Exit");
        int chosen = scanner.nextInt();
        switch(chosen){
            case 1:
                prepareMeal(chosenMeal.getName(), chosenMeal.children());
                break;
            case 2:
                return;
            default:
                System.out.println("Enter a correct choice");
        }
    }
    
    /**
     * This function lets the user create a meal
     * They can add new recipes or choose from existing ones to add to their meal
     */
    private void addMeal(){
        //The app helps users to prepare meals when the user chooses recipes to combine. 
        System.out.println("Enter the name of the meal");
        String name = scanner.nextLine();
        List<Recipe> recipes = new ArrayList<>();

        while(true){
            System.out.println("1 Add new recipe");
            System.out.println("2 Add an existing recipe");
            System.out.println("3 Begin preparation");
            System.out.println("4 Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice){
                case 1:
                    addRecipe(recipes);
                    break;
                case 2:
                    viewExistingRecipes(recipes);
                    break;
                case 3:
                    prepareMeal(name, recipes);
                    return;
                case 4:
                    return;
                default:
                    System.out.println("Enter a correct choice");
            }
        }
    }

    /**
     * This method lets users view their existing recipes and choose one to add to a meal
     * @param recipes The list of recipes included in the meal
     */
    public void viewExistingRecipes(List<Recipe> recipes){
        System.out.println("Here is a list of your previous recipes: ");
        List<Recipe> previousRecipes = userController.getCurrentUser().getRecipes();
        for(int x = 1; x<previousRecipes.size(); x++){
            System.out.println(x + " " + previousRecipes.get(x-1).getName());
        }
        System.out.println("Enter the number associated with the recipe or \"exit\" to return");
        System.out.println("Enter your choice: ");
        String choice = scanner.nextLine();
    
        if(choice.equalsIgnoreCase("exit")){
            return;
        }

        Recipe chosenRecipe = previousRecipes.get(Integer.valueOf(choice)-1);
        System.out.println("You have chosen " + chosenRecipe.getName());

        recipes.add(chosenRecipe);
        return;

    }

    /**
     * This method lets the user prepare their meal
     * If there are no warnings, the user is given the instructions for making the meal
     * @param name The name of the meal
     * @param recipes The list of recipes included in the meal
     */
    private void prepareMeal(String name, List<Recipe> recipes){
        // The user is warned if they will exceed their daily target of calories by consuming the meal.
        // The user is warned if they try to prepare a meal but the ingredients are not in stock.
        undoCaretaker.storeStock(); // Store stock before changing it to undo later
        Meal meal = foodController.makeMeal(name, recipes);
        List<String> warnings = foodController.addMeal(meal);
        userController.getCurrentUser().addMeal(meal);
        for (String warning : warnings){
            if (warning=="stock") {
                System.out.println("Ingredient(s) needed out of stock. Cancelling preparation.");
                foodController.unaddMeal(meal);
                undoCaretaker.undo(); // Undo adding the stock that we just did
                return;
            } else if (warning=="calorie") {
                System.out.println("This meal has put you over your limit.");
            }
        }

        //The user is guided through the steps to prepare each recipe.
        for (Recipe r : recipes) {
            System.out.print(r.getInstructions() + '\n');
        }
    }

    /**
     * Users can add a recipe to their meal by entering the name and instructions and choosing ingredients to incorporate
     * @param recipes The list of recipes included in the meal
     */
    private void addRecipe(List<Recipe> recipes){
        System.out.println("Enter the name of the recipe: ");
        String name = scanner.nextLine();
        System.out.println("Enter the instructions for the recipe: ");
        String instructions = scanner.nextLine();
        Map<Ingredient, Integer> ingredients = new HashMap<>();
        while(true){
            System.out.println("1. Enter ingredient");
            System.out.println("2. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice){
                case 1:
                    Ingredient ingredient = addIngredient();
                    System.out.println("Enter the amount of that ingredient");
                    int amntNeeded = scanner.nextInt();
                    scanner.nextLine();
                    ingredients.put(ingredient, amntNeeded);
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Enter a correct choice");
            }

            Recipe recipe = foodController.addRecipe(name, ingredients, instructions);
            recipes.add(recipe);
            return;
        }   
    }

    /**
     * The user can add ingredients to their recipes
     * This method lets them look through the list of ingredients that matches the name of the ingredient they enter
     * They can then enter the amount of the ingredient needed for the recipe
     */
    private Ingredient addIngredient(){
        System.out.println("Enter the name of the ingredient");
        String name = scanner.nextLine();
        HashMap<String, Ingredient> searchedIngredients = ingredientDatabase.search(name);
        System.out.println("Here are the ingredients that match that name: ");
        int count=1;
        List<String> ingredientList = new ArrayList<>();
        for(String ingredientName: searchedIngredients.keySet()){
            System.out.println(count + " " + ingredientName);
            ingredientList.add(ingredientName);
            count++;
        }
        System.out.println("Enter the number of the ingredient you want: ");
        int ingNum = scanner.nextInt() - 1;
        scanner.nextLine();
        return searchedIngredients.get(ingredientList.get(ingNum));
    }
}