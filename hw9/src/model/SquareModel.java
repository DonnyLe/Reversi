package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import controller.ModelObserver;

public class SquareModel implements IReversi, ModelStatus, ReadonlyIReversi {

  private boolean isGameStarted;

  private Hexagon[][] board;


  private int numSkips;
  private int turn;
  private final int numPlayers = 2;
  //hashmap for connecting the player number and their color
  private final HashMap<Integer, DiscState> playerColors;

  private List<ModelObserver> controllers = new ArrayList<>();

  public SquareModel() {
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

  protected void startGame(Hexagon[][] board, int turn) {
    this.isGameStarted = true;
    this.board = board;
    this.turn = turn;


  }

  /**
   * Helper function for initializing board. Side length must be at least 4 (since the initial
   * state shown in assignment page requires a board with a side length of at least 4 for it
   * to playable).
   *
   * @param sideLen number of squares in the side length of the hexagonal board
   * @throws IllegalArgumentException if side length is less than 4
   * @throws IllegalArgumentException id side length is odd
   */
  private void initializeBoard(int sideLen) {
    if (sideLen < 4) {
      throw new IllegalArgumentException("Side length must be at least 4");
    } else if (sideLen % 2 == 1) {
      throw new IllegalArgumentException("Side length must be even");
    }

    this.board = new Hexagon[sideLen][sideLen];
    for (int r = 0; r < sideLen; r++) {
      for (int q = 0; q < sideLen; q++) {
        this.board[r][q] = new Hexagon(DiscState.NONE);

      }
    }


    this.board[(sideLen / 2) - 1][(sideLen / 2) - 1] = new Hexagon(DiscState.BLACK);
    this.board[(sideLen / 2)][(sideLen / 2)] = new Hexagon(DiscState.BLACK);
    this.board[(sideLen / 2) - 1][(sideLen / 2)] = new Hexagon(DiscState.WHITE);
    this.board[(sideLen / 2)][(sideLen / 2) - 1] = new Hexagon(DiscState.WHITE);



  }


  /**
   * Notifies first turn.
   */
  public void init() {
    notifyYourTurn();
  }



  /**
   * Place a move based on rules of Reversi.
   *
   * @param q   q coord for current players move
   * @param r   r coord for current players move
   * @param who integer representing current player
   */
  @Override
  public void placeMove(int q, int r, int who) throws IllegalStateException,
          IllegalArgumentException {

    this.placeMoveHelper(q, r, who);
    this.notifyUpdateView();
    this.nextPlayer();

  }

  protected void placeMoveHelper(int q, int r, int who) throws IllegalStateException,
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
   * Checks all adjacent hexagons at hexagon q, r for opposing players discs. Coordinates
   * q and r uses a system where the origin, (0, 0), represents the top left item in
   * the 2D board array.
   *
   * @param q   q coord for current players move
   * @param r   r coord for current players move
   * @param who integer representing current player
   * @return returns a list of the directions that have opposing players discs.
   */
  private ArrayList<int[]> getListDirectionsToSearch(int q, int r, int who) {
    int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, -1}, {-1, 1}, {1, 1}, {-1, -1}};
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
   * Finds pairs of dx, dy that allow the player to flip pieces. Coordinates q and r uses a
   * system where the origin, (0, 0), represents the top left item in the 2D board array.
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
   * Flips discs in the directions where discs COULD be flipped. Coordinates q and r uses a
   * system where the origin, (0, 0), represents the top left item in the 2D board array.
   *
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
   * Function to allow player to pass. Passing is the only move if there are no legal moves for
   * the player.
   */
  @Override
  public void passTurn() {

    gameStartedCheck();
    numSkips++;
    this.nextPlayer();
  }

  /**
   * Helper function that increments the player and makes sure after the last player does action,
   * it returns to the first player.
   */
  private void nextPlayer() {
    if (this.isGameOver()) {
      this.notifyGameOver();
      this.notifyUpdateView();
    }
    else {
      turn++;
      turn %= this.numPlayers;
      this.notifyYourTurn();
    }

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
  @Override
  public void notifyYourTurn() {

    if (!controllers.isEmpty()) {
      controllers.get(this.turn).yourTurn();

    }
  }

  /**
   * Sends a notification to all subscribed controllers to update their views.
   */
  @Override
  public void notifyUpdateView() {

    for (ModelObserver controller : controllers) {
      controller.updateView();
    }
  }

  /**
   * Determines the winner and sends a notification to its controller, or sends a notification to
   * both in case of a draw.
   */
  @Override
  public void notifyGameOver() {

    for (ModelObserver controller : controllers) {
      controller.stopGame();
    }

    if (this.getScore(0) > this.getScore(1)) {
      controllers.get(0).youWin();
    }
    else if (this.getScore(0) < this.getScore(1)) {
      controllers.get(1).youWin();
    }
    else {
      for (ModelObserver controller : controllers) {
        controller.draw();
      }
    }
  }

  /**
   * Gets the disc of the hexagon at the given q and r coords (given q and r are valid coords).
   *
   * @param q q coords
   * @param r r coords
   * @return a Hexagon Enum
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
   * Returns the size of the array containing the board (not the side length of the hexagon board).
   *
   * @return integer for the size of the board
   */
  @Override
  public int getBoardArrayLength() {
    return this.board.length;
  }

  /**
   * Returns the side length of the hexagon board.
   *
   * @return integer for the size of sides
   */
  @Override
  public int getSideLength() {
    return this.board.length;
  }

  /**
   * Throws an exception is the player has no moves.
   *
   * @param who integer representing the player
   * @throws IllegalStateException if player has no moves. Forces player to pass turn.
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
   * Checks the score increase of a given move.
   *
   * @param q   q coordinate of hex
   * @param r   r coordinate of hex
   * @param who int representing player
   * @return score increase of move
   */
  @Override
  public int checkMove(int q, int r, int who) {
    SquareModel copy = this.copyBoard();
    copy.placeMoveHelper(q, r, who);
    return copy.getScore(who) - this.getScore(who);
  }

  /**
   * Returns a copy of the model.
   *
   * @return ReversiModel deep copy of the model
   */
  private SquareModel copyBoard() {
    SquareModel copy = new SquareModel();
    Hexagon[][] copyB = new Hexagon[this.getBoardArrayLength()][this.getBoardArrayLength()];
    for (int i = 0; i < this.board.length; i++) {
      copyB[i] = Arrays.copyOf(this.board[i], this.board[i].length);

    }


    copy.startGame(copyB, this.turn);
    return copy;
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
   * Checks if q and r coordinates are out of bounds (out of bounds for out of the 2D list bounds
   * or q and r represent the coordinates of a null spot). Coordinates q and r use a
   * system where the origin, (0, 0), represents the top left, item in the 2D board
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
}
