package strategy;

import java.util.ArrayList;

import model.AxialCoord;
import model.ReadonlyIReversi;

public class ModularStrategy implements ReversiStrategy{

  ArrayList<ReversiStrategy> strategies;

  public ModularStrategy(ArrayList<ReversiStrategy> strategies) {
    this.strategies = strategies;

  }
  @Override
  public AxialCoord chooseMove(ReadonlyIReversi model, int who) {
    AxialCoord ans = null;
    for (ReversiStrategy strategy : strategies){
      ans = strategy.chooseMove(model, who);
      if(ans != null) {return ans;}
    }

    return ans;

  }
}
