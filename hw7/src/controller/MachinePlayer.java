package controller;

import java.util.ArrayList;

import model.AxialCoord;
import model.IReversi;
import strategy.ReversiStrategy;

public class MachinePlayer implements IPlayer{
  private IReversi model;
  private ReversiStrategy strat;

  private ArrayList<Features> features;

  /**
   * Constructor for machine player.
   * @param model the model for the game
   * @param strat the strategy used by the machine player
   */
  public MachinePlayer(IReversi model, ReversiStrategy strat) {
    this.model = model;
    this.strat = strat;
    this.features = new ArrayList<>();

  }

  /**
   * Chooses a move according to the strategy and moves or passes.
   */
  @Override
  public void move() {
   AxialCoord coord = strat.chooseMove(model, model.getTurn());
//   for(Features f: features) {
     if(coord == null) {this.pass();}
     else{features.get(0).move(coord);}

//   }
  }

  /**
   * Passes the turn.
   */
  @Override
  public void pass() {
    for(Features f: features) {
      f.pass();
    }
  }

  /**
   * Adds features to the player.
   * @param feature features to be added
   */
  @Override
  public void addFeatures(Features feature) {
    features.add(feature);
  }
}
