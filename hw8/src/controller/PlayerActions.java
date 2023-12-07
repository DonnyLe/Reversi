package controller;

import model.AxialCoord;
import model.IReversi;
import model.ReversiModel;
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

  /**
   * Gets the Disc color associated with the current player.
   * @return Disc color
   */
  Disc getPlayer();

  /**
   * Gets the Player for the current turn.
   * @return Player
   */
  Player getTurn();

  /**
   * Returns the length of the board array of the model.
   * @return int board array length
   */
  int getBoardArrayLength();

  /**
   * Gets the model.
   * @return IReversi model
   */
  IReversi getModel();


}
