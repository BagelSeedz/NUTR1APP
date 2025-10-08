package edu.rit.swen262.model.UndoClasses;

import java.util.HashMap;

import edu.rit.swen262.model.Ingredient;

/**
 * Memento encapsulating Stock data
 * 
 * @author David Martinez
 */
public class StockMemento implements Memento {
    /**
     * The stock at the time of the snapshot
     */
    private final HashMap<Ingredient, Integer> ingredientStock;

    /**
     * Creates a StockMemento
     * @param ingredientStock The current stock
     */
    public StockMemento(HashMap<Ingredient, Integer> ingredientStock) {
        this.ingredientStock = ingredientStock;
    }
    
    public HashMap<Ingredient, Integer> getStock() {
        return ingredientStock;
    }

    @Override
    public String toString() {
        return "Stock restored.";
    }
}
