package controller;

import java.util.ArrayList;

import model.AxialCoord;
import model.IReversi;
import model.ReadonlyIReversi;
import strategy.ReversiStrategy;

public class HumanPlayer implements IPlayer{
  ReadonlyIReversi model;
  ArrayList<Features> features;

  public HumanPlayer(ReadonlyIReversi model) {
    this.model = model;
    this.features = new ArrayList<>();
  }

  @Override
  public void move() {


  }

  @Override
  public void pass() {

  }
  @Override
  public void addFeatures(Features feature) {
    features.add(feature);
  }
};

