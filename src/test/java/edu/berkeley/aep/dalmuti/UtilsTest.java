package edu.berkeley.aep.dalmuti;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * A class that provides test for Utils class
 * @Author rahul
 * @Create Apr 2020
 */
public class UtilsTest {

    @Test
    public void testCase1(){
        List<Card> cards = TestUtils.getCardListWithRanks(new int[]{2,3,2,4,4,5,4,6,6});
        Map<Integer, Integer> cardCountByRank = Utils.getCardCountByRank(cards);
        assertEquals(5, cardCountByRank.size());
        assertEquals(2, (int)cardCountByRank.get(2));
        assertEquals(1, (int)cardCountByRank.get(3));
        assertEquals(3, (int)cardCountByRank.get(4));
        assertEquals(1, (int)cardCountByRank.get(5));
        assertEquals(2, (int)cardCountByRank.get(6));
    }
}
