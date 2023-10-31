package Model;

import java.util.List;

public interface IReversi {
  void placeMove(int r, int s, int who);

  void startGame(int sideLen);
  Hexagon[][] getBoard();

  boolean isGameOver();

  int getTurn();

  void passTurn();

}
