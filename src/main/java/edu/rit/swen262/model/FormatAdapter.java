package edu.rit.swen262.model;
import com.fasterxml.jackson.core.type.TypeReference;


/**
 * Target Interface in the Adapter Pattern
 */
public interface FormatAdapter {
    /**
     * Deserializing (convert data - steam of bytes) to origional object ==> JSON from a file to object of type T 
     * @param <T> - flexible for any type: it can be for PersonalHistoryModel.class, Meal.class etc
     * Class<T> - simple objects = PersonalHistoryModel.class
     * @param userId
     * @param dataType
     * @param fileName
     * @return
     */
    <T> T importDataWithClass(String userId, Class<T> dataType, String fileName);



    /**
     * Imports - deserialize JSON from a file: for TypeReference
     * TypeReference<T> for collections (maps, lists)
     * Since the generic type information = ex String/Meal in HashMap<String, Meal> will be erased at runtime --- TypeReference<T> is needed 
     *      it preserves generic type information at runtime, 
     *      so Jackson library can deserialize the data w/specified type
     * @param <T>
     * @param userId
     * @param typeRef
     * @param fileName
     * @return
     */
    <T> T importDataWithTypeReference(String userId, TypeReference<T> typeRef, String fileName);


    /**
     * Exports the Object data to a JSON file
     * Serializes data object to JSON format - writes to a file
     * @param username
     * @param data
     * @param fileName
     */
    void exportData(String username, Object data, String fileName);

   

    


}

