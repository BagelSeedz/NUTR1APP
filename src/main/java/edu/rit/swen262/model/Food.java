package edu.rit.swen262.model;

/**
 * @author Emily Santillanes
 */
public interface Food {
    public abstract String getName();
    public abstract int getCalories();
    public abstract float getFat();
    public abstract float getProtein();
    public abstract float getFiber();
    public abstract float getCarbs();
    // public abstract void addChild(Object obj);
    // public abstract void removeChild(Object obj);
    // public abstract List children();
}
