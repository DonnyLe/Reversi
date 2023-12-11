import java.util.ArrayList;

import model.SquareModel;
import player.HumanPlayer;
import player.MachinePlayer;
import controller.ReversiController;
import model.ReversiModel;
import strategy.AvoidBeforeCornersStrategy;
import strategy.CornersStrategy;
import strategy.ModularStrategy;
import strategy.MostCapturesStrategy;
import strategy.ReversiStrategy;
import view.ReversiGraphicsView;
import view.SquareReversiGraphicsView;


/**
 * Main file to run Reversi.
 */
public class RunReversi {
  /**
   * Runs reversi.
   * @param args args
   */
  public static void main(String[] args) {

    SquareModel model = new SquareModel();

   SquareReversiGraphicsView  rv1 = new SquareReversiGraphicsView(model);
    SquareReversiGraphicsView rv2 = new SquareReversiGraphicsView(model);
    ArrayList<ReversiStrategy> strategies = new ArrayList<>();
    strategies.add(new CornersStrategy());
    strategies.add(new AvoidBeforeCornersStrategy());
    strategies.add(new MostCapturesStrategy());
    ModularStrategy strat = new ModularStrategy(strategies);

    HumanPlayer player1 = new HumanPlayer(model);
    HumanPlayer player2 = new HumanPlayer(model);
    MachinePlayer mplayer1 = new MachinePlayer(model, strat);
    MachinePlayer mplayer2 = new MachinePlayer(model, strat);



    ReversiController c1 = new ReversiController(model, player1, rv1);
    ReversiController c2 = new ReversiController(model, mplayer2, rv2);

    model.startGame(8);
    rv1.render();
    rv2.render();
    model.init();

  }
}



