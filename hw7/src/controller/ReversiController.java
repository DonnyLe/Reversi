package controller;

import model.AxialCoord;
import model.ReversiModel;
import view.ReversiGraphicsView;

public class ReversiController implements Features{
  ReversiModel model;
  IPlayer player;
  ReversiGraphicsView view;
  public ReversiController(ReversiModel model, IPlayer player, ReversiGraphicsView view){
    this.model = model;
    this.player = player;
    this.view = view;
    this.model.addObserver(this);
    this.view.addObserver(this);
    this.player.addFeatures(this);
  }
  //have a method

  //features.processTurnChange(color)

  public void yourTurn() {
    this.player.move();
    this.view.startView();


  }

  public void updateView(){
    this.view.updateView();

  }

  public void displayError(){
    this.view.displayError();
  }

  @Override
  public void move(AxialCoord coord){
    System.out.println(model.getTurn());
    try{
      this.model.placeMove(coord.q, coord.r, model.getTurn());
    }
    catch (IllegalArgumentException | IllegalStateException e){
      this.displayError();
      this.view.repaint();
      return;
    }
    this.view.stopView();
  }

  @Override
  public void pass(){
    this.model.passTurn();
    this.view.stopView();

  }




}
