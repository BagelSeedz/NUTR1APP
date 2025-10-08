package edu.rit.swen262.model.GoalInterface;

import java.io.Serializable;

import edu.rit.swen262.model.User;

/**
 * @author Dominick Polakowski 
 */
public class AdaptiveWeight implements Goal, Serializable {
    @SuppressWarnings("unused")
    private static final long serial = 1L; 

    private User user; 
    private int calories; // How many extra calories to add to the user to meet their exersize goals 

    //Default constructor needed for Jackson
    public AdaptiveWeight() { }
    /**
     * AdaptiveWeight Constructor 
     * 
     * @param user
     */
    public AdaptiveWeight(User user, int calories) {
        this.user = user; 
        this.calories = calories; 
    }

    @Override
    public void setCalories() {
        user.setCalories(Goal.super.calculateBMR(user) + calories); 
    }

    /**
     * String representation: "Adaptive Weight"
     */
    @Override
    public String toString() {
        return "Adaptive Weight"; 
    }
}
