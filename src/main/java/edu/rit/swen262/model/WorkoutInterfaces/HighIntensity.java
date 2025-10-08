package edu.rit.swen262.model.WorkoutInterfaces;

import java.io.Serializable;

public class HighIntensity implements IntensityModel, Serializable { 
    @SuppressWarnings("unused")
    private static final long serial = 1L; 

    //Default constructor needed for Jackson
    public HighIntensity() { }

    @Override
    public int calculateCaloriesBurned(int duration) {
        return duration * 10; 
    }

    @Override
    public String toString() {
        return "High";
    }  
}