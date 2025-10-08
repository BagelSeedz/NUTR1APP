package edu.rit.swen262.model;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.rit.swen262.model.GoalInterface.GainWeight;
import edu.rit.swen262.model.GoalInterface.Goal;
import edu.rit.swen262.model.GoalInterface.LoseWeight;
import edu.rit.swen262.model.GoalInterface.MaintainWeight;
import edu.rit.swen262.model.UndoClasses.UserMemento;

/**
 * @author Dominick Polakowski 
 */
@JsonIgnoreProperties({"stock"})
public class User implements Serializable, Comparable<User> {
    @SuppressWarnings("unused")
    private static final long serial = 1L; 

    //User Values 
    private final String name; 
    private final LocalDate birthdate; 
    private final Sex sex; 
    private final int height; 
    private double weight; 
    private int hashPass;
    private String username;

    // JSON 
    @SuppressWarnings("unused") 
    private int age; // Added for Jackson 

    // Goal State 
    private Goal goal; 
    private int targetCalories; 
    private double targetWeight; 

    // Food 
    @JsonIgnore
    private Stock stock;
    private ShoppingList shoppingList;
    private List<Recipe> recipes;
    private List<Meal> meals;

    // Teams 
    @JsonIgnore
    private Team team; 
    
    @JsonIgnore
    //@JsonProperty("online")
    private boolean isOnline;
   
    @JsonIgnore 
    private List<Team> invites; 

    
    public static enum Sex {
        Male, 
        Female; 
    }

    /**
     * Needed for Jackson
     */
    public User() {
        //this.id = UUID.randomUUID().toString();
        this.name = ""; 
        this.username = "";
        this.hashPass = 1;
        this.birthdate = LocalDate.now();
        this.sex = Sex.Male; 
        this.height = 0; 
        this.weight = 0.0; 
        this.targetWeight = 0.0;
        this.age = Period.between(birthdate, LocalDate.now()).getYears();  
        this.goal = null;
        this.targetCalories = 0;
        this.shoppingList = new ShoppingList();
        this.stock = new Stock(shoppingList);
        this.recipes = new ArrayList<>();
        this.meals = new ArrayList<>();
        this.invites = new ArrayList<>();
    }

    /**
     * User Constructor 
     * @param name
     * @param username
     * @param birthdate
     * @param sex
     * @param height
     * @param weight
     * @param targetWeight
     * @param password
     */
    public User(String name, String username, LocalDate birthdate, Sex sex, int height, double weight, double targetWeight, String password) {
        this.name = name;
        this.username = username; 
        this.birthdate = birthdate; 
        this.sex = sex; 
        this.height = height; 
        this.weight = weight; 
        this.targetWeight = targetWeight; 
        this.hashPass = password.hashCode();
        //this.hashPass = password.hashCode();
        //System.out.println(hashPass);
        this.goal = null; 
        this.targetCalories = 0;
        this.shoppingList = new ShoppingList();
        this.stock = new Stock(shoppingList);
        this.age = Period.between(birthdate, LocalDate.now()).getYears();  
        this.recipes = new ArrayList<>();
        this.meals = new ArrayList<>();

        this.team = null; 
        this.isOnline = true; 
        this.invites = new ArrayList<>();
    } 

    // @JsonValue
    // public String toJsonKey() {
    //     return this.username;
    // }

    // @JsonCreator
    // public static User fromJsonKey(String key) {
    //     User user = new User();
    //     user.username = key;
    //     return user;
    // }

    /**
     * @return User's username
     */
    public String getUsername() {
        return username; 
    }

    /**
     * @return User's Name 
     */
    public String getName() {
        return name; 
    }

    /**
     * @return User's current Weight 
     */
    public double getWeight() {
        return weight; 
    }

    /**
     * Sets a new weight to the user 
     * @param weight
     */
    public void setWeight(double weight) {
        this.weight = weight; 
    }

    /**
     * @return User's Height 
     */
    public int getHeight() {
        return height; 
    }

    /**
     * @return User's current Age 
     */
    public int getAge() {
        return Period.between(birthdate, LocalDate.now()).getYears(); 
    }

    /**
     * @return User's Sex
     */
    public Sex getSex() {
        return sex; 
    }

    /**
     * @return Shopping List
     */
    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    /**
     * @return User's Daily Target Calories 
     */
    public int getTargetCalories() {
        return targetCalories; 
    }

    /**
     * @return User's Current Goal 
     */
    public Goal getGoal() {
        return goal; 
    }

    /**
     * State changer, gets called whenever the user 
     * reaches a goal, or when the user gets off track 
     * @param goal
     */
    public Goal changeGoal(Goal goal) {
        this.goal = goal;
        this.goal.setCalories();
        return goal; 
    } 

    /**
     * Called whenever a state change occurs, 
     * setting the targetCalories for a user 
     * to a new number from the goal state 
     * @param calories
     */
    public void setCalories(int calories) {
        this.targetCalories = calories; 
    }

    /**
     * Checks if the goal still aligns with the user's weight and target weight 
     * @return A goal for the user 
     */
    public Goal goalCheck() {
        if (weight == targetWeight) 
            return new MaintainWeight(this);
    
        // Stay in MaintainWeight if within +-5 lbs 
        if (goal instanceof MaintainWeight) {
            if (Math.abs(weight - targetWeight) < 5) 
                return goal; 
        }
    
        if (weight > targetWeight) 
            return new LoseWeight(this);
        else 
           return new GainWeight(this);
    }

    /**
     * Set of commands to be executed whenever the user indicates to move onto the next day 
     */
    public int nextDay(double weight) {
        // Set new weight 
        this.weight = weight; 

        // Check if it still aligns with the goal 
        Goal newGoal = this.goalCheck(); 
        if (newGoal != goal) {
            changeGoal(newGoal);
        }

        // Return the new targetCalories to the Personal History 
        return targetCalories; 
    }

    /**
     * @return User's stock 
     */
    @JsonIgnore
    public Stock getStock() {
        return stock;
    }
    
    public List<Recipe> getRecipes(){
        return recipes;
    }

    public void addRecipe(Recipe recipe){
        if(this.recipes.contains(recipe)){
            return;
        }else{
            recipes.add(recipe);
        }
    }

    public List<Meal> getMeals(){
        return meals;
    }

    public void restore(UserMemento memento) {
        changeGoal(memento.getGoal());
        team.setUsers(memento.getTeam().getUsers());
    }
    
    public void addMeal(Meal meal){
        if(this.meals.contains(meal)){
            return;
        }else{
            meals.add(meal);
        }
    }

    @JsonProperty("ingredientStock")
    public void setStock(Stock stock) {
        this.stock = stock;
    }

    /**
     * convert Stock object to format - for importing 
     * ensures stock field is serialized when user obj converted to json
     * @return
     */
    @JsonProperty("stock")
    public Stock getStockForExport() {
        return this.stock;
    }

    
    /**
     * @return User's Team 
     */
    public Team getTeam() {
        return team; 
    }

    /**
     * Changes the user's team to t
     * 
     * @param t
     */
    public void joinTeam(Team t) {
        team = t; 
    }

    /**
     * User leaves the team 
     */
    public void leaveTeam() {
        team = null; 
    }

    /**
     * Invites the user to Team t
     * 
     * @param t 
     */
    public void addInvites(Team t) {
        invites.add(t); 
    }

    /**
     * @return a list of teams the user is invited to join 
     */
    public List<Team> viewInvites() {
        return invites; 
    }

    /**
     * @return User's Online status 
     */
    @JsonIgnore
    public boolean isOnline() {
        return isOnline; 
    }

    /**
     * Changes the user's online status 
     */
    @JsonIgnore
    public void setOnline() {
        isOnline = !isOnline; 
    }

    /**
     * @return Any unread notifications from your team 
     */
    @JsonIgnore
    public List<String> getNotifications() {
        return team.checkNotifications(this); 
    } 
    
    /**
     * Checks if a given password matches with the user's 
     * current password.
     * 
     * @param password
     */
    public boolean checkPassword(String password) {
        return this.hashPass==password.hashCode();
    } 

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false; 
        }

        User other = (User) o; 

        return username.equals(other.username); 
    } 

    @Override 
    public String toString() {
        return name; 
    }

    @Override 
    public int compareTo(User other) {
        return username.compareTo(other.getUsername()); 
    }

    public int getHashPass() {
        return hashPass;
    }
}