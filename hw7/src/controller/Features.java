package controller;

import model.AxialCoord;

public interface Features {

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
