package model;


/**
 * Interface for a Reversi game. Contains all move and retrieval methods required for Reversi.
 */
public interface IReversi extends ReadonlyIReversi, ModelStatus {

  /**
   * Starts the ReversiModel game and produces a 2D array representation of the board.
   *
   */
  void startGame();

  /**
   * Place a move based on rules of Reversi.
   *
   * @param q   q coord for current players move
   * @param r   r coord for current players move
   * @param who integer representing current player
   */
  void placeMove(int q, int r, int who);




  /**
   * Function to allow player to pass. Passing is the only move if there are no legal moves for
   * the player.
   */
  void passTurn();
}



