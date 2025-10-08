package edu.rit.swen262.ui; 

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.rit.swen262.controller.TeamController;
import edu.rit.swen262.controller.UndoCaretaker;
import edu.rit.swen262.controller.UserController;
import edu.rit.swen262.controller.WorkoutController;
import edu.rit.swen262.model.Challenge;
import edu.rit.swen262.model.DateTracker;
import edu.rit.swen262.model.Team;
import edu.rit.swen262.model.User; 

/**
 * @author Dominick Polakowski 
 */
public class TeamView {
    private final Scanner scanner; 
    private TeamController tc;
    private WorkoutController wc;  
    private UserController uc; 
    private DateTracker dt; 
    private UndoCaretaker utc;

    public TeamView(TeamController tc, WorkoutController wc, UserController uc, DateTracker dt, UndoCaretaker utc) {
        this.scanner = new Scanner(System.in); 
        this.tc = tc; 
        this.wc = wc; 
        this.uc = uc; 
        this.dt = dt; 
        this.utc = utc;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\nTeams\n");
            System.out.println("1. Join Team"); 
            System.out.println("2. Invite Members");
            System.out.println("3. View Member Workout Histories");
            System.out.println("4. Start Challenge");
            System.out.println("5. View Challenge");
            System.out.println("6. View Most Recent Challenge");
            System.out.println("7. Leave Team");
            System.out.println("8. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    joinTeam(); 
                    break;
                case 2:
                    inviteMembers();
                    break;
                case 3:
                    viewHistories();
                    break;
                case 4:
                    startChallenge();
                    break;
                case 5:
                    viewChallenge();
                    break;
                case 6: 
                    viewMostRecentChallenge(); 
                    break; 
                case 7: 
                    leaveTeam(); 
                    break; 
                case 8: 
                    return; 
                default:
                    System.out.println("Enter 1 or 2");
            }
        }
    }

    /**
     * User can Join a team by browsing all current 
     * invites to teams or create a team. 
     * 
     * The User cannot actively be in a team 
     * when joining a team. 
     */
    public void joinTeam() {
        User user = uc.getCurrentUser(); 

        if (user.getTeam() == null) {
            System.out.println("Leave your team first to join another team"); 
        } 

        System.out.println("\nInvites: ");

        List<Team> invites = user.viewInvites(); 
        for (int i = 0; i < invites.size(); i++) {
            System.out.println(i+1 + ". " + invites.get(i).toString()); 
        }

        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); 
        
        utc.storeUser(); // Save snapshot before changing state
        tc.addUser(user, invites.get(choice-1)); 
    }

    /**
     * Users can invite other users to their team 
     */
    public void inviteMembers() {
        Team team = uc.getCurrentUser().getTeam(); 

        if (team == null) {
            System.out.println("Join or create a team to invite members");
            return; 
        }

        System.out.println("Enter a username to invite: ");
        String username = scanner.nextLine().toLowerCase();
        scanner.nextLine(); 

        for (User u : uc.getUsers().values()) {
            if (username.equals(u.getUsername())) {
                u.addInvites(team); 
                return; 
            }
        }

        System.out.println("Username was not found"); 
    }

    /**
     * Users can see team members' workout histories 
     */
    public void viewHistories() {
        Team team = uc.getCurrentUser().getTeam(); 

        if (team == null) {
            System.out.println("Join or create a team to view member workout histories");
        }

        System.out.println("View Team Member Workout Histories: ");

        List<User> users = team.getUsers(); 
        for (int i = 0; i < users.size(); i++) {
            System.out.println(i+1 + ". " + users.get(i).toString()); 
        }

        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); 

        System.out.println(users.get(choice-1).getName() + "'s Workout History: " + wc.getPreviousWorkouts(users.get(choice-1))); 
    }

    /**
     * Users can start week-long team challenges 
     * if a current one isn't currently active 
     */
    public void startChallenge() {
        uc.getCurrentUser().getTeam().startChallenge(dt); 
    }

    /**
     * Users can view the weekly challenge if 
     * one is currenlty active 
     */
    public void viewChallenge() {
        boolean challenge = uc.getCurrentUser().getTeam().activeChallenge(); 
        if (challenge) {
            Map<User, Integer> stats = uc.getCurrentUser().getTeam().getChallenge().getStats(); 
            LocalDate endDate = uc.getCurrentUser().getTeam().getChallenge().getEndDate(); 
            System.out.println("\nChallenge End: " + endDate + "\nChallenge Ranking: "); 

            stats.entrySet().stream()
                .sorted(Map.Entry.<User, Integer>comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(entry.getKey() + " : " + entry.getValue())); 

        } else {
            System.out.println("\nThere is no current active challenge for " + uc.getCurrentUser().getTeam().toString());
        }
    }

    /**
     * Prints the most recent challenge stats 
     */
    public void viewMostRecentChallenge() {
        Challenge challenge = uc.getCurrentUser().getTeam().getMostRecentChallenge(); 
        if (challenge != null) {
            Map<User, Integer> stats = challenge.getStats(); 
            LocalDate endDate = challenge.getEndDate(); 
            System.out.println("\nChallenge End: " + endDate + "\nChallenge Ranking: "); 

            stats.entrySet().stream()
                .sorted(Map.Entry.<User, Integer>comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(entry.getKey() + " : " + entry.getValue())); 

        } else {
            System.out.println("\nTeam " + uc.getCurrentUser().getTeam().toString() + " has never had a challenge");
        }
    }

    /**
     * Users can leave their team 
     */
    public void leaveTeam() {
        tc.removeUser(uc.getCurrentUser(), uc.getCurrentUser().getTeam()); 
        uc.getCurrentUser().leaveTeam(); 
    }
}