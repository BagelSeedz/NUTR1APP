package edu.rit.swen262.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Scanner;

import edu.rit.swen262.controller.UndoCaretaker;
import edu.rit.swen262.controller.UserController;
import edu.rit.swen262.model.User;
import edu.rit.swen262.model.GoalInterface.AdaptiveWeight;
import edu.rit.swen262.model.GoalInterface.GainWeight;
import edu.rit.swen262.model.GoalInterface.Goal;
import edu.rit.swen262.model.GoalInterface.LoseWeight;
import edu.rit.swen262.model.GoalInterface.MaintainWeight;
import edu.rit.swen262.model.User.Sex;

/**
 * @author Dominick Polakowski 
 */
public class UserView {
    private final UserController userController; 
    private UndoCaretaker undoCaretaker;
    private final Scanner scanner; 

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public UserView(UserController userController) {
        this.userController = userController; 
        this.undoCaretaker = null;
        this.scanner = new Scanner(System.in); 
    }

    public void showMenu() {
        while (true) {
            System.out.println("\nProfile\n");
            System.out.println("1. Change Goal");
            System.out.println("2. View Goal");
            System.out.println("3. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    changeGoal();
                    break;
                // case 2:
                //     createUser(); 
                //     break; 
                case 2:
                    showGoal();
                    break;
                case 3: 
                    return; 
                default:
                    System.out.println("Enter 1 or 2");
            }
        }
    }

    public void changeGoal() {
        User user = userController.getCurrentUser(); 

        while (true) {
            System.out.println("\nChoose a new goal: \n");
            System.out.println("1. Lose Weight");
            System.out.println("2. Gain Weight ");
            System.out.println("3. Maintain Weight");
            System.out.println("4. Adaptive Weight");
            System.out.println("5. Exit"); 

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            Goal goal = null; 

            switch (choice) {
                case 1:
                    goal = new LoseWeight(user); 
                    break;
                case 2:
                    goal = new GainWeight(user);
                    break;
                case 3:
                    goal = new MaintainWeight(user); 
                    break; 
                case 4: 
                    System.out.println("Enter a calorie goal to add to your daily target to improve your physical fitness: ");
                    int calories = 0; 
                    String text = null; 
                    while (calories <= 0) {
                        try {
                            text = scanner.nextLine(); 
                            calories = Integer.parseInt(text); 
                            if (calories <= 0) 
                                System.out.print("Enter a positive calorie goal: "); 
                        } catch (Exception e) {
                            System.out.print("Enter a positive calorie goal: "); 
                        }
                    }
                    goal = new AdaptiveWeight(user, calories); 
                    break; 
                case 5: 
                    return; 
                default:
                    System.out.println("Enter 1, 2, 3, 4, or 5");
            }

            undoCaretaker.storeUser(); // Store goal before changing it to undo later
            System.out.println("\nNew selected goal: " + userController.changeGoal(goal).toString());
            return; 
        }
    }

    public User createUser() {
        String name = null; 
        LocalDate birthdate = null; 
        Sex sex = null; 
        int height = 0; 
        double weight = 0; 
        double targetWeight = 0; 
        String username = null;
        String password = null;

        System.out.print("Enter your name: ");
        name = scanner.nextLine(); 

        System.out.print("Enter your desired username: ");
        username = scanner.nextLine(); 
        //checks for duplicate usernames
        Map<String, User> users = userController.getUsers();
        while (users.get(username) != null) {
            System.out.print("Given username is already taken. Choose another: ");
            username = scanner.nextLine();
        }

        System.out.print("Enter your desired password: ");
        password = scanner.nextLine();

        System.out.print("Enter your birthday <mm/dd/yyyy>: "); 
        String text = scanner.nextLine(); 

        while (birthdate == null) {
            try {
                birthdate = LocalDate.parse(text, FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.print("Enter in mm/dd/yyyy format: "); 
                text = scanner.nextLine(); 
            }
        }

        System.out.print("Enter your sex [M/F]: "); 
        while (sex == null) {
            switch (scanner.nextLine().toLowerCase()) {
                case "m": 
                    sex = Sex.Male; 
                    break; 
                case "f": 
                    sex = Sex.Female; 
                    break; 
                default: 
                    System.out.print("Enter m or f: ");
            }
        }

        System.out.print("Enter your height [inches]: ");
        while (height <= 0) {
            try {
                text = scanner.nextLine(); 
                height = Integer.parseInt(text); 
                if (height <= 0) 
                    System.out.print("Enter a positive height in inches: "); 
            } catch (Exception e) {
                System.out.print("Enter a positive height in inches: "); 
            }
        }

        System.out.print("Enter your weight [lbs]: ");
        while (weight <= 0) {
            try {
                text = scanner.nextLine(); 
                weight = Double.parseDouble(text);
                if (weight <= 0) 
                    System.out.print("Enter a positive weight in pounds: ");
            } catch (Exception e) {
                System.out.print("Enter a positive weight in pounds: ");
            }
        }

        System.out.print("Enter your target weight [lbs]: ");
        while (targetWeight <= 0) {
            try {
                text = scanner.nextLine(); 
                targetWeight = Double.parseDouble(text);
                if (targetWeight <= 0) 
                    System.out.print("Enter a positive target weight in pounds: ");
            } catch (Exception e) {
                System.out.print("Enter a positive target weight in pounds: ");
            }
        }        

        User user = userController.createUser(name, username, birthdate, sex, height, weight, targetWeight, password); 
        userController.goalCheck(); 
        return user; 
    }

    public void setUndoCaretaker(UndoCaretaker caretaker) {
        this.undoCaretaker = caretaker;
    }
    
    private void showGoal() {
        User currentUser = userController.getCurrentUser();
        Goal goal = currentUser.getGoal();
        System.out.println("\nCurrent Goal: " + goal);
    }
}