package edu.berkeley.aep.dalmuti;

import edu.berkeley.aep.dalmuti.exceptions.GameAlreadyFullException;
import edu.berkeley.aep.dalmuti.exceptions.InsufficientPlayersException;

import java.util.*;

/**
 * A class that ndertands the current Game, by the deck and the current players
 *
 * @Author rahul
 * @Create Apr 2020
 */
public class Game {

    /**
     * Maintains a set of unique players
     */
    private final Set<Player> players;

    /**
     * Random generator for the Game
     */
    private final Random randomizer;

    /**
     * Contains the current playing order
     */
    private List<Player> playingOrder;

    /**
     * MAX nos of players allowed in a game
     */
    private final int MAX_PLAYERS = 8;

    /**
     * Maintains the winning order for Players
     */
    private List<Player> winningOrder;

    public Game() {
        this.players = new HashSet<>();
        this.playingOrder = new LinkedList<>();
        this.winningOrder = new LinkedList<>();
        this.randomizer = new Random(System.currentTimeMillis());
    }

    /**
     * Registers a player in the game and returns True if successful otherwise False
     *
     * @param player
     * @return
     */
    public boolean registerPlayer(Player player) {
        if(this.players.size() == MAX_PLAYERS){
            throw new GameAlreadyFullException();
        }
        if (this.players.add(player)) {
            playingOrder.add(player);
            return true;
        }
        return false;
    }

    /**
     * Gives the current playing order of the Game
     *
     * @return
     */
    public List<Player> getCurrentPlayers() {
        return playingOrder;
    }

    /**
     * Method used to assign a playing order for the Game
     *
     * @return
     */
    public void assignPlayingOrder() {
        Collections.shuffle(playingOrder, randomizer);
    }

    /**
     * A method that distributes cards to the users
     */
    public void distributeCards() {
        List<Card> cards = Card.getACardDeck();
        Collections.shuffle(cards, randomizer);
        int round = 0;
        int cardIdx = 0;
        int playerCount = playingOrder.size();
        do {
            for (int playerNos = 0; playerNos < playerCount; playerNos++) {
                cardIdx = (round * playerCount) + playerNos;
                if (cardIdx >= cards.size()) {
                    break;
                }
                playingOrder.get(playerNos).receiveCard(cards.get(cardIdx));
            }
            round++;
        } while (cardIdx < cards.size());
    }

    /**
     * A method to start a game
     */
    public void startGame() {
        if(getCurrentPlayers().size()<4){
            throw new InsufficientPlayersException();
        }
        assignPlayingOrder();
        distributeCards();
        play();
        System.out.println("------------------------------- !! GAME OVER !! ------------------------------- ");
        System.out.println("------------------------------- !! WINNERS !! ------------------------------- ");
        getWinningOrder().forEach((player -> player.printName()));
    }

    /**
     * A method that ends the game for the given player
     * @param player
     */
    public void markGameOver(Player player) {
        playingOrder.remove(player);
        winningOrder.add(player);
    }

    /**
     * Returns the winning order of the game
     * @return
     */
    public List<Player> getWinningOrder() {
        return new LinkedList<>(winningOrder);
    }

    /**
     * Playing logic of the game
     */
    public void play() {

    }
}
