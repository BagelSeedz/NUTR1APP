package edu.rit.swen262.ui;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.rit.swen262.controller.UndoCaretaker;
import edu.rit.swen262.controller.UserController;
import edu.rit.swen262.model.Ingredient;
import edu.rit.swen262.model.IngredientDatabase;
import edu.rit.swen262.model.ShoppingList;
import edu.rit.swen262.model.Stock;
import edu.rit.swen262.model.User;

/**
 * @author David Martinez
 */
public class ShoppingListView {
    private IngredientDatabase idb;
    private Scanner scanner;
    private UserController uc;
    private UndoCaretaker undoCaretaker;

    public ShoppingListView(IngredientDatabase idb, UserController uc, UndoCaretaker undoCaretaker) {
        this.idb = idb;
        this.scanner = new Scanner(System.in);
        this.uc = uc;
        this.undoCaretaker = undoCaretaker;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\nShopping List\n");
            System.out.println("1. View Shopping List");
            System.out.println("2. Add to Shopping List");
            System.out.println("3. Remove from Shopping List");
            System.out.println("4. Purchase items on Shopping List");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1:
                    showList();
                    break;
                case 2:
                    addToList();
                    break;
                case 3:
                    removeFromList();
                    break;
                case 4:
                    purchaseAll();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Enter 1, 2, or 3");
            }
        }
    }

    @JsonIgnore
    private void showList() {
        User user = uc.getCurrentUser();
        ShoppingList shoppingList = user.getShoppingList();
        HashMap<Ingredient, Integer> items = shoppingList.getList();
        Set<Ingredient> keys = items.keySet();
        
        System.out.println("\nSHOPPING LIST:");

        if (keys.isEmpty()) {
            System.out.println("Shopping List is empty.");
            return;
        }

        items.keySet().stream().forEach(ingredient -> {
            System.out.println(items.get(ingredient) + " - " + ingredient.getName());
        });
    }

    private void addToList() {
        while (true) {
            System.out.println("Enter the name of an ingredient to add or 'Exit' to exit");
            String nameEntered = scanner.nextLine();

            if (nameEntered.toLowerCase().equals("exit")) {
                break;
            }

            HashMap<String, Ingredient> results = idb.search(nameEntered);
            Object[] names = results.keySet().toArray();
            
            if (names.length == 0) {
                System.out.println("No ingredients found.");
                continue;
            }

            for (int i=0; i<names.length; i++) {
                System.out.println((i+1) + ": " + names[i]);
            }

            int id;
            while (true) {
                System.out.println("Enter the ingredient's number or 'Exit' to exit");
                String numberEntered = scanner.nextLine();
                
                if (numberEntered.toLowerCase().equals("exit")) {
                    return;
                }
                
                try {
                    id = Integer.parseInt(numberEntered);
                } catch (NumberFormatException e) {
                    System.out.println("Enter a number between 1 and " + names.length);
                    continue;
                }

                if (id < 1 || id > names.length) {
                    System.out.println("Enter a number between 1 and " + names.length);
                } else {
                    break;
                }
            }

            int number;
            while (true) {
                System.out.println("Enter the amount of the ingredient to add to the Shopping List or 'Exit' to exit");
                String numberEntered = scanner.nextLine();

                if (numberEntered.toLowerCase().equals("exit")) {
                    return;
                }

                try {
                    number = Integer.parseInt(numberEntered);
                    break;
                } catch (NumberFormatException e) {
                    continue;
                }
            }
            
            String name = (String) names[id-1];
            Ingredient ingredient = results.get(name);
            User user = uc.getCurrentUser();
            ShoppingList shoppingList = user.getShoppingList();
            for (int i=0; i<number; i++) {
                shoppingList.addIngredient(ingredient);
            }
            System.out.println("Added " + number + " " +  name + " to the Shopping List!");
        }
    }

    @JsonIgnore
    public void removeFromList() {
        User user = uc.getCurrentUser();
        ShoppingList shoppingList = user.getShoppingList();
        HashMap<Ingredient, Integer> items = shoppingList.getList();
        Object[] ingredients = items.keySet().toArray();

        if (ingredients.length == 0) {
            System.out.println("Nothing to remove from the list.");
            return;
        }

        System.out.println("\nSHOPPING LIST");
        for (int i=0; i<ingredients.length; i++) {
            System.out.println((i+1) + ": " + ((Ingredient) ingredients[i]).getName());
        }

        int number;
        while (true) {
            System.out.println("Enter the ingredient's number or 'Exit' to exit");
            String numberEntered = scanner.nextLine();
            
            if (numberEntered.toLowerCase().equals("exit")) {
                return;
            }

            try {
                number = Integer.parseInt(numberEntered);
            } catch (NumberFormatException e) {
                System.out.println("Enter a number between 1 and " + ingredients.length);
                continue;
            }

            if (number < 1 || number > ingredients.length) {
                System.out.println("Enter a number between 1 and " + ingredients.length);
            } else {
                break;
            }
        }

        Ingredient ingredient = (Ingredient) ingredients[number-1];
        shoppingList.removeIngredient(ingredient);
        System.out.println("Removed " + ingredient.getName() + " from the Shopping List.");
    }

    public void purchaseAll() {
        System.out.println("Are you sure you want to purchase all ingredients on the Shopping List? (Y/N)\n(This will empty your shopping list)");
        String answer = scanner.nextLine();

        if (!answer.toLowerCase().equals("y")) {
            return;
        }

        undoCaretaker.storeStock(); // Store stock before changing it to undo later

        User user = uc.getCurrentUser();
        ShoppingList shoppingList = user.getShoppingList();
        HashMap<Ingredient, Integer> items = shoppingList.getList();
        Stock stock = uc.getCurrentUser().getStock();
        items.keySet().stream().forEach(ingredient -> {
            stock.addIngredient(ingredient,items.get(ingredient));
        });

        shoppingList.clear();

        System.out.println("Purchased all items on the shopping list!");
    }
}
