package edu.rit.swen262.model;

public interface FoodVisitor {
    public void visitMeal(Meal meal);
    public void visitRecipe(Recipe recipe);
    public void visitIngredient(Ingredient ingredient);
}