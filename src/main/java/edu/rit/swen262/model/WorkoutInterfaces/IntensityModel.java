package edu.rit.swen262.model.WorkoutInterfaces;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * The intensity field in WorkoutModel is of type IntensityModel = interface
 * Jackson does not know how to instantiate an interface or abstract class unless you provide it with this info
 */




 @JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"  // this property will indicate the concrete type in JSON
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = LowIntensity.class, name = "Low"),
    @JsonSubTypes.Type(value = MediumIntensity.class, name = "Medium"),
    @JsonSubTypes.Type(value = HighIntensity.class, name = "High")
})


public interface IntensityModel {
    int calculateCaloriesBurned(int duration); 
}