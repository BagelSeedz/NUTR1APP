package edu.rit.swen262.model.UndoClasses;

import edu.rit.swen262.model.Team;
import edu.rit.swen262.model.GoalInterface.Goal;

/**
 * Memento encapsulating Goal and Team data
 * 
 * @author David Martinez
 */
public class UserMemento implements Memento{
    /**
     * The goal at the time of the snapshot
     */
    private Goal goal;

    /**
     * The team at the time of the snapshot
     */
    private Team team;

    /**
     * Creates a UserMemento
     * @param goal The current Goal 
     * @param team The current Team
     */
    public UserMemento(Goal goal, Team team) {
        this.goal = goal;
        this.team = team;
    }

    public Goal getGoal() {
        return goal;
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return "Goal/Team restored";
    }
}
