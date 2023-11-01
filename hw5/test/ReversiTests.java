import org.junit.*;

import Model.DiscState;
import Model.Hexagon;
import Model.ReversiModel;
import View.ReversiTextualView;

public class ReversiTests {


  @Test
  public void testVisualize() {
    ReversiModel m1 = new ReversiModel();
    m1.startGame(6);
    ReversiTextualView v1 = new ReversiTextualView(m1);
    System.out.print(v1.toString());

    Assert.assertEquals(v1.toString(),
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
    System.out.print(v2.toString());

    Assert.assertEquals(v2.toString(),
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

    m1.placeMove(2, 2, 1);

    Hexagon[][] currentState = m1.getBoard();

    Assert.assertEquals(currentState[2][2].getDiscOnHex(), DiscState.BLACK);
    Assert.assertEquals(currentState[2][3].getDiscOnHex(), DiscState.BLACK);

    m1.placeMove(1, 2, 0);

    currentState = m1.getBoard();

    Assert.assertEquals(currentState[2][2].getDiscOnHex(), DiscState.WHITE);
    Assert.assertEquals(currentState[3][2].getDiscOnHex(), DiscState.WHITE);

    m1.placeMove(4, 1, 1);

    currentState = m1.getBoard();

    Assert.assertEquals(currentState[3][2].getDiscOnHex(), DiscState.BLACK);
    Assert.assertEquals(currentState[4][2].getDiscOnHex(), DiscState.BLACK);
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


    Exception exception = Assert.assertThrows(IllegalArgumentException.class,
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
    m1.placeMove(5, 2, 0);

    Exception exception = Assert.assertThrows(IllegalArgumentException.class,
            () -> m1.placeMove(6, 1, 0));

    String expectedMessage = "Chosen move coordinates has no adjacent " +
            "discs of the opposite player. Move not allowed";
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


}

