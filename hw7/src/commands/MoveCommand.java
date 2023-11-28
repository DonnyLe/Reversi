package commands;

import view.ReversiGraphicsView;

/**
 * Placeholder move command.
 */
public class MoveCommand implements Runnable {

  ReversiGraphicsView view;

  public MoveCommand(ReversiGraphicsView view){
    this.view = view;
  }

  /**
   * Placeholder move command.
   */
  @Override
  public void run() {
    //Placeholder
    System.out.println("move placeholder");
    this.view.notifyMove();


  }
}
