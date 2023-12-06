package adapters;


import model.AxialCoord;
import model.ReadonlyIReversi;
import provider.controller.aistrat.ReversiStratagy;
import provider.model.Coordinate;
import provider.model.Disc;
import strategy.ReversiStrategy;

public class StrategyAdapter implements ReversiStrategy {

  ReversiStratagy strat;
  public StrategyAdapter(ReversiStratagy strat){
    this.strat = strat;

  }
  /**
   * Chooses an AxialCoord on the board according to the strategy of the implementation.
   *
   * @param model The model representing the current board state to be analyzed
   * @param who   integer representing which player is moving
   * @return AxialCoord coordinate of the optimal move for the given strategy
   */
  @Override
  public AxialCoord chooseMove(ReadonlyIReversi model, int who) {

    /*
    Disc d;
    if (who == 0) {
      d = Disc.WHITE;
    }
    else {d = Disc.BLACK;}
    Coordinate c = this.strat.chooseMove(new ModelAdapter(model), d);
    return this.translateAxialCoords(c.getQ(), c.getR(), model);


     */

    return null;
  }

  /**
   * Converts coordinates from center-origin to top left origin coordinates.
   * @param q q coord
   * @param r r coord
   * @param model the model to base calculations on
   * @return converted AxialCoord
   */
  private AxialCoord translateAxialCoords(int q, int r, ReadonlyIReversi model) {
    int centerR = (int) Math.floor(model.getBoardArrayLength() / 2);
    return new AxialCoord(q + centerR, r + centerR);

  }
}
