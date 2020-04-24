package edu.berkeley.aep.dalmuti;

import java.util.List;

/**
 * @Author rahul
 * @Create Apr 2020
 */
public interface CardPlayer {
    List<Card> play(List<Card> lastMove);
    boolean isGameOver();
    void onGameOver();
}
