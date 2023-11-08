package view;


import model.DiscState;
import model.Hexagon;
import model.IReversi;

/**
 * Temporary implementation of the reversi view. Displays ReversiModel as a string.
 */
public class ReversiTextualView implements IView {
  private IReversi model;

  /**
   * Constructor for reversi textual view.
   * @param model an IReversi object (the model)
   */
  public ReversiTextualView(IReversi model) {
    this.model = model;
  }


  /**
   * Produces textual representation of the Reversi board model.
   * X represents Black hexes, O represents white hexes, _ represents empty hexes.
   *
   * @return Textual representation of the current state of Reversi board
   */
  @Override
  public String toString() {
    String view = "";

    int boardLength = this.model.getBoardArrayLength();
    int afterHalf = 1;
    for (int i = 0; i < boardLength; i++) {
      if (i > (boardLength / 2)) {
        for (int b = 0; b < afterHalf; b++) {
          view += " ";
        }
        afterHalf++;
      }

      for (int j = 0; j < boardLength; j++) {
        try {
          DiscState disc = this.model.getDiscAt(j, i);
          if(disc == DiscState.NONE) {
            view += "_ ";
          }
          else if (disc == DiscState.BLACK) {
            view += "X ";
          }
          else if (disc == DiscState.WHITE) {
            view += "O ";
          }
        }
        //catch outOfBounds error (j and i are the coordinates of a null spot)
        catch(IllegalArgumentException e) {
          view += " ";
        }
      }
      view += "\n";
    }
    return view;
  }
}