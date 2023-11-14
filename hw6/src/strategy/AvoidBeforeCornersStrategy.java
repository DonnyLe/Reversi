package strategy;

import model.AxialCoord;
import model.ReadonlyIReversi;
import model.ReversiModel;

/**
 * Strategy to choose move not before corners.
 */
public class AvoidBeforeCornersStrategy implements ReversiStrategy {
  /**
   * Chooses an AxialCoord on the board according to the number of captured, but avoiding
   * the hexes adjacent to the corners of the board.
   * @param model The model representing the current board state to be analyzed
   * @param who integer representing which player is moving
   * @return AxialCoord coordinate of the optimal move for the given strategy
   */
  @Override
  public AxialCoord chooseMove(ReadonlyIReversi model, int who) {
    int maxScore = 0;
    AxialCoord optimalMove = null;
    ReversiModel copy = model.copyBoard();
    int var = copy.getSideLength() - 2;
    int center = copy.getSideLength() - 1;



    for (int r = 0; r < model.getBoardArrayLength(); r++) {
      for (int q = 0; q < model.getBoardArrayLength(); q++) {
        copy = model.copyBoard();
        if (!(r == center + var || r == center - var || q == center + var
                || q == center - var || - q - r == (- center - center) + var
                || - q - r == (- center - center) - var)) {
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
    }


    return optimalMove;
  }
}
