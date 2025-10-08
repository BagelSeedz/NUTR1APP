package edu.rit.swen262.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.rit.swen262.model.TeamMediator.WorkoutNotification;

/**
 * @author Dominick Polakowski 
 */
public class Team {
    @JsonProperty("name")
    @JsonIgnore
    private String name;   
    
    @JsonIgnore
    private Challenge challenge; 

    @JsonIgnore
    private List<Challenge> previousChallenges; 

    // Mediator 
    @JsonIgnore
    private List<User> users; 

    @JsonIgnore
    private WorkoutNotification mediator; 

    public Team(String name) {
        this.name = name; 
        this.users = new ArrayList<>(); 
        this.previousChallenges = new LinkedList<>(); 
    }


    /**
     * Creates a new Team with the same state of another
     * @param team Team to clone
     */
    public Team(Team team) {
        this.name = team.getName();
        
        List<User> usersCopy = new ArrayList<>();
        usersCopy.addAll(team.getUsers());
        this.users = usersCopy;
    }

    /**
     * @return Team's Name 
     */
    public String getName() {
        return name; 
    }

    /**
     * @return All users in the team in a list 
     */
    public List<User> getUsers() {
        return users; 
    }

    /**
     * @param users User list to set users to
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * Adds User u to the team and 
     * notify the mediator of the change 
     * 
     * @param u
     */
    public void addUser(User u) {
        users.add(u); 
        mediator.addUser(u);
    }

    /**
     * Removes User u from the team and 
     * notifies the mediator of the change 
     * 
     * @param u
     */
    public void removeUser(User u) {
        users.remove(u); 
        mediator.removeUser(u); 
    }

    /**
     * @return True if there is an active challenge, 
     *         False if there is no active challenge 
     */
    public boolean activeChallenge() {
        return challenge == null ? false : true; 
    }

    /**
     * Creates a challenge is there is no active one already 
     * 
     * @return False if there is an active challenge, 
     *         True if a challenge is created 
     */
    public boolean startChallenge(DateTracker dt) {
        if (activeChallenge()) {
            return false; 
        } else {
            Map<User, Integer> map = new TreeMap<>(); 
            for (User u : users) {
                map.put(u, 0); 
            }
            this.challenge = new Challenge(map, dt); 
            return true; 
        }
    }

    /**
     * Saves the most recently ended challenge to the teams history 
     * 
     * @param c
     */
    public void saveChallenge(Challenge c) {
        previousChallenges.add(c); 
    }

    /**
     * @return The current Challenge 
     */
    public Challenge getChallenge() {
        return challenge; 
    }

    /**
     * @return The most recent Challenge 
     */
    public Challenge getMostRecentChallenge() {
        return previousChallenges.getLast(); 
    }

    /**
     * Ends the current challenge by adding it to the histories 
     * and making the current/active challenge null 
     */
    public void endChallenge() {
        previousChallenges.add(challenge); 
        challenge = null; 
    }

    /**
     * Sets the mediator for the team 
     * 
     * @param wn
     */
    public void setMediator(WorkoutNotification wn) {
        mediator = wn; 
    }

    /**
     * A User user notifies the team about a workout who 
     * delegates the notification handling to the mediator 
     * 
     * @param user who logged a workout 
     * @param workout 
     */
    public void notifyWorkout(User user, WorkoutModel workout) {
        mediator.notify(user, workout); 
    }

    /**
     * Checks for any unread notifications for User u 
     * 
     * @param u
     * @return unread notifications 
     */
    public List<String> checkNotifications(User u) {
        return mediator.checkNotifications(u); 
    }

    @Override 
    public String toString() {
        return name; 
    }
}