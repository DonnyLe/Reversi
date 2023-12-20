package view;

import java.util.ArrayList;
import java.util.List;

import controller.PlayerActions;
import model.AxialCoord;

/**
 * Mock view for testing purposes.
 */
public class MockView implements IView {

  private List<PlayerActions> features = new ArrayList<PlayerActions>();
  private StringBuilder log = new StringBuilder();


  /**
   * Renders a visual representation of the current state of the board.
   */
  @Override
  public void render() {

    //empty
  }

  /**
   * Adds features to the view.
   *
   * @param playerActions the features to be added.
   */
  @Override
  public void addPlayerActionsListeners(PlayerActions playerActions) {
    features.add(playerActions);
  }

  @Override
  public void notifyMove() {

    //empty
  }

  @Override
  public void notifyPass() {

    //empty
  }

  /**
   * Mock input.
   * @param coord coordinate
   */
  public void mockInput(AxialCoord coord) {
    for (PlayerActions f : features) {
      f.move(coord);
    }
  }

  /**
   * Mock pass.
   */
  public void mockPass() {
    for (PlayerActions f : features) {
      f.pass();
    }
  }

  /**
   * Returns the log as a string.
   * @return String with log
   */
  public String getLog() {
    return log.toString();
  }

  /**
   * Enables making moves for the view.
   */
  @Override
  public void startView() {

    log.append("\nStart view notification recieved");

  }

  /**
   * Updates the view to correspond to the state of the model.
   */
  @Override
  public void updateView() {

    log.append("\nUpdate view notification recieved");
  }

  /**
   * Displays an error message saying that a move is illegal.
   * @param e The error to display
   */
  @Override
  public void displayError(RuntimeException e) {

    log.append("\nDisplay error notification recieved");
  }

  /**
   * Displays a message stating that the player has won.
   */
  @Override
  public void displayWin() {

    log.append("\nDisplay win notification recieved");
  }

  /**
   * Displays a message stating that there is a draw.
   */
  @Override
  public void displayDraw() {

    log.append("\nDisplay draw notification recieved");
  }

  /**
   * Disables making moves for the view.
   */
  @Override
  public void stopView() {

    log.append("\nStop view notification recieved");
  }

  /**
   * Updates the view.
   */
  @Override
  public void repaint() {

    log.append("\nRepaint notification recieved");

  }

  @Override
  public void passMessage() {

    //empty
  }
}
