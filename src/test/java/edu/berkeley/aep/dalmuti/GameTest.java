package edu.berkeley.aep.dalmuti;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author rahul
 * @Create Apr 2020
 */
public class GameTest {

    @Test
    public void registerPlayerShouldAddPlayerToGamePlayers(){
        Player player = new Player("Rahul", 22);
        Game newGame = new Game();
        assertTrue(newGame.registerPlayer(player));
        assertTrue(newGame.getCurrentPlayers().contains(player));
    }

    @Test
    public void playersWithSameNameLuckyNosShouldBeAbleToRegister(){
        Player player = new Player("Rahul", 22);
        Player anotherPlayer = new Player("Rahul", 22);
        Game newGame = new Game();
        assertTrue(newGame.registerPlayer(player));
        assertTrue(newGame.registerPlayer(anotherPlayer));
        assertEquals(2, newGame.getCurrentPlayers().size());
        assertTrue(newGame.getCurrentPlayers().contains(player));
        assertTrue(newGame.getCurrentPlayers().contains(anotherPlayer));
    }

    @Test
    public void registeringSamePlayerTwiceDoesNotAddThemtoGame(){
        Player player = new Player("Rahul", 22);
        Game newGame = new Game();
        assertTrue(newGame.registerPlayer(player));
        assertFalse(newGame.registerPlayer(player));
        assertEquals(1, newGame.getCurrentPlayers().size());
        assertTrue(newGame.getCurrentPlayers().contains(player));
    }

}
