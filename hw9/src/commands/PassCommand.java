package commands;

import view.IView;

/**
 * Placeholder pass command.
 */
public class PassCommand implements Runnable {

  private IView view;

  public PassCommand(IView view) {
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
