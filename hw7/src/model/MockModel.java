package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import controller.ModelObserver;

/**
 * `
 * Model for Reversi game. Implements IReversi and follows rules for a standard Reversi game except
 * game uses Hexagons instead of square (for shape of board and shape of spaces).
 */
public class MockModel implements IReversi, ReadonlyIReversi, ModelStatus {
  private boolean isGameStarted;
  //uses the axial coordinate system (see README for visual)
  //2D array is zero-indexed, using q and r from the axial coordinate system as inputs
  //origin or center of the hexagonal board is the (sideLen - 1, sideLen - 1)

  private Hexagon[][] board;


  private int numSkips;
  private int turn;
  private final int numPlayers = 2;
  //hashmap for connecting the player number and their color
  private final HashMap<Integer, DiscState> playerColors;

  List<ModelObserver> controllers = new ArrayList<>();
  int placemovecounter = 0;

  private StringBuilder log = new StringBuilder();
  private StringBuilder log2 = new StringBuilder();

  /**
   * Default constructor for a ReversiModel. Initializes all fields.
   */
  public MockModel() {
    this.isGameStarted = false;
    this.board = new Hexagon[0][0];
    this.numSkips = 0;
    this.playerColors = new HashMap<>();
    DiscState[] colorList = DiscState.values();

    for (int i = 0; i < this.numPlayers; i++) {
      this.playerColors.put(i, colorList[i]);
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

  public void init() {
    notifyYourTurn();
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
    initalizeTopHalf(sideLen);

    intializeBottomHalf(sideLen);

    setInitialDiscs(sideLen - 1);


  }

  private void intializeBottomHalf(int sideLen) {
    int endNullIndex = this.board.length - 1;
    int startNullIndex = endNullIndex;

    for (int q = sideLen; q < this.board.length; q++) {
      for (int r = 0; r < startNullIndex; r++) {
        this.board[q][r] = new Hexagon(DiscState.NONE);
      }
      for (int i = startNullIndex; i <= endNullIndex; i++) {
        this.board[q][i] = null;
      }
      startNullIndex--;
    }
  }

  private void initalizeTopHalf(int sideLen) {
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
  }

  private void setInitialDiscs(int center) {
    //initialize center as shown in assignment page
    this.board[center][center - 1] = new Hexagon(DiscState.BLACK);
    this.board[center - 1][center + 1] = new Hexagon(DiscState.BLACK);
    this.board[center + 1][center] = new Hexagon(DiscState.BLACK);
    this.board[center + 1][center - 1] = new Hexagon(DiscState.WHITE);
    this.board[center - 1][center] = new Hexagon(DiscState.WHITE);
    this.board[center][center + 1] = new Hexagon(DiscState.WHITE);

  }

  /**
   * Place a move based on rules of Reversi. Coordinates q and r uses the axial system
   * where the origin, (0, 0), is the center hexagon of the board.
   *
   * @param q   q coord
   * @param r   r coord
   * @param who integer representing current player
   * @throws IllegalStateException For invalid move
   * @throws IllegalArgumentException For invalid move
   */
  public void placeMove(int q, int r, int who) throws IllegalStateException,
          IllegalArgumentException {

    this.placeMoveHelper(q, r, who);
    this.notifyUpdateView();
    this.nextPlayer();



  }

  private void placeMoveHelper(int q, int r, int who) throws IllegalStateException,
          IllegalArgumentException {
    //precursor checks
    gameStartedCheck();
    gameOverCheck();
    hasMovesCheck(who);


    moveAllowedCheck(q, r, who);





    ArrayList<int[]> directions = getListDirectionsToSearch(q, r, who);
    ArrayList<int[]> validStraightLines = findValidStraightLines(directions, q, r, who);
    flipDiscs(q, r, validStraightLines, who);
    numSkips = 0;


  }

  /**
   * Checks the score increase of a given move.
   * @param q q coordinate of hex
   * @param r r coordinate of hex
   * @param who int representing player
   * @return score increase of move
   */
  public int checkMove(int q, int r, int who) {
    ReversiModel copy = this.copyBoard();
    copy.placeMoveHelper(q, r, who);
    log.append("\n" + String.format("checked %d, %d", q, r));
    return copy.getScore(who) - this.getScore(who);
  }


  /**
   * Helper function that increments the player and makes sure after the last player does action,
   * it returns to the first player.
   */
  private void nextPlayer() {
    if (this.isGameOver()) {
      this.notifyGameOver();
      this.notifyUpdateView();
      return;
    }
    turn++;
    turn %= this.numPlayers;
    this.notifyYourTurn();

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
   * Finds pairs of dx, dy that allow the player to flip pieces. Coordinates q and r uses the
   * axial system where the origin, (0, 0), represents the top left item in the 2D board array
   * (always null).
   *
   * @param validDirections adjacent directions that have the opposite player's disc
   *                        (return value of getListDirectionsToSearch)
   * @param q               q coordinate of move
   * @param r               r coordinate of move
   * @param who             integer representing the player
   * @return                a list of dx, dy pairs that point to directions where the player
   *                        will be able to flip discs (therefore, the move is valid).
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
   * Flips discs in the directions where discs COULD be flipped. Coordinates q and r uses the
   * axial system where the origin, (0, 0), represents the top left item in the 2D board array
   * (always null).
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
   * Checks all adjacent hexagons at hexagon q, r for opposing players discs. Coordinates
   * q and r uses the axial system where the origin, (0, 0), represents the top left item in
   * the 2D board array (always null).
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
   * or q and r represent the coordinates of a null spot). Coordinates q and r use the
   * axial system where the origin, (0, 0), represents the top left, unused item in the 2D board
   * array. Used to preserve invariant of all unused spots are null.
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
    if (this.board[q][r].getDiscOnHex() != DiscState.NONE) {
      throw new IllegalStateException("Need to choose a place that does"
              + " not have a disc on it");
    }
    ArrayList<int[]> validDirections = getListDirectionsToSearch(q, r, who);
    if (validDirections.isEmpty()) {
      throw new IllegalStateException("Chosen move coordinates has no adjacent "
              + "discs of the opposite player. Move not allowed");
    } else {
      ArrayList<int[]> validStraightLines = findValidStraightLines(validDirections, q, r, who);
      if (validStraightLines.isEmpty()) {
        throw new IllegalStateException("Chosen move coordinates has no straights lines "
                + "that allow player to flip pieces. ");
      }
    }

    if (who != this.getTurn()) {
      throw new IllegalStateException("Not your turn. Choose the other player.");
    }
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
   * Returns the status of the disc at the specified axial coordinates.
   *
   * @param q q coord
   * @param r r coord
   * @return DiscState of the specified hex
   */
  @Override
  public DiscState getDiscAt(int q, int r) {
    gameStartedCheck();

    if (isOutOfBounds(q, r)) {
      throw new IllegalArgumentException("Chosen coordinates are out of bounds");
    }
    return this.board[q][r].getDiscOnHex();
  }


  /**
   * Gets the length of the board array.
   *
   * @return int length of board array
   */
  @Override
  public int getBoardArrayLength() {
    gameStartedCheck();
    return this.board.length;
  }

  /**
   * Gets the side length of the board.
   *
   * @return int side length
   */
  @Override
  public int getSideLength() {
    gameStartedCheck();
    return (this.board.length + 1) / 2;
  }

  /**
   * Checks whether the given player has any legal moves.
   *
   * @param who integer representing the player
   */
  @Override
  public void hasMovesCheck(int who) {
    gameStartedCheck();

    for (int q = 0; q < this.board.length; q++) {
      for (int r = 0; r < this.board.length; r++) {
        try {
          this.moveAllowedCheck(q, r, who);
          //if no exception is thrown
          return;
        }
        //Exception e because it doesn't matter which exception is thrown,
        //if an exception is thrown, the move is not allowed

        catch (Exception e) {
          //this exception will be handled later by moveAllowedCheck, ignore it in this method
          if (e.getMessage().equals("Not your turn. Choose the other player.")) {
            return;
          }
          continue;
        }
      }
    }
    throw new IllegalStateException("No possible moves. You must pass turn");
  }

  /**
   * Gets the score of a player. Counts the number of discs a player has on the board.
   *
   * @param who integer representing the player
   * @return integer for the score.
   */
  @Override
  public int getScore(int who) {
    gameStartedCheck();

    int counter = 0;
    DiscState color = this.playerColors.get(who);
    for (int q = 0; q < this.board.length; q++) {
      for (int r = 0; r < this.board.length; r++) {
        if (!isOutOfBounds(q, r) && this.board[q][r].getDiscOnHex() == color) {
          counter++;
        }
      }
    }
    return counter;
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

  /**
   * Returns a copy of the model.
   *
   * @return ReversiModel deep copy of the model
   */
  @Override
  public ReversiModel copyBoard() {
    ReversiModel copy = new ReversiModel();
    Hexagon[][] copyB = new Hexagon[this.getBoardArrayLength()][this.getBoardArrayLength()];
    for (int i = 0; i < this.board.length; i++) {
      copyB[i] = Arrays.copyOf(this.board[i], this.board[i].length);

    }


    copy.startGame(copyB, this.turn);
    return copy;
  }

  /**
   * Adds the controller as an observer so that it can notify it.
   * @param controller the controller to be added
   */
  @Override
  public void addObserver(ModelObserver controller) {
    controllers.add(controller);
  }



  /**
   * Sends a notification to the controller for the current player that it is their turn.
   */
  public void notifyYourTurn() {
    //System.out.println("Controller " + this.turn + " " + controllers.get(this.turn));


    if (!controllers.isEmpty()) {
      controllers.get(this.turn).yourTurn();
      log2.append("\nSent your turn notification");

    }
  }

  /**
   * Sends a notification to all subscribed controllers to update their views.
   */
  public void notifyUpdateView() {
    for (ModelObserver controller : controllers) {
      controller.updateView();
      log2.append("\nSent update view notification");
    }
  }

  /**
   * Determines the winner and sends a notification to its controller, or sends a notification to
   * both in case of a draw.
   */
  public void notifyGameOver() {
    for (ModelObserver controller : controllers) {
      log2.append("\nSent stop game notification");
      controller.stopGame();
    }

    if (this.getScore(0) > this.getScore(1)) {
      controllers.get(0).youWin();
      log2.append("\nSent player 1 win notification");
    }
    else if (this.getScore(0) < this.getScore(1)) {
      controllers.get(1).youWin();
      log2.append("\nSent player 2 win notification");
    }
    else {
      for (ModelObserver controller : controllers) {
        controller.draw();
        log2.append("\nSent draw notification");
      }
    }
  }

  /**
   * Returns the log as a string.
   * @return String with log
   */
  public String getLog() {
    return log.toString();
  }

  /**
   * Returns the log 2 as a string.
   * @return String with log
   */
  public String getLog2() {
    return log2.toString();
  }

}
