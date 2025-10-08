
package edu.rit.swen262.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import edu.rit.swen262.model.PersonalHistoryModel;

/**
 * @author Dominick Polakowski 
 */
public class UserHistoryStorage  {
    private static final String PATH = "src/main/java/edu/rit/swen262/data/UserHistory.dat";

    /**
     * Saves the History to a dat file 
     * @param historyModel
     */
    public static void saveHistory (PersonalHistoryModel historyModel) {
        try(FileOutputStream fileOutput = new FileOutputStream(PATH);
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput)) {
            objectOutput.writeObject(historyModel);       
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return PersonalHistoryModel object loaded in from memory 
     * @return null if the file is empty or DNE 
     */
    public static PersonalHistoryModel loadHistory() {
        // Return null if file is empty or DNE 
        File file = new File(PATH); 
        if (!file.exists() || file.length() == 0) {
            return null; 
        }

        // Read from the file and load in the data 
        try(FileInputStream fis = new FileInputStream(PATH); 
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (PersonalHistoryModel) ois.readObject(); 
        } catch (IOException e) {
            e.printStackTrace();
            return null; 
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); 
            return null; 
        }
    }
}