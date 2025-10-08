package edu.rit.swen262.model.TeamMediator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.rit.swen262.model.Team;
import edu.rit.swen262.model.User;
import edu.rit.swen262.model.WorkoutModel;

/**
 * Acts as a mediator for all teams in order to send notifications 
 * to and from each member through a single endpoint 
 * 
 * @author Dominick Polakowski 
 */
public class WorkoutNotification implements NotificationsInterface {
    // Map <User, Notifications> 
    private Map<User, List<String>> pending; 

    private Team team; 

    public WorkoutNotification(Team team) {
        this.team = team; 
        team.setMediator(this); 
        this.pending = new HashMap<>(); 
        populatePending(); 
    }

    /**
     * Private method used to populate the original map 
     * with a key for every user and empty value to start 
     */
    private void populatePending() {
        for (User user : team.getUsers()) {
            pending.put(user, new ArrayList<String>()); 
        }
    }

    @Override
    public void notify(User user, WorkoutModel workout) {
        String message = team.getName().toUpperCase() + ": " + user.getName() + "'s workout: " + workout.toString(); 

        for (User u : team.getUsers()) {
            if (!u.equals(user)) {
                storeOffline(u, message);
            } 
        }
    }

    @Override
    public void storeOffline(User user, String message) {
        pending.get(user).add(message); 
    }

    @Override
    public List<String> checkNotifications(User user) {
        if (pending.get(user).size() > 0) { 
            return pending.get(user); 
        } else {
            return null; 
        }
    } 

    /**
     * Adds a User u to the mediator 
     * 
     * @param u
     */
    public void addUser(User u) {
        pending.put(u, new ArrayList<>()); 
    }

    /**
     * Removes User u from the mediator 
     * 
     * @param u
     */
    public void removeUser(User u) {
        pending.remove(u); 
    }
}