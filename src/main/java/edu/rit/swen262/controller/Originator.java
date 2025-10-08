package edu.rit.swen262.controller;

public interface Originator {
    /**
     * Creates a snapshot of the current state
     * @return
     */
    Object createMemento();

    /**
     * Restores a snapshot of some previous state
     * @param memento Memento encapsulating previous state
     */
    void restoreMemento(Object memento);
}
