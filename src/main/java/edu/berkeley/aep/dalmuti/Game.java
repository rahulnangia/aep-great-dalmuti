package edu.berkeley.aep.dalmuti;

import java.util.*;

/**
 * A class that ndertands the current Game, by the deck and the current players
 * @Author rahul
 * @Create Apr 2020
 */
public class Game {

    /**
     * Maintains a set of unique players
     */
    private final Set<Player> players;

    /**
     * Random generator for the Game
     */
    private final Random randomizer;

    /**
     * Contains the current playing order
     */
    private List<Player> playingOrder;

    public Game() {
        this.players = new HashSet<>();
        this.playingOrder = new ArrayList<>();
        this.randomizer = new Random(System.currentTimeMillis());
    }

    /**
     * Registers a player in the game and returns True if successful otherwise False
     * @param player
     * @return
     */
    public boolean registerPlayer(Player player) {
        if(this.players.add(player)){
            playingOrder.add(player);
            return true;
        }
        return false;
    }

    /**
     * Gives the current playing order of the Game
     * @return
     */
    public List<Player> getCurrentPlayers() {
        return playingOrder;
    }

    /**
     * Method used to assign a playing order for the Game
     * @return
     */
    public void assignPlayingOrder() {
        Collections.shuffle(playingOrder, randomizer);
    }
}
