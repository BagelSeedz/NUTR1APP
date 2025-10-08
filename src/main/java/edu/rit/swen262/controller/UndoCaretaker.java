package edu.rit.swen262.controller;

import java.util.HashMap;
import java.util.Stack;

import edu.rit.swen262.model.UndoClasses.Memento;

/**
 * UndoCaretaker stores Memento history and provides an undo
 * method used to instruct originators to restore Memento state
 * 
 * @author David Martinez
 */
public class UndoCaretaker {
    /**
     * Stores the history of originators that were last used in FILO order
     */
    private final Stack<Originator> actionHistory;

    /**
     * Stores DailyHistoryMementos in FILO order
     */
    private final Stack<Memento> dailyHistory;

    /**
     * Stores UserMementos in FILO order
     */
    private final Stack<Memento> userHistory;

    /**
     * Stores StockMementos in FILO order
     */
    private final Stack<Memento> stockHistory;

    /**
     * Originator for DailyHistoryMemento
     * Provides methods for restoring and creating DailyHistoryMementos
     */
    private final PersonalHistoryController phc;

    /**
     * Originator for UserMemento
     * Provides methods for restoring and creating UserMementos
     */
    private final UserController userController;

    /**
     * Originator for StockMemento
     * Provides methods for restoring and creating StockMementos
     */
    private final FoodController foodController;

    /**
     * Maps originator to its respective Stack of Mementos
     */
    private final HashMap<Originator, Stack<Memento>> originator_to_history;

    /**
     * Creates an UndoCaretaker
     * @param phc PersonalHistoryController
     * @param userController UserController
     * @param foodController FoodController
     */
    public UndoCaretaker(PersonalHistoryController phc, UserController userController, FoodController foodController) {
        this.actionHistory = new Stack<>();
        this.dailyHistory = new Stack<>();
        this.userHistory = new Stack<>();
        this.stockHistory = new Stack<>();
        this.phc = phc;
        this.userController = userController;
        this.foodController = foodController;

        this.originator_to_history = new HashMap<>();
        originator_to_history.put(phc, dailyHistory);
        originator_to_history.put(userController, userHistory);
        originator_to_history.put(foodController, stockHistory);
    }

    /**
     * Creates and stores a new DailyHistoryMemento
     */
    public void storeDaily() {
        dailyHistory.push(phc.createMemento());
        actionHistory.push(phc);
    }

    /**
     * Creates and stores a new UserMemento
     */
    public void storeUser() {
        userHistory.push(userController.createMemento());
        actionHistory.push(userController);
    }

    /**
     * Creates and stores a new StockMemento
     */
    public void storeStock() {
        stockHistory.push(foodController.createMemento());
        actionHistory.push(foodController);
    }

    /**
     * Restores the state changed by the previous action
     * @return String to be displayed to the user
     */
    public String undo() {
        if (actionHistory.empty())
            return "Failed to undo.";
            
        Originator originator = actionHistory.pop();
        Memento memento = originator_to_history.get(originator).pop();
        originator.restoreMemento(memento);
        return memento.toString();
    }

    /**
     * Clears all Memento history
     * Should be called when switching users
     */
    public void purge() {
        actionHistory.clear();
        dailyHistory.clear();
        userHistory.clear();
        stockHistory.clear();
    }
}
