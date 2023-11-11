package strategy;

import model.AxialCoord;
import model.ReadonlyIReversi;

public interface ReversiStrategy {
  AxialCoord chooseMove(ReadonlyIReversi model, int who);
}
