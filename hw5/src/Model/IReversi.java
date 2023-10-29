package Model;

import java.util.List;

public interface IReversi {
  void placeMove(int r, int s, int who);


  Hexagon[][] getBoard();

  boolean isGameOver();


}
