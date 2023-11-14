package view;


/**
 * Temporary implementation of the reversi view. Displays ReversiModel as a string.
 */
public interface IView {

  /**
   * Produces textual representation of the Reversi board model.
   * X represents Black hexes, O represents white hexes, _ represents empty hexes.
   *
   * @return Textual representation of the current state of Reversi board
   */
  String toString();

}
