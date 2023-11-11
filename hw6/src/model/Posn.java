package model;

/**
 * A value object representing coordinates in the board
 */
public class Posn {
  public final int r, q;
  public Posn(int r, int q) {
    this.r = r;
    this.q = q;
  }
}