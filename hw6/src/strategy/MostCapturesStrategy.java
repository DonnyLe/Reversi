package strategy;

import model.Posn;
import model.ReadonlyIReversi;
import model.ReversiModel;

public class MostCapturesStrategy implements ReversiStrategy{
  @Override
  public Posn chooseMove(ReadonlyIReversi model, int who) {
    int maxScore = 0;
    Posn optimalMove = null;


    for (int r = 0; r < model.getBoardArrayLength(); r++) {
      for (int q = 0; q < model.getBoardArrayLength(); q++) {
        ReversiModel copy = model.copyBoard();
        try {
          copy.placeMove(q, r, who);
          if (copy.getScore(who) > maxScore) {
            maxScore = copy.getScore(who);
            optimalMove = new Posn(r, q);
          }
        }
        catch (IllegalArgumentException | IllegalStateException ignored){}

      }
    }


    return optimalMove;
  }
}
