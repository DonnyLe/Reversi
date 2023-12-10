package strategy;

import model.AxialCoord;
import model.ReadonlyIReversi;

/**
 * Strategy to choose move not before corners.
 */
public class AvoidBeforeCornersStrategy implements ReversiStrategy {
  /**
   * Chooses an AxialCoord on the board according to the number of captured, but avoiding
   * the hexes adjacent to the corners of the board.
   *
   * @param model The model representing the current board state to be analyzed
   * @param who   integer representing which player is moving
   * @return AxialCoord coordinate of the optimal move for the given strategy
   */
  @Override
  public AxialCoord chooseMove(ReadonlyIReversi model, int who) {
    int maxScore = 0;
    AxialCoord optimalMove = null;
    int var = model.getSideLength() - 2;
    int center = model.getSideLength() - 1;
    int squareLen = model.getBoardArrayLength() - 1;


    try {
      model.getDiscAt(0, 0);
    } catch (Exception e) {
      for (int r = 0; r < model.getBoardArrayLength(); r++) {
        for (int q = 0; q < model.getBoardArrayLength(); q++) {

          if (!(r == center + var || r == center - var || q == center + var
                  || q == center - var || -q - r == (-center - center) + var
                  || -q - r == (-center - center) - var)) {
            try {

              if (model.checkMove(q, r, who) > maxScore) {
                maxScore = model.checkMove(q, r, who);
                optimalMove = new AxialCoord(q, r);
              }
            } catch (IllegalArgumentException | IllegalStateException ignored) {
            }
          }


        }
      }
      return optimalMove;
    }



    for (int r = 0; r < model.getBoardArrayLength(); r++) {
      for (int q = 0; q < model.getBoardArrayLength(); q++) {

        if (!(
                (r == 1 && !(q == 0 || q == squareLen))
                        ||
                        (q == 1 && !(r == 0 || r == squareLen))
                        || (r == (squareLen - 1)) && !(q == 0 || q == squareLen)
                        || (q == (squareLen - 1)) && !(r == 0 || r == squareLen))) {
          try {

            if (model.checkMove(q, r, who) > maxScore) {
              maxScore = model.checkMove(q, r, who);
              optimalMove = new AxialCoord(q, r);
            }
          } catch (IllegalArgumentException | IllegalStateException ignored) {
          }
        }


      }
    }

    return optimalMove;
  }
}
