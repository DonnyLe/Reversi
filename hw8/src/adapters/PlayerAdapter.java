package adapters;

import controller.PlayerActions;
import player.IPlayer;
import provider.controller.Player;
import provider.model.Coordinate;
import provider.model.Disc;
import provider.model.Reversi;

/**
 * Adapts our player implementation into their player implementation.
 */
public class PlayerAdapter implements Player, IPlayer {
  private IPlayer player;
  private Disc playerDisc;

  /**
   * Constructor for player adapter.
   * @param player Our player implementation
   * @param playerDisc The color associated with eh player.
   */
  public PlayerAdapter(IPlayer player, Disc playerDisc) {
      this.player = player;
      this.playerDisc = playerDisc;

  }

  /**
   * Places a move.
   */
  @Override
  public void move() {
      player.move();
  }

  /**
   * Passes the turn.
   */
  @Override
  public void pass() {
    player.pass();


  }

  /**
   * Adds features.
   * @param feature features to be added
   */
  @Override
  public void addFeatures(PlayerActions feature) {
      player.addFeatures(feature);
  }

  /**
   * Makes a move.
   *
   * @param model The {@link Reversi} game model representing the current game state.
   * @param move  The {@link Coordinate} representing the move to be made. If the player is
   *              unable to make a move, this parameter can be set to null.
   */
  @Override
  public void makeAMove(Reversi model, Coordinate move) {
      return;
  }

  /**
   * Returns true if it is the player's turn.
   * @param model The {@link Reversi} game model representing the current game state.
   * @return true if your turn
   */
  @Override
  public boolean isPlayerTurn(Reversi model) {
    Disc disc = model.currentColor();
    return disc == this.playerDisc;
  }

  /**
   * Returns Disc color associated with player.
   * @return Disc color
   */
  @Override
  public Disc getDisc() {
    return this.playerDisc;
  }

  /**
   * Passes turn.
   */
  @Override
  public void passTurn() {
    return;
  }
}