package controller;

import model.AxialCoord;
import provider.controller.Player;
import provider.model.Disc;

/**
 * Player actions interface.
 */
public interface PlayerActions {

  /**
   * Tells the model to move at given coordinates.
   * @param coord coordinates for move
   */
  void move(AxialCoord coord);

  /**
   * Tells model to pass.
   */
  void pass();

  Disc getPlayer();

  Player getTurn();
}
