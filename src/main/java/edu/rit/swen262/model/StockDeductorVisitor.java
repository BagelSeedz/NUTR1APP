package edu.rit.swen262.model;

public class StockDeductorVisitor implements FoodVisitor{
    private Stock stock;
    private String deductStatus;
    private ShoppingList shoppingList;

    public StockDeductorVisitor(User user) {
        this.stock = user.getStock();
        this.shoppingList = user.getShoppingList();

        this.deductStatus = "";
    }

    public String getDeductStatus() {
        return deductStatus;
    }

    public void visitMeal(Meal meal){
        return;
    }
    public void visitRecipe(Recipe recipe){
        return;
    }
    public void visitIngredient(Ingredient ingredient){
        stock.removeIngredient(ingredient,1);

        if (stock.getAmount(ingredient)<0) {
            deductStatus = "warning";
            stock.addIngredient(ingredient,1);
            shoppingList.accept(new ShoppingListVisitor(ingredient));
        }
        return;
    }
}