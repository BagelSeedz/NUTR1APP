package edu.rit.swen262.controller;


import java.util.List;

import edu.rit.swen262.model.Meal;
import edu.rit.swen262.model.PersonalHistoryModel;
import edu.rit.swen262.model.WorkoutModel;


/**
 * @author Kendra Patel
 */
public class DailyHistoryController {
    private PersonalHistoryModel personalHistoryModel;

    public DailyHistoryController(PersonalHistoryModel personalHistoryModel) {
        this.personalHistoryModel = personalHistoryModel;
    }

    public double getWeight() {
        return personalHistoryModel.getCurrentDay().getWeight();
    }

    public double getCaloriesConsumed() {
        return personalHistoryModel.getCurrentDay().getCaloriesConsumed();
    }

    public double getTargetCalories() {
        return personalHistoryModel.getCurrentDay().getTargetCalories();
    }

    public List<Meal> getMeals() {
        return personalHistoryModel.getCurrentDay().getMeals();
    }

    public List<WorkoutModel> getWorkouts() {
        return personalHistoryModel.getCurrentDay().getWorkouts();
    }

}
