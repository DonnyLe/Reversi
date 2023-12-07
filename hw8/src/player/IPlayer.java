package player;

import controller.PlayerActions;

/**
 * Interface for player.
 */
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
  public void addFeatures(PlayerActions feature);


}
