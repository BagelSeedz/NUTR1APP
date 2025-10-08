package edu.rit.swen262.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.rit.swen262.controller.UndoCaretaker;
import edu.rit.swen262.controller.UserController;
import edu.rit.swen262.model.Meal;
import edu.rit.swen262.model.PersonalHistoryModel;
import edu.rit.swen262.model.Recipe;
import edu.rit.swen262.model.User;
import edu.rit.swen262.persistence.UserDataPersistence;

/**
 * @author All
 */
public class MainView {
    private Scanner scanner;
    private HistoryView historyView;
    private WorkoutView workoutView;
    private UserView userView; 
    private FoodView foodView; 
    private ShoppingListView shoppingListView;
    private StockView stockView;
    private PersonalHistoryModel personalHistoryModel;
    private UserController userController;
    private FileView fileView;
    private UndoCaretaker undoCaretaker;
    private UserDataPersistence userDataPersistence;

    public MainView(HistoryView historyView, WorkoutView workoutView, UserView userView, FoodView foodView, ShoppingListView shoppingListView, StockView stockView, PersonalHistoryModel personalHistoryModel, FileView fileView, UserController userController, UndoCaretaker undoCaretaker, UserDataPersistence userDataPersistence) {
        this.scanner = new Scanner(System.in);
        this.historyView = historyView;
        this.workoutView = workoutView;
        this.userView = userView;
        this.foodView = foodView;
        this.shoppingListView = shoppingListView;
        this.stockView = stockView;
        this.userController = userController; 
        this.personalHistoryModel = personalHistoryModel; 
        this.fileView = fileView;
        this.undoCaretaker = undoCaretaker;
        this.userDataPersistence = userDataPersistence;
    }

    public void showMenu() {
        while (true) {
            LocalDate currentDate = personalHistoryModel.getCurrentDay().getDate();
            System.out.println("\nMain Menu - Date: " + currentDate + "\n");
            System.out.println("1. Profile");
            System.out.println("2. History");
            System.out.println("3. Workouts");
            System.out.println("4. Food");
            System.out.println("5. Shopping List");
            System.out.println("6. New Day");
            System.out.println("7. View Stock");
            System.out.println("8. View Current Users");
            System.out.println("9. File Management");
            System.out.println("10. Undo");
            System.out.println("11. Exit");

            System.out.print("\nEnter a command: ");
            int choice = scanner.nextInt();
            scanner.nextLine();   

            switch (choice) {
                case 1:
                    userView.showMenu();
                    break;
                case 2:
                    historyView.showMenu();
                    break;
                case 3:
                    workoutView.showMenu(); 
                    break; 
                case 4: 
                    foodView.showMenu(); 
                    break;
                case 5:
                    shoppingListView.showMenu();
                    break;
                case 6: 
                    historyView.newDay(); 
                    break; 
                case 7:
                    stockView.showStock();
                    break;
                case 8:
                    viewCurrentUsers();
                    break;
                case 9: 
                    fileView.showMenu();
                    break;
                case 10: 
                    System.out.println(undoCaretaker.undo());
                    break;
                case 11:
                    userDataPersistence.saveUsers(userController.getUsers());
                    System.out.println("Users map before export: " + userController.getUsers().keySet()); // Add this
                    //personalHistoryModel.save();                     
                    return; 
                default:
                    System.out.println("Enter a command 1-11"); 
            }
        }
    }
    
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