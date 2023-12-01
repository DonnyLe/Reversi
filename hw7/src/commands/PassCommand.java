package commands;

import view.ReversiGraphicsView;

/**
 * Placeholder pass command.
 */
public class PassCommand implements Runnable {

  ReversiGraphicsView view;

  public PassCommand(ReversiGraphicsView view) {
    this.view = view;
  }

  /**
   * Placeholder pass command.
   */
  @Override
  public void run() {
    this.view.notifyPass();
  }
}
