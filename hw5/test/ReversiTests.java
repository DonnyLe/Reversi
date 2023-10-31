import org.junit.*;

import Model.DiscState;
import Model.Hexagon;
import Model.ReversiModel;
import View.ReversiTextualView;

public class ReversiTests {


  @Test
  public void testVisualize(){
    ReversiModel m1 = new ReversiModel();
    m1.startGame(6);
    ReversiTextualView v1 = new ReversiTextualView(m1);
    System.out.print(v1.toString());

    Assert.assertEquals(v1.toString(),
            "     _ _ _ _ _ _ \n" +
            "    _ _ _ _ _ _ _ \n" +
            "   _ _ _ _ _ _ _ _ \n" +
            "  _ _ _ _ _ _ _ _ _ \n" +
            " _ _ _ _ X O _ _ _ _ \n" +
            "_ _ _ _ O _ X _ _ _ _ \n" +
            " _ _ _ _ X O _ _ _ _  \n" +
            "  _ _ _ _ _ _ _ _ _   \n" +
            "   _ _ _ _ _ _ _ _    \n" +
            "    _ _ _ _ _ _ _     \n" +
            "     _ _ _ _ _ _      \n");

    ReversiModel m2 = new ReversiModel();
    m2.startGame(4);
    ReversiTextualView v2 = new ReversiTextualView(m2);
    System.out.print(v2.toString());

    Assert.assertEquals(v2.toString(),
            "   _ _ _ _ \n" +
                    "  _ _ _ _ _ \n" +
                    " _ _ X O _ _ \n" +
                    "_ _ O _ X _ _ \n" +
                    " _ _ X O _ _  \n" +
                    "  _ _ _ _ _   \n" +
                    "   _ _ _ _    \n");
  }

  @Test
  public void testMove(){
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
  public void testPassGameOver(){
    ReversiModel m1 = new ReversiModel();
    m1.startGame(4);

    m1.passTurn();
    m1.passTurn();

    Assert.assertTrue(m1.isGameOver());
  }





}

