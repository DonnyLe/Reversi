package controller;

import model.AxialCoord;
import model.DiscState;

public interface IPlayer {

  /**
   * Places a move.
   */
  public void move();

  /**
   * Passes the turn.
   */

  public void pass();

  /**
   * Adds the features to the player.
   * @param feature features to be added
   */
  public void addFeatures(Features feature);


}
