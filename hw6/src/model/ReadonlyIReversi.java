package model;

public interface ReadonlyIReversi{
  /**
   * Gets the disc of the hexagon at the given q and r coords (given q and r are valid coords).
   * @param q q coords
   * @param r r coords
   * @return a Hexagon Enum
   */
  DiscState getDiscAt(int q, int r);

  /**
   * Returns the size of the array containing the board (not the side length of the hexagon board).
   * @return integer for the size of the board
   */
  int getBoardArrayLength();

  /**
   * Throws an exception is the player has no moves.
   * @param who integer representing the player
   * @throws IllegalStateException if player has no moves. Forces player to pass turn.
   */
  void hasMovesCheck(int who);

  /**
   * Gets the score of a player. Counts the number of discs a player has on the board.
   * @param who integer representing the player
   * @return integer for the score.
   */
  int getScore(int who);

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

}


