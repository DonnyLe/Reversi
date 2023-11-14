package strategy;

import model.AxialCoord;
import model.ReadonlyIReversi;
import model.ReversiModel;

/**
 * Strategy to capture the maximum number of discs.
 */
public class MostCapturesStrategy implements ReversiStrategy {
  /**
   * Chooses an AxialCoord on the board according to how many pieces are captured given a move.
   * @param model The model representing the current board state to be analyzed
   * @param who integer representing which player is moving
   * @return AxialCoord coordinate of the optimal move for the given strategy
   */
  @Override
  public AxialCoord chooseMove(ReadonlyIReversi model, int who) {
    int maxScore = 0;
    AxialCoord optimalMove = null;


    for (int r = 0; r < model.getBoardArrayLength(); r++) {
      for (int q = 0; q < model.getBoardArrayLength(); q++) {
        ReversiModel copy = model.copyBoard();
        try {
          copy.placeMove(q, r, who);
          if (copy.getScore(who) > maxScore) {
            maxScore = copy.getScore(who);
            optimalMove = new AxialCoord(q, r);
          }
        }
        catch (IllegalArgumentException | IllegalStateException ignored) { }

      }
    }


    return optimalMove;
  }
}
