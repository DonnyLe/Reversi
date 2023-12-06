package adapters;

import java.util.HashMap;

import controller.PlayerActions;
import model.DiscState;
import model.Hexagon;
import model.ReadonlyIReversi;
import model.ReversiModel;
import provider.model.Cell;
import provider.model.Coordinate;
import provider.model.Disc;
import provider.model.ReversiReadOnly;
import provider.view.BoardPanel;
import provider.view.ReversiFrame;
import view.IView;

public class ViewAdapter implements IView {

  private ReversiFrame view;

  private ReversiReadOnly model;

  public ViewAdapter(ReversiFrame view){
    this.view = view;
  }




  /**
   * Renders a visual representation of the current state of the board.
   */
  @Override
  public void render() {
    this.view.setVisible(true);
  }

  /**
   * Adds features to the view.
   *
   * @param playerActions the features to be added.
   */
  @Override
  public void addPlayerActionsListeners(PlayerActions playerActions) {

  }

  /**
   * Enables making moves for the view.
   */
  @Override
  public void startView() {


  }

  /**
   * Updates the view to correspond to the state of the model.
   */
  @Override
  public void updateView() {

  }

  /**
   * Displays an error message saying that a move is illegal.
   *
   * @param e The error to display
   */
  @Override
  public void displayError(RuntimeException e) {

  }

  /**
   * Displays a message stating that the player has won.
   */
  @Override
  public void displayWin() {

  }

  /**
   * Displays a message stating that there is a draw.
   */
  @Override
  public void displayDraw() {

  }

  /**
   * Disables making moves for the view.
   */
  @Override
  public void stopView() {

  }

  /**
   * Updates the view.
   */
  @Override
  public void repaint() {

  }

  @Override
  public void passMessage() {

  }
}
