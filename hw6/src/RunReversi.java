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
    rv.render();

  }
}
