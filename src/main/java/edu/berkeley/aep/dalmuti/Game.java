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

    public Game() {
        this.players = new HashSet<>();
        this.playingOrder = new ArrayList<>();
        this.randomizer = new Random(System.currentTimeMillis());
    }

    /**
     * Registers a player in the game and returns True if successful otherwise False
     *
     * @param player
     * @return
     */
    public boolean registerPlayer(Player player) {
        if(this.players.size() == 8){
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
        if(players.size()<4){
            throw new InsufficientPlayersException();
        }
    }
}
