package Model;

public class Main {
  public static void main(String args[]) {
      ReversiModel r = new ReversiModel();
      r.startGame(4);
      Hexagon[][] h = r.getBoard();
      for(int i = 0; i < h.length; i++) {
        for(int j = 0; j < h[0].length; j++) {
          System.out.print(h[i][j]);
        }
        System.out.println();
      }


  }
}
