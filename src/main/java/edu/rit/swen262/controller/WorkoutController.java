package edu.rit.swen262.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.rit.swen262.model.DailyHistoryModel;
import edu.rit.swen262.model.PersonalHistoryModel;
import edu.rit.swen262.model.User;
import edu.rit.swen262.model.WorkoutModel;
import edu.rit.swen262.model.WorkoutInterfaces.IntensityModel;  

/**
 * @author Kendra Patel
 * 
 * Controller for workouts 
 */

public class WorkoutController {
    private UserController userController; 
    private PersonalHistoryController personalHistoryController;
    
    public WorkoutController(UserController userController, PersonalHistoryController personalHistoryController) {
        this.userController = userController;
        this.personalHistoryController = personalHistoryController;
    }

    /**
     * Adds a workout to the current day's history
     * currentDay: stores the DailyHistoryModel for today
     * 
     * @param minutes the duration  
     * @param intensityLevel the intensity level  
     * 
     * @return the calories burned during the workout
     */
    public int addWorkout(int minutes, IntensityModel intensityLevel) {
        User currentUser = userController.getCurrentUser();
        Map<String, PersonalHistoryModel> allPersonalHistories = personalHistoryController.getAllPersonalHistories(); 

        PersonalHistoryModel currentUserHistory = null;
        for (PersonalHistoryModel history : allPersonalHistories.values()) {
            if (history.getUser().getName().equals(currentUser.getName())) {
                currentUserHistory = history;
                break;
            }
        }

        DailyHistoryModel currentDay = currentUserHistory.getCurrentDay();
        WorkoutModel workout = new WorkoutModel(minutes, intensityLevel, currentDay.getDate());
        currentDay.addWorkout(workout); 
        return workout.calculateCaloriesBurned(); 
    }

    /**
     * @return All previous workouts logged by the User user 
     */
    public List<WorkoutModel> getPreviousWorkouts(User user) {
        List<WorkoutModel> allWorkouts = new ArrayList<>();
    
        for (PersonalHistoryModel personalHistory : personalHistoryController.getAllPersonalHistories().values()) {
            if (personalHistory.getUser().equals(user)) { 
                for (DailyHistoryModel day : personalHistory.getTotalDailyHistory()) {
                    allWorkouts.addAll(day.getWorkouts());
                }
            }
        }
        return allWorkouts;
    }
}