# The Great DalMuti
This is a simplified implementation of the Game - The Great Dalmuti, built for a class project - Agile Engineering Practices.

Original Game Rules can be found here: https://media.wizards.com/2015/downloads/ah/great_dalmuti_rules.pdf

About the Game: https://en.wikipedia.org/wiki/The_Great_Dalmuti

The original game was designed by Richard Garfield. 

Some Rules have been simplified for this version of the game.


#### Number of Players
This game is suitable for 4-8 Players

#### Card Deck
The deck contains cards from rank 0-12 (both inclusive). There is a single card for rank 0. Rest of the cards are in the
same number as their rank. For instance, 3 cards of Rank 3, 4 cards of Rank 4, etc

#### Playing Order
The order in which the players play is randomly decided by the game and stays fixed throughout. Players play in a circular
fashion.

#### Deal
Each player gets a set of cards to play. Players may not get equal cards. They are distributed in the playing order in a
circular fashion.

#### Starting the Game
The first player in the playing order(known as the Great Dalmuti) starts the game by playing some cards of the same rank.

#### Valid Move
A valid move consists of playing the same number of cards as the last move but with a strictly lower rank. For instance,
if in the last move a player played 3 5's, the next player will have to play 3 cards from either of the ranks from 0 to 4. If they don't have any such card, the turn passes to next player until either the turn reaches the same player or 
someone in the game finishes their cards. Once this happens, the round starts again from the player who played last.

#### Winning
The goal of the game is to finish your set of cards as soon as possible.

#### Playing the interactive version
In the file ```GameMatch.java``` un-comment the line where a ```HumanPlayer``` is registered.
To add more players use the following format 
```game.registerPlayer(new HumanPlayer("Player Name"));```

Run the file ```GameMatch.java```
   