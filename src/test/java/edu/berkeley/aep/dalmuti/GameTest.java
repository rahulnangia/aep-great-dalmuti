package edu.berkeley.aep.dalmuti;

import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author rahul
 * @Create Apr 2020
 */
public class GameTest {

    @Test
    public void registerPlayerShouldAddPlayerToGamePlayers() {
        Player player = new Player("Rahul", 22);
        Game newGame = new Game();
        assertTrue(newGame.registerPlayer(player));
        assertTrue(newGame.getCurrentPlayers().contains(player));
    }

    @Test
    public void playersWithSameNameLuckyNosShouldBeAbleToRegister() {
        Player player = new Player("Rahul", 22);
        Player anotherPlayer = new Player("Rahul", 22);
        Game newGame = new Game();
//        Add the players
        assertTrue(newGame.registerPlayer(player));
        assertTrue(newGame.registerPlayer(anotherPlayer));
//        check if added
        assertEquals(2, newGame.getCurrentPlayers().size());
        assertTrue(newGame.getCurrentPlayers().contains(player));
        assertTrue(newGame.getCurrentPlayers().contains(anotherPlayer));
    }

    @Test
    public void registeringSamePlayerTwiceDoesNotAddThemToGame() {
        Player player = new Player("Rahul", 22);
        Game newGame = new Game();
        assertTrue(newGame.registerPlayer(player));
        assertFalse(newGame.registerPlayer(player));
        assertEquals(1, newGame.getCurrentPlayers().size());
        assertTrue(newGame.getCurrentPlayers().contains(player));
    }

    @Test
    public void playersShouldBePlacedInADifferentOrderThanRegistering() {
        int count = 10;
        List<Player> players = initializeNPlayers(count);
        Game newGame = new Game();
        for (Player player : players) {
            newGame.registerPlayer(player);
        }
        newGame.assignPlayingOrder();
        Iterator<Player> playingOrderItr = newGame.getCurrentPlayers().iterator();
        int mismatchCount = 0;
        for (Player originalOrderPlayer : players) {
            Player playerForPlayingOrder = playingOrderItr.next();
//            Check if current player in playing order belongs to original but is not at the same place
            if (players.contains(playerForPlayingOrder) && originalOrderPlayer != playerForPlayingOrder) {
                mismatchCount++;
            }
        }
        assertTrue(mismatchCount > 0);
    }

    /**
     * Initializes N players and returns.
     *
     * @param count
     * @return
     */
    public List<Player> initializeNPlayers(int count) {
        List<Player> list = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            list.add(new Player("abc", 10));
        }
        return list;
    }

}
