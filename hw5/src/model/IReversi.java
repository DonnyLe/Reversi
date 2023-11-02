package model;


/**
 * Interface for a Reversi game. Contains all move and retrieval methods required for Reversi.
 */
public interface IReversi {

  void placeMove(int r, int s, int who);

  /**
   * Starts the ReversiModel game and produces a 2D array representation of the board based on the
   * sideLen.
   *
   * @param sideLen side length of Hexagonal board
   */
  void startGame(int sideLen);

  Hexagon[][] getBoard();

  boolean isGameOver();

  int getTurn();

  void passTurn();

}
