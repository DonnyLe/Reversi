

import org.junit.Assert;
import org.junit.Test;


import java.util.ArrayList;

import model.DiscState;
import model.MockModel;
import model.ReversiModel;
import strategy.AvoidBeforeCornersStrategy;
import strategy.CornersStrategy;
import strategy.ModularStrategy;
import strategy.MostCapturesStrategy;
import model.AxialCoord;
import strategy.ReversiStrategy;
import view.ReversiTextualView;

/**
 * Tests for ReversiModel.
 */
public class ReversiTests {


  @Test
  public void testVisualize() {
    ReversiModel m1 = new ReversiModel();
    m1.startGame(6);
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

    ReversiModel m2 = new ReversiModel();
    m2.startGame(4);
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
    ReversiModel m1 = new ReversiModel();
    m1.startGame(4);
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
    ReversiModel m1 = new ReversiModel();
    ReversiTextualView v1 = new ReversiTextualView(m1);

    m1.startGame(4);
    m1.passTurn();
    m1.placeMove(2, 2, 1);
    m1.placeMove(1, 2, 0);
    m1.placeMove(4, 1, 1);
    Assert.assertEquals(m1.getScore(0), 3);
    Assert.assertEquals(m1.getScore(1), 6);
  }


  @Test
  public void testPassGameOver() {
    ReversiModel m1 = new ReversiModel();
    m1.startGame(4);

    m1.passTurn();
    m1.passTurn();

    Assert.assertTrue(m1.isGameOver());
  }

  @Test
  public void testWrongTurn() {
    ReversiModel m1 = new ReversiModel();
    m1.startGame(4);

    Exception exception = Assert.assertThrows(IllegalStateException.class,
        () -> m1.placeMove(2, 2, 1));

    String expectedMessage = "Not your turn. Choose the other player.";
    String actualMessage = exception.getMessage();

    Assert.assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void testCannotMoveWhileGameOver() {
    ReversiModel m1 = new ReversiModel();
    m1.startGame(4);

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
    ReversiModel m1 = new ReversiModel();


    Exception exception = Assert.assertThrows(IllegalStateException.class,
        () -> m1.placeMove(2, 2, 1));

    String expectedMessage = "Game not started! Cannot make move";
    String actualMessage = exception.getMessage();

    Assert.assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void testInvalidSideLength() {
    ReversiModel m1 = new ReversiModel();


    Exception exception = Assert.assertThrows(IllegalArgumentException.class,
        () -> m1.startGame(2));

    String expectedMessage = "Side length must be at least 3";
    String actualMessage = exception.getMessage();

    Assert.assertEquals(expectedMessage, actualMessage);
  }



  @Test
  public void testOutOfBoundsMove() {
    ReversiModel m1 = new ReversiModel();
    m1.startGame(4);


    Exception exception = Assert.assertThrows(IllegalArgumentException.class,
        () -> m1.placeMove(0, 0, 0));

    String expectedMessage = "Chosen coordinates are out of bounds";
    String actualMessage = exception.getMessage();

    Assert.assertEquals(expectedMessage, actualMessage);
  }



  @Test
  public void testInvalidInBoundsMove() {
    ReversiModel m1 = new ReversiModel();
    m1.startGame(4);


    Exception exception = Assert.assertThrows(IllegalStateException.class,
        () -> m1.placeMove(3, 0, 0));

    String expectedMessage = "Chosen move coordinates has no adjacent " +
            "discs of the opposite player. Move not allowed";
    String actualMessage = exception.getMessage();

    Assert.assertEquals(expectedMessage, actualMessage);

  }

  @Test
  public void testInvalidInBoundsMove2() {
    ReversiModel m1 = new ReversiModel();
    m1.startGame(4);

    Exception exception = Assert.assertThrows(IllegalStateException.class,
        () -> m1.placeMove(6, 1, 0));

    String expectedMessage = "Chosen move coordinates has no adjacent "
            + "discs of the opposite player. Move not allowed";
    String actualMessage = exception.getMessage();

    Assert.assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void testInvalidMoveAlreadyFilledHex() {
    ReversiModel m1 = new ReversiModel();
    m1.startGame(4);

    Exception exception = Assert.assertThrows(IllegalStateException.class,
        () -> m1.placeMove(3, 2, 0));

    String expectedMessage = "Need to choose a place that does"
            + " not have a disc on it";
    String actualMessage = exception.getMessage();

    Assert.assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void testTurns() {
    ReversiModel m1 = new ReversiModel();
    m1.startGame(4);
    m1.passTurn(); //turn = 0
    m1.passTurn(); //turn = 1
    m1.passTurn(); //turn = 0
    m1.passTurn(); //turn = 1
    Assert.assertEquals(m1.getTurn(), 0); //turn = 0
  }

  @Test
  public void testMoveWithNoValidStraightLines() {
    ReversiModel m1 = new ReversiModel();
    m1.startGame(4);
    Exception exception = Assert.assertThrows(IllegalStateException.class,
        () -> m1.placeMove(5, 3, 0));
    String expectedMessage = "Chosen move coordinates has no straights lines "
            + "that allow player to flip pieces. ";
    String actualMessage = exception.getMessage();
    Assert.assertEquals(expectedMessage, actualMessage);
  }


  @Test
  public void testMostCapturesStrategy() {
    ReversiModel m1 = new ReversiModel();
    m1.startGame(4);
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
    ReversiModel m1 = new ReversiModel();
    m1.startGame(4);
    Assert.assertEquals(m1.getSideLength(), 4);
    Assert.assertEquals(m1.getBoardArrayLength(), 7);
  }

  @Test
  public void testAvoidBeforeCornersStrategy() {
    ReversiModel m1 = new ReversiModel();
    m1.startGame(5);
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
    ReversiModel m1 = new ReversiModel();
    m1.startGame(4);
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
    ReversiModel m1 = new ReversiModel();
    m1.startGame(4);
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
    MockModel m1 = new MockModel();
    m1.startGame(4);
    MostCapturesStrategy strat = new MostCapturesStrategy();

    ReversiTextualView v = new ReversiTextualView(m1);



    AxialCoord p = strat.chooseMove(m1, 0);

    System.out.print(m1.log);
    p = strat.chooseMove(m1, 0);
    Assert.assertEquals(p.r, 1);
    Assert.assertEquals(p.q, 4);

  }




}

