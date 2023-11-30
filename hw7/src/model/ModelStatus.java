package model;

import controller.ModelObserver;
import controller.ReversiController;

public interface ModelStatus {
  void addObserver(ModelObserver controller);

  void notifyYourTurn();

  void notifyUpdateView();

  void notifyGameOver();
}
