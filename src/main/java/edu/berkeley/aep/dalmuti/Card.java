package edu.berkeley.aep.dalmuti;

import java.util.*;

/**
 * A card that understands a Playing card containing just number rank and no suits
 * @Author rahul
 * @Create Apr 2020
 */
public class Card {

    /**
     * Max rank in the Deck
     */
    public static final int MAX_RANK = 12;

    /**
     * The entire Deck
     */
    private static final List<Card> CARD_DECK;

    static {
        CARD_DECK = new LinkedList<>();
        for(int card = 1; card<13;card++){
            for(int j=0;j<card;j++) {
                CARD_DECK.add(new Card(card));
            }
        }
        //Add 1 Great Dalmuti
        CARD_DECK.add(new Card(0));
//        JESTER cards removed for simplicity
//        CARD_DECK.add(new Card(13));
//        CARD_DECK.add(new Card(13));
    }

    public static List<Card> getACardDeck() {
        return new LinkedList<>(CARD_DECK);
    }

    private final int rank;

    public Card(int rank) {
        this.rank = rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return rank == card.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank);
    }

    public int getRank() {
        return rank;
    }
}
