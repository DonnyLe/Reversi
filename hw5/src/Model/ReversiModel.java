package Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Model for Reversi game. Implements IReversi and follows rules for a standard Reversi game except
 * game uses Hexagons instead of square (for shape of board and shape of spaces).
 */
public class ReversiModel implements IReversi {
  private boolean isGameStarted;
  //uses the axial coordinate system (see README for more information)
  private Hexagon[][] board;
  private int numSkips;
  private int turn;
  private final int numPlayers = 2;
  //hashmap for connecting the player number and their color
  private final HashMap<Integer, DiscState> playerColors;

  /**
   * Default constructor for a ReversiModel. Initializes all fields.
   */
  public ReversiModel() {
    this.isGameStarted = false;
    this.board = new Hexagon[0][0];
    this.numSkips = 0;
    this.playerColors = new HashMap<>();
    DiscState[] colorList = DiscState.values();
    for (int i = 0; i < this.numPlayers; i++) {
      if (colorList[i] != DiscState.NONE) {
        this.playerColors.put(i, colorList[i]);
      }
    }

  }

  /**
   * Starts the ReversiModel game and produces a 2D array representation of the board based on the
   * sideLen.
   *
   * @param sideLen side length of Hexagonal board
   */
  @Override
  public void startGame(int sideLen) {
    this.isGameStarted = true;
    initializeBoard(sideLen);
  }

  /**
   * Helper function for initializing board. Side length must be at least 3 (since the initial
   * state shown in assignment page requires a board with a side length of at least 3 for it
   * to playable). Since the hexagonal shape of board does not completely fill 2D array,
   * all unused spaces will be always null (INVARIANT). For each used space, they will be initalized
   * as hexagons with DiscState.NONE.
   *
   * @param sideLen number of hexagon
   * @throws IllegalArgumentException if side length is less than 3
   */
  private void initializeBoard(int sideLen) {
    if (sideLen < 3) {
      throw new IllegalArgumentException("Side length must be at least 3");
    }
    this.board = new Hexagon[2 * sideLen - 1][2 * sideLen - 1];
    int startNullIndex = 0;
    int endNullIndex = this.board.length - 1 - sideLen;
    for (int q = 0; q < sideLen; q++) {
      //hexagonal shape of board does not completely fill 2D array
      //INVARIANT: unused spaces are always null
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

    //initialize center as shown in assignment page
    this.board[center][center - 1] = new Hexagon(DiscState.BLACK);
    this.board[center - 1][center + 1] = new Hexagon(DiscState.BLACK);
    this.board[center + 1][center] = new Hexagon(DiscState.BLACK);
    this.board[center + 1][center - 1] = new Hexagon(DiscState.WHITE);
    this.board[center - 1][center] = new Hexagon(DiscState.WHITE);
    this.board[center][center + 1] = new Hexagon(DiscState.WHITE);
  }

  /**
   * Place a move based on rules of Reversi.
   *
   * @param q   q coord for current players move
   * @param r   r coord for current players move
   * @param who integer representing current player
   */
  @Override
  public void placeMove(int q, int r, int who) {
    gameStartedCheck();
    gameOverCheck();
    moveAllowedCheck(q, r, who);
    ArrayList<int[]> directions = getListDirectionsToSearch(q, r, who);
    boolean found = false;
    for (int[] directionsPair : directions) {
      int dq = directionsPair[0];
      int dr = directionsPair[1];
      //found a valid straight line to parse
      found = doMove(q, r, dq, dr, who);
      //if doMove did not find a straight line to parse for all 4


    }
    //if doMove did not find a straight line to parse for all directions, move is not valid
    //therefore throw IllegalStateException
    if (!found) {
      throw new IllegalStateException("Move has no valid paths");
    }
  }

  /**
   * Throws IllegalState exception if game is not started. Helper function is called
   * by other public methods to make sure those methods are not running when the game
   * has not been started.
   */
  private void gameStartedCheck() {
    if (!isGameStarted) {
      throw new IllegalStateException("Game not started! Cannot make move");
    }
  }

  /**
   * Goes through a straight line based on dq, dr and flips discs if move is allowable.
   *
   * @param q   q coord for current players move
   * @param r   r coord for current players move
   * @param dq  change in q (direction to look in the q plane)
   * @param dr  change in r (direction to look in the r plane)
   * @param who integer representing current player
   */
  private boolean doMove(int q, int r, int dq, int dr, int who) {
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
    return foundSameColorDisc;
  }

  /**
   * Checks all adjacent hexagons at hexagon q, r for opposing players discs.
   *
   * @param q   q coord for current players move
   * @param r   r coord for current players move
   * @param who integer representing current player
   * @return returns a list of the directions that have opposing players discs.
   */
  private ArrayList<int[]> getListDirectionsToSearch(int q, int r, int who) {
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, -1}, {-1, 1}};
    Hexagon newHex = new Hexagon(this.playerColors.get(who));
    ArrayList<int[]> res = new ArrayList<>();
    for (int[] dirPair : directions) {
      int dq = dirPair[0];
      int dr = dirPair[1];
      if (this.isOutOfBounds(q + dq, r + dr)) {
        continue;
      }

      if (this.board[q + dq][r + dr].differentColor(newHex)) {
        res.add(dirPair);

      }
    }

    return res;
  }

  /**
   * Checks if q and r coordinates are out of bounds (out of bounds for out of the 2D list bounds
   * or q and r represent the coordinates of a null spot). Used to preseve invariant of all unused
   * spots are null.
   *
   * @param q q coord
   * @param r r coord
   * @return boolean returns true if q, r is out of bounds or a null spot, false otherwise
   */
  private boolean isOutOfBounds(int q, int r) {
    //preserves variant, out of bounds of q, r is null,
    //therefore, placeMove cannot occur
    return q < 0 || q >= this.board.length || r < 0 || r >= this.board[0].length
            || this.board[q][r] == null;
  }

  /**
   * Checks if chosen coordinates for move is out of bounds. Makes sure chosen move doesn't already
   * have a disc on it. Make sure that chosen move
   *
   * @param q q coord
   * @param r r coord
   * @param who integer representing player
   */
  private void moveAllowedCheck(int q, int r, int who) {
    if (isOutOfBounds(q, r)) {
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

  /**
   * Returns a copy of the board.
   *
   * @return a copy of the board
   */
  @Override
  public Hexagon[][] getBoard() {
    gameOverCheck();
    gameStartedCheck();

    Hexagon[][] res = new Hexagon[this.board.length][this.board[0].length];
    for (int q = 0; q < this.board.length; q++) {
      for (int r = 0; r < this.board[0].length; r++) {
        Hexagon hex = this.board[q][r];
        res[q][r] = hex;
      }
    }
    return res;
  }

  /**
   * Function to allow player to pass. Players are forced to manually pass if there are no
   * legal moves.
   */
  @Override
  public void passTurn() {
    gameStartedCheck();
    numSkips++;
    turn++;
    turn %= this.numPlayers;

  }

  /**
   * Throws error if attempting to do anything when the game is over.
   */
  private void gameOverCheck() {
    if (this.isGameOver()) {
      throw new IllegalStateException("Game is over! Cannot do anything.");
    }
  }

  /**
   * Returns true if two skips in a row occur (numSkips resets everytime a player makes a move).
   *
   * @return boolean for if game is over.
   */
  @Override
  public boolean isGameOver() {
    gameStartedCheck();
    return numSkips == 2;
  }

  /**
   * Returns an integer for the player turn. Player 1 starts at 0.
   *
   * @return integer
   */
  @Override
  public int getTurn() {
    gameStartedCheck();
    return this.turn;
  }


}
