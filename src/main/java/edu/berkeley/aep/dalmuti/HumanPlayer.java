package edu.berkeley.aep.dalmuti;

import java.util.*;

/**
 * A class that understands a Human Player which takes user input
 *
 * @Author rahul
 * @Create Apr 2020
 */
public class HumanPlayer extends Player {

    /**
     * Scanner to read input
     */
    private Scanner scanner;

    /**
     * Overridden constructor
     *
     * @param name
     */
    public HumanPlayer(String name) {
        super(name);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Constructor for testing
     *
     * @param name
     * @param playingHand
     */
    public HumanPlayer(String name, List<Card> playingHand) {
        super(name, playingHand);
        this.scanner = new Scanner(System.in);
    }

    @Override
    public List<Card> play(List<Card> lastMove) {
        System.out.print(String.format("[%s] Select card's rank to play from Deck [", this.name));
        List<Card> playingHand = getPlayingHand();
        Map<Integer, Integer> cardRankMap = Utils.getCardCountByRank(playingHand);
        cardRankMap.forEach((rank, count) -> System.out.print(String.format("%d->(%d), ", rank, count)));
        System.out.println("]. -1 to Pass");


        int selectedRank = this.readNextInput();
        if(selectedRank!= -1 && !lastMove.isEmpty()) {
            while (selectedRank >= lastMove.get(0).getRank() ||
                    !cardRankMap.containsKey(selectedRank) || cardRankMap.get(selectedRank) < lastMove.size()) {
                System.out.println("Invalid Card Selected, choose again");
                selectedRank = this.readNextInput();
                if(selectedRank == -1){
                    break;
                }
            }
        }
        if(selectedRank == -1){
            return new LinkedList<>();
        }
        int expectedCardsToPlay = lastMove.isEmpty() ? cardRankMap.get(selectedRank) : lastMove.size();
        return buildMove(expectedCardsToPlay, selectedRank);


    }

    /**
     * Reads next input from user
     * @return
     */
    public int readNextInput(){
        return scanner.nextInt();
    }

}
