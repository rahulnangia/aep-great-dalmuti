package edu.berkeley.aep.dalmuti;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.*;

/**
 *A class to test HumanPlayer
 * @Author rahul
 * @Create Apr 2020
 */
public class HumanPlayerTest {

    @Test
    public void selectingCardFromHand() {
        List<Card> hand = List.of(new Card(5), new Card(6), new Card(6));
        HumanPlayer mockHuman = Mockito.mock(HumanPlayer.class);
        when(mockHuman.readNextInput()).thenReturn(6);
        when(mockHuman.getPlayingHand()).thenReturn(hand);
        when(mockHuman.play(anyList())).thenCallRealMethod();

        List<Card> move = mockHuman.play(new LinkedList<>());
        verify(mockHuman, times(1)).buildMove(eq(2), eq(6));

    }

    @Test
    public void selectingCardsFromHandAfterRetry() {
        List<Card> hand = List.of(new Card(5), new Card(6), new Card(6));
        HumanPlayer mockHuman = Mockito.mock(HumanPlayer.class);
        //Try to choose invalid move first
        when(mockHuman.readNextInput()).thenReturn(6).thenReturn(5);
        when(mockHuman.getPlayingHand()).thenReturn(hand);
        when(mockHuman.play(anyList())).thenCallRealMethod();

        List<Card> move = mockHuman.play(List.of(new Card(6)));
        verify(mockHuman, times(1)).buildMove(eq(1), eq(5));

    }

    @Test
    public void retryWhenPlayerEntersNonExistantRank() {
        List<Card> hand = List.of(new Card(5), new Card(6), new Card(6));
        HumanPlayer mockHuman = Mockito.mock(HumanPlayer.class);
        //Try to choose invalid move first (Rank 2 is not present with player) and then has to pass
        when(mockHuman.readNextInput()).thenReturn(2).thenReturn(-1);
        when(mockHuman.getPlayingHand()).thenReturn(hand);
        when(mockHuman.play(anyList())).thenCallRealMethod();

        List<Card> move = mockHuman.play(List.of(new Card(3)));
        verify(mockHuman, times(0)).buildMove(anyInt(), anyInt());

    }

    @Test
    public void playerTriesToPassReturnsEmptyMove() {
        List<Card> hand = List.of(new Card(5), new Card(6), new Card(6));
        HumanPlayer mockHuman = Mockito.mock(HumanPlayer.class);
        //Try to choose invalid move first
        when(mockHuman.readNextInput()).thenReturn(-1);
        when(mockHuman.getPlayingHand()).thenReturn(hand);
        when(mockHuman.play(anyList())).thenCallRealMethod();

        List<Card> move = mockHuman.play(List.of(new Card(6)));
        verify(mockHuman, times(0)).buildMove(anyInt(), anyInt());

    }


}
