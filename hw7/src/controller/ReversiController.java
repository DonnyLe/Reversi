package controller;

import model.AxialCoord;
import model.ReversiModel;
import view.IView;

public class ReversiController implements Features{
  private ReversiModel model;
  private IPlayer player;
  private IView view;

  /**
   * Constructor for the controller.
   * @param model The model to be controlled
   * @param player The player, machine or human
   * @param view The view to be controlled
   */
  public ReversiController(ReversiModel model, IPlayer player, IView view){
    this.model = model;
    this.player = player;
    this.view = view;
    this.model.addObserver(this);
    this.view.addObserver(this);
    this.player.addFeatures(this);
  }
  //have a method

  //features.processTurnChange(color)

  /**
   * Notifies the view that it is its turn.
   */
  public void yourTurn() {

    this.player.move();
    if(this.player instanceof HumanPlayer) {
      this.view.startView();
    }
  }

  /**
   * Notifies the view to update itself.
   */
  public void updateView(){
    this.view.updateView();

  }


  /**
   * Notifies the view to display an error message.
   * @param e The error to pass on
   */
  public void displayError(RuntimeException e){
    this.view.displayError(e);
  }

  /**
   * Notifies the view to display a win message.
   */
  public void youWin(){
    this.view.displayWin();
  }

  /**
   * Notifies the view to display a draw message.
   */
  public void draw(){
    this.view.displayDraw();
  }

  /**
   * Notifies the view to stop taking commands.
   */
  public void stopGame(){
    this.view.stopView();

  }

  /**
   * Notifies the model to make a move at the specified coordinates.
   * @param coord coordinates for move
   */
  @Override
  public void move(AxialCoord coord){
    try{
      this.view.stopView();
      this.model.placeMove(coord.q, coord.r, model.getTurn());
      System.out.println("entered");

    }
    catch (IllegalArgumentException | IllegalStateException e){
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
  public void pass(){
    this.view.stopView();
    this.model.passTurn();

  }

  private static void wait(int ms)
  {
    try
    {
      Thread.sleep(ms);
    }
    catch(InterruptedException ex)
    {
      Thread.currentThread().interrupt();
    }
  }


}
