package edu.berkeley.aep.dalmuti;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * A class to test Card
 * @Author rahul
 * @Create Apr 2020
 */
public class CardTest {

    @Test
    public void deckContainsAllCardsAsPerRules() {
        Map<Integer, Integer> cardRankMap = new HashMap<>();
        for(Card card: Card.getACardDeck()){
            int count = cardRankMap.getOrDefault(card.getRank(), 0);
            cardRankMap.put(card.getRank(), count+1);
        }

        int expectedCount;
        for(Map.Entry<Integer, Integer> cardRankEntry: cardRankMap.entrySet()){
            if(cardRankEntry.getKey() == 0){
                // Only 1 Dalmuti present
                expectedCount = 1;
            }else if(cardRankEntry.getKey() == 13){
                // 2 Jesters can be there
                expectedCount = 2;
            }else {
                // Count of rest of cards should be same as their rank, i.e, 2 2's, 3 3's, etc
                expectedCount = cardRankEntry.getKey();
            }
            assertEquals(expectedCount, (int) cardRankEntry.getValue());
        }
    }
}
