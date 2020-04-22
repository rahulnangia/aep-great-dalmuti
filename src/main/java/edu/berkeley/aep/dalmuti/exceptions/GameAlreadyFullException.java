package edu.berkeley.aep.dalmuti.exceptions;

/**
 * An exception thrown when the game is full and no more players can be added
 * @Author rahul
 * @Create Apr 2020
 *
 */
public class GameAlreadyFullException extends RuntimeException {
    public GameAlreadyFullException() {
        super("GAME already full. Please try again.");
    }
}
