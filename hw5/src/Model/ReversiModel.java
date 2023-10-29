package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReversiModel implements IReversi{
  boolean isGameStarted;
  //uses the axial coordinate system
  List<List<Hexagon>> board;
  HashMap<Hexagon, HexagonColor> hexColors = new HashMap<>();
  int numSkips;

  public ReversiModel() {
      numSkips = 0;
      this.isGameStarted = false;
      this.board = new ArrayList<>();
      this.numSkips = 0;

  }
  public void startGame(int sideLen) {
    this.isGameStarted = true;
  }

  @Override
  public void placeMove(int q, int r, int who) {

  }

  private boolean isMovedAllowed(int q, int r, int who) {
    return false;
  }



  @Override
  public List<List<Hexagon>> getBoard() {
    return null;
  }

  @Override
  public Hexagon getHex(int q, int r) {
    return null;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public int getLengthRAxis() {
    return 0;
  }

  @Override
  public int getLengthSAxis() {
    return 0;
  }

  @Override
  public int getLengthQAxis() {
    return 0;
  }

  private boolean gameStarted() {
    if(!isGameStarted) {
      throw new IllegalStateException();
    }
    return true;
  }
}
