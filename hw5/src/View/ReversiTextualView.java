package View;


import Model.HexState;
import Model.Hexagon;
import Model.IReversi;

public class ReversiTextualView implements IView{

  public void render(IReversi model){
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



        if (board[i][j] == null) {
          view += " ";
        } else if (board[j][i].isEmpty()) {
          view += "_ ";
        } else if (board[j][i].getDiscOnHex() == HexState.BLACK) {
          view += "X ";
        } else if (board[j][i].getDiscOnHex() == HexState.WHITE) {
          view += "O ";
        }
      }
      view += "\n";
    }

    System.out.print(view);



  }

}
