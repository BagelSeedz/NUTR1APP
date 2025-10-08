package edu.rit.swen262.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.rit.swen262.persistence.UserHistoryStorage;

/**
 * @author Kendra Patel
 * /**
 * keeps track of a list of DailyHistoryModel instances
 * updates when a new day starts
 * represents the user's entire history, which are made up of multiple daily histories
 * 
*/


@JsonIgnoreProperties({"currentDay"})
 public class PersonalHistoryModel implements Serializable {
    @SuppressWarnings("unused")
    private static final long serial = 1L; 

    private List<DailyHistoryModel> totalDailyHistory;
    private DateTracker dateTracker; 
    private User user;

    //needed for the import to work
    private String username;
    private DateTracker currentDate;
    private double usersUpdatedTargetCalories;
    
    
    public PersonalHistoryModel() {
        //needed for importing
        this.usersUpdatedTargetCalories = 0;
    }



    /**
     * Constructswith a User and DateTracker
     * Initializes the list of daily histories and starts with the first day
    */
    public PersonalHistoryModel(User user, DateTracker dateTracker, String username, DateTracker currentDate){
        this.user = user;
        this.totalDailyHistory = new ArrayList<>();
        this.dateTracker = dateTracker;
        this.username = username;
        this.currentDate = currentDate;
        this.usersUpdatedTargetCalories = 0; 
        DailyHistoryModel firstDay = new DailyHistoryModel(dateTracker.getCurrentDate(), user.getWeight(), user.getTargetCalories());
        totalDailyHistory.add(firstDay);
        save(); 
    }

    

    /**
     * gets the list of all daily histories
     */
    public List<DailyHistoryModel> getTotalDailyHistory() {
        return totalDailyHistory;
    }

    /**
     * @return User 
     */
    public User getUser() {
        return user; 
    }

    public String getUsername(){
        return user.getUsername();
    }

    /**
     * Adds a daily history to the total
     */
    public void addDailyHistory(DailyHistoryModel dailyHistory) {
        this.totalDailyHistory.add(dailyHistory);
        save(); 
    }
    

    /**
     * Starts a new day by advancing the date, creating a new DailyHistoryModel,
     * and adding it to the list of daily histories.
     */
    public void startNewDay(double weight, int targetCalories) {
        dateTracker.goToNextDay();  
        DailyHistoryModel newDay = new DailyHistoryModel(dateTracker.getCurrentDate(), weight, targetCalories);
        totalDailyHistory.add(newDay);
        save(); 
    }
    
    /**
     * @return User's updated calories for the day  
     */
    public double getUsersUpdatedTargetCalories() {
        return user.getTargetCalories();
    }
    
    /**
     * Retrieves the DailyHistoryModel for the current day
     *      which holds data =  workouts, meals, weight, and calories
     * @return
     */
    public DailyHistoryModel getCurrentDay() {      
        int lastDayIndex = totalDailyHistory.size()-1;
        return totalDailyHistory.get(lastDayIndex);
    }

    /**
     * @return Current Date 
     */
    public DateTracker getCurrentDate() {
        return dateTracker; 
    }

    public void save() {
        UserHistoryStorage.saveHistory(this); 
    }
}