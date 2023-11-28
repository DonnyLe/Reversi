package controller;

import model.AxialCoord;
import model.ReadonlyIReversi;
import model.ReversiModel;
import view.ReversiGraphicsView;

public class ReversiController {
  ReversiModel model;
  IPlayer player;
  ReversiGraphicsView view;
  public ReversiController(ReversiModel model, IPlayer player, ReversiGraphicsView view){
    this.model = model;
    this.player = player;
    this.view = view;
    this.model.addObserver(this);
    this.view.addObserver(this);
  }

  public void yourTurn(){


  }

  public void updateView(){
    this.view.updateView();

  }

  public void move(AxialCoord coord){
    this.model.placeMove(coord.q, coord.r, model.getTurn());

  }

  public void pass(){

  }




}
