package edu.rit.swen262.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.rit.swen262.model.UndoClasses.DailyHistoryMemento;

/**
 * @author Kendra Patel
 * Represents a single day's history
 */

public class DailyHistoryModel implements Serializable {
    @SuppressWarnings("unused")
    private static final long serial = 1L; 

    private LocalDate date;  
    private double weight;   
    private int targetCalories; 
    private int caloriesConsumed;  
    private List<Meal> meals; 
    private List<WorkoutModel> workouts; 

    //needed for not throwing errors on the import
    private String mostCommonIntensity;

    public DailyHistoryModel() {
        // Needed for Jackson deserialization
    }

    public DailyHistoryModel(LocalDate date, double weight, int targetCalories) {
        this.date = date;
        this.weight = weight;
        this.targetCalories = targetCalories;
        this.caloriesConsumed = 0; 
        this.meals = new ArrayList<>();
        this.workouts = new ArrayList<>(); 
        this.mostCommonIntensity = "Low";
    }

    

    /**
     * Gets the date of the day
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the weight of the day
     * @return the weight
     */
    public double getWeight(){
        return weight;
    }

    /**
     * Gets the calories consumed on the day
     * @return the calories consumed
     */
    public double getCaloriesConsumed(){
        return caloriesConsumed;
    }

    public void setCaloriesConsumed(int caloriesConsumed) {
        this.caloriesConsumed = caloriesConsumed;
    }

    /**
     * Gets the target calories for the day
     * @return the target calories
     */
    public double getTargetCalories(){
        return targetCalories;
    }

    /**
     * Gets the meals that were consumed on the day
     * @return the meals
     */
    public List<Meal> getMeals(){
        return meals;
    }

    /**
     * Gets the workouts that were completed on the day
     * @return the workouts
     */
    public List<WorkoutModel> getWorkouts() {
        return workouts;
    }

    /**
     * Adds a meal to the day's history
     * @param meal the meal to add
     */
    public boolean addMeal(Meal meal) {
        meals.add(meal);
        caloriesConsumed += meal.getCalories();
        suggestExercise();

        if (caloriesConsumed>targetCalories){
            return false;
        }

        return true;
    }

    /**
     * Unadds a meal from the day's history
     * @param meal the meal to remove
     */
    public void unaddMeal(Meal meal) {
        if (meals.remove(meal)) { // Remove and check if successful
            caloriesConsumed -= meal.getCalories();
        } else {
            System.out.println("Meal not found in the list!"); // Optional debug message
        }
    }

    /**
     * Adds a workout to the day's history
     * @param workout the workout to add
     */
    public void addWorkout(WorkoutModel workout) {
        workouts.add(workout);
        double caloriesBurned = workout.calculateCaloriesBurned();
        caloriesConsumed -= caloriesBurned;
        suggestExercise(); 
    }

    /**
     * Gets the most common intensity level from the workouts that were completed
     */
    public String getMostCommonIntensity() {
        int lowIntensityCount = 0, mediumIntensityCount = 0, highIntensityCount = 0;
        
        for (WorkoutModel workout : workouts) {
            String intensity = workout.getIntensityLevel();
            if (intensity.equals("Low")) {
                lowIntensityCount++;
            } else if (intensity.equals("Medium")) {
                mediumIntensityCount++;
            } else if (intensity.equals("High")) {
                highIntensityCount++;
            }
        }        
        if (mediumIntensityCount > lowIntensityCount && mediumIntensityCount > highIntensityCount) {
            return "Medium";
        } else if (highIntensityCount > lowIntensityCount && highIntensityCount > mediumIntensityCount) {
            return "High";
        } else {
            return "Low";   
        }
    }

    /**
     * Calculates the minutes needed to burn the excess calories based on intensity that is given
     */
    public int calculateMinutesForExcess(double excessCalories, String intensity) {
        double caloriesPerMinute = caloriesBurnedPerMinute(intensity);
        return (int) (excessCalories / caloriesPerMinute);  
    }

    /**
     * Calculates the calories burned per minute based on the given intensity level
     */
    public double caloriesBurnedPerMinute(String intensity) {
        switch (intensity) {
            case "Low":
                return 5;  
            case "Medium":
                return 8;   
            case "High":
                return 12;  
            default:
                return 0;
        }
    }

    /**
     * Suggests exercise based on excess calories
     */
    public void suggestExercise() {
        double excessCalories = caloriesConsumed - targetCalories;
        
        if (excessCalories > 0) {
            String frequentIntensity = getMostCommonIntensity();
            
            int minutesToBurn = calculateMinutesForExcess(excessCalories, frequentIntensity);
            
            System.out.println("Number of excess calories: " + excessCalories + " Complete " + minutesToBurn + " minutes of " + frequentIntensity + " intensity exercise");
        } else {
            System.out.println("You have not exceeded your calorie target today");
        }
    }

    public void restore(DailyHistoryMemento memento) {
        this.weight = memento.getWeight();
    }
}