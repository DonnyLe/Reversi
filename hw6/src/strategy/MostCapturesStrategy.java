package strategy;

import model.AxialCoord;
import model.ReadonlyIReversi;
import model.ReversiModel;

public class MostCapturesStrategy implements ReversiStrategy{
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
            optimalMove = new AxialCoord(r, q);
          }
        }
        catch (IllegalArgumentException | IllegalStateException ignored){}

      }
    }


    return optimalMove;
  }
}
