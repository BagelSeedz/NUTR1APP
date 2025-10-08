package edu.rit.swen262.ui;

import java.util.List;
import java.util.Scanner;

import edu.rit.swen262.controller.PersonalHistoryController;
import edu.rit.swen262.controller.TeamController;
import edu.rit.swen262.controller.UndoCaretaker;
import edu.rit.swen262.controller.UserController;
import edu.rit.swen262.model.DailyHistoryModel;
import edu.rit.swen262.model.WorkoutModel;
import edu.rit.swen262.model.DateTracker;
import edu.rit.swen262.model.Meal;

/**
 * @author Kendra Patel
 * CHANGE DIALYHSIOTYRCONTROLLER - PERSONAL SINCE WE ALREAYD HAVE THE CURRENT IN THE PERSONAL 
 */

public class HistoryView {
    private Scanner scanner;
    private PersonalHistoryController personalHistoryController;
    private UserController userController; 
    private TeamController tc; 
    private UndoCaretaker undoCaretaker;
    private DateTracker date; 

    public HistoryView(PersonalHistoryController personalHistoryController, UserController userController, TeamController tc, DateTracker date, UndoCaretaker undoCaretaker) {
        this.scanner = new Scanner(System.in);
        this.personalHistoryController = personalHistoryController;
        this.userController = userController; 
        this.tc = tc; 
        this.undoCaretaker = undoCaretaker;
        this.date = date; 

    }
  
    public void showMenu() {
        while (true) {
            System.out.println("\nHistory Tracker\n");
            System.out.println("1. View Today's History with full details");
            System.out.println("2. View Total History with full details");

            System.out.println("3. View Past Weights");
            System.out.println("4. View Past Calories Consumed vs Target");
            System.out.println("5. View Past Meals");
            System.out.println("6. View Past Workouts");

            System.out.println("7. Exit");
            
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1:
                    viewDayHistory();
                    break;
                case 2:
                    viewTotalHistory();
                    break;
                case 3:
                    viewPastWeights();
                    break;
                case 4:
                    viewCaloriesVsTarget();
                    break;
                case 5:
                    viewPastMeals();
                    break;
                case 6:
                    viewPastWorkouts();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Enter a correct choice.");
            }
        }
    }

    public void viewDayHistory(){
        System.out.println("Today's Weight: " + personalHistoryController.getWeight());
        System.out.println("Calories Consumed Today: " + personalHistoryController.getCaloriesConsumed());
        System.out.println("Target Calories Today: " + personalHistoryController.getTargetCalories());
        System.out.println("Meals Consumed Today: " + personalHistoryController.getMeals());
        System.out.println("Workouts Completed Today: " + personalHistoryController.getWorkouts());
    }

    private void viewTotalHistory() {
        List<DailyHistoryModel> totalHistory = personalHistoryController.getTotalHistory();

        if (totalHistory.isEmpty()) {
            System.out.println("No total history available");

        } else {
            for (DailyHistoryModel dailyHistory : totalHistory) {
                System.out.println("Date: " + dailyHistory.getDate() + ", Weight: " + dailyHistory.getWeight() +
                        ", Calories: " + dailyHistory.getCaloriesConsumed() +
                        "/" + dailyHistory.getTargetCalories() +
                        ", Meals: " + dailyHistory.getMeals() +
                        ", Workouts: " + dailyHistory.getWorkouts());
            }
        }
    }

    private void viewPastWeights() {
        List<DailyHistoryModel> history = personalHistoryController.getTotalHistory();
        if (history.isEmpty()) {
            System.out.println("No past weights available");

        } else {
            System.out.println("Past Weights:");
            for (DailyHistoryModel day : history) {
                System.out.println(day.getWeight() + ", ");
            }
        }
    }

    private void viewCaloriesVsTarget() {
        List<DailyHistoryModel> history = personalHistoryController.getTotalHistory();
        if (history.isEmpty()) {
            System.out.println("No calorie vs target data available");
            
        } else {
            System.out.println("Calories Consumed vs Target:");
            for (DailyHistoryModel day : history) {
                System.out.println("Consumed: " + day.getCaloriesConsumed() + " / Target: " + day.getTargetCalories() + ", ");
            }
        }
    }

    private void viewPastMeals() {
        List<DailyHistoryModel> history = personalHistoryController.getTotalHistory();
        if (history.isEmpty()) {
            System.out.println("No past meals available");
        } else {
            System.out.println("Past Meals:");
            for (DailyHistoryModel day : history) {
                for (Meal meal : day.getMeals()) {
                    System.out.println("- " + meal.getName());
                }
            }
        }
    }

    private void viewPastWorkouts() {
        List<DailyHistoryModel> history = personalHistoryController.getTotalHistory();
        if (history.isEmpty()) {
            System.out.println("No past workouts available");
        } else {
            System.out.println("Past Workouts:");
            for (DailyHistoryModel day : history) {
                for (WorkoutModel workout : day.getWorkouts()) {
                    System.out.println(workout + ", " );
                }
            }
        }
    }

    public void newDay() {
        System.out.println("\nNew Day - " + date.getCurrentDate());
        System.out.print("Enter your weight: ");
        double newWeight = scanner.nextDouble();
        
        undoCaretaker.storeDaily(); // Store daily history before changing it so it an be undone
        int updatedTargetCalories = personalHistoryController.moveToNextDay(newWeight); 
        System.out.println("Target Calories: " + updatedTargetCalories + "\nGoal: " + userController.getCurrentUser().getGoal());

        tc.checkChallenges(); 
        return; 
    }
}