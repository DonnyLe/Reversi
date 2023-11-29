package controller;

import java.util.ArrayList;
import model.ReadonlyIReversi;


public class HumanPlayer implements IPlayer{
  private ReadonlyIReversi model;
  private ArrayList<Features> features;

  /**
   * Constructor for human player, initializes fields.
   * @param model the model for the game
   */
  public HumanPlayer(ReadonlyIReversi model) {
    this.model = model;
    this.features = new ArrayList<>();
  }

  /**
   *
   */
  @Override
  public void move() {


  }

  @Override
  public void pass() {

  }
  @Override
  public void addFeatures(Features feature) {
    features.add(feature);
  }
}

