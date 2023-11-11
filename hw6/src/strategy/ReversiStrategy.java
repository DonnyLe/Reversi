package strategy;

import model.Posn;
import model.ReadonlyIReversi;

public interface ReversiStrategy {
  Posn chooseMove(ReadonlyIReversi model, int who);
}
