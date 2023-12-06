package adapters;

import java.util.ArrayList;
import java.util.HashMap;

import controller.PlayerActions;
import model.AxialCoord;
import model.DiscState;
import model.Hexagon;
import model.ReadonlyIReversi;
import model.ReversiModel;
import provider.controller.ControllerFeatures;
import provider.controller.Player;
import provider.model.Cell;
import provider.model.Coordinate;
import provider.model.Disc;
import provider.model.ReversiReadOnly;
import provider.view.BoardPanel;
import provider.view.IBoardPanel;
import provider.view.ReversiFrame;
import view.IView;

public class ViewAdapter implements IView, ControllerFeatures {

  private boolean active;
  private ReversiFrame view;


  private ArrayList<PlayerActions> features = new ArrayList<>();
  private AxialCoord selectedHexLocation;

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

    features.add(playerActions);
    IBoardPanel p  = this.view.getBoardPanel();
    p.setController(this);



  }

  /**
   * Enables making moves for the view.
   */
  @Override
  public void startView() {
    this.active = true;


  }

  @Override
  public void selectHexagon(int q, int r) {

    this.selectedHexLocation = new AxialCoord(q, r);


  }

  @Override
  public void confirmMove() {
    if(active) {
      if (this.selectedHexLocation != null) {
        for (PlayerActions f : this.features) {
          f.move(this.selectedHexLocation);
        }
        this.selectedHexLocation = null;
      }
    }

  }

  @Override
  public void passTurn() {
    if(active) {
      for (PlayerActions f : this.features) {
        f.pass();
      }
    }


  }

  @Override
  public Disc getPlayer() {
    return null;
  }

  @Override
  public Player getTurn() {
    return null;
  }

  @Override
  public String getLog() {
    return null;
  }

  /**
   * Updates the view to correspond to the state of the model.
   */
  @Override
  public void updateView() {
    this.view.repaint();
  }

  /**
   * Displays an error message saying that a move is illegal.
   *
   * @param e The error to display
   */
  @Override
  public void displayError(RuntimeException e) {

      this.view.getBoardPanel().showInvalidMoveDialog(e.getMessage());
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
    this.active = false;

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
