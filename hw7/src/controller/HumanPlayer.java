package controller;

import java.util.ArrayList;

import model.ReadonlyIReversi;

/**
 * Human player.
 */
public class HumanPlayer implements IPlayer {
  ReadonlyIReversi model;
  ArrayList<PlayerActions> features;

  /**
   * Constructor for human player
   * @param model model for use in strategy
   */
  public HumanPlayer(ReadonlyIReversi model) {
    this.model = model;
    this.features = new ArrayList<>();
  }

  /**
   * Places a move.
   */
  @Override
  public void move() {

    //empty
  }

  /**
   * Passes the turn.
   */
  @Override
  public void pass() {

    //empty
  }

  /**
   * Adds the features to the player.
   * @param feature features to be added
   */
  @Override
  public void addFeatures(PlayerActions feature) {
    features.add(feature);
  }
}

