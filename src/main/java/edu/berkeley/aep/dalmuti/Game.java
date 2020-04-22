package edu.berkeley.aep.dalmuti;

import java.util.*;

/**
 * @Author rahul
 * @Create Apr 2020
 */
public class Game {
    private final Set<Player> players;

    public Game() {
        this.players = new HashSet<>();
    }

    public boolean registerPlayer(Player player) {
        return this.players.add(player);
    }

    public List<Player> getCurrentPlayers() {
        return new LinkedList<>(players);
    }
}
