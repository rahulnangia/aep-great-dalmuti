package edu.berkeley.aep.dalmuti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author rahul
 * @Create Apr 2020
 */
public class Utils {

    public static Map<Integer, Integer> getCardCountByRank(List<Card> cardDeck) {
        Map<Integer, Integer> cardRankMap = new HashMap<>();
        for(Card card: cardDeck){
            int count = cardRankMap.getOrDefault(card.getRank(), 0);
            cardRankMap.put(card.getRank(), count+1);
        }
        return cardRankMap;
    }
}
