package edu.berkeley.aep.dalmuti;

import edu.berkeley.aep.dalmuti.exceptions.GameAlreadyFullException;
import edu.berkeley.aep.dalmuti.exceptions.InsufficientPlayersException;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
    public void startingGameDefinesPlayingOrderDistributesCardsStartsPlaying() {
        Game newGame = Mockito.mock(Game.class);
        Mockito.doCallRealMethod().when(newGame).startGame();
        doNothing().when(newGame).assignPlayingOrder();
        doNothing().when(newGame).distributeCards();
        doNothing().when(newGame).play();
        List<Player> playerMocks = TestUtils.getPlayerMocks(4);
        when(newGame.getCurrentPlayers()).thenReturn(playerMocks);
        when(newGame.getWinningOrder()).thenReturn(playerMocks);
        newGame.startGame();
        verify(newGame, times(1)).assignPlayingOrder();
        verify(newGame, times(1)).distributeCards();
        verify(newGame, times(1)).play();

    }

    @Test
    public void markingGameOverForPlayerRemovesFromPlayingOrderAndAddsToWinningOrder() {
        Pair<List<Player>, Game> playersAndGame = TestUtils.initializeNPlayersAndAGame(4);
        List<Player> players = playersAndGame.getFirst();
        Game newGame = playersAndGame.getSecond();

        Player finishedPlayer = players.get(1);
        newGame.markGameOver(finishedPlayer);
        //Check if removed from playing order
        assertFalse(newGame.getCurrentPlayers().contains(finishedPlayer));
        // Check inserted at end of winning order
        List<Player> winningOrder = newGame.getWinningOrder();
        assertEquals(finishedPlayer, winningOrder.get(winningOrder.size()-1));

    }

    @Test
    public void retainLastMoveWhenLastPlayerPasses() {
        List<Player> playerMocks =  TestUtils.getPlayerMocks(4);
        Player p1 = playerMocks.get(0);
        Player p2 = playerMocks.get(1);
        Player p3 = playerMocks.get(2);
        Player p4 = playerMocks.get(3);
        //Configure so that game ends in one round
        when(p1.isGameOver()).thenReturn(false).thenReturn(true);// Return true the second time
        when(p2.isGameOver()).thenReturn(false).thenReturn(true);// Return true the second time
        when(p3.isGameOver()).thenReturn(true);
        when(p4.isGameOver()).thenReturn(true);

        Game newGame = new Game(playerMocks);
        List<Card> lastMove = List.of(new Card(5));
        //P1 plays last move
        when(p1.play(anyList())).thenReturn(lastMove);
        // P2 passes
        when(p2.play(anyList())).thenReturn(new LinkedList());

        newGame.play();

        // P1's move should be last move for P3
        verify(p3, times(1)).play(lastMove);
    }

    @Test
    public void startNewRoundWhenTurnReturnsToSamePlayer() {
        List<Player> playerMocks =  TestUtils.getPlayerMocks(4);
        Player p1 = playerMocks.get(0);
        Player p2 = playerMocks.get(1);
        Player p3 = playerMocks.get(2);
        Player p4 = playerMocks.get(3);
        //Configure so that game ends in one round
        when(p1.isGameOver()).thenReturn(false).thenReturn(true);
        when(p2.isGameOver()).thenReturn(false).thenReturn(true); // Return true the second time
        when(p3.isGameOver()).thenReturn(false).thenReturn(true);
        when(p4.isGameOver()).thenReturn(false).thenReturn(true);

        Game newGame = new Game(playerMocks);
        List<Card> lastMove = List.of(new Card(5));
        //P1 plays last move
        when(p1.play(anyList())).thenReturn(lastMove);
        // P2,P3,P4 passes
        when(p2.play(anyList())).thenReturn(new LinkedList());
        when(p3.play(anyList())).thenReturn(new LinkedList());
        when(p4.play(anyList())).thenReturn(new LinkedList());

        newGame.play();

        // P1 should start a new Round
        verify(p1, times(2)).play(eq(new LinkedList<>()));

    }

    @Test
    public void startNewRoundWhenPreviousPlayerFinishes() {
        List<Player> playerMocks =  TestUtils.getPlayerMocks(4);
        Player p1 = playerMocks.get(0);
        Player p2 = playerMocks.get(1);
        Player p3 = playerMocks.get(2);
        Player p4 = playerMocks.get(3);
        //Configure so that game ends in one round
        when(p1.isGameOver()).thenReturn(false).thenReturn(true);
        when(p2.isGameOver()).thenReturn(true); // Return true the second time
        when(p3.isGameOver()).thenReturn(true);
        when(p4.isGameOver()).thenReturn(true);

        Game newGame = new Game(playerMocks);
        List<Card> lastMove = List.of(new Card(5));
        //P1 plays last move
        when(p1.play(anyList())).thenReturn(lastMove);
        // P2,P3,P4 passes
        when(p2.play(anyList())).thenReturn(new LinkedList());
        when(p3.play(anyList())).thenReturn(new LinkedList());
        when(p4.play(anyList())).thenReturn(new LinkedList());

        newGame.play();

        // P2 should use P1's last move and wins
        verify(p2, times(1)).play(eq(lastMove));
        // P3, P4 should start a new Round, coz both P2 and P3 exit
        verify(p3, times(1)).play(eq(new LinkedList<>()));
        verify(p4, times(1)).play(eq(new LinkedList<>()));

    }


}
