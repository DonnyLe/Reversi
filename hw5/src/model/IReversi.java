package model;


/**
 * Interface for a Reversi game. Contains all move and retrieval methods required for Reversi.
 */
public interface IReversi {

  /**
   * Place a move based on rules of Reversi.
   *
   * @param q   q coord for current players move
   * @param r   r coord for current players move
   * @param who integer representing current player
   */
  void placeMove(int q, int r, int who);

  /**
   * Starts the ReversiModel game and produces a 2D array representation of the board based on the
   * sideLen.
   *
   * @param sideLen side length of Hexagonal board
   */
  void startGame(int sideLen);

  /**
   * Returns a copy of the board.
   *
   * @return a copy of the board
   */
  Hexagon[][] getBoard();

  /**
   * Returns true if two skips in a row occur (numSkips resets everytime a player makes a move).
   *
   * @return boolean for if game is over.
   */
  boolean isGameOver();

  /**
   * Returns an integer for the player turn. Player 1 starts at 0.
   *
   * @return integer
   */
  int getTurn();

  /**
   * Function to allow player to pass. Passing is the only move if there are no legal moves for
   * the player.
   */
  void passTurn();

  /**
   * Returns size of board edge as int
   *
   * @return integer
   */
  int getBoardSize();

  /**
   * Calculates the current score of the inputted player
   * @param player player id
   * @return integer of the total number of hexes the player has his color on
   */
  int getPlayerScore(int who);



}
