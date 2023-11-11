import model.IReversi;
import model.ReversiModel;
import view.ReversiGraphicsView;

public class RunReversi {
  public static void main(String[] args) {
    IReversi model = new ReversiModel();
    model.startGame(4);
    ReversiGraphicsView rv = new ReversiGraphicsView(model);
  }
}
