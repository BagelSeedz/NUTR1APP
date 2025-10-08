package edu.rit.swen262.model;
import com.fasterxml.jackson.core.type.TypeReference;



public class JsonAdapter implements FormatAdapter {

    //reference to the API that will handle the actual methods
    private JsonAPI JsonAPI;

    /**
     * This is the adapter class for JSON handling the deserialization and serialization
     * This is the Adapter in the Adapter Pattern
     */
    public JsonAdapter(){
        this.JsonAPI = new JsonAPI();
    }



    /**
     * Imports from a JSON file into an object with the specified type 
     * Class<T> - the class type itself, not an actual instance of an object
     * a reference to the class but doesn't give you the actual data that we want to work with
     * use class when importing = we need to tell the system what type of object we expect to receive when deserializing - Jackson needs to know what type of object it should create
     * calls the correlating API
     */
    @Override
    public <T> T importDataWithClass(String userId, Class<T> dataType, String fileName){
        return JsonAPI.importDataWithClass(userId, dataType, fileName);
    }

    /**
     * Imports data from a JSON file into data structures like maps or lists though TypeReference<T>
     * When we need to work with generic types
     * Calls the correlating API
     */
    @Override
    public <T> T importDataWithTypeReference(String userId, TypeReference<T> typeRef, String fileName) {
        return JsonAPI.importDataWithTypeReference(userId, typeRef, fileName);
    }

    /**
     * Exports an object to a JSON file.
     * serializes an object into JSON format and writes it to a file
     * Object = allow method to accept any type of data that can be serialized into json
     * object represents the instance of an object
     */
    @Override
    public void exportData(String username, Object data, String filename) {
        JsonAPI.exportData(username, data, filename);
    }
}
