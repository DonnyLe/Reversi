
package controller;

import model.AxialCoord;
import model.ReversiModel;
import player.IPlayer;
import provider.controller.Player;
import provider.model.Disc;

/**
 * Mock player actions for testing purposes.
 */
public class MockPlayerActions implements PlayerActions, ModelObserver {
  private ReversiModel model;
  private IPlayer player;
  private StringBuilder log = new StringBuilder();


  /**
   * Constructor for the controller.
   * @param model The model to be controlled
   * @param player The player, machine or human
   */
  public MockPlayerActions(ReversiModel model, IPlayer player) {
    this.model = model;
    this.player = player;
    this.player.addFeatures(this);
    this.model.addObserver(this);


  }
  //have a method

  //features.processTurnChange(color)

  /**
   * Notifies the view that it is its turn.
   */
  @Override
  public void yourTurn() {
    this.player.move();
  }

  public String getLog() {
    return log.toString();
  }


  @Override
  public void updateView() {
    //empty

  }

  @Override
  public void displayError(RuntimeException e) {

    //empty
  }

  @Override
  public void youWin() {

    //empty
  }

  @Override
  public void draw() {

    //empty
  }

  @Override
  public void stopGame() {

    //empty
  }

  /**
   * Notifies the model to make a move at the specified coordinates.
   * @param coord coordinates for move
   */
  @Override
  public void move(AxialCoord coord) {
    log.append("Placed move to (" + coord.q + " , " + coord.r + ")\n");

    try {
      this.model.placeMove(coord.q, coord.r, model.getTurn());
    }
    catch (IllegalArgumentException | IllegalStateException e) {

      this.yourTurn();
      return;

    }
  }

  /**
   * Notifies the model to pass the turn.
   */
  @Override
  public void pass() {
    log.append("Passed move." + "\n");
    this.model.passTurn();
  }

  @Override
  public Disc getPlayer() {
    return null;
  }

  @Override
  public Player getTurn() {
    return null;
  }


}
