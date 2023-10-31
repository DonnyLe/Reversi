package View;


import Model.DiscState;
import Model.DiscState;
import Model.Hexagon;
import Model.IReversi;

public class ReversiTextualView implements IView{
  IReversi model;
  public ReversiTextualView(IReversi model) {
    this.model = model;

  }


  @Override
  public String toString(){
    String view = "";
    Hexagon[][] board = null;

    board = model.getBoard();

    int afterHalf = 1;
    for (int i = 0; i < board.length; i++) {
      if (i > (board.length / 2)) {
        for (int b = 0; b < afterHalf; b++){
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