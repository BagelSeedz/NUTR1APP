package edu.rit.swen262.model;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Dominick Polakowski 
 */
public class Challenge {
    private LocalDate end; 
    private DateTracker dateTracker; 

    private Map<User, Integer> stats; 

    public Challenge(Map<User, Integer> stats, DateTracker dateTracker) {
        this.end = dateTracker.getCurrentDate().plusDays(7); 
        this.dateTracker = dateTracker; 
        this.stats = stats; 
    }

    /**
     * @return Stats for the current Challenge 
     */
    public Map<User, Integer> getStats() {
        return stats; 
    }

    /**
     * @return Gets the end date for the challenge 
     */
    public LocalDate getEndDate() {
         return end; 
    }

    /**
     * Adds the duration of Workout wc to the 
     * User user's spot in the stats map 
     * 
     * @param user
     * @param wc
     */
    public void addWorkout(User user, WorkoutModel wc) {
        // Make it so that you find the user and add the minutes from the workout to the key 
        int currentMinutes = stats.get(user); 
        currentMinutes += wc.getMinutes(); 
        stats.put(user, currentMinutes); 
    }

    /**
     * Checks to see if the challenge is over 
     * 
     * @return A Map of users ranked in order of 
     *         who particapated the most in the challenge 
     */
    public boolean newDay() {
        if (end.isEqual(dateTracker.getCurrentDate())) {
            return true; 
        } else {
            return false; 
        }
    }
}