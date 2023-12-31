import java.util.ArrayList;
import java.util.HashMap;

import adapters.ModelAdapter;
import adapters.StrategyAdapter;
import adapters.ViewAdapter;
import player.HumanPlayer;
import player.IPlayer;
import player.MachinePlayer;
import controller.ReversiController;
import model.ReadonlyIReversi;
import model.ReversiModel;
import provider.controller.aistrat.AvoidCorners;
import provider.controller.aistrat.CaptureCorners;
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
public class ReversiCreator {
  /**
   * Runs reversi.
   * @param args args
   */
  public static void main(String[] args) {
    ReversiModel model = new ReversiModel(4);
    int sideLength = 4;


    boolean sideLengthSearch = false;
    boolean sideLengthFound = false;

    IPlayer player1 = null;
    IPlayer player2 = null;
    IPlayer[] players = new IPlayer[2];
    players[0] = player1;
    players[1] = player2;
    int currentPlayer = 0;





    for (int i = 0; i < args.length; i++) {
      if (currentPlayer < 2) {
        if (sideLengthSearch && isInt(args[i]) && !sideLengthFound) {
          sideLength = getInt(args[i]);
          sideLengthSearch = false;
          sideLengthFound = true;
          model = new ReversiModel(sideLength);
        }
        if (args[i].equals("side-length")) {
          sideLengthSearch = true;

        }
        if (args[i].equals("human-player")) {
          IPlayer player = players[currentPlayer];
          player = new HumanPlayer(model);
          players[currentPlayer] = player;
          currentPlayer++;
        }
        if (args[i].equals("machine-player")) {
          IPlayer player = players[currentPlayer];
          player = getMachinePlayer(model, i, args);
          players[currentPlayer] = player;
          currentPlayer++;
        }
      }
    }
    ReversiGraphicsView rv1 = new ReversiGraphicsView(model);
    IView rv2 = new ViewAdapter(new ReversiFrame(new ModelAdapter(model)));


    if (players[0] != null && players[1] != null ) {
      ReversiController c1 = new ReversiController(model, players[0], rv1);
      ReversiController c2 = new ReversiController(model, players[1], rv2);
      model.startGame();
      rv1.render();
      rv2.render();
      model.init();
    }

  }

  /**
   * Gets machine player based on string args.
   * @param model Model used
   * @param startIndex index in args where string "machine-player" is found
   * @param args the string arguments
   * @return Machine player
   */
  public static MachinePlayer  getMachinePlayer(ReadonlyIReversi model, int startIndex,
                                                String[] args) {
    ArrayList<ReversiStrategy> strategies = new ArrayList<>();
    strategies.add(new MostCapturesStrategy());
    HashMap<String, ReversiStrategy> allStrats = new HashMap<>();
    allStrats.put("strategy2", new AvoidBeforeCornersStrategy());
    allStrats.put("strategy3", new CornersStrategy());
    allStrats.put("providerStrategy1", new StrategyAdapter(new CaptureMost()));
    allStrats.put("providerStrategy2", new StrategyAdapter(new AvoidCorners()));
    allStrats.put("providerStrategy3", new StrategyAdapter(new CaptureCorners()));



    for (int i = startIndex + 1; i < i + 3 && i < args.length; i++) {
      if (allStrats.containsKey(args[i])) {
        strategies.add(allStrats.get(args[i]));
        allStrats.remove(args[i]);
      }
      else {
        break;
      }
    }
    return new MachinePlayer(model, new ModularStrategy(strategies));
  }


  /**
   * Checks if the string is an int.
   *
   * @param s String
   * @return true if int
   */
  public static boolean isInt(String s) {
    try {
      int val = Integer.valueOf(s);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  /**
   * Converts a string into an integer.
   *
   * @param s string representation of an integer.
   * @return an integer
   */
  public static int getInt(String s) {
    return Integer.valueOf(s);
  }
}