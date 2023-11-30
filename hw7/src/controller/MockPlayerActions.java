
package controller;

        import model.AxialCoord;
        import model.ReversiModel;
        import view.IView;

public class MockPlayerActions implements PlayerActions, ModelObserver{
  private ReversiModel model;
  private IPlayer player;
  private StringBuilder log = new StringBuilder();


  /**
   * Constructor for the controller.
   * @param model The model to be controlled
   * @param player The player, machine or human
   */
  public MockPlayerActions(ReversiModel model, IPlayer player){
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

  @Override
  public void updateView() {

  }

  @Override
  public void displayError(RuntimeException e) {

  }

  @Override
  public void youWin() {

  }

  @Override
  public void draw() {

  }

  @Override
  public void stopGame() {

  }

  /**
   * Notifies the model to make a move at the specified coordinates.
   * @param coord coordinates for move
   */
  @Override
  public void move(AxialCoord coord){
    log.append("Placed move to (" + coord.q + " , " + coord.r );

    try{
      this.model.placeMove(coord.q, coord.r, model.getTurn());
      System.out.println("entered");

    }
    catch (IllegalArgumentException | IllegalStateException e){

      this.yourTurn();
      return;

    }
  }

  /**
   * Notifies the model to pass the turn.
   */
  @Override
  public void pass(){
    log.append("Passed move.");
    this.model.passTurn();
  }



}
