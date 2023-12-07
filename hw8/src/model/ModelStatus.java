package model;

import controller.ModelObserver;

/**
 * Interface for model status.
 */
public interface ModelStatus {

  /**
   * Adds the controller as an observer so that it can notify it. In addition,
   * by returning the integer representing the observer/player, the observer can know their
   * color (0 is white, 1 is black)
   *
   * @param controller the controller to be added
   * @return the player's number in the model
   */
  int addObserver(ModelObserver controller);

  /**
   * Sends a notification to the controller for the current player that it is their turn.
   */
  void notifyYourTurn();

  /**
   * Sends a notification to all subscribed controllers to update their views.
   */
  void notifyUpdateView();

  /**
   * Determines the winner and sends a notification to its controller, or sends a notification to
   * both in case of a draw.
   */
  void notifyGameOver();
}
