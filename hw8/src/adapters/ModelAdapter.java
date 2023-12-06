package adapters;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import model.AxialCoord;
import model.DiscState;
import model.Hexagon;
import model.ReadonlyIReversi;
import model.ReversiModel;
import provider.model.Cell;
import provider.model.Coordinate;
import provider.model.Disc;
import provider.model.Reversi;
import provider.model.ReversiReadOnly;

public class ModelAdapter implements Reversi {
  ReversiModel model;
  public ModelAdapter(ReversiModel model){
    this.model = model;
  }

  public HashMap<Coordinate, Cell> convertBoard(Hexagon[][] board){
    HashMap<Coordinate, Cell> outBoard = new HashMap<>();
    for (int q = 0; q < board.length; q++){
      for (int r = 0; r < board[q].length; r++){
        outBoard.put(new Coordinate(q, r), new Cell(this.convertDisc(board[q][r].getDiscOnHex())));
      }
    }
    return outBoard;

  }


  /**
   * Converts DiscState enum to Disc enum.
   * @param state DiscState enum
   * @return Disc enum
   */
  public Disc convertDisc(DiscState state){
    if (state == DiscState.WHITE) {return Disc.WHITE;}
    else if (state == DiscState.BLACK) {return Disc.BLACK;}
    else {return Disc.EMPTY;}
  }

  /**
   * Returns current color.
   */
  @Override
  public Disc currentColor() {
    if(this.model.getTurn() == 0) {return Disc.WHITE;}
    else {return Disc.BLACK;}
  }

  /**
   * Passes the turn to the next player.
   */
  @Override
  public void passTurn() {

    this.model.passTurn();
  }

  /**
   * Attempts to make a move on the board by placing the current
   * player's disc at the specified coordinate.
   * The method validates the move, flips any captured opponent discs,
   * and switches the turn to the next player.
   *
   * @param dest The target coordinate where the current player's disc should be placed.
   * @throws IllegalArgumentException If the move is invalid, such as when the target
   *                                  cell is already occupied or doesn't result in any opponent
   *                                  disc captures.
   */
  @Override
  public void makeMove(Coordinate dest) {

    this.model.placeMove(dest.getQ(), dest.getR(), this.model.getTurn());
  }

  /**
   * Places a disc at the specified cell coordinates.
   *
   * @param q The q-coordinate of the cell.
   * @param r The r-coordinate of the cell.
   * @param d The disc to be placed.
   * @throws IllegalArgumentException If the cell doesn't exist in the grid.
   */
  @Override
  public void placeDisc(int q, int r, Disc d) {

    int turn;
    if (d == Disc.WHITE) {turn = 0;}
    else {turn = 1;}
    this.model.placeMove(q, r, turn);
  }

  /**
   * Retrieves the disc at the specified cell coordinates.
   *
   * @param q The q-coordinate of the cell.
   * @param r The r-coordinate of the cell.
   * @return The disc present at the specified coordinates.
   * @throws IllegalArgumentException If the cell doesn't exist in the grid.
   */
  @Override
  public Disc getDiscAt(int q, int r) {
    AxialCoord centerCoord = this.translateAxialCoords(q, r);
    return convertDisc(this.model.getDiscAt(centerCoord.q, centerCoord.r));
  }

  /**
   * Checks if the cell at the specified coordinates is empty.
   *
   * @param q The q-coordinate of the cell.
   * @param r The r-coordinate of the cell.
   * @return True if the cell is empty, otherwise false.
   * @throws IllegalArgumentException If the cell doesn't exist in the grid.
   */
  @Override
  public boolean isCellEmpty(int q, int r) {
    return this.model.getDiscAt(q, r) == DiscState.NONE;
  }

  /**
   * Retrieves the size of the game board.
   *
   * @return The size of the board.
   */
  @Override
  public int getSize() {
    return this.model.getSideLength();
  }

  /**
   * Checks if the game is over.
   *
   * @return True if the game is over, otherwise false.
   */
  @Override
  public boolean isGameOver() {
    return this.model.isGameOver();
  }

  /**
   * Creates a deep copy of the current game board.
   *
   * @return A {@link HashMap} representing a copy of the board. Each {@link Coordinate}.
   * key is mapped to a {@link Cell} value.
   */
  @Override
  public HashMap<Coordinate, Cell> createCopyOfBoard() {
    return this.convertBoard(this.model.copyBoard2());
  }

  /**
   * Calculates and returns a list of all possible moves for the current player.
   *
   * @return An {@link ArrayList} of {@link Coordinate} objects representing all possible moves.
   * that the current player can make.
   */
  @Override
  public ArrayList<Coordinate> getPossibleMoves() {
    ArrayList<Coordinate> possibleCoords = new ArrayList<Coordinate>();
    for (int q = 0; q < this.model.getBoardArrayLength(); q++) {
      for (int r = 0; r < this.model.getBoardArrayLength(); r++) {
        if(this.model.moveAllowedCheck2(q, r, this.model.getTurn())) {
          possibleCoords.add(new Coordinate(q, r));
        }
      }
    }
    return possibleCoords;

  }

  /**
   * Returns the current score of a specified player.
   *
   * @param player
   * @return True if the game is over, otherwise false.
   */
  @Override
  public int getScore(Disc player) {
    if (player == Disc.BLACK) {
      this.model.getScore(1);
    }
    else if (player == Disc.WHITE) {
      this.model.getScore(0);
    }
    return 0;
  }

  @Override
  public int checkMove(Reversi model, Coordinate move) {
    AxialCoord centerCoord = this.translateAxialCoords(move.getQ(), move.getR());
    return this.model.checkMove(centerCoord.q, centerCoord.r, this.model.getTurn());
  }

  @Override
  public ArrayList<Coordinate> getPossibleMoves() {
    return null;
  }


  /**
   * Converts coordinates from center-origin to top left origin coordinates.
   * @param q q coord
   * @param r r coord
   * @return converted AxialCoord
   */
  private AxialCoord translateAxialCoords(int q, int r) {
    int centerR = (int) Math.floor(this.model.getBoardArrayLength() / 2);
    return new AxialCoord(q + centerR, r + centerR);

  }


}



