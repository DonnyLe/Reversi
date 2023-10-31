package Model;

public class Hexagon {
  private final DiscState state;

  public Hexagon(DiscState state) {
    this.state = state;
  }

  public boolean differentColor(Hexagon other) {
    if(this.state == DiscState.NONE || other.state == DiscState.NONE) {
      return false;
    }
    return !(this.state == other.state);
  }

  public boolean sameColor(Hexagon other) {
    if(this.state == DiscState.NONE || other.state == DiscState.NONE) {
      return false;
    }
    return this.state == other.state;
  }
  public DiscState getDiscOnHex() {
    return this.state;
  }

  public boolean hasNoDisk() {
    return DiscState.NONE == this.state;
  }
}
