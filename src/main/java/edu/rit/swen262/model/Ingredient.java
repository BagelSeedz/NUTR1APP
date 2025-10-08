package edu.rit.swen262.model;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;



public class Ingredient implements Food, Serializable {
    @SuppressWarnings("unused")
    private static final long serial = 1L; 
    
    private String name;
    private int calories;
    private float fatPerUnit;
    private float proteinPerUnit;
    private float fiberPerUnit;
    private float carbsPerUnit;

    //needed for desterialization from jackson
    public Ingredient() { }

    //consturctor w name
    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient(String name, int calories, float fatPerUnit, float proteinPerUnit, float fiberPerUnit, float carbsPerUnit){
        this.name = name;
        this.calories = calories;
        this.fatPerUnit = fatPerUnit;
        this.proteinPerUnit = proteinPerUnit;
        this.fiberPerUnit = fiberPerUnit;
        this.carbsPerUnit = carbsPerUnit;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public int getCalories(){
        return this.calories;
    }

    @Override
    public float getFat(){
        return this.fatPerUnit;
    }

    @Override
    public float getProtein(){
        return this.proteinPerUnit;
    }

    @Override
    public float getFiber(){
        return this.fiberPerUnit;
    }

    @Override
    public float getCarbs(){
        return this.carbsPerUnit;
    }

    public void addChild(){
        return;
    }

    public void accept(FoodVisitor visitor){
        visitor.visitIngredient(this);
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Ingredient) {
            Ingredient ingredient = (Ingredient) obj;
            return ingredient.name.equals(this.name);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Ingredient{name='" + name;
    }

  
    /**
     * @JsonValue - tells Jackson how to serialize an Ingredient object into JSON
     * it converts the Ingredient object into a string representation of its name
     * @return
     */
    @JsonValue
    public String toJson() {
        return this.name;  
    }
    /**
     * @JsonCreator -  how to deserialize a JSON string into an Ingredient object
     * given key - create an Ingredient object with the name of key
     * @param key
     * @return
     */
    @JsonCreator
    public static Ingredient fromJson(String key) {
        return new Ingredient(key);
    }
    

}
