package edu.rit.swen262.model.GoalInterface;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import edu.rit.swen262.model.User;

/**
 * @author Dominick Polakowski 
 */


/**
 * Jackson needs this for an interface otherwise Jackson wouldn't know which specific classes to serialize/deserialize
 * Jackson needs to know which specific Goal to create
 * instead of the .toString like exporting - for improting it usees the "type" field to determine the correct class for the intensityobject 
 */
@JsonTypeInfo(
    //tells Jackson to use the name of the concrete class to identify the type
    use = JsonTypeInfo.Id.NAME,
    //tells Jackson to add the type information ("type") as a seperate property in the JSON object 
    include = JsonTypeInfo.As.PROPERTY,
    property = "type" //The name of the field Jackson adds to the JSON to indicate the type of the class
)

/**
 * lists all the possible subclasses that can be used
 * Each @JsonSubTypes.Type -  maps a type value (like "LoseWeight") to a Java class (like LoseWeight.class)
 * needed = If "type": "LoseWeight" appears in the JSON, Jackson knows to create a LoseWeight object.
 */
@JsonSubTypes({
    @JsonSubTypes.Type(value = AdaptiveWeight.class, name = "AdaptiveWeight"),
    @JsonSubTypes.Type(value = MaintainWeight.class, name = "MaintainWeight"),
    @JsonSubTypes.Type(value = GainWeight.class, name = "GainWeight"),
    @JsonSubTypes.Type(value = LoseWeight.class, name = "LoseWeight")
})


public interface Goal {
    /**
     * Concrete States calculate the user's daily calorie goal 
     */
    void setCalories(); 

    /**
     * Calculates the BMR for a user based on 
     * their height, weight, age, and sex
     * 
     * @param User 
     * 
     * @return BMR for the user 
     */
    default int calculateBMR(User user) {
        // Get Weight ang Height in kg and cm for formula
        double weight = user.getWeight() * 0.453592; 
        double height = user.getHeight() * 2.54; 

        // Make BMR calculation for Men and Women separately 
        if (user.getSex() == User.Sex.Male) {
            return (int) (10 * weight + 6.25 * height - 5 * user.getAge() + 5);
        } else {
            return (int) (10 * weight + 6.25 * height - 5 * user.getAge() - 161);
        }
    }
}