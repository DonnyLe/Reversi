package controller;

import model.IReversi;
import view.ReversiGraphicsView;

public class Controller {
  IReversi model;
  Player player;
  ReversiGraphicsView rgv;

  PlayerActions playerActions;

  public Controller(IReversi model, ReversiGraphicsView rgv, Player player) {
    this.model = model;
    this.rgv = rgv;
    this.player = player;
    this.playerActions = new PlayerActionsImpl(model);
  }

  public void playGame() {
    this.rgv.addFeatures(playerActions);
  }



}
