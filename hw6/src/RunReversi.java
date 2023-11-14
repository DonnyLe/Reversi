import model.IReversi;
import model.ReversiModel;
import view.ReversiGraphicsView;


/**
 * Main file to run Reversi.
 */
public class RunReversi {
  /**
   * Runs reversi.
   * @param args args
   */
  public static void main(String[] args) {
    IReversi model = new ReversiModel();
    model.startGame(4);
    ReversiGraphicsView rv = new ReversiGraphicsView(model);
    model.placeMove(4, 1, 0);



    rv.render();

  }
}
