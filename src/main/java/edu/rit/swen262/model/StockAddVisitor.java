package edu.rit.swen262.model;

public class StockAddVisitor implements FoodVisitor{
    private Stock stock;

    public StockAddVisitor(Stock stock) {
        this.stock = stock;
    }

    public void visitMeal(Meal meal){
        return;
    }

    public void visitRecipe(Recipe recipe){
        return;
    }
    
    public void visitIngredient(Ingredient ingredient){
        stock.addIngredient(ingredient,1);
        return;
    }
}