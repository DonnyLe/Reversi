import java.util.ArrayList;

import adapters.ModelAdapter;
import adapters.StrategyAdapter;
import adapters.ViewAdapter;
import player.HumanPlayer;
import player.MachinePlayer;
import controller.ReversiController;
import model.ReversiModel;
import provider.controller.aistrat.CaptureMost;
import provider.view.ReversiFrame;
import strategy.AvoidBeforeCornersStrategy;
import strategy.CornersStrategy;
import strategy.ModularStrategy;
import strategy.MostCapturesStrategy;
import strategy.ReversiStrategy;
import view.IView;
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

    ReversiModel model = new ReversiModel(4);
    ReversiGraphicsView rv1 = new ReversiGraphicsView(model);
    //ReversiGraphicsView rv2 = new ReversiGraphicsView(model);


    /*
    ReversiGraphicsView rv2 = new ReversiGraphicsView(model);
    ArrayList<ReversiStrategy> strategies = new ArrayList<>();
    strategies.add(new CornersStrategy());
    strategies.add(new AvoidBeforeCornersStrategy());
    strategies.add(new MostCapturesStrategy());
    ModularStrategy strat = new ModularStrategy(strategies);

    HumanPlayer player2 = new HumanPlayer(model);
    MachinePlayer mplayer1 = new MachinePlayer(model, strat);
    MachinePlayer mplayer2 = new MachinePlayer(model, strat);





    ReversiController c1 = new ReversiController(model, player1, rv1);
    ReversiController c2 = new ReversiController(model, mplayer2, rv2);

     */




    //rv1.render();
    //rv2.render();


    //model.init();
    ReversiFrame theirView = new ReversiFrame(new ModelAdapter(model));
    ArrayList<ReversiStrategy> strategies = new ArrayList<>();
    strategies.add(new CornersStrategy());
    strategies.add(new AvoidBeforeCornersStrategy());
    strategies.add(new MostCapturesStrategy());
    ModularStrategy strat = new ModularStrategy(strategies);


    HumanPlayer player1 = new HumanPlayer(model);
    HumanPlayer player2 = new HumanPlayer(model);

    MachinePlayer mach = new MachinePlayer(model, new StrategyAdapter(new CaptureMost()));
    MachinePlayer mach1 = new MachinePlayer(model, new MostCapturesStrategy());

    IView rv2 = new ViewAdapter(theirView);

    ReversiController c1 = new ReversiController(model, mach, rv1);
    ReversiController c2 = new ReversiController(model, mach1, rv2);
    model.startGame();
    rv1.render();
    rv2.render();
    model.init();


  }
}



