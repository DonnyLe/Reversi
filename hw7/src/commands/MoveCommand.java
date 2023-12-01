package commands;

import view.ReversiGraphicsView;

/**
 * Placeholder move command.
 */
public class MoveCommand implements Runnable {

  private ReversiGraphicsView view;

  public MoveCommand(ReversiGraphicsView view) {
    this.view = view;
  }

  /**
   * Placeholder move command.
   */
  @Override
  public void run() {
    this.view.notifyMove();
    this.view.updateView();


  }
}
