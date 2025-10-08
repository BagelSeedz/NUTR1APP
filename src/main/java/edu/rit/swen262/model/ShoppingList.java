package edu.rit.swen262.model;
import java.io.Serializable;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Shopping List to help users manage what
 * they need to purchase.
 * 
 * @author David Martinez
 */
public class ShoppingList implements Serializable {
    @SuppressWarnings("unused")
    private static final long serial = 1L; 
    /**
     * Map to hold the ingredients in the list and the amount of each.
     */
    @JsonIgnore
    private HashMap<Ingredient, Integer> ingredients;

    /**
     * Constructor for Shopping List.
     * Initializes ingredients map as empty.
     */
    public ShoppingList() {
        this.ingredients = new HashMap<>();
    }

    /**
     * Adds an ingredient to the list.
     * @param ingredient The ingredient to add
     */
    public void addIngredient(Ingredient ingredient) {
        int current = ingredients.containsKey(ingredient) ? ingredients.get(ingredient) : 0;
        ingredients.put(ingredient, current + 1);
    }

    /**
     * Removes an ingredient from the list.
     * @param ingredient The ingredient to remove
     */
    public void removeIngredient(Ingredient ingredient) {
        if (ingredients.containsKey(ingredient)) {
            int current = ingredients.get(ingredient);
            current -= 1;
            if (current == 0) {
                ingredients.remove(ingredient);
            } else {
                ingredients.put(ingredient, current);
            }
        }
    }

    /**
     * Empties the list.
     * This will likely be called after purchasing items.
     */
    public void clear() {
        ingredients.clear();
    }

    @JsonIgnore
    @SuppressWarnings("unchecked")
    public HashMap<Ingredient, Integer> getList() {
        return (HashMap<Ingredient, Integer>) ingredients.clone();
    }

    /**
     * Accepting the visitor to add ingredients to the list
     * @param visitor 
     */
    public void accept(ShoppingListVisitor visitor) {
        visitor.visitShoppingList(this);
    }
}
