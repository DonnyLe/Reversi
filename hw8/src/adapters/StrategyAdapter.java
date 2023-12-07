package adapters;


import model.AxialCoord;
import model.IReversi;
import model.ReadonlyIReversi;
import provider.controller.aistrat.ReversiStratagy;
import provider.model.Coordinate;
import provider.model.Disc;
import strategy.ReversiStrategy;

/**
 * Adapter to turn their strategies into our strategies.
 */
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


    Disc d;
    if (who == 0) {
      d = Disc.WHITE;
    }
    else {d = Disc.BLACK;}
    Coordinate c = this.strat.chooseMove(new ModelAdapter((IReversi) model), d);
    return new AxialCoord(c.getQ(), c.getR());

  }
}
