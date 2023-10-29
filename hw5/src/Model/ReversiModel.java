package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReversiModel implements IReversi{
  boolean isGameStarted;
  //uses the axial coordinate system
  Hexagon[][] board;
  int numSkips;

  public ReversiModel() {
      this.isGameStarted = false;
      this.board = new Hexagon[0][0];
      this.numSkips = 0;

  }
  public void startGame(int sideLen) {
    this.isGameStarted = true;
    int startNull = 0;
    int endNull = this.board.length - 1 - sideLen;
    this.board = new Hexagon[2*sideLen - 1][2 * sideLen - 1];
    for(int q = 0; q<sideLen; q++) {
      for(int i = startNull; i <= endNull; i++) {
        this.board[q][i] = null;
      }
      for(int r = endNull + 1; r < 2 * sideLen - 1; r++) {
        this.board[q][r] = new Hexagon(HexState.EMPTY);
      }
      endNull--;
      }

    endNull = this.board.length - 1;
    startNull = endNull;

    for(int q = sideLen; q<this.board.length; q++) {
      for(int r = 0; r < startNull; r++) {
        this.board[q][r] = new Hexagon(HexState.EMPTY);
      }
      for(int i = startNull; i <= endNull; i++) {
        this.board[q][i] = null;
      }
      startNull--;
    }
    int center = sideLen - 1;

    this.board[center][center - 1] = new Hexagon(HexState.BLACK);
    this.board[center-1][center + 1] = new Hexagon(HexState.BLACK);
    this.board[center + 1][center] = new Hexagon(HexState.BLACK);
    this.board[center + 1][center - 1] = new Hexagon(HexState.WHITE);
    this.board[center - 1][center] = new Hexagon(HexState.WHITE);
    this.board[center][center + 1] = new Hexagon(HexState.WHITE);

  }


  @Override
  public void placeMove(int q, int r, int who) {

  }



  private List<Hexagon> getAdjacentHexagons(int q, int r) {
    ArrayList<Hexagon> res = new ArrayList<>();

    return res;

  }

  //psuedocode
  private boolean isMovedAllowed(int q, int r, int who) {
    return false;
  }

  @Override
  public Hexagon[][] getBoard() {
    Hexagon[][] res = null;
    res = this.board;
    return res;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }


  private boolean gameStarted() {
    if(!isGameStarted) {
      throw new IllegalStateException();
    }
    return true;
  }
}
