package model;

import controller.ModelObserver;

/**
 * Interface for model status.
 */
public interface ModelStatus {
  int addObserver(ModelObserver controller);

  void notifyYourTurn();

  void notifyUpdateView();

  void notifyGameOver();
}
