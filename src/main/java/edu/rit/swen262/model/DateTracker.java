package edu.rit.swen262.model;
import java.io.Serializable;
import java.time.LocalDate;

/*
 * @author: Kendra Patel
 * keeps track of current date and moves foward when the new day starts
 */

public class DateTracker implements Serializable {
    @SuppressWarnings("unused")
    private static final long serial = 1L; 

    private LocalDate currentDate;


    //Default constructor for Jackson
    public DateTracker() {
        this.currentDate = LocalDate.now();
    }

    public DateTracker(LocalDate starDate) {
        this.currentDate = starDate;   
    }

    /*
     * gets the current date
     */
    public LocalDate getCurrentDate() {
        return currentDate;
    }

    /**
     * sets the current date
     */
    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    /**
     * goes to the next day
     */
    public void goToNextDay() {
        this.currentDate = currentDate.plusDays(1);
    }
    
}
