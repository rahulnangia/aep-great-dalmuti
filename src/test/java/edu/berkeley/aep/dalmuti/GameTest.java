package edu.berkeley.aep.dalmuti;

import edu.berkeley.aep.dalmuti.exceptions.GameAlreadyFullException;
import edu.berkeley.aep.dalmuti.exceptions.InsufficientPlayersException;
import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * A class to test the Game
 * @Author rahul
 * @Create Apr 2020
 */
public class GameTest {

    @Test
    public void registerPlayerShouldAddPlayerToGamePlayers() {
        Player player = new Player("Rahul");
        Game newGame = new Game();
        assertTrue(newGame.registerPlayer(player));
        assertTrue(newGame.getCurrentPlayers().contains(player));
    }

    @Test
    public void playersWithSameNameLuckyNosShouldBeAbleToRegister() {
        Player player = new Player("Rahul");
        Player anotherPlayer = new Player("Rahul");
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
        Player player = new Player("Rahul");
        Game newGame = new Game();
        assertTrue(newGame.registerPlayer(player));
        assertFalse(newGame.registerPlayer(player));
        assertEquals(1, newGame.getCurrentPlayers().size());
        assertTrue(newGame.getCurrentPlayers().contains(player));
    }

    @Test(expected = GameAlreadyFullException.class)
    public  void registeringMoreThan8PlayersThrowsException() {
        Pair<List<Player>, Game> playersAndGame = initializeNPlayersAndAGame(8);
        Game newGame = playersAndGame.getSecond();
        newGame.registerPlayer(new Player("Extra"));
    }

    @Test
    public void playersShouldBePlacedInADifferentOrderThanRegistering() {
        Pair<List<Player>, Game> playersAndGame = initializeNPlayersAndAGame(8);
        List<Player> players = playersAndGame.getFirst();
        Game newGame = playersAndGame.getSecond();

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

    @Test
    public void eachPlayerGetsOneCardOnDistribution() {
        Pair<List<Player>, Game> playersAndGame = initializeNPlayersAndAGame(8);
        List<Player> players = playersAndGame.getFirst();
        Game newGame = playersAndGame.getSecond();
        newGame.distributeCards();
        for(Player player: players) {
            assertTrue(player.getPlayingHand().size() > 0);
        }
    }


    /**
     * This test shows that the deck is completely distributed
     */
    @Test
    public void combiningPlayingHandOfAllPlayersShouldCompleteDeck() {

        Pair<List<Player>, Game> playersAndGame = initializeNPlayersAndAGame(8);
        List<Player> players = playersAndGame.getFirst();
        Game newGame = playersAndGame.getSecond();
        newGame.distributeCards();

        List<Card> collection = new LinkedList<>();
        for(Player player: players){
            collection.addAll(player.getPlayingHand());
        }
        CardTest.verifyCardRankMapAsPerRules(CardTest.getCardCountByRank(collection));
    }

    @Test(expected = InsufficientPlayersException.class)
    public void startingGameBeforeThreePlayersJoinGivesException() {
        Pair<List<Player>, Game> playersAndGame = initializeNPlayersAndAGame(3);
        List<Player> players = playersAndGame.getFirst();
        Game newGame = playersAndGame.getSecond();
        newGame.startGame();
    }

    /**
     * Initializes N players and returns.
     *
     * @param count
     * @return
     */
    public Pair<List<Player>, Game> initializeNPlayersAndAGame(int count) {
        List<Player> list = new LinkedList<>();
        Game newGame = new Game();
        for (int i = 0; i < count; i++) {
            Player player = new Player("abc");
            list.add(player);
            newGame.registerPlayer(player);
        }
        return new Pair<>(list, newGame);
    }

}
