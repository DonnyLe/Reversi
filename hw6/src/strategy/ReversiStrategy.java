package strategy;

import model.AxialCoord;
import model.ReadonlyIReversi;

/**
 * Strategy pattern to determine the coordinate to move to a given the current board state.
 */

public interface ReversiStrategy {
  /**
   * Chooses an AxialCoord on the board according to the strategy of the implementation.
   * @param model The model representing the current board state to be analyzed
   * @param who integer representing which player is moving
   * @return AxialCoord coordinate of the optimal move for the given strategy
   */
  AxialCoord chooseMove(ReadonlyIReversi model, int who);
}
