package Model;

public class Hexagon {
  private final HexState color;

  public Hexagon(HexState color) {
    this.color = color;
  }

  public boolean differentColor(Hexagon other) {
    if(this.color == HexState.EMPTY || other.color == HexState.EMPTY) {
      throw new IllegalArgumentException();
    }
    return this.color == other.color;
  }
  public HexState getDiscOnHex() {
    return this.color;
  }

  public boolean isEmpty() {
    return HexState.EMPTY == this.color;
  }
}
