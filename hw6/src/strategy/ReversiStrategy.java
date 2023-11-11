package strategy;

import model.ReadonlyIReversi;

public interface ReversiStrategy {
  Posn chooseMove(ReadonlyIReversi model, int who);
}
