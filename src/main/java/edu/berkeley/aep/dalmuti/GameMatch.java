package edu.berkeley.aep.dalmuti;

/**
 * A class that starts a game match
 * @Author rahul
 * @Create Apr 2020
 */
public class GameMatch {

    public static void main(String[] args) {
        Game game = new Game();
        game.registerPlayer(new Player("ROSS"));
        game.registerPlayer(new Player("RACHEL"));
        game.registerPlayer(new Player("PHOEBE"));
        game.registerPlayer(new Player("MONICA"));
        game.registerPlayer(new Player("CHANDLER"));
        game.registerPlayer(new Player("JOEY"));
//        TODO: Uncomment human player to play interactively
//        game.registerPlayer(new HumanPlayer("Rahul"));
        game.startGame();
    }
}
