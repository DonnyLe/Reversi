package adapters;

import controller.PlayerActions;
import player.IPlayer;
import provider.controller.Player;
import provider.model.Coordinate;
import provider.model.Disc;
import provider.model.Reversi;

public class PlayerAdapter implements Player, IPlayer {
  private IPlayer player;
  private Disc playerDisc;

  public PlayerAdapter(IPlayer player, Disc playerDisc) {
      this.player = player;
      this.playerDisc = playerDisc;

  }

  @Override
  public void move() {
      player.move();
  }

  @Override
  public void pass() {
    player.pass();


  }

  @Override
  public void addFeatures(PlayerActions feature) {
      player.addFeatures(feature);
  }

  @Override
  public void makeAMove(Reversi model, Coordinate move) {
      return;
  }

  @Override
  public boolean isPlayerTurn(Reversi model) {
    Disc disc = model.currentColor();
    return disc == this.playerDisc;
  }

  @Override
  public Disc getDisc() {
    return this.playerDisc;
  }

  @Override
  public void passTurn() {
    return;
  }
}