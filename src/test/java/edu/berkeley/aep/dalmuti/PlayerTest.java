package edu.berkeley.aep.dalmuti;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @Author rahul
 * @Create Apr 2020
 */
public class PlayerTest {

    @Test
    public void addCardToHandOnReceivingCard() {
        Player p1 = new Player("p1");
        Card card1 = new Card(5);
        p1.receiveCard(card1);
        Card card2 = new Card(6);
        p1.receiveCard(card2);
        p1.getPlayingHand().contains(card1);
        p1.getPlayingHand().contains(card2);
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

    @Test
    public void playerPassesTurnWhenSameNumberOfCardsNotFound() {
        List<Card> playingHand = TestUtils.getCardListWithRanks(new int[]{3,3,4,5,11,11,11,11,12,12});
        int initialHandSize = playingHand.size();
        Player player = new Player("p1", playingHand);

        List<Card> lastMove = TestUtils.getCardListWithRanks(new int[]{11,11,11});
        //Check cards played is empty
        assertEquals(0, player.play(lastMove).size());
        //check balance cards same as initial
        assertEquals(initialHandSize, player.getPlayingHand().size());
    }

    @Test
    public void playerPassesTurnWhenBetterRankCardsNotFound() {
        List<Card> playingHand = TestUtils.getCardListWithRanks(new int[]{3,3,4,5,11,11,11,11,12,12});
        int initialHandSize = playingHand.size();
        Player player = new Player("p1", playingHand);

        List<Card> lastMove = TestUtils.getCardListWithRanks(new int[]{2});
        //Check cards played is empty
        assertEquals(0, player.play(lastMove).size());
        //check balance cards same as initial
        assertEquals(initialHandSize, player.getPlayingHand().size());
    }

    @Test
    public void playerPlaysAllCardsWithMaxRankWhenStartingRoundWithLastMoveEmpty() {
        List<Card> playingHand = TestUtils.getCardListWithRanks(new int[]{3,3,4,5,11,11,11,11,12,12});
        int initialHandSize = playingHand.size();
        Player player = new Player("p1", playingHand);
        //Check move
        checkValidMove(player.play(new LinkedList<>()), playingHand.subList(8, 10) );
        //check balance cards
        assertEquals(initialHandSize-2, player.getPlayingHand().size());
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
