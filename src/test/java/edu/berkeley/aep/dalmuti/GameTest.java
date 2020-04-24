package edu.berkeley.aep.dalmuti;

import edu.berkeley.aep.dalmuti.exceptions.GameAlreadyFullException;
import edu.berkeley.aep.dalmuti.exceptions.InsufficientPlayersException;
import org.junit.Test;

import java.util.*;

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
        Pair<List<Player>, Game> playersAndGame = TestUtils.initializeNPlayersAndAGame(8);
        Game newGame = playersAndGame.getSecond();
        newGame.registerPlayer(new Player("Extra"));
    }

    @Test
    public void playersShouldBePlacedInADifferentOrderThanRegistering() {
        Pair<List<Player>, Game> playersAndGame = TestUtils.initializeNPlayersAndAGame(8);
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
        Pair<List<Player>, Game> playersAndGame = TestUtils.initializeNPlayersAndAGame(8);
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

        Pair<List<Player>, Game> playersAndGame = TestUtils.initializeNPlayersAndAGame(8);
        List<Player> players = playersAndGame.getFirst();
        Game newGame = playersAndGame.getSecond();
        newGame.distributeCards();

        List<Card> collection = new LinkedList<>();
        for(Player player: players){
            collection.addAll(player.getPlayingHand());
        }
        CardTest.verifyCardRankMapAsPerRules(Utils.getCardCountByRank(collection));
    }

    @Test(expected = InsufficientPlayersException.class)
    public void startingGameBeforeThreePlayersJoinGivesException() {
        Pair<List<Player>, Game> playersAndGame = TestUtils.initializeNPlayersAndAGame(3);
        Game newGame = playersAndGame.getSecond();
        newGame.startGame();
    }

    @Test
    public void playerPlaysTwo11sWhenLastMoveTwo12s() {
        List<Card> playingHand = TestUtils.getCardListWithRanks(new int[]{3,3,4,5,11,11,11,11,12,12});
        int initialHandSize = playingHand.size();
        Player player = new Player("p1", playingHand);

        List<Card> lastMove = TestUtils.getCardListWithRanks(new int[]{12,12});
        //Check cards played
        checkValidMove(player.play(lastMove), playingHand.subList(4, 6));
        //check balance cards
        assertEquals(initialHandSize-lastMove.size(), player.getPlayingHand().size());
    }

    @Test
    public void playerPlaysTwo3sWhenLastMoveTwo11s() {
        List<Card> playingHand = TestUtils.getCardListWithRanks(new int[]{3,3,4,5,11,11,11,11,12,12});
        int initialHandSize = playingHand.size();
        Player player = new Player("p1", playingHand);

        List<Card> lastMove = TestUtils.getCardListWithRanks(new int[]{11,11});
        //Check cards played
        checkValidMove(player.play(lastMove), playingHand.subList(0, 2));
        //check balance cards
        assertEquals(initialHandSize-lastMove.size(), player.getPlayingHand().size());
    }


    /**
     * Verifies if a move is valid
     * @param move - the move to verify
     * @param expectedCards - expected cards that should have been played
     * @return
     */
    public void checkValidMove(List<Card> move, List<Card> expectedCards) {
        assertEquals(expectedCards.size(), move.size());
        for (int i=0;i<move.size();i++){
            assertEquals(expectedCards.get(i), move.get(i));
        }
    }

}
