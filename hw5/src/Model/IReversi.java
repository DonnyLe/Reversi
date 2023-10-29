package Model;

import java.util.List;

public interface IReversi {
  void placeMove(int r, int s, int who);


  List<List<Hexagon>> getBoard();

  Hexagon getHex(int r, int s);

  boolean isGameOver();

  int getLengthRAxis();

  int getLengthSAxis();

  int getLengthQAxis();

}
