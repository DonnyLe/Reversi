
import org.junit.Assert;
import org.junit.Test;


import java.util.ArrayList;

import player.HumanPlayer;
import player.MockMachinePlayer;
import controller.MockPlayerActions;
import controller.ReversiController;
import model.DiscState;
import model.MockModel;
import model.ReversiModel;
import strategy.AvoidBeforeCornersStrategy;
import strategy.CornersStrategy;
import strategy.ModularStrategy;
import strategy.MostCapturesStrategy;
import model.AxialCoord;
import strategy.ReversiStrategy;
import view.MockView;
import view.ReversiTextualView;

/**
 * Tests for ReversiModel.
 */
public class ReversiTests {


  @Test
  public void testVisualize() {
    ReversiModel m1 = new ReversiModel(6);
    m1.startGame();
    ReversiTextualView v1 = new ReversiTextualView(m1);


    Assert.assertEquals(v1.getString(),
            "     _ _ _ _ _ _ \n"
                    + "    _ _ _ _ _ _ _ \n"
                    + "   _ _ _ _ _ _ _ _ \n"
                    + "  _ _ _ _ _ _ _ _ _ \n"
                    + " _ _ _ _ X O _ _ _ _ \n"
                    + "_ _ _ _ O _ X _ _ _ _ \n"
                    + " _ _ _ _ X O _ _ _ _  \n"
                    + "  _ _ _ _ _ _ _ _ _   \n"
                    + "   _ _ _ _ _ _ _ _    \n"
                    + "    _ _ _ _ _ _ _     \n"
                    + "     _ _ _ _ _ _      \n");

    ReversiModel m2 = new ReversiModel(4);
    m2.startGame();
    ReversiTextualView v2 = new ReversiTextualView(m2);
    System.out.print(v2.getString());

    Assert.assertEquals(v2.getString(),
            "   _ _ _ _ \n"
                    + "  _ _ _ _ _ \n"
                    + " _ _ X O _ _ \n"
                    + "_ _ O _ X _ _ \n"
                    + " _ _ X O _ _  \n"
                    + "  _ _ _ _ _   \n"
                    + "   _ _ _ _    \n");
  }

  @Test
  public void testMove() {
    ReversiModel m1 = new ReversiModel(4);
    m1.startGame();
    m1.passTurn();
    m1.placeMove(2, 2, 1);

    Assert.assertEquals(m1.getDiscAt(2, 2), DiscState.BLACK);
    Assert.assertEquals(m1.getDiscAt(2, 3), DiscState.BLACK);


    m1.placeMove(1, 2, 0);


    Assert.assertEquals(m1.getDiscAt(2, 2), DiscState.WHITE);
    Assert.assertEquals(m1.getDiscAt(3, 2), DiscState.WHITE);


    m1.placeMove(4, 1, 1);

    Assert.assertEquals(m1.getDiscAt(3, 2), DiscState.BLACK);
    Assert.assertEquals(m1.getDiscAt(4, 2), DiscState.BLACK);
  }

  @Test
  public void testScore() {
    ReversiModel m1 = new ReversiModel(4);
    ReversiTextualView v1 = new ReversiTextualView(m1);

    m1.startGame();
    m1.passTurn();
    m1.placeMove(2, 2, 1);
    m1.placeMove(1, 2, 0);
    m1.placeMove(4, 1, 1);
    Assert.assertEquals(m1.getScore(0), 3);
    Assert.assertEquals(m1.getScore(1), 6);
  }


  @Test
  public void testPassGameOver() {
    ReversiModel m1 = new ReversiModel(4);
    m1.startGame();

    m1.passTurn();
    m1.passTurn();

    Assert.assertTrue(m1.isGameOver());
  }

  @Test
  public void testWrongTurn() {
    ReversiModel m1 = new ReversiModel(4);
    m1.startGame();

    Exception exception = Assert.assertThrows(IllegalStateException.class,
            () -> m1.placeMove(2, 2, 1));

    String expectedMessage = "Not your turn. Choose the other player.";
    String actualMessage = exception.getMessage();

    Assert.assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void testCannotMoveWhileGameOver() {
    ReversiModel m1 = new ReversiModel(4);
    m1.startGame();

    m1.passTurn();
    m1.passTurn();

    Exception exception = Assert.assertThrows(IllegalStateException.class,
            () -> m1.placeMove(2, 2, 1));

    String expectedMessage = "Game is over! Cannot do anything.";
    String actualMessage = exception.getMessage();

    Assert.assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void testCannotMoveBeforeGameStarted() {
    ReversiModel m1 = new ReversiModel(4);


    Exception exception = Assert.assertThrows(IllegalStateException.class,
            () -> m1.placeMove(2, 2, 1));

    String expectedMessage = "Game not started! Cannot make move";
    String actualMessage = exception.getMessage();

    Assert.assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void testInvalidSideLength() {
    ReversiModel m1 = new ReversiModel(2);


    Exception exception = Assert.assertThrows(IllegalArgumentException.class,
            () -> m1.startGame());

    String expectedMessage = "Side length must be at least 3";
    String actualMessage = exception.getMessage();

    Assert.assertEquals(expectedMessage, actualMessage);
  }


  @Test
  public void testOutOfBoundsMove() {
    ReversiModel m1 = new ReversiModel(4);
    m1.startGame();


    Exception exception = Assert.assertThrows(IllegalArgumentException.class,
            () -> m1.placeMove(0, 0, 0));

    String expectedMessage = "Chosen coordinates are out of bounds";
    String actualMessage = exception.getMessage();

    Assert.assertEquals(expectedMessage, actualMessage);
  }


  @Test
  public void testInvalidInBoundsMove() {
    ReversiModel m1 = new ReversiModel(4);
    m1.startGame();


    Exception exception = Assert.assertThrows(IllegalStateException.class,
            () -> m1.placeMove(3, 0, 0));

    String expectedMessage = "Chosen move coordinates has no adjacent "
            +
            "discs of the opposite player. Move not allowed";
    String actualMessage = exception.getMessage();

    Assert.assertEquals(expectedMessage, actualMessage);

  }

  @Test
  public void testInvalidInBoundsMove2() {
    ReversiModel m1 = new ReversiModel(4);
    m1.startGame();

    Exception exception = Assert.assertThrows(IllegalStateException.class,
            () -> m1.placeMove(6, 1, 0));

    String expectedMessage = "Chosen move coordinates has no adjacent "
            + "discs of the opposite player. Move not allowed";
    String actualMessage = exception.getMessage();

    Assert.assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void testInvalidMoveAlreadyFilledHex() {
    ReversiModel m1 = new ReversiModel(4);
    m1.startGame();

    Exception exception = Assert.assertThrows(IllegalStateException.class,
            () -> m1.placeMove(3, 2, 0));

    String expectedMessage = "Need to choose a place that does"
            + " not have a disc on it";
    String actualMessage = exception.getMessage();

    Assert.assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void testTurns() {
    ReversiModel m1 = new ReversiModel(4);
    m1.startGame();
    m1.passTurn(); //turn = 0
    m1.passTurn(); //turn = 1
    m1.passTurn(); //turn = 0
    m1.passTurn(); //turn = 1
    Assert.assertEquals(m1.getTurn(), 1); //turn = 0
  }

  @Test
  public void testMoveWithNoValidStraightLines() {
    ReversiModel m1 = new ReversiModel(4);
    m1.startGame();
    Exception exception = Assert.assertThrows(IllegalStateException.class,
            () -> m1.placeMove(5, 3, 0));
    String expectedMessage = "Chosen move coordinates has no straights lines "
            + "that allow player to flip pieces. ";
    String actualMessage = exception.getMessage();
    Assert.assertEquals(expectedMessage, actualMessage);
  }


  @Test
  public void testMostCapturesStrategy() {
    ReversiModel m1 = new ReversiModel(4);
    m1.startGame();
    MostCapturesStrategy strat = new MostCapturesStrategy();

    AxialCoord p = strat.chooseMove(m1, 0);
    Assert.assertEquals(p.r, 1);
    Assert.assertEquals(p.q, 4);

    m1.placeMove(4, 1, 0);

    p = strat.chooseMove(m1, 1);
    Assert.assertEquals(p.r, 0);
    Assert.assertEquals(p.q, 4);
  }

  @Test
  public void testAdditionalMethods() {
    ReversiModel m1 = new ReversiModel(4);
    m1.startGame();
    Assert.assertEquals(m1.getSideLength(), 4);
    Assert.assertEquals(m1.getBoardArrayLength(), 7);
  }

  @Test
  public void testAvoidBeforeCornersStrategy() {
    ReversiModel m1 = new ReversiModel(5);
    m1.startGame();
    AvoidBeforeCornersStrategy strat = new AvoidBeforeCornersStrategy();

    AxialCoord p = strat.chooseMove(m1, 0);
    Assert.assertEquals(p.r, 2);
    Assert.assertEquals(p.q, 5);

    m1.placeMove(5, 2, 0);


    p = strat.chooseMove(m1, 1);
    Assert.assertEquals(p.r, 3);
    Assert.assertEquals(p.q, 3);

  }

  @Test
  public void testCornersStrategy() {
    ReversiModel m1 = new ReversiModel(4);
    m1.startGame();
    CornersStrategy strat = new CornersStrategy();

    ReversiTextualView v = new ReversiTextualView(m1);

    m1.placeMove(2, 2, 0);

    System.out.print(v);


    AxialCoord p = strat.chooseMove(m1, 1);
    Assert.assertEquals(p.r, 1);
    Assert.assertEquals(p.q, 2);

  }

  @Test
  public void testModularStrategy() {
    ReversiModel m1 = new ReversiModel(4);
    m1.startGame();
    ArrayList<ReversiStrategy> strategies = new ArrayList<ReversiStrategy>();
    strategies.add(new CornersStrategy());
    strategies.add(new AvoidBeforeCornersStrategy());
    strategies.add(new MostCapturesStrategy());
    ModularStrategy strat = new ModularStrategy(strategies);

    ReversiTextualView v = new ReversiTextualView(m1);
    v.render();

    AxialCoord p = strat.chooseMove(m1, 0);
    Assert.assertEquals(p.r, 1);
    Assert.assertEquals(p.q, 4);
    m1.placeMove(4, 1, 0);

    v.render();
    p = strat.chooseMove(m1, 1);
    Assert.assertEquals(p.r, 0);
    Assert.assertEquals(p.q, 4);
    m1.placeMove(4, 0, 1);

    v.render();

    p = strat.chooseMove(m1, 0);
    Assert.assertEquals(p.r, 0);
    Assert.assertEquals(p.q, 5);
    m1.placeMove(5, 0, 0);

    v.render();

    p = strat.chooseMove(m1, 1);
    Assert.assertEquals(p.r, 0);
    Assert.assertEquals(p.q, 6);
    m1.placeMove(6, 0, 1);

    v.render();

    p = strat.chooseMove(m1, 0);
    Assert.assertEquals(p.r, 2);
    Assert.assertEquals(p.q, 5);
    m1.placeMove(5, 2, 0);

    v.render();

    p = strat.chooseMove(m1, 1);
    Assert.assertEquals(p.r, 4);
    Assert.assertEquals(p.q, 4);
    m1.placeMove(4, 4, 1);

    v.render();
  }

  @Test
  public void testMock() {
    MockModel m1 = new MockModel(4);
    m1.startGame();
    MostCapturesStrategy strat = new MostCapturesStrategy();

    ReversiTextualView v = new ReversiTextualView(m1);


    AxialCoord p = strat.chooseMove(m1, 0);

    System.out.print(m1.getLog());
    p = strat.chooseMove(m1, 0);
    Assert.assertEquals(p.r, 1);
    Assert.assertEquals(p.q, 4);

  }

  @Test
  public void testController() {
    ReversiModel m1 = new ReversiModel(4);
    MockView v1 = new MockView();
    MockView v2 = new MockView();
    HumanPlayer p1 = new HumanPlayer(m1);
    HumanPlayer p2 = new HumanPlayer(m1);
    ReversiController c1 = new ReversiController(m1, p1, v1);
    ReversiController c2 = new ReversiController(m1, p2, v2);
    m1.startGame();
    m1.init();

    c1.move(new AxialCoord(4, 1));
    Assert.assertEquals(m1.getDiscAt(4, 1), DiscState.WHITE);

    c2.move(new AxialCoord(4, 0));
    Assert.assertEquals(m1.getDiscAt(4, 0), DiscState.BLACK);
    c1.move(new AxialCoord(0, 5));
    c2.move(new AxialCoord(0, 6));
    c1.move(new AxialCoord(2, 5));
    c1.pass();
    c2.pass();


    System.out.println(v1.getLog());
    System.out.println(v2.getLog());

    Assert.assertEquals(v1.getLog(),
            "\n" +
                    "Start view notification recieved\n" +
                    "Stop view notification recieved\n" +
                    "Update view notification recieved\n" +
                    "Update view notification recieved\n" +
                    "Start view notification recieved\n" +
                    "Stop view notification recieved\n" +
                    "Display error notification recieved\n" +
                    "Repaint notification recieved\n" +
                    "Start view notification recieved\n" +
                    "Stop view notification recieved\n" +
                    "Update view notification recieved\n" +
                    "Stop view notification recieved\n" +
                    "Start view notification recieved\n" +
                    "Stop view notification recieved\n" +
                    "Display win notification recieved\n" +
                    "Update view notification recieved");

    Assert.assertEquals(v2.getLog(),
            "\n" +
                    "Update view notification recieved\n" +
                    "Start view notification recieved\n" +
                    "Stop view notification recieved\n" +
                    "Update view notification recieved\n" +
                    "Stop view notification recieved\n" +
                    "Display error notification recieved\n" +
                    "Repaint notification recieved\n" +
                    "Start view notification recieved\n" +
                    "Update view notification recieved\n" +
                    "Start view notification recieved\n" +
                    "Stop view notification recieved\n" +
                    "Stop view notification recieved\n" +
                    "Update view notification recieved");
  }

  @Test
  public void testObserver() {
    MockModel m1 = new MockModel(4);
    MockView v1 = new MockView();
    MockView v2 = new MockView();
    HumanPlayer p1 = new HumanPlayer(m1);
    HumanPlayer p2 = new HumanPlayer(m1);
    ReversiController c1 = new ReversiController(m1, p1, v1);
    ReversiController c2 = new ReversiController(m1, p2, v2);
    m1.startGame();
    m1.init();

    c1.move(new AxialCoord(4, 1));

    c2.move(new AxialCoord(4, 0));

    c1.move(new AxialCoord(5, 0));
    c2.move(new AxialCoord(6, 0));
    c1.move(new AxialCoord(5, 2));
    c2.pass();
    c1.pass();

    System.out.println(m1.getLog2());

    Assert.assertEquals(m1.getLog2(),
            "\n" +
                    "Sent your turn notification\n" +
                    "Sent update view notification\n" +
                    "Sent update view notification\n" +
                    "Sent your turn notification\n" +
                    "Sent update view notification\n" +
                    "Sent update view notification\n" +
                    "Sent your turn notification\n" +
                    "Sent update view notification\n" +
                    "Sent update view notification\n" +
                    "Sent your turn notification\n" +
                    "Sent update view notification\n" +
                    "Sent update view notification\n" +
                    "Sent your turn notification\n" +
                    "Sent update view notification\n" +
                    "Sent update view notification\n" +
                    "Sent your turn notification\n" +
                    "Sent your turn notification\n" +
                    "Sent stop game notification\n" +
                    "Sent stop game notification\n" +
                    "Sent player 1 win notification\n" +
                    "Sent update view notification\n" +
                    "Sent update view notification");

  }

  @Test
  public void testMachinePlayer() {
    ReversiModel model = new ReversiModel(4);
    ArrayList<ReversiStrategy> stratList = new ArrayList<>();
    stratList.add(new CornersStrategy());
    stratList.add(new AvoidBeforeCornersStrategy());
    stratList.add(new MostCapturesStrategy());
    ModularStrategy strats = new ModularStrategy(stratList);
    MockMachinePlayer player1 = new MockMachinePlayer(model, strats);
    MockMachinePlayer player2 = new MockMachinePlayer(model, strats);

    MockPlayerActions mock1 = new MockPlayerActions(model, player1);
    MockPlayerActions mock2 = new MockPlayerActions(model, player2);
    model.startGame();
    model.init();
    ReversiTextualView view = new ReversiTextualView(model);
    view.render();
    String player1Actual = mock1.getLog();
    String player2Actual = mock2.getLog();

    ReversiModel sameModel = new ReversiModel(4);
    sameModel.startGame();

    ArrayList<ArrayList<AxialCoord>> chosenMoves = new ArrayList<>();
    chosenMoves.add(new ArrayList<AxialCoord>());
    chosenMoves.add(new ArrayList<AxialCoord>());


    for (int i = 0; i < 26; i++) {
      AxialCoord coord = strats.chooseMove(sameModel, i % 2);
      chosenMoves.get(i % 2).add(coord);
      if (coord != null) {
        sameModel.placeMove(coord.q, coord.r, i % 2);
      } else {
        try {
          sameModel.passTurn();
        }
        //when pass turn ends the game (throws exception since no controllers)
        catch (IndexOutOfBoundsException e) {
          break;
        }
      }
    }
    String player1ExpectedRes = "";
    String player2ExpectedRes = "";


    for (int i = 0; i < 13; i++) {
      AxialCoord coord = chosenMoves.get(0).get(i);
      if (coord != null) {
        player1ExpectedRes += "Placed move to (" + coord.q + " , " + coord.r + ")\n";
      } else {
        player1ExpectedRes += "Passed move.\n";
      }
    }
    for (int i = 0; i < 13; i++) {
      AxialCoord coord = chosenMoves.get(1).get(i);
      if (coord != null) {
        player2ExpectedRes += "Placed move to (" + coord.q + " , " + coord.r + ")\n";
      } else {
        player2ExpectedRes += "Passed move.\n";
      }
    }
    Assert.assertEquals(player1Actual, player1ExpectedRes);
    Assert.assertEquals(player2Actual, player2ExpectedRes);
  }
}

