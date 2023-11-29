package controller;

import java.util.ArrayList;

import model.AxialCoord;
import model.IReversi;
import strategy.ReversiStrategy;

public class MachinePlayer implements IPlayer{
  IReversi model;
  ReversiStrategy strat;

  ArrayList<Features> features;
  public MachinePlayer(IReversi model, ReversiStrategy strat) {
    this.model = model;
    this.strat = strat;
    this.features = new ArrayList<>();

  }

  @Override
  public void move() {
   AxialCoord coord = strat.chooseMove(model, model.getTurn());
//   for(Features f: features) {
     if(coord == null) {this.pass();}
     else{features.get(0).move(coord);}

//   }
  }

  @Override
  public void pass() {
    for(Features f: features) {
      f.pass();
    }
  }

  @Override
  public void addFeatures(Features feature) {
    features.add(feature);
  }
}
