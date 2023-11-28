import controller.IPlayer;
import controller.ReversiController;
import model.AxialCoord;
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

    ReversiGraphicsView rv1 = new ReversiGraphicsView(model);
    ReversiGraphicsView rv2 = new ReversiGraphicsView(model);
    IPlayer player = new IPlayer() {
      @Override
      public AxialCoord move() {
        return null;
      }

      @Override
      public void pass() {

      }
    };

    ReversiController c1 = new ReversiController(model, player, rv1);
    ReversiController c2 = new ReversiController(model, player, rv2);

    model.startGame(4);
    rv1.render();
    rv2.render();

  }
}
