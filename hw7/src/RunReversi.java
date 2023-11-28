import controller.Controller;
import controller.Player;
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
    ReversiModel model = new ReversiModel();
    model.startGame(4);
    ReversiGraphicsView rv = new ReversiGraphicsView(model);
    rv.render();
    Player p = new Player(model);
    Controller c = new Controller(model, rv, p);
    c.playGame();
    }

}
