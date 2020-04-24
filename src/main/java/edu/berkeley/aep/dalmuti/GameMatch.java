package edu.berkeley.aep.dalmuti;

/**
 * A class that starts a game match
 * @Author rahul
 * @Create Apr 2020
 */
public class GameMatch {

    public static void main(String[] args) {
        Game game = new Game();
        game.registerPlayer(new Player("ABC"));
        game.registerPlayer(new Player("DEF"));
        game.registerPlayer(new Player("GHI"));
        game.registerPlayer(new Player("JKL"));
        game.registerPlayer(new Player("XYZ"));
        game.registerPlayer(new HumanPlayer("Rahul"));
        game.startGame();
    }
}
