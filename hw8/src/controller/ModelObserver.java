package controller;

/**
 * Model observer interface.
 */
public interface ModelObserver {
  void yourTurn();

  void updateView();

  void displayError(RuntimeException e);

  void youWin();

  void draw();

  void stopGame();

}
