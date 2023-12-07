package adapters;


import controller.PlayerActions;
import model.AxialCoord;
import provider.controller.ControllerFeatures;
import provider.controller.Player;
import provider.model.Disc;
import provider.view.IBoardPanel;
import provider.view.ReversiFrame;
import view.IView;

/**
 * Adapter that makes their views work with our controllers.
 */
public class ViewAdapter implements IView, ControllerFeatures {

  private boolean active;
  private ReversiFrame view;


  private PlayerActions features;
  private AxialCoord selectedHexLocation;


  /**
   * Constructor for view.
   * @param view view
   */
  public ViewAdapter(ReversiFrame view) {
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

    features = playerActions;
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

  /**
   * Updates the currently selected hexagon.
   * @param q The column coordinate of the selected hexagon.
   * @param r The row coordinate of the selected hexagon.
   */
  @Override
  public void selectHexagon(int q, int r) {


    this.selectedHexLocation = new AxialCoord(q, r);


  }


  /**
   * Confirms move at the currently selected hexagon.
   */
  @Override
  public void confirmMove() {
    System.out.println(this.selectedHexLocation.q + " " + this.selectedHexLocation.r);
    if (active) {
      if (this.selectedHexLocation != null) {
        features.move(this.translateAxialCoords(this.selectedHexLocation.q,
                this.selectedHexLocation.r));
      }
      this.selectedHexLocation = null;
    }
  }


  /**
   * Passes turn.
   */
  @Override
  public void passTurn() {
    if (active) {
      features.pass();
    }

  }

  /**
   * Gets the Disc color associated with the current player.
   * @return Disc color
   */
  @Override
  public Disc getPlayer() {
    return features.getPlayer();
  }

  /**
   * Gets the player whose turn it is.
   * @return Player
   */
  @Override
  public Player getTurn() {
    return features.getTurn();
  }

  /**
   * Unused method.
   * @return null
   */
  @Override
  public String getLog() {
    return null;
  }

  /**
   * Updates the view to correspond to the state of the model.
   */
  @Override
  public void updateView() {

    this.view.getBoardPanel().initializeHexagons(new ModelAdapter(this.features.getModel()));
    this.view.getBoardPanel().repaint();
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

    //empty
  }

  /**
   * Displays a message stating that there is a draw.
   */
  @Override
  public void displayDraw() {
    //empty
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
    System.out.println("update request");
    this.view.getBoardPanel().repaint();

  }


  /**
   * Passes a message in the view.
   */
  @Override
  public void passMessage() {
    this.view.getBoardPanel().showInvalidMoveDialog("Invalid move");

  }

  /**
   * Converts coordinates from center-origin to top left origin coordinates.
   * @param q q coord
   * @param r r coord
   * @return converted AxialCoord
   */
  private AxialCoord translateAxialCoords(int q, int r) {
    int centerR = (int) Math.floor(this.features.getBoardArrayLength() / 2);
    return new AxialCoord(q + centerR, r + centerR);

  }
}
