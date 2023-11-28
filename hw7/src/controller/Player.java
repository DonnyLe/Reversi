package controller;

import model.IReversi;
import model.ModelFeatures;
import model.ReversiModel;

public class Player {
  Player currentTurn;
  ModelFeatures model;
  public Player(ModelFeatures model) {
    this.model = model;
    model.addPlayer(this);
  }

  public void setCurrentTurn(Player player) {
    this.currentTurn = player;
  }

  public boolean isTurn(Player player) {
      return this.equals(player);
  }
}
