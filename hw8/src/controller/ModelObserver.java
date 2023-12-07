package controller;

/**
 * Model observer interface.
 */
public interface ModelObserver {

  /**
   * Notifies the view that it is its turn.
   */
  void yourTurn();

  /**
   * Notifies the view to update itself.
   */
  void updateView();

  /**
   * Notifies the view to display an error message.
   * @param e The error to pass on
   */
  void displayError(RuntimeException e);

  /**
   * Notifies the view to display a win message.
   */
  void youWin();

  /**
   * Notifies the view to display a draw message.
   */
  void draw();

  /**
   * Notifies the view to stop taking commands.
   */
  void stopGame();

}
