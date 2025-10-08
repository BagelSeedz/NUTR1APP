package edu.rit.swen262.controller;

import java.util.ArrayList;
import java.util.List;

import edu.rit.swen262.model.DateTracker;
import edu.rit.swen262.model.Team;
import edu.rit.swen262.model.User;

/**
 * @author Dominick Polakowski 
 */
public class TeamController {
    private DateTracker dt; 
    private List<Team> teams; 
    
    public TeamController(DateTracker dt) {
        this.dt = dt; 
        this.teams = new ArrayList<>(); 
    }
    /**
     * Adds User u to Team t 
     * 
     * @param u
     * @param t
     */
    public void addUser(User u, Team t) {
        t.addUser(u); 
        u.joinTeam(t); 
        teams.add(t); 
    }

    /**
     * Removes User u from Team t 
     * 
     * @param u
     * @param t
     */
    public void removeUser(User u, Team t) {
        t.removeUser(u); 
        u.leaveTeam(); 
        teams.remove(t); 
    }

    /**
     * Notifies User u's team of his workout, 
     * allowing the team to contact it's 
     * individual mediator for handling 
     * 
     * @param u
     * @param t
     */
    public void notifyTeam(User u, Team t) {
        t.notifyWorkout(u, null);
    }

    /**
     * Checks a user's notifications 
     * 
     * @param u
     * @param t
     * 
     * @return A User's list of notifications 
     */
    public List<String> checkNotifications(User u, Team t) {
        return t.checkNotifications(u); 
    }

    /**
     * Starts a challenge for a team 
     * 
     * @param team
     * @return True if a Challenge is Started 
     *         False if a Challenge is Active 
     */
    public boolean startChallenge(Team team) {
        return team.startChallenge(dt); 
    }

    /**
     * If a team's challenge ends, add it to the teams 
     * previous challenge history 
     */
    public void checkChallenges() {
        for (Team t : teams) {
            if (t.getChallenge().newDay()) {
                t.endChallenge(); 
            }
        }
    }
}
