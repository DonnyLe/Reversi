package model;

/**
 * A value object representing coordinates in the board
 */
public class AxialCoord {
  public final int r, q;
  public AxialCoord(int q, int r) {
    this.q = q;
    this.r = r;
  }
}