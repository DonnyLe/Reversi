package commands;

import view.IView;

/**
 * Placeholder move command.
 */
public class MoveCommand implements Runnable {

  private IView view;

  public MoveCommand(IView view) {
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
