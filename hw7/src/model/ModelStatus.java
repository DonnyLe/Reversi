package model;

import controller.ReversiController;

public interface ModelStatus {
  void addObserver(ReversiController controller);

  void notifyYourTurn();

  void notifyUpdateView();

  void notifyGameOver();
}
