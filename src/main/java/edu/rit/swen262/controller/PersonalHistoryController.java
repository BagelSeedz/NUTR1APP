package edu.rit.swen262.controller;
import edu.rit.swen262.model.PersonalHistoryModel;
import edu.rit.swen262.model.Recipe;
import edu.rit.swen262.model.Stock;
import edu.rit.swen262.model.WorkoutModel;
import edu.rit.swen262.model.UndoClasses.DailyHistoryMemento;
import edu.rit.swen262.model.DailyHistoryModel;
import edu.rit.swen262.model.FormatAdapter;
import edu.rit.swen262.model.Meal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Kendra Patel
 * keeps track of the DailyHistory instances
 */

public class PersonalHistoryController implements Originator {
    private PersonalHistoryModel currentpersonalHistoryModel; //CURRENT PERSONALMODEL AND LIST OF EVERY PERSONALHISTORYMODEL - KNOWS ABOUT EVERY ONE OF THEM
    private Map<String, PersonalHistoryModel> personalHistories; //map of hashmap usernsame and personalhistory
    private FormatAdapter formatAdapter;  // The adapter for export/import operations



    //Constructor
    public PersonalHistoryController(FormatAdapter formatAdapter) {
        this.currentpersonalHistoryModel = null;
        this.personalHistories = new HashMap<>();
        this.formatAdapter = formatAdapter;
    }

    
   
    

    /**
     * want to set current personal history model to @param personalHistoryModel  
     * stores it in the map of personalhistories
     */
    public void setCurrentPersonalHistoryModel(PersonalHistoryModel personalHistoryModel) {
        this.currentpersonalHistoryModel = personalHistoryModel;
        //user's ID = key  
        personalHistories.put(personalHistoryModel.getUser().getUsername(), personalHistoryModel);
    }

    public PersonalHistoryModel getCurrentPersonalHistoryModel(){
        return currentpersonalHistoryModel;
    }
    /**
     * Imports personal history to json file: personalhistoryimport.json
     * checks if there is a current personal history model - valid user
     * get the current user that is loggin: username - for the folder to import it 
     * import the data to the JSON file - adapter pattern: desreializes it
     * if it was a success then it updates with the new personalhistory model
     * 
     */
    public void importPersonalHistory() {
        if (currentpersonalHistoryModel != null) {
            String username = currentpersonalHistoryModel.getUsername();

            PersonalHistoryModel importedHistory = formatAdapter.importDataWithClass(username, PersonalHistoryModel.class, "personalhistoryimport.json");
            
            if (importedHistory != null) {
                setCurrentPersonalHistoryModel(importedHistory);
                System.out.println("Personal history imported successfully for " + username);
            }
        }
    }

    public void importPersonalHistory(String username) {
        PersonalHistoryModel importedHistory = formatAdapter.importDataWithClass(username, PersonalHistoryModel.class, "personalhistoryimport.json");
        if (importedHistory != null) {
            setCurrentPersonalHistoryModel(importedHistory);
            System.out.println("Personal history imported successfully for " + username);
        }
    }

    /**
     * Exports Personal History to JSON file: personalhistoryexport.json
     * checks if there is a current personal history model - valid user
     * get the current user that is loggin: username - for the folder to export it 
     * export the data to the JSON file - adapter pattern: seralizes it
     */
    public void exportPersonalHistory() {
        if (currentpersonalHistoryModel != null) {
            String username = currentpersonalHistoryModel.getUsername();

            formatAdapter.exportData(username, currentpersonalHistoryModel, "personalhistoryexport.json");
            System.out.println("Personal history exported successfully for " + username);
        }
    }
    

    /**
     * Imports meal from JSON
     */
    public void importMeals() {
        //first check if the current personal history model exits 
        if (currentpersonalHistoryModel != null) {
            //get the username from whoever is loggin
            String username = currentpersonalHistoryModel.getUsername();
            
            //for importing - its a map with the string as the name and the meal object as the value 
            // Import the meals as a map from the JSON file.
            HashMap<String, Meal> importedMealMap = formatAdapter.importDataWithTypeReference(username, new com.fasterxml.jackson.core.type.TypeReference<HashMap<String, Meal>>() {}, "meals.json");
            
            //now check if it was a success and if the personalhistorymodel isn't null
            if (importedMealMap != null && currentpersonalHistoryModel != null) {
                //Get a list of the meals of the current day 
                List<Meal> currentMeals = currentpersonalHistoryModel.getCurrentDay().getMeals();
                Collection<Meal> values = importedMealMap.values();
                currentMeals.addAll(values);
                
                //now recalculate the total calories consumed by all meals in the list
                int updatedCalories = 0;
                for (Meal meal : currentMeals) {
                    updatedCalories += meal.getCalories();   
                }
                //Updates the currents
                currentpersonalHistoryModel.getCurrentDay().setCaloriesConsumed(updatedCalories);
                System.out.println("Meals imported successfully for " + username);
            }
        }
    }


    /**
     * Exports the meals from the current days history
     * The list of meals = converted into a Map with the key as the name and value is the meal 
     */
    public void exportMeals() {
        if (currentpersonalHistoryModel != null) {
            String username = currentpersonalHistoryModel.getUsername();
            //list of meals for the current day
            List<Meal> mealList = currentpersonalHistoryModel.getCurrentDay().getMeals();

            //empty map to hold meals keyed by their name
            Map<String, Meal> mealMap = new HashMap<>();

            //Iterate over the list of meals - for each meal - get name (key) and value is the meal
            for (Meal meal : mealList) {
                String key = meal.getName();
                mealMap.put(key, meal);
            }
            formatAdapter.exportData(username, mealMap, "meals.json");
            System.out.println("Meals exported successfully for " + username);
        }
    }


     /**
     * Imports recipe from JSON
     * checks if there is a current personal history model - valid user
     * import the data to the JSON file - adapter pattern: desreializes it
     * if it was a success then  
     */
    public void importRecipes() {
        if (currentpersonalHistoryModel != null) {
            String username = currentpersonalHistoryModel.getUsername();
            
            //Imports the recipes from the JSON file as a list
            List<Recipe> importedRecipeList = formatAdapter.importDataWithTypeReference(username, new com.fasterxml.jackson.core.type.TypeReference<List<Recipe>>() {}, "recipe.json");
            
            //if success - get the current list of recipies in the user
            if (importedRecipeList != null) {
                List<Recipe> currentUserRecipes = currentpersonalHistoryModel.getUser().getRecipes();
                
                //now add the recipes that are imported to the list of the users recipes
                for (Recipe recipe : importedRecipeList) {
                    if (!currentUserRecipes.contains(recipe)) {
                        currentUserRecipes.add(recipe);
                        System.out.println("Recipes imported successfully for " + username);
                    }
                }
            }
        }
    }


    /**
     * Exports the recipes from the current personalhistorymodel to JSON file: recipe.json
     * checks if there is a current personal history model - valid user
     * get the current user that is loggin: username - for the folder to export it 
    */
    public void exportRecipes() {
        if (currentpersonalHistoryModel != null) {
            String username = currentpersonalHistoryModel.getUsername();
            
            //get the list of recipes for the user
            List<Recipe> recipeList = currentpersonalHistoryModel.getUser().getRecipes();

            //export this list  
            formatAdapter.exportData(username, recipeList, "recipe.json");
            System.out.println("Recipes exported successfully for " + username);
        }
    }


    /**
     * imports the stock to a JSON file: stock.json
     * checks if there is a current personal history model - valid user
     * get the current user that is loggin: username - for the folder to export it 
     */
    public void importStock() {
        if (currentpersonalHistoryModel != null) {
            String username = currentpersonalHistoryModel.getUsername();
            
            //imports the stock from the file: stock.json  
            Stock importedStock = formatAdapter.importDataWithClass(username, Stock.class, "stock.json");
            
            //if success = updates the stock in the user 
            if (importedStock != null) {
                currentpersonalHistoryModel.getUser().setStock(importedStock);
                System.out.println("Sock imported successfully for " + username);
            }
        }
    }


    /**
     * Exports the stock to a JSON file: stock.json
     * checks if there is a current personal history model - valid user
     * get the current user that is loggin: username - for the folder to export it 
     * 
     */
    public void exportStock() {
        if (currentpersonalHistoryModel != null) {
            String username = currentpersonalHistoryModel.getUsername();

            //get the stock to export 
            Stock stockToExport = currentpersonalHistoryModel.getUser().getStockForExport();
            //export
            formatAdapter.exportData(username, stockToExport, "stock.json");
            System.out.println("Stock exported successfully for " + username);
        }
    }


    /**
     * Moves to the next day
     * Updates the user's weight and target calories
     * @return the updated target calories
     */
    public int moveToNextDay(double newWeight) {
        int targetCalories = currentpersonalHistoryModel.getUser().nextDay(newWeight); 
        currentpersonalHistoryModel.startNewDay(newWeight, targetCalories); 
        return targetCalories; 
    }

    /**
     * gets the total history of daily histories
     * @return a list of all daily histories
     */
    public List<DailyHistoryModel> getTotalHistory() {
        return currentpersonalHistoryModel.getTotalDailyHistory();  
    }

    public double getUsersUpdatedTargetCalories() {
        return currentpersonalHistoryModel.getUsersUpdatedTargetCalories();
    }

    public double getWeight() {
        return currentpersonalHistoryModel.getCurrentDay().getWeight();
    }

    public double getCaloriesConsumed() {
        return currentpersonalHistoryModel.getCurrentDay().getCaloriesConsumed();
    }

    public double getTargetCalories() {
        return currentpersonalHistoryModel.getCurrentDay().getTargetCalories();
    }

    public List<Meal> getMeals() {
        return currentpersonalHistoryModel.getCurrentDay().getMeals();
    }

    public List<WorkoutModel> getWorkouts() {
        return currentpersonalHistoryModel.getCurrentDay().getWorkouts();
    }

    public DailyHistoryMemento createMemento() {
        return new DailyHistoryMemento(getWeight());
    }

    @Override
    public void restoreMemento(Object m) {
        DailyHistoryMemento memento = (DailyHistoryMemento) m;
        currentpersonalHistoryModel.getCurrentDay().restore(memento);
    }

    public Map<String, PersonalHistoryModel> getAllPersonalHistories(){
        return personalHistories;
    }
}

    






