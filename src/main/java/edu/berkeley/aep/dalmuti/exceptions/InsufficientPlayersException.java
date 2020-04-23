package edu.berkeley.aep.dalmuti.exceptions;

/**
 * This exception is thrown when there are not enough players to start a game
 * @Author rahul
 * @Create Apr 2020
 */
public class InsufficientPlayersException extends RuntimeException {

    public InsufficientPlayersException() {
        super("There must be minimum 4 players to start a game");
    }
}
