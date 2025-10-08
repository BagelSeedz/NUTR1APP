package edu.rit.swen262.model;
import java.util.ArrayList;
import java.util.List;


/*
 * The client in the adapter pattern needs UserDataModel
 * Since the user will be able to inport and export ingredients, recipies, meals and personalhistory, UserDataModel represents this
 * 
 */
public class UserDataModel {
    // private List<Ingredient> ingredients;
    // private List<Recipe> recipes;
    private List<Meal> meals;
    private List<PersonalHistoryModel> personalHistories;


    /**
     * Constructs a UserDataModel with a list of the personalHistories
     * @param personalHistories
     */
    public UserDataModel(List<PersonalHistoryModel> personalHistories) {
        this.personalHistories = personalHistories;
        this.meals = deriveMeals(personalHistories);
    }

    /**
     * since personalhistories also contain the total meals eaten, this method isolates meals and returns the list of meals
     * @param personalhistories
     * @return
     */
    private List<Meal> deriveMeals(List<PersonalHistoryModel> personalhistories){
        List<Meal> allMeals = new ArrayList<>();
        for(PersonalHistoryModel allHistories : personalhistories){
            List<DailyHistoryModel> dailyHistories = allHistories.getTotalDailyHistory();
            for(DailyHistoryModel indivHistories : dailyHistories){
                List<Meal> meals = indivHistories.getMeals();
                allMeals.addAll(meals);
            }
        }
        return allMeals;
    }

    
    public List<Meal> getMeals() {
        return meals;
    }

    public List<PersonalHistoryModel> getPersonalHistories() {
        return personalHistories;
    }

    // public void loadIngredientsFromDatabase() throws IOException {
    //     IngredientDatabase db = new IngredientDatabase();
    //     this.ingredients = new ArrayList<>(db.getAllIngredients());  
    // }
}
