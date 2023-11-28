package model;

/**
 * A value object representing coordinates in the board.
 */
public class AxialCoord {
  public final int r;
  public final int q;

  /**
   * A value object representing coordinates in the board.
   */
  public AxialCoord(int q, int r) {

    this.q = q;
    this.r = r;
  }
}