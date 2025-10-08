package edu.rit.swen262.ui;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import edu.rit.swen262.controller.UserController;
import edu.rit.swen262.controller.WorkoutController;
import edu.rit.swen262.model.WorkoutModel;
import edu.rit.swen262.model.WorkoutInterfaces.HighIntensity;
import edu.rit.swen262.model.WorkoutInterfaces.IntensityModel;
import edu.rit.swen262.model.WorkoutInterfaces.LowIntensity;
import edu.rit.swen262.model.WorkoutInterfaces.MediumIntensity;


/**
 * @author Kendra Patel
 * ui view for Workouts 
 */
public class WorkoutView {
    
    private WorkoutController workoutController;
    private UserController uc; 
    private Scanner scanner;

    public WorkoutView(WorkoutController workoutController, UserController uc) {
        this.workoutController = workoutController;
        this.uc = uc; 
        this.scanner = new Scanner(System.in);
    }

    /**
     * Menu for workout
     */
    public void showMenu() {
        while (true) {
            System.out.println("\nWorkout Tracker\n");
            System.out.println("1. Add Workout");
            System.out.println("2. View Workout History");
            System.out.println("3. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1:
                    addWorkout();
                    break;
                case 2:
                    showWorkoutHistory();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Enter 1, 2, or 3");
            }
        }
    }

    /**
     * Asks the user for their workout details and adds it for the workout 
     */
    private void addWorkout() {
        System.out.print("\nHow many minutes was your workout: ");
        int minutes = 0; 
        String text = null; 
        while (minutes <= 0) {
            try {
                text = scanner.nextLine(); 
                minutes = Integer.parseInt(text); 
                if (minutes <= 0) 
                    System.out.print("Enter the duration of your workout in minutes: ");
            } catch (Exception e) {
                System.out.print("Enter the duration of your workout in minutes: ");
            }
        }

        System.out.print("Enter the intensity of your workout (low, medium, high): ");
        IntensityModel intensity = null;

        while (intensity == null) {
            switch (scanner.nextLine().toLowerCase()) {
                case "low": 
                    intensity = new LowIntensity(); 
                    break; 
                case "medium": 
                    intensity = new MediumIntensity(); 
                    break; 
                case "high": 
                    intensity = new HighIntensity(); 
                    break; 
                default: 
                    System.out.print("Enter low, medium, or high for the intensity of your workout: ");
            }
        }

        System.out.println("Calories Burned: " + workoutController.addWorkout(minutes, intensity));  
        System.out.println("Workout added to history!");
    }
    
    /**
     * Displays the workout history
     */
    private void showWorkoutHistory() { 
        uc.getCurrentUser();
        List<WorkoutModel> workouts = workoutController.getPreviousWorkouts(uc.getCurrentUser());

        if (workouts.isEmpty()) {
            System.out.println("no history yet");
            return;
        }

        System.out.println("\nWorkout History:");
        Iterator<WorkoutModel> iterator = workouts.iterator();
        while(iterator.hasNext()){
            WorkoutModel workout = iterator.next();
            System.out.printf("Date: %s   |    Intensity: %s    |    Duration: %d min    |    Calories Burned: %d%n",
                    workout.getDate(), workout.getIntensityLevel(), workout.getMinutes(), workout.calculateCaloriesBurned());
        }
    }
}

