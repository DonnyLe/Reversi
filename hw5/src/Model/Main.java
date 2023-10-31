package Model;

import View.ReversiTextualView;

public class Main {
  public static void main(String args[]) {
    ReversiModel r = new ReversiModel();
    ReversiTextualView rtv = new ReversiTextualView(r);
    r.startGame(4);
    System.out.println(rtv.toString());
    r.placeMove(2, 2, 0);
    System.out.println(rtv.toString());
  }
}
