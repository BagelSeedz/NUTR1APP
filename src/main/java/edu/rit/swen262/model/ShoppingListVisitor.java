package edu.rit.swen262.model;

public class ShoppingListVisitor {
    /**
     * Ingredient to add to the list
     */
    private Ingredient ingredient;

    /**
     * Constructor for ShoppingListVisitor
     * @param ingredient Ingredient to add to the list
     */
    public ShoppingListVisitor(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * Adds an ingredient to the shopping list
     * @param shoppingList The list to add an ingredient to
     */
    public void visitShoppingList(ShoppingList shoppingList) {
        shoppingList.addIngredient(ingredient);
    }
}
