package controller;

import model.AxialCoord;

/**
 * Player actions interface.
 */
public interface PlayerActions extends ExtraFeatures {

  /**
   * Tells the model to move at given coordinates.
   * @param coord coordinates for move
   */
  void move(AxialCoord coord);

  /**
   * Tells model to pass.
   */
  void pass();



}
