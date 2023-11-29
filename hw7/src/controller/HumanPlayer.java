package controller;

import java.util.ArrayList;
import model.ReadonlyIReversi;


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
}

