package edu.rit.swen262.model.GoalInterface;

import java.io.Serializable;

import edu.rit.swen262.model.User;

/**
 * @author Dominick Polakowski 
 */
public class LoseWeight implements Goal, Serializable {
    @SuppressWarnings("unused")
    private static final long serial = 1L; 

    private User user; 

    //Default constructor needed for Jackson
    public LoseWeight() { }

    /**
     * LoseWeight Constructor 
     * 
     * @param user
     */
    public LoseWeight(User user) {
        this.user = user; 
    }

    @Override
    public void setCalories() {
        user.setCalories(Goal.super.calculateBMR(user) - 500); 
    }

    /**
     * String representation: "Lose Weight"
     */
    @Override
    public String toString() {
        return "Lose Weight"; 
    }
}
