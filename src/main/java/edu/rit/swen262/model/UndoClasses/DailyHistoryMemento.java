package edu.rit.swen262.model.UndoClasses;

/**
 * Memento to encapsulate weight data
 * 
 * @author David Martinez
 */
public class DailyHistoryMemento implements Memento {
    /**
     * The weight at the time of the snapshot
     */
    private final double weight;

    /**
     * Creates a new DailyHistoryMemento
     * @param weight the current weight
     */
    public DailyHistoryMemento(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Weight restored.";
    }
}
