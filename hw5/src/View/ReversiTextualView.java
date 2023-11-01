package View;


import Model.DiscState;
import Model.Hexagon;
import Model.IReversi;

/**
 * Temporary implementation of the reversi view. Displays ReversiModel as a string.
 */
public class ReversiTextualView implements IView {
  private IReversi model;

  /**
   * Constructor for reversi textual view.
   * @param model
   */
  public ReversiTextualView(IReversi model) {
    this.model = model;
  }


  /**
   * Produces textual representation of the Reversi board model
   * <p>X represents Black hexes, O represents white hexes, _ represents empty hexes</p>
   * @return Textual representation of the current state of Reversi board
   */
  @Override
  public String toString() {
    String view = "";
    Hexagon[][] board = null;

    board = model.getBoard();

    int afterHalf = 1;
    for (int i = 0; i < board.length; i++) {
      if (i > (board.length / 2)) {
        for (int b = 0; b < afterHalf; b++) {
          view += " ";
        }
        afterHalf++;
      }

      for (int j = 0; j < board.length; j++) {


        if (board[j][i] == null) {
          view += " ";
        } else if (board[j][i].hasNoDisk()) {
          view += "_ ";
        } else if (board[j][i].getDiscOnHex() == DiscState.BLACK) {
          view += "X ";
        } else if (board[j][i].getDiscOnHex() == DiscState.WHITE) {
          view += "O ";
        }
      }
      view += "\n";
    }
    return view;
  }
}