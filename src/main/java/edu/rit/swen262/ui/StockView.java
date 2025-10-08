package edu.rit.swen262.ui;

import java.util.HashMap;
import java.util.Set;

import edu.rit.swen262.controller.UserController;
import edu.rit.swen262.model.Ingredient;
import edu.rit.swen262.model.Stock;
import edu.rit.swen262.model.User;

public class StockView {
    private UserController uc;

    public StockView(UserController uc) {
        this.uc = uc;
    }

    public void showStock() {
        User user = uc.getCurrentUser();
        Stock stock = user.getStock();
        HashMap<Ingredient, Integer> ingredients = stock.getStock();

        Set<Ingredient> keys = ingredients.keySet();
        if (keys.isEmpty()) {
            System.out.println("No ingredients in stock.");
            return;
        }

        System.out.println("STOCK:");
        keys.stream().forEach(ingredient -> {
            System.out.println(ingredients.get(ingredient) + " - " + ingredient.getName());
        });
    }
}
