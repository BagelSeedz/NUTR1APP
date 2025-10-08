package edu.rit.swen262.model.GoalInterface;

import java.io.Serializable;

import edu.rit.swen262.model.User;

/**
 * @author Dominick Polakowski 
 */
public class GainWeight implements Goal, Serializable {
    @SuppressWarnings("unused")
    private static final long serial = 1L; 


    //Default constructor needed for Jackson
    public GainWeight() { }

    private User user; 

    /**
     * GainWeight Constructor 
     * 
     * @param user
     */
    public GainWeight(User user) {
        this.user = user; 
    }

    @Override
    public void setCalories() {
        user.setCalories(Goal.super.calculateBMR(user) + 500); 
    }

    /**
     * String representation: "Gain Weight"
     */
    @Override
    public String toString() {
        return "Gain Weight"; 
    }
}
