package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import model.AxialCoord;
import model.ReadonlyIReversi;
import strategy.ReversiStrategy;

public class MachinePlayer implements IPlayer{
  private ReadonlyIReversi model;
  private ReversiStrategy strat;

  private ArrayList<PlayerActions> features;

  /**
   * Constructor for machine player.
   * @param model the model for the game
   * @param strat the strategy used by the machine player
   */
  public MachinePlayer(ReadonlyIReversi model, ReversiStrategy strat) {
    this.model = model;
    this.strat = strat;
    this.features = new ArrayList<>();

  }

  /**
   * Chooses a move according to the strategy and moves or passes.
   */
  @Override
  public void move() {
    int delay = 1000;
    Timer timer = new Timer(delay, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        AxialCoord coord = strat.chooseMove(model, model.getTurn());
        for (PlayerActions f : features) {
          if (coord == null) {
            MachinePlayer.this.pass();
          } else {
            f.move(coord);
          }
        }
      }
    });
    timer.setRepeats(false); // Set to false to run the timer only once
    timer.start();


  }


  /**
   * Passes the turn.
   */
  @Override
  public void pass() {
    for(PlayerActions f: features) {
      f.pass();
    }
  }

  /**
   * Adds features to the player.
   * @param feature features to be added
   */
  @Override
  public void addFeatures(PlayerActions feature) {
    features.add(feature);
  }
}
