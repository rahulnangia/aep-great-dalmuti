package edu.berkeley.aep.dalmuti;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * A class that understands a player in the game.
 * @Author rahul
 * @Create Apr 2020
 *
 */
public class Player implements CardReceiver {

    /**
     * Holds the name of the player
     */
    private final String name;

    /**
     * Keeps a record of the playing hand of the player
     */
    private List<Card> playingHand;

    /**
     * Creates an instance of player
     * @param name
     *
     */
    public Player(String name) {
        this.name = name;
        this.playingHand = new ArrayList<>();
    }

    public List<Card> getPlayingHand() {
        return new LinkedList<>(this.playingHand);
    }

    @Override
    public void receiveCard(Card card) {
        this.playingHand.add(card);
    }
}
