package edu.rit.swen262.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.rit.swen262.controller.PersonalHistoryController;
import edu.rit.swen262.controller.UserController;
import edu.rit.swen262.model.DateTracker;
import edu.rit.swen262.model.Meal;
import edu.rit.swen262.model.PersonalHistoryModel;
import edu.rit.swen262.model.Recipe;
import edu.rit.swen262.model.User;

/**
 * @author Emily Santillanes
 */
public class GuestView {
    private Scanner scanner;
    private UserView userView;
    private UserController userController;
    private PersonalHistoryController phc;

    public GuestView(UserView userView, UserController userController, PersonalHistoryController phc){
        this.scanner = new Scanner(System.in);
        this.userView = userView;
        this.userController = userController;
        this.phc = phc;
    }
    
    public PersonalHistoryModel showMenu(){
        while(true){
            System.out.println("1. Login");
            System.out.println("2. Create Account");
            System.out.println("3. View Current Users");

            System.out.println("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch(choice){
                case 1:
                    PersonalHistoryModel loggedUserHistory = login();
                    if(loggedUserHistory==null){
                        break;
                    }else{
                        return loggedUserHistory;
                    }
                case 2:
                    User user = userView.createUser();
                    userController.addUser(user);
                    DateTracker dateTracker = new DateTracker(LocalDate.now());
                    PersonalHistoryModel currentUserHistory = new PersonalHistoryModel(user, dateTracker, user.getUsername(), dateTracker);
                    return currentUserHistory;
                case 3:
                    viewCurrentUsers();
                    break;
                default:
                    System.out.println("Enter a correct choice");
            }
        }
    }

    public PersonalHistoryModel login(){
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("User name entered." + username);

        Map<String, User> users = userController.getUsers();
        System.out.println("Available users: " + users.keySet());  


        while (users.get(username) == null) {
            //no matching username in system
            System.out.println("Username doesn't exist.");
            System.out.println("1. Try again");
            System.out.println("2. Return to main menu");
            System.out.println("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  

            if (choice == 2) {
                return null;
            }

            System.out.println("Enter your username: ");
            username = scanner.nextLine();
        }

        //matching username in system
        User user = users.get(username);
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();

        // check password until they get it right
        while (!user.checkPassword(password)) {
            System.out.println("Wrong password. Try again: ");
            password = scanner.nextLine();
        } 

        userController.switchUser(username);
        userController.addUser(user);
        User currentUser = userController.getCurrentUser();
        Map<String, PersonalHistoryModel> allPersonalHistories = phc.getAllPersonalHistories();
        PersonalHistoryModel currentUserHistory = null;
        for(PersonalHistoryModel history: allPersonalHistories.values()){
            if(history.getUser().getName().equals(currentUser.getName())){
                currentUserHistory = history;
                break;
            }
        }

        return currentUserHistory;
    }

    /**
     * This method lets the guest look through the list of current users and choose one to view the meals of
     */
    public void viewCurrentUsers(){
        System.out.println("Here is a list of the current users: ");
        Map<String, User> users = userController.getUsers();
        List<String> names = new ArrayList<>();
        int count = 1;
        for(String x: users.keySet()){
            System.out.println(count + ": " + x);
            names.add(x);
            count++;
        }

        while(true){
            System.out.println("Enter a user's number to see their food");
            System.out.println("Enter \"exit\" to return to the previous page");
            System.out.println("Enter your choice: ");
            String choice = scanner.nextLine();
    
            if(choice.equalsIgnoreCase("exit")){
                return;
            }
            User viewedUser = users.get(names.get(Integer.valueOf(choice)-1));
            List<Recipe> recipes = viewedUser.getRecipes();
            List<Meal> meals = viewedUser.getMeals();
            System.out.print("Recipes: ");
            System.out.println(recipes);
            System.out.print("Meals: ");
            System.out.println(meals);
        }
        
    }

}
