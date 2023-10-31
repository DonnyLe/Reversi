package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class ReversiModel implements IReversi {
  private boolean isGameStarted;
  //uses the axial coordinate system
  private Hexagon[][] board;
  private int numSkips;
  private int turn;
  private final int numPlayers = 2;
  private final HashMap<Integer, DiscState> playerColors;

  public ReversiModel() {
    this.isGameStarted = false;
    this.board = new Hexagon[0][0];
    this.numSkips = 0;
    this.playerColors = new HashMap<>();
    DiscState[] colorList = DiscState.values();
    for(int i = 0; i < this.numPlayers; i++) {
      if(colorList[i] != DiscState.NONE) {
        this.playerColors.put(i, colorList[i]);
      }
    }

  }

  @Override
  public void startGame(int sideLen) {
    this.isGameStarted = true;
    initializeBoard(sideLen);
  }

  private void initializeBoard(int sideLen) {
    if (sideLen < 4) {
      throw new IllegalArgumentException("Side length must be at least 4");
    }
    this.board = new Hexagon[2 * sideLen - 1][2 * sideLen - 1];
    int startNullIndex = 0;
    int endNullIndex = this.board.length - 1 - sideLen;
    for (int q = 0; q < sideLen; q++) {
      for (int i = startNullIndex; i <= endNullIndex; i++) {
        this.board[q][i] = null;
      }
      for (int r = endNullIndex + 1; r < 2 * sideLen - 1; r++) {
        this.board[q][r] = new Hexagon(DiscState.NONE);
      }
      endNullIndex--;
    }

    endNullIndex = this.board.length - 1;
    startNullIndex = endNullIndex;

    for (int q = sideLen; q < this.board.length; q++) {
      for (int r = 0; r < startNullIndex; r++) {
        this.board[q][r] = new Hexagon(DiscState.NONE);
      }
      for (int i = startNullIndex; i <= endNullIndex; i++) {
        this.board[q][i] = null;
      }
      startNullIndex--;
    }
    int center = sideLen - 1;

    this.board[center][center - 1] = new Hexagon(DiscState.BLACK);
    this.board[center - 1][center + 1] = new Hexagon(DiscState.BLACK);
    this.board[center + 1][center] = new Hexagon(DiscState.BLACK);
    this.board[center + 1][center - 1] = new Hexagon(DiscState.WHITE);
    this.board[center - 1][center] = new Hexagon(DiscState.WHITE);
    this.board[center][center + 1] = new Hexagon(DiscState.WHITE);
  }


  @Override
  public void placeMove(int q, int r, int who) {
    gameStartedCheck();
    moveAllowedCheck(q, r, who);
    ArrayList<int[]> directions = getListDirectionsToSearch(q, r, who);
    for (int[] directionsPair : directions) {
      int dq = directionsPair[0];
      int dr = directionsPair[1];
      doMove(q, r, dq, dr, who);
    }
  }

  private void gameStartedCheck() {
    if(!isGameStarted) {
      throw new IllegalStateException("Game not started! Cannot make move");
    }
  }

  private void doMove(int q, int r, int dq, int dr, int who) {
    if (this.isGameOver()){
      throw new IllegalStateException("Cannot place move while game is over");
    }
    Hexagon newHex = new Hexagon(this.playerColors.get(who));
    int s = q + dq;
    int t = r + dr;
    boolean foundSameColorDisc = false;
    while (!this.isOutOfBounds(s, t)) {
      if (this.board[s][t].getDiscOnHex() == DiscState.NONE) {
        break;
      } else if (this.board[s][t].sameColor(newHex)) {
        foundSameColorDisc = true;
        break;
      }
      s += dq;
      t += dr;
    }

    if (foundSameColorDisc) {
      this.board[q][r] = newHex;
      q += dq;
      r += dr;
      while (s != q || t != r) {
        this.board[q][r] = new Hexagon(newHex.getDiscOnHex());
        q += dq;
        r += dr;
      }

    }
  }

  private ArrayList<int[]> getListDirectionsToSearch(int q, int r, int who) {
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, -1}, {-1, 1}};
    Hexagon newHex = new Hexagon(this.playerColors.get(who));
    ArrayList<int[]> res = new ArrayList<>();
    for (int[] dirPair: directions) {
      int dq = dirPair[0];
      int dr = dirPair[1];
      if(this.isOutOfBounds(q + dq, r+ dr)) {
        continue;
      }

      if (this.board[q + dq][r + dr].differentColor(newHex)) {
        res.add(dirPair);

      }
    }

    return res;
  }

  private boolean isOutOfBounds(int q, int r) {
    if (q < 0 || q >= this.board.length || r < 0 || r >= this.board[0].length
            || this.board[q][r] == null) {
      return true;
    }
    return false;
  }

  //psuedocode
  private void moveAllowedCheck(int q, int r, int who) {
    if(isOutOfBounds(q, r)) {
      throw new IllegalArgumentException("Chosen coordinates are out of bounds");
    }
    if (!this.board[q][r].hasNoDisk()) {
      throw new IllegalStateException("Need to choose a place that does"
              + " not have a disc on it");
    }
    ArrayList<int[]> dxdyList = getListDirectionsToSearch(q, r, who);
    if (dxdyList.size() == 0) {
      throw new IllegalArgumentException("Chosen move coordinates has no adjacent "
              + "discs of the opposite player. Move not allowed");
    }
  }

  @Override
  public Hexagon[][] getBoard() {
    gameStartedCheck();
    Hexagon[][] res = new Hexagon[this.board.length][this.board[0].length];
    for(int q = 0; q < this.board.length; q++) {
      for(int r = 0; r < this.board[0].length; r++) {
        Hexagon hex = this.board[q][r];
        res[q][r] = hex;
      }
    }
     return res;
  }

  @Override
  public void passTurn() {
    gameStartedCheck();
    numSkips++;
    turn++;
    turn %= this.numPlayers;

  }


  @Override
  public boolean isGameOver() {
    gameStartedCheck();
    return numSkips == 2;
  }

  @Override
  public int getTurn() {
    gameStartedCheck();
    return this.turn;
  }




}
