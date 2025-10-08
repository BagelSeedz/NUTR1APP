package edu.rit.swen262.model;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;



public class JsonAPI {

    //Part of Jackson library
    private ObjectMapper objectMapper;


    public JsonAPI() {
        this.objectMapper = new ObjectMapper(); //inialize the ObjectMapper

        // Register module to support Java date/time types  
        //Jackson doesn't have the time class - need JavaTimeModule so doesn't it throw an error when trying to serialize/deserialize LocalDate 
        objectMapper.registerModule(new JavaTimeModule());
        
        //These throw errors if don't do
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }



    /**
     * Imports data from a JSON file and deserializes it into the specified class type
     * used when the type is known/Class<T>
     * @param <T>
     * @param userId
     * @param dataType
     * @param fileName
     * @return
     */
    public <T> T importDataWithClass(String userId, Class<T> dataType, String fileName) {
        try {
            //creates a file path based on the users data
            File file = new File("data/" + userId + "/" + fileName);

            //if the file DNE - returns null so don't have to read it
            if (!file.exists()) {
                System.out.println("File not found: " + fileName);
                return null;
            }
            //Jackson uses - parameter of dataType -> .class = determines what type of object it should create
            return objectMapper.readValue(file, dataType);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * Imports data from a JSON file and deserializes it into the specified type
     * USes the Jackson Library - TypeReference to for the generic types 
     * @param <T>
     * @param userId
     * @param typeRef
     * @param fileName
     * @return
     */
    public <T> T importDataWithTypeReference(String userId, TypeReference<T> typeRef, String fileName) {
        try {
            //creates a file path based on the users data
            File file = new File("data/" + userId + "/" + fileName);

            //if the file DNE - returns null so don't have to read it
            if (!file.exists()) {
                System.out.println("File not found: " + fileName);
                return null;
            }

            //Deserialize JSON file - puts it into specified type 
            return objectMapper.readValue(file, typeRef);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    


    public Map<String, User> importUsers(String fileName) {
        try {
            File file = new File("data/users/" + fileName);

            if (!file.exists()) {
                System.out.println("File not found: " + fileName);
                return null;
            }
            TypeReference<Map<String, User>> typeRef = new TypeReference<Map<String, User>>() {};
            return objectMapper.readValue(file, typeRef);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void exportUsers (Map<String, User> users, String fileName) {
        try {
            File directory = new File("data/users");   
    
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (created) {
                    System.out.println("Success creating directory");
                } else {
                    System.out.println("Failure creating directory");
                }
            }
            File file = new File(directory, fileName);
            objectMapper.writeValue(file, users);
            System.out.println("Successfully exported user data to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    




    /**
     * This exports an object to a JSON file
     * whatever object that is given, it serializes it into JSON format and writes it to a file
     * @param username
     * @param data
     * @param fileName
     */
    public void exportData(String username, Object data, String fileName) {
        try {
            //If the directory DNE - creates a new one  
            File directory = new File("data/" + username);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();  
                if (created){
                    System.out.println("Successfully created a new directory");
                } else {
                    System.out.println("Failure creating a new directory");
                }
            }

            //Creates the file given in the directory
            //File is not created yet - just an object that represents the path of the file
            File file = new File(directory, fileName);

            //Converts the data object to JSON format: Jackson serializes it
            //Then writes the content which is in JSON to the file  
            objectMapper.writeValue(file, data);
            
            //message printing 
            System.out.println("User: " + username + " Successfully exported " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


   
    
}




