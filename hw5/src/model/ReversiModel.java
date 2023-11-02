package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Model for Reversi game. Implements IReversi and follows rules for a standard Reversi game except
 * game uses Hexagons instead of square (for shape of board and shape of spaces).
 */
public class ReversiModel implements IReversi {
  private boolean isGameStarted;
  //uses the axial coordinate system (see README for visual)
  //2D array is zero-indexed, using q and r from the axial coordinate system as inputs
  //origin/center of the hexagonal board is the (sideLen - 1, sideLen - 1)
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
   * all unused spaces will be always null (INVARIANT). For each used space, they will be
   * initialized as hexagons with DiscState.NONE.
   *
   * @param sideLen number of hexagons in the side length of the hexagonal board
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
    ArrayList<int[]> validStraightLines = findValidStraightLines(directions, q, r, who);
    flipDiscs(q, r, validStraightLines, who);
    this.nextPlayer();
  }

  /**
   * Helper function that increments the player and makes sure after the last player does action,
   * it returns to the first player.
   */
  private void nextPlayer() {
    turn++;
    turn %= this.numPlayers;

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
   * Finds pairs of dx, dy that allow the player to flip pieces.
   *
   * @param validDirections adjacent directions that have the opposite player's disc
   *                        (return value of getListDirectionsToSearch)
   * @param q q coordinate of move
   * @param r r coordinate of move
   * @param who integer representing the player
   * @return a list of dx, dy pairs that point to directions where the player will be able
   *         to flip discs (therefore, the move is valid).
   */
  private ArrayList<int[]> findValidStraightLines(ArrayList<int[]> validDirections,
                                                  int q, int r, int who) {
    Hexagon newHex = new Hexagon(this.playerColors.get(who));
    ArrayList<int[]> validStraightLines = new ArrayList<>();
    for (int[] validDirectionPair : validDirections) {
      int dq = validDirectionPair[0];
      int dr = validDirectionPair[1];
      int s = q + dq;
      int t = r + dr;
      while (!this.isOutOfBounds(s, t)) {
        if (this.board[s][t].getDiscOnHex() == DiscState.NONE) {
          break;
        } else if (this.board[s][t].sameColor(newHex)) {
          validStraightLines.add(validDirectionPair);
          break;
        }
        s += dq;
        t += dr;
      }
    }
    return validStraightLines;
  }


  /**
   * Flips discs in the directions where discs COULD be flipped.
   *
   * @param q                  q coordinate of move
   * @param r                  r coordinate of move
   * @param validStraightLines directions where discs could be flipped
   * @param who                integer representing the player.
   */
  private void flipDiscs(int q, int r, ArrayList<int[]> validStraightLines, int who) {
    Hexagon newHex = new Hexagon(this.playerColors.get(who));
    this.board[q][r] = newHex;
    for (int[] dqdr : validStraightLines) {
      int dq = dqdr[0];
      int dr = dqdr[1];
      int s = q + dq;
      int t = r + dr;
      while (!this.board[s][t].sameColor(newHex)) {
        this.board[s][t] = new Hexagon(newHex.getDiscOnHex());
        s += dq;
        t += dr;
      }
    }

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
   * Throws exceptions if chosen move is illegal.
   *
   * @param q   q coord
   * @param r   r coord
   * @param who integer representing player
   * @throws IllegalArgumentException if chosen coordinates are out of bounds
   * @throws IllegalStateException    if chosen hexagon does not have adjacent
   *                                  that belong that to the opposite player, if the chosen
   *                                  hexagon has no valid straight lines
   *                                  (that allow the player to flip pieces), and if the
   *                                  chosen hexagon already has a disc on it
   */
  private void moveAllowedCheck(int q, int r, int who) {
    if (isOutOfBounds(q, r)) {
      throw new IllegalArgumentException("Chosen coordinates are out of bounds");
    }
    if (!this.board[q][r].hasNoDisk()) {
      throw new IllegalStateException("Need to choose a place that does"
              + " not have a disc on it");
    }
    ArrayList<int[]> validDirections = getListDirectionsToSearch(q, r, who);
    if (validDirections.size() == 0) {
      throw new IllegalStateException("Chosen move coordinates has no adjacent "
              + "discs of the opposite player. Move not allowed");
    } else {
      ArrayList<int[]> validStraightLines = findValidStraightLines(validDirections, q, r, who);
      if (validStraightLines.size() == 0) {
        throw new IllegalStateException("Chosen move coordinates has no straights lines "
                + "that allow player to flip pieces. ");
      }
    }

    if (who != this.getTurn()) {
      throw new IllegalStateException("Not your turn. Choose the other player.");
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
    this.nextPlayer();

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
