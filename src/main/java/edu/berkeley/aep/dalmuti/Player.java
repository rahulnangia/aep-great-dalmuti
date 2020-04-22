package edu.berkeley.aep.dalmuti;

import java.util.Calendar;
import java.util.Date;

/**
 * A class that understands a player in the game.
 * @Author rahul
 * @Create Apr 2020
 *
 */
public class Player {

    /**
     * Holds the name of the player
     */
    private final String name;

    /**
     * Holds the lucky number of the player to bring the role of their destiny
     */
    private final int luckyNumber;

    /**
     * Store's today's date at start of the day. Again, to factor in destiny on that day
     */
    private final Calendar todaysDate;

    /**
     * Creates an instance of player
     * @param name
     * @param luckyNumber
     */
    public Player(String name, int luckyNumber) {
        this.name = name;
        this.luckyNumber = luckyNumber;
        this.todaysDate = Calendar.getInstance();
        //Set date to start of the Day
        this.todaysDate.set(
                this.todaysDate.get(Calendar.YEAR),
                this.todaysDate.get(Calendar.MONTH),
                this.todaysDate.get(Calendar.DATE),
                0,
                0,
                0
        );
    }
}
