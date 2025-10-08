package edu.rit.swen262.model;
import java.io.Serializable;
import java.time.LocalDate;

import edu.rit.swen262.model.WorkoutInterfaces.IntensityModel;

/**
 * @author Kendra Patel
 * Represents an individual workout with duration, intensity, and date
 */
public class WorkoutModel implements Serializable {
    @SuppressWarnings("unused")
    private static final long serial = 1L;
    private int minutes;
    private IntensityModel intensity;  
    private int caloriesBurned;
    private LocalDate date;

    public WorkoutModel() {
        // Needed for Jackson deserialization
    }

    public WorkoutModel(int minutes, IntensityModel intensity, LocalDate date){
        this.minutes = minutes;
        this.intensity = intensity;
        this.date = date;  
    }

    /**
     * gets the calories burend during the workout
     * @return calories burned
     */
    public int getCaloriesBurned(){
        return this.caloriesBurned;
    }

    /**
     * gets the amount of minutes that were exercised
     * @return minutes
     */
    public int getMinutes(){
        return this.minutes;
    }

    /**
     * gets the intensity level of a workout
     * @return as a string
     */
    public String getIntensityLevel() {
        return intensity.toString();  
    }

    /**
     * Gets the date of the workout
     * @return date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the intensity level of the workout
     * @param intensity the new intensity level
     */
    public void setIntensity(IntensityModel intensity) {
        this.intensity = intensity;
    }

    /**
     * figures out the calories burned on the intensity and duration
     * @return calories burned
     */
    public int calculateCaloriesBurned(){
        return intensity.calculateCaloriesBurned(minutes);
    }

    /**
     * String of a workout
     */
    @Override
    public String toString() {
        return "(minutes exercised = " + minutes + ", intensity level = " + intensity + ")";
    }
    
}
