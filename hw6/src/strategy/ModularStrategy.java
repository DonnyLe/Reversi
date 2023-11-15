package strategy;

import java.util.ArrayList;

import model.AxialCoord;
import model.ReadonlyIReversi;

/**
 * Strategy to combine multiple strategies.
 */
public class ModularStrategy implements ReversiStrategy {

  ArrayList<ReversiStrategy> strategies;

  public ModularStrategy(ArrayList<ReversiStrategy> strategies) {
    this.strategies = strategies;

  }

  /**
   * Chooses an AxialCoord on the board according to the strategies added to the strategies
   * ArrayList.
   *
   * @param model The model representing the current board state to be analyzed
   * @param who   integer representing which player is moving
   * @return AxialCoord coordinate of the optimal move for the given strategy
   */
  @Override
  public AxialCoord chooseMove(ReadonlyIReversi model, int who) {

    AxialCoord ans = null;

    for (ReversiStrategy strategy : strategies) {
      ans = strategy.chooseMove(model, who);
      if (ans != null) {
        return ans;
      }
    }

    return ans;

  }
}
