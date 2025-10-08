package edu.rit.swen262.model.GoalInterface;

import java.io.Serializable;

import edu.rit.swen262.model.User;

/**
 * @author Dominick Polakowski 
 */
public class MaintainWeight implements Goal, Serializable {
    @SuppressWarnings("unused")
    private static final long serial = 1L; 

    private User user; 

    //Default constructor needed for Jackson
    public MaintainWeight() { }

    /**
     * MaintainWeight Constructor 
     * 
     * @param User 
     */
    public MaintainWeight(User user) {
        this.user = user; 
    }

    @Override
    public void setCalories() {
        user.setCalories(Goal.super.calculateBMR(user)); 
    }

    /**
     * String representation: "Maintain Weight"
     */
    @Override
    public String toString() {
        return "Maintain Weight"; 
    }
}