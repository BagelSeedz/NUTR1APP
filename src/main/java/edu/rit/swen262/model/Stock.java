package edu.rit.swen262.model;
import java.io.Serializable;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.rit.swen262.model.UndoClasses.StockMemento;



public class Stock implements Serializable {
    @SuppressWarnings("unused")
    private static final long serial = 1L; 
    
    @JsonIgnore
    private HashMap<Ingredient, Integer> ingredientStock;
    private ShoppingList shoppingList;

    
    //Constructor
    public Stock(ShoppingList shoppingList) {
        this.ingredientStock = new HashMap<>();
        this.shoppingList = shoppingList;
    }

    // Default constuctor for jackson
     public Stock() {
        this.ingredientStock = new HashMap<>();
        this.shoppingList = new ShoppingList();  
    }
    

    /**
     * since we had marked the stock property as @JsonIgnore --- when we serialize it for export (personal history) - didn't want that it excludes that 
     * @JsonCreator - jackson will be able to deserialize it for import - consturcot it will use: sepcify the intialization
     * @param ingredientStock
     * @param shoppingList
     */
    //constuctor used for deserialization
    @JsonCreator
    public Stock(
        //@JsonProperty annotations map JSON properties to the constructor's parameters
        @JsonProperty("ingredientStock") HashMap<Ingredient, Integer> ingredientStock,
        @JsonProperty("shoppingList") ShoppingList shoppingList // Additional property
        ) 
    {
    //if null or empty - so no data was provided into the json - the hashmap/shopping lists are created 
    if (ingredientStock == null || ingredientStock.isEmpty()) {
        this.ingredientStock = new HashMap<>();
    } else {
        this.ingredientStock = ingredientStock;
    }
    if (shoppingList == null) {
        this.shoppingList = new ShoppingList();  
    } else {
        this.shoppingList = shoppingList;
    }
}


   

    public void addIngredient(Ingredient ingredient, int amount) {
        int current = ingredientStock.containsKey(ingredient) ? ingredientStock.get(ingredient) : 0;
        ingredientStock.put(ingredient, current+amount);
    }

    public void removeIngredient(Ingredient ingredient,int amount) {
        int current = ingredientStock.containsKey(ingredient) ? ingredientStock.get(ingredient) : 0;
        ingredientStock.put(ingredient, current-amount);

        if (current == 0) {
            shoppingList.accept(new ShoppingListVisitor(ingredient)); // Automatically add ingredient to shoppingList
        }
    }

    public int getAmount(Ingredient ingredient) {
        return ingredientStock.containsKey(ingredient) ? ingredientStock.get(ingredient) : 0;
    }

    @JsonIgnore
    @SuppressWarnings("unchecked")
    public HashMap<Ingredient, Integer> getStock() {
        return (HashMap<Ingredient, Integer>) this.ingredientStock.clone();
    }

    public void restore(StockMemento memento) {
        this.ingredientStock = memento.getStock();
    }
    
    /**
     * goes around the JsonIgnore
     * @return
     */
    @JsonProperty("ingredientStock")
    public HashMap<Ingredient, Integer> getStockExport() {
        return new HashMap<>(this.ingredientStock);
    }
}