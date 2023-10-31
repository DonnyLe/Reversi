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

  public Hexagon(DiscState state) {
    this.state = state;
  }

  /**
   * Checks whether two non-empty hexagons are different colors
   *
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
   * Checks whether two non-empty hexagons are the same color
   *
   * @param other Hexagon
   * @return true if both hexagons are non-empty and the same color
   */
  public boolean sameColor(Hexagon other) {
    if(this.state == DiscState.NONE || other.state == DiscState.NONE) {
      return false;
    }
    return this.state == other.state;
  }

  /**
   * Checks the state of the Hex, whether it is Empty, Black or White
   *
   * @return whether the hex is empty or its color
   */
  public DiscState getDiscOnHex() {
    return this.state;
  }


  /**
   * Checks whether the Hexagon is empty
   *
   * @return true if Hexagon state is Empty
   */

  public boolean hasNoDisk() {
    return DiscState.NONE == this.state;
  }
}
