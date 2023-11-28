package controller;

import model.IReversi;

public class HumanPlayer implements PlayerActions {
  IReversi model;
  public HumanPlayer(IReversi model) {
    this.model = model;
  }
  @Override
  public void pass() {
      model.passTurn();
  }

  @Override
  public void move(int q, int r, int who) {
    this.model.placeMove(q, r, who);
  }

}
