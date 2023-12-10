package view;


import model.DiscState;
import model.IReversi;


/**
 * Temporary implementation of the reversi view. Displays ReversiModel as a string.
 */
public class SquareReversiTextualView {
  private final IReversi model;

  /**
   * Constructor for reversi textual view.
   *
   * @param model an IReversi object (the model)
   */
  public SquareReversiTextualView(IReversi model) {
    this.model = model;
  }


  /**
   * Prints a textual representation of the board.
   */
  public void render() {
    System.out.print(this.getString());
  }

  /**
   * Produces textual representation of the Reversi board model.
   * X represents Black hexes, O represents white hexes, _ represents empty hexes.
   *
   * @return Textual representation of the current state of Reversi board
   */
  public String getString() {
    String view = "";
    int boardLength = this.model.getBoardArrayLength();
    for (int i = 0; i < boardLength; i++) {
      for (int j = 0; j < boardLength; j++) {
        DiscState disc = this.model.getDiscAt(j, i);
        if (disc == DiscState.NONE) {
          view += "_";
        } else if (disc == DiscState.BLACK) {
          view += "X";
        } else if (disc == DiscState.WHITE) {
          view += "O";
        }
      }
      view += "\n";
    }
    return view;
  }
}

