package controller;

import adapters.PlayerAdapter;
import model.AxialCoord;
import model.IReversi;
import model.ReversiModel;
import player.HumanPlayer;
import player.IPlayer;
import provider.controller.Player;
import provider.model.Disc;
import view.IView;

/**
 * Controller for reversi.
 */
public class ReversiController implements PlayerActions, ModelObserver {
  private IReversi model;
  private IPlayer player;
  private IView view;

  private Disc playerDisc;

  /**
   * Constructor for the controller.
   * @param model The model to be controlled
   * @param player The player, machine or human
   * @param view The view to be controlled
   */
  public ReversiController(IReversi model, IPlayer player, IView view) {
    this.model = model;
    this.player = player;
    this.view = view;
    int playerNum = this.model.addObserver(this);
    if(playerNum== 0) {
      this.playerDisc = Disc.WHITE;
    }
    else {
      this.playerDisc = Disc.BLACK;
    }

    this.view.addPlayerActionsListeners(this);
    this.player.addFeatures(this);
  }



  /**
   * Notifies the view that it is its turn.
   */
  @Override
  public void yourTurn() {


    this.player.move();
    if (this.player instanceof HumanPlayer) {
      this.view.startView();
    }
  }

  /**
   * Notifies the view to update itself.
   */
  @Override
  public void updateView() {
    this.view.updateView();

  }


  /**
   * Notifies the view to display an error message.
   * @param e The error to pass on
   */
  @Override
  public void displayError(RuntimeException e) {
    this.view.displayError(e);
  }

  /**
   * Notifies the view to display a win message.
   */
  @Override
  public void youWin() {
    this.view.displayWin();
  }

  /**
   * Notifies the view to display a draw message.
   */
  @Override
  public void draw() {
    this.view.displayDraw();
  }

  /**
   * Notifies the view to stop taking commands.
   */
  @Override
  public void stopGame() {
    this.view.stopView();

  }

  /**
   * Notifies the model to make a move at the specified coordinates.
   * @param coord coordinates for move
   */
  @Override
  public void move(AxialCoord coord) {
    try {
      this.view.stopView();
      this.model.placeMove(coord.q, coord.r, model.getTurn());

    }
    catch (IllegalArgumentException | IllegalStateException e) {
      this.displayError(e);
      this.view.repaint();
      this.yourTurn();
      return;

    }
  }

  /**
   * Notifies the model to pass the turn.
   */
  @Override
  public void pass() {
    this.view.stopView();
    this.view.passMessage();
    this.model.passTurn();
  }

  /**
   * Gets the Disc color associated with the current player.
   * @return Disc color
   */
  @Override
  public Disc getPlayer() {
    return this.playerDisc;
  }

  /**
   * Gets the Player for the current turn.
   * @return Player
   */
  @Override
  public Player getTurn() {
    return new PlayerAdapter(this.player, this.playerDisc);

  }

  /**
   * Returns the length of the board array of the model.
   * @return int board array length
   */
  @Override
  public int getBoardArrayLength() {
    return this.model.getBoardArrayLength();
  }

  /**
   * Gets the model.
   * @return IReversi model
   */
  public IReversi getModel() {
    return this.model;
  }




}
