package model;

import controller.ModelObserver;

/**
 * Interface for model status.
 */
public interface ModelStatus {
  void addObserver(ModelObserver controller);

  void notifyYourTurn();

  void notifyUpdateView();

  void notifyGameOver();
}
