import org.junit.*;

import Model.ReversiModel;
import View.ReversiTextualView;

public class ReversiTests {

  @Test
  public void test(){
    ReversiModel m1 = new ReversiModel();
    m1.startGame(6);
    ReversiTextualView v1 = new ReversiTextualView();
    v1.render(m1);

    ReversiModel m2 = new ReversiModel();
    m2.startGame(4);
    ReversiTextualView v2 = new ReversiTextualView();
    v2.render(m2);
  }


}
