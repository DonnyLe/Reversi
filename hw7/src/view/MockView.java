package view;

import java.util.ArrayList;
import java.util.List;

import controller.Features;
import model.AxialCoord;

public class MockView implements IView{

  private List<Features> features = new ArrayList<Features>();
  private StringBuilder log = new StringBuilder();


  /**
   * Renders a visual representation of the current state of the board.
   */
  @Override
  public void render() {

  }

  /**
   * Adds features to the view.
   *
   * @param feature the features to be added.
   */
  @Override
  public void addObserver(Features feature) {
    features.add(feature);
  }

  public void mockInput(AxialCoord coord) {
    for (Features f : features){
      f.move(coord);
    }
  }

  public void mockPass() {
    for (Features f : features){
      f.pass();
    }
  }

  /**
   * Returns the log as a string.
   * @return String with log
   */
  public String getLog(){
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

  }
}
