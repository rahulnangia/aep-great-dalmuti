package edu.berkeley.aep.dalmuti;

import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A class that provides utility methods for testing
 * @Author rahul
 * @Create Apr 2020
 */
public class TestUtils {

    /**
     * To get a Mock Players for a game
     * @param count
     * @return
     */
    public static List<Player> getPlayerMocks(int count){
        List<Player> players = new LinkedList<>();
        for(int i=0;i<count;i++){
            players.add(Mockito.mock(Player.class));
        }
        return players;
    }

    /**
     * Utility method to create a list of cards with given ranks
     * @param cardRanks
     * @return
     */
    public static List<Card> getCardListWithRanks(int[] cardRanks){
        List<Card> hand = new ArrayList<>(cardRanks.length);
        for(int rank: cardRanks) {
            hand.add(new Card(rank));
        }
        return hand;
    }

    /**
     * Initializes N players and returns.
     * @param count
     * @return
     */
    public static Pair<List<Player>, Game> initializeNPlayersAndAGame(int count) {
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
