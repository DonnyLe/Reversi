package adapters;

import java.awt.*;
import java.util.HashMap;

import model.AxialCoord;
import model.DiscState;
import model.Hexagon;
import model.ReadonlyIReversi;
import provider.model.Cell;
import provider.model.Coordinate;
import provider.model.Disc;
import provider.model.ReversiReadOnly;

public class ModelAdapter implements ReversiReadOnly {
  ReadonlyIReversi model;
  public ModelAdapter(ReadonlyIReversi model){
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
