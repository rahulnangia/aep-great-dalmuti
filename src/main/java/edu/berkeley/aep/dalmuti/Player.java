package edu.berkeley.aep.dalmuti;

import java.util.*;

/**
 * A class that understands a player in the game.
 * @Author rahul
 * @Create Apr 2020
 *
 */
public class Player implements CardReceiver, CardPlayer {

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

    /**
     * This constructor is created for testing purposes
     * @param name
     * @param playingHand
     */
    public Player(String name, List<Card> playingHand) {
        this.name = name;
        this.playingHand = new ArrayList<>(playingHand);
    }

    public List<Card> getPlayingHand() {
        return new LinkedList<>(this.playingHand);
    }

    @Override
    public void receiveCard(Card card) {
        this.playingHand.add(card);
    }

    @Override
    public List<Card> play(List<Card> lastMove) {
        int lastMoveRank, expectedCardsToBePlayed;
        if(lastMove == null || lastMove.isEmpty()){
            lastMoveRank = Card.MAX_RANK+1;
            expectedCardsToBePlayed = -1;
        }else{
            lastMoveRank = lastMove.get(0).getRank();
            expectedCardsToBePlayed = lastMove.size();
        }

        //Find if potential move exists
        TreeMap<Integer, Integer> cardCountByRank = new TreeMap<>(Utils.getCardCountByRank(getPlayingHand()));
        Map.Entry<Integer, Integer> playable = cardCountByRank.lowerEntry(lastMoveRank);
        // Keep on lowering rank till we find same number of cards
        while(playable!=null){
            // If we have same number of cards available
            if(expectedCardsToBePlayed == -1){
                expectedCardsToBePlayed = playable.getValue();
                break;
            }else if(playable.getValue() >= expectedCardsToBePlayed){
                break;
            }
            playable = cardCountByRank.lowerEntry(playable.getKey());
        }
        List<Card> move = new LinkedList<>();

        // Find cards corresponding to move
        if(playable != null ){
            int count = 0;
            Iterator<Card> cardItr = playingHand.iterator();
            while(cardItr.hasNext()){
                Card card = cardItr.next();
                if(card.getRank() == playable.getKey()){
                    move.add(card);
                    cardItr.remove();
                    count++;
                }
                if(count == expectedCardsToBePlayed){
                    break;
                }
            }
        }
        return move;
    }

    /**
     * Tells if Game is over for a player
     * @return
     */
    public boolean isGameOver() {
        return playingHand == null || playingHand.isEmpty();
    }

    /**
     * Prints name of the player
     */
    public void printName() {
        System.out.println(name);
    }
}
