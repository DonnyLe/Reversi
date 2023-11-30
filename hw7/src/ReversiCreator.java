import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import controller.HumanPlayer;
import controller.IPlayer;
import controller.MachinePlayer;
import controller.ReversiController;
import model.ReadonlyIReversi;
import model.ReversiModel;
import strategy.AvoidBeforeCornersStrategy;
import strategy.CornersStrategy;
import strategy.ModularStrategy;
import strategy.MostCapturesStrategy;
import strategy.ReversiStrategy;
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
    ReversiModel model = new ReversiModel();
    int sideLength = 4;


    boolean sideLengthSearch = false;
    boolean sideLengthFound = false;

    IPlayer player1 = null;
    IPlayer player2 = null;
    IPlayer[] players = new IPlayer[2];
    players[0] = player1;
    players[1] = player2;
    int currentPlayer = 0;





    for(int i = 0; i< args.length; i++) {
      if(currentPlayer < 2) {
        if (sideLengthSearch && isInt(args[i]) && !sideLengthFound) {
          sideLength = getInt(args[i]);
          sideLengthSearch = false;
          sideLengthFound = true;
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
    ReversiGraphicsView rv2 = new ReversiGraphicsView(model);


    if(players[0] != null && players[1] != null ) {
      ReversiController c1 = new ReversiController(model, players[0], rv1);
      ReversiController c2 = new ReversiController(model, players[1], rv2);
      model.startGame(sideLength);
      rv1.render();
      rv2.render();
    }

  }

  public static MachinePlayer  getMachinePlayer(ReadonlyIReversi model, int startIndex, String[] args) {
    ArrayList<ReversiStrategy> strategies = new ArrayList<>();
    strategies.add(new MostCapturesStrategy());
    HashMap<String, ReversiStrategy> allStrats = new HashMap<>();
    allStrats.put("strategy2", new AvoidBeforeCornersStrategy());
    allStrats.put("strategy3", new CornersStrategy());

    for (int i = startIndex + 1; i < i + 3 && i < args.length; i++) {
      if (allStrats.containsKey(args[i])) {
        strategies.add(allStrats.get(args[i]));
        allStrats.remove(args[i]);
      }
      else {
        return null;
      }
    }
    return new MachinePlayer(model, new ModularStrategy(strategies));
  }


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




//
//package cs3500.klondike;
//
//        import java.io.InputStreamReader;
//
//        import cs3500.klondike.controller.KlondikeTextualController;
//        import cs3500.klondike.model.hw02.KlondikeModel;
//        import cs3500.klondike.model.hw04.LimitedDrawKlondike;
//
//
///**
// * Main class for creating a klondike game.
// */
//public final class Klondike {
//  /**
//   * Main method for starting a klondike game.
//   * Input "basic", "whitehead", and "limited" followed by integer arguments
//   * First and second integers for whitehead and basic represent the number of piles
//   * and the number of draw cards (respectively). First number for limited represents
//   * number of cards number of redraws allowed (must include this input). Next two inputs
//   * for limited piles are for the number of piles and number of cards in draw pile.
//   *
//   * @param args list of strings input through run configs.
//   */
//  public static void main(String[] args) {
//    KlondikeTextualController kc = new KlondikeTextualController(
//            new InputStreamReader(System.in), System.out);
//    try {
//      if (args[0].equals("basic")) {
//        GameType game = GameType.BASIC;
//        startBasicKlondikeOrWhiteheadKlondike(game, kc, args);
//      } else if (args[0].equals("whitehead")) {
//        GameType game = GameType.WHITEHEAD;
//        startBasicKlondikeOrWhiteheadKlondike(game, kc, args);
//      } else if (args[0].equals("limited")) {
//        GameType game = GameType.LIMITED;
//        startLimitedDrawKlondike(kc, args);
//      }
//      else {
//        throw new IllegalArgumentException();
//      }
//    } catch (IndexOutOfBoundsException e) {
//      throw new IllegalArgumentException();
//    }
//
//  }
//
//  /**
//   * Helper method to create a basic klondike game or a whitehead game (these two
//   * games have similar implementations.
//   *
//   * @param game GameType enum representing the game.
//   * @param kc   KlondikeTextualController
//   * @param args list of string from main
//   */
//  public static void startBasicKlondikeOrWhiteheadKlondike(GameType game,
//                                                           KlondikeTextualController kc, String[] args) {
//    KlondikeModel k = KlondikeCreator.create(game);
//    int numPiles = 7;
//    int numDraw = 3;
//    for (int i = 1; i < args.length; i++) {
//      String s = args[i];
//      if (isInt(args[i])) {
//        if (i == 1) {
//          numPiles = getInt(s);
//        }
//        if (i == 2) {
//          numDraw = getInt(s);
//        }
//      } else {
//        return;
//      }
//    }
//    try {
//      kc.playGame(k, k.getDeck(), false, numPiles, numDraw);
//    } catch (Exception e) {
//      return;
//    }
//  }
//
//  /**
//   * Initalize a limited draw klondike game.
//   *
//   * @param kc   a klondike controller
//   * @param args string arguments
//   */
//  public static void startLimitedDrawKlondike(KlondikeTextualController kc,
//                                              String[] args) {
//    int numLimitedDraw = 0;
//    int numPiles = 7;
//    int numDraw = 3;
//    if (args.length == 1) {
//      return;
//    }
//    for (int i = 1; i < args.length; i++) {
//      String s = args[i];
//      if (isInt(s)) {
//        if (i == 1) {
//          numLimitedDraw = getInt(s);
//        }
//        if (i == 2) {
//          numPiles = getInt(s);
//
//        }
//        if (i == 3) {
//          numDraw = getInt(s);
//
//        }
//      } else {
//        return;
//      }
//    }
//    KlondikeModel k = new LimitedDrawKlondike(numLimitedDraw);
//    kc.playGame(k, k.getDeck(), false, numPiles, numDraw);
//  }
//
//  /**
//   * Checks if a string is an integer.
//   *
//   * @param s a string representation of an integer
//   * @return boolean
//   */
//  public static boolean isInt(String s) {
//    try {
//      int val = Integer.valueOf(s);
//    } catch (NumberFormatException e) {
//      return false;
//    }
//    return true;
//  }
//
//  /**
//   * Converts a string into an integer.
//   *
//   * @param s string representation of an integer.
//   * @return an integer
//   */
//  public static int getInt(String s) {
//    return Integer.valueOf(s);
//  }
//
//
