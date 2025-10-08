package edu.rit.swen262.model.WorkoutInterfaces;

import java.io.Serializable;

public class MediumIntensity implements IntensityModel, Serializable {
    @SuppressWarnings("unused")
    private static final long serial = 1L; 

    //Default constructor needed for Jackson
    public MediumIntensity() { }

    @Override
    public int calculateCaloriesBurned(int duration) {
        return  (int) (duration * 7.5); 
    }

    @Override
    public String toString() {
        return "Medium";
    }
    
}
