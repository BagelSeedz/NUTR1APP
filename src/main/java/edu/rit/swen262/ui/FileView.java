package edu.rit.swen262.ui;
import java.util.Scanner;
import edu.rit.swen262.controller.PersonalHistoryController;


public class FileView {

    private Scanner scanner;
    private PersonalHistoryController personalHistoryController;

    public FileView(PersonalHistoryController personalHistoryController) {
        this.scanner = new Scanner(System.in);
        this.personalHistoryController = personalHistoryController;
    }


    public void showMenu(){
        while(true){
            System.out.println("\nImport or Export");
            System.out.println("1. Import");
            System.out.println("2. Export");
            System.out.println("3. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1:
                    handleImport();
                    break;
                case 2:
                    handleExport();
                    break;
                case 3:
                    return;  
                default:
                    System.out.println("Enter a correct choice.");
            }
        }
    }


  
    private void handleImport() {
        System.out.println("What do you want to import?");
        System.out.println("1. Personal History");
        System.out.println("2. Meals");
        System.out.println("3. Recipes");
        System.out.println("4. Stock");

        System.out.print("Enter choice: ");

        int importChoice = scanner.nextInt();
        scanner.nextLine();

        switch(importChoice) {
            case 1:
                personalHistoryController.importPersonalHistory();
                break;
            case 2:
                personalHistoryController.importMeals();
                break;
            case 3:
                personalHistoryController.importRecipes();
            case 4:
                personalHistoryController.importStock();
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    private void handleExport() {
        System.out.println("What do you want to export?");
        System.out.println("1. Personal History");
        System.out.println("2. Meals");
        System.out.println("3. Recipes");
        System.out.println("4. Stock");
        System.out.print("Enter choice: ");

        int exportChoice = scanner.nextInt();
        scanner.nextLine();

        switch (exportChoice) {
            case 1:
                personalHistoryController.exportPersonalHistory();
                break;
            case 2:
                personalHistoryController.exportMeals();
                break;
            case 3:
                personalHistoryController.exportRecipes();
                break;
            case 4:
                personalHistoryController.exportStock();
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }
    
}

