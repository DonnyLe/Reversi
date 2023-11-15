package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**`
 * Model for Reversi game. Implements IReversi and follows rules for a standard Reversi game except
 * game uses Hexagons instead of square (for shape of board and shape of spaces).
 */
public class MockModel implements ReadonlyIReversi, IReversi {

  public StringBuilder log;
  public MockModel() {

    log = new StringBuilder();
  }
  /**
   * Gets the disc of the hexagon at the given q and r coords (given q and r are valid coords).
   *
   * @param q q coords
   * @param r r coords
   * @return a Hexagon Enum
   */
  @Override
  public DiscState getDiscAt(int q, int r) {
    return null;
  }

  /**
   * Returns the size of the array containing the board (not the side length of the hexagon board).
   *
   * @return integer for the size of the board
   */
  @Override
  public int getBoardArrayLength() {
    return 7;
  }

  /**
   * Returns the side length of the hexagon board.
   *
   * @return integer for the size of sides
   */
  @Override
  public int getSideLength() {
    return 4;
  }

  /**
   * Throws an exception is the player has no moves.
   *
   * @param who integer representing the player
   * @throws IllegalStateException if player has no moves. Forces player to pass turn.
   */
  @Override
  public void hasMovesCheck(int who) {

  }

  /**
   * Gets the score of a player. Counts the number of discs a player has on the board.
   *
   * @param who integer representing the player
   * @return integer for the score.
   */
  @Override
  public int getScore(int who) {
    return 0;
  }

  /**
   * Returns true if two skips in a row occur (numSkips resets everytime a player makes a move).
   *
   * @return boolean for if game is over.
   */
  @Override
  public boolean isGameOver() {
    return false;
  }

  /**
   * Returns an integer for the player turn. Player 1 starts at 0.
   *
   * @return integer
   */
  @Override
  public int getTurn() {
    return 0;
  }

  @Override
  public ReversiModel copyBoard() {
    return null;
  }

  @Override
  public int checkMove(int q, int r, int who) {
    log.append(String.format("checked %d, %d", q, r) + "\n");
    return 0;
  }

  /**
   * Place a move based on rules of Reversi.
   *
   * @param q   q coord for current players move
   * @param r   r coord for current players move
   * @param who integer representing current player
   */
  @Override
  public void placeMove(int q, int r, int who) {

  }

  /**
   * Starts the ReversiModel game and produces a 2D array representation of the board based on the
   * sideLen.
   *
   * @param sideLen side length of Hexagonal board
   */
  @Override
  public void startGame(int sideLen) {

  }

  /**
   * Function to allow player to pass. Passing is the only move if there are no legal moves for
   * the player.
   */
  @Override
  public void passTurn() {

  }
}