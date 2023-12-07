package controller;

import model.IReversi;
import provider.controller.Player;
import provider.model.Disc;

/**
 * Extra features.
 */
public interface ExtraFeatures {
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
