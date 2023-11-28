package model;


/**
 * Interface for a Reversi game. Contains all move and retrieval methods required for Reversi.
 */
public interface IReversi extends ReadonlyIReversi {

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
   * Function to allow player to pass. Passing is the only move if there are no legal moves for
   * the player.
   */
  void passTurn();


}
