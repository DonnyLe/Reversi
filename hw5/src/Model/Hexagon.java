package Model;

/**
 * This class represents an individual Hexagon on the Reversi board
 * <p>
 *   A Hexagon is characterised by a DiscState, which represents whether the hex
 *   has a disc on it and what color it is if it does.
 * </p>
 */
public class Hexagon {
  private final DiscState state;

  /**
   * Constructor for a Hexagon. Takes in a DiscState enum. All hexagons are initialized as
   * having a DiscState.NONE when game is initially started.
   * @param state DiscState object
   */
  public Hexagon(DiscState state) {
    this.state = state;
  }

  /**
   * Checks whether two non-empty hexagons have discs of different colors.
   * @param other Hexagon
   * @return true if one hexagon is black and the other is white
   */
  public boolean differentColor(Hexagon other) {
    if(this.state == DiscState.NONE || other.state == DiscState.NONE) {
      return false;
    }
    return !(this.state == other.state);
  }

  /**
   * Checks whether two non-empty Hexagons have discs of the same color.
   * @param other Hexagon
   * @return true if both discs on the hexagons are non-empty and the same color
   */
  public boolean sameColor(Hexagon other) {
    if(this.state == DiscState.NONE || other.state == DiscState.NONE) {
      return false;
    }
    return this.state == other.state;
  }

  /**
   * Checks the state of the Hex, whether it is None, Black or White.
   * @return whether the hex is empty or its color
   */
  public DiscState getDiscOnHex() {
    return this.state;
  }


  /**
   * Checks whether the Hexagon has no disk on top of it.
   * @return true if Hexagon has no disk on it.
   */

  public boolean hasNoDisk() {
    return DiscState.NONE == this.state;
  }
}
