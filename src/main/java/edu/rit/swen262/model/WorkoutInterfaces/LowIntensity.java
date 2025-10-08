package edu.rit.swen262.model.WorkoutInterfaces;

import java.io.Serializable;

public class LowIntensity implements IntensityModel, Serializable {
    @SuppressWarnings("unused")
    private static final long serial = 1L; 
    
    //Default constructor needed for Jackson
    public LowIntensity() { }

    @Override
    public int calculateCaloriesBurned(int duration) {
        return duration * 5; 
    }

    @Override
    public String toString() {
        return "Low";
    }


    
}
