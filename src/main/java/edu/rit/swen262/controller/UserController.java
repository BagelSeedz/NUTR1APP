package edu.rit.swen262.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import edu.rit.swen262.model.Team;
import edu.rit.swen262.model.User;
import edu.rit.swen262.model.User.Sex;
import edu.rit.swen262.model.GoalInterface.Goal;
import edu.rit.swen262.model.UndoClasses.UserMemento;
import edu.rit.swen262.persistence.UserDataPersistence;

/**
 * @author Dominick Polakowski 
 */
public class UserController implements Originator {
    private Map<String, User> users; 
    private User currentUser; 

    /**
     * UserController Constructor 
     */
    public UserController() {
        this.users = new HashMap<>(); 
        this.currentUser = null; 
    }

    /**
     * @return The current User selected 
     */
    public User getCurrentUser() {
        return currentUser; 
    }

    /**
     * Add the user to the system and select it as the current user 
     * 
     * @param user
     */
    public void addUser(User user) {
        users.put(user.getUsername(), user); 
        currentUser = user; 
        System.out.println("Users map: " + users);
    }

    /**
     * Switches to the user if found in the system, 
     * current user stays the same if user DNE 
     * 
     * @param name
     * 
     * @return True or False, based on if the switch was a success 
     */
    public boolean switchUser(String username) {
        if (users.containsKey(username)) {
            currentUser = users.get(username);
            return true; 
        } else {
            return false; 
        }
    }

    /**
     * Create the user, assign its goal, assign 
     * the targetCalories, and add the user to the system 
     * 
     * @param name
     * @param username
     * @param birthdate
     * @param sex
     * @param height
     * @param weight
     * @param targetWeight
     * @param password
     * 
     * @return New User
     */
    public User createUser(String name, String username, LocalDate birthdate, Sex sex, int height, double weight, double targetWeight, String password) {
        // Create the user 
        User user = new User(name, username, birthdate, sex, height, weight, targetWeight, password); 

        // Assign the goal and targetCals 
        user.changeGoal(user.goalCheck()); 

        // Add to list and set as current 
        addUser(user); 

        return user; 
    }

    /**
     * Changes the User's goal 
     * 
     * @param user
     * @param goal
     */
    public Goal changeGoal(Goal goal) {
        return currentUser.changeGoal(goal);
    }

    /**
     * Checks if the goal still aligns with the user's weight and target weight 
     * 
     * @return A goal for the user 
     */
    public void goalCheck() {
        currentUser.goalCheck(); 
    }

    public Map<String, User> getUsers(){
        return users;
    }

    public void setUsers(Map<String, User> loadedUsers) {
        this.users = loadedUsers;
    }

    @Override
    public UserMemento createMemento() {
        return new UserMemento(currentUser.getGoal(), new Team(currentUser.getTeam()));
    }

    @Override
    public void restoreMemento(Object m) {
        UserMemento memento = (UserMemento) m;
        currentUser.restore(memento);
    }



}