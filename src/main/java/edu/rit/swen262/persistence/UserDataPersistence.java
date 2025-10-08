package edu.rit.swen262.persistence;

import java.util.Map;

import edu.rit.swen262.controller.PersonalHistoryController;
import edu.rit.swen262.model.JsonAPI;
import edu.rit.swen262.model.User;

public class UserDataPersistence {

    private JsonAPI jsonAPI;
    private PersonalHistoryController phc;

    public UserDataPersistence(PersonalHistoryController phc) {
        jsonAPI = new JsonAPI();
        this.phc = phc;
    }

    public Map<String, User> loadUsers() {
        Map<String, User> users = jsonAPI.importUsers("users.json");
        users.keySet().stream().forEach(username -> {
            phc.importPersonalHistory(username);
        });
        return users;
    }
    

    // Save users to JSON file
    public void saveUsers(Map<String, User> users) {
        phc.exportPersonalHistory();
        jsonAPI.exportUsers(users, "users.json");
    }
    
    
}
    

