package edu.rit.swen262.model.TeamMediator;

import java.util.List;

import edu.rit.swen262.model.User;
import edu.rit.swen262.model.WorkoutModel;

/**
 * @author Dominick Polakowski 
 */
public interface NotificationsInterface {
    /**
     * Notifies the team about a user's workout 
     * 
     * @param user
     * @param workout
     */
    void notify(User user, WorkoutModel workout); 

    /**
     * Stores notifications for offline users to be 
     * sent out once they log back into the system 
     * 
     * @param user who gets the notification 
     * @param message to be stored 
     */
    void storeOffline(User user, String message); 

    /**
     * Checks for unread notifications for 
     * any user who logs in and sends them 
     * 
     * @param user
     * 
     * @return The notifications for the user in 
     *         the form of a List of Strings 
     */
    List<String> checkNotifications(User user); 
}