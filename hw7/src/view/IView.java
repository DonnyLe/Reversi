package view;


import controller.Features;
import controller.ReversiController;

/**
 * View interface for all views for Reversi.
 */
public interface IView {

  /**
   * Renders a visual representation of the current state of the board.
   */
  void render();


  /**
   * Adds features to the view.
   * @param feature the features to be added.
   */
  public void addObserver(Features feature);

  /**
   * Enables making moves for the view.
   */
  void startView();

  /**
   * Updates the view to correspond to the state of the model.
   */
  void updateView();

  /**
   * Displays an error message saying that a move is illegal.
   * @param e The error to display
   */
  void displayError(RuntimeException e);

  /**
   * Displays a message stating that the player has won.
   */
  void displayWin();

  /**
   * Displays a message stating that there is a draw.
   */
  void displayDraw();

  /**
   * Disables making moves for the view.
   */
  void stopView();

  /**
   * Updates the view.
   */
  void repaint();

  void passMessage();
}
