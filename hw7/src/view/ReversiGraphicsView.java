package view;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.swing.*;

import commands.MoveCommand;
import commands.PassCommand;
import controller.Features;
import model.ReadonlyIReversi;

/**
 * Graphical view for Reversi. Implements IView interface.
 */
public class ReversiGraphicsView extends JFrame implements IView {
  private ReversiPanel reversiBoard;
  private ReadonlyIReversi model;

  private boolean active;
  private List<Features> features = new ArrayList<Features>();

  /**
   * Reversi graphics view that takes in a ReadonlyIReversi model.
   * @param model Reversi model
   */
  public ReversiGraphicsView(ReadonlyIReversi model) {
    super("Reversi");
    this.model = model;
    this.active = false;
    this.reversiBoard = new ReversiPanel(model);
    this.add(reversiBoard);


  }




  /**
   * Initializes the graphical view by adding a ReversiPanel,
   * adding Keyboard and mouse listeners, and setting the view
   * as visible.
   */
  public void render() {
    this.setBackground(Color.DARK_GRAY);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.reversiBoard.initializeHexImageList();

    KeyboardListener keyboardListener = new KeyboardListener();
    HashMap<Character, Runnable> controls = new HashMap<Character, Runnable>();
    HashMap<Integer, Runnable> controls2 = new HashMap<Integer, Runnable>();
    controls.put('m', new MoveCommand(this));
    controls.put('p', new PassCommand(this));
    keyboardListener.setKeyTypedMap(controls);
    keyboardListener.setKeyPressedMap(controls2);
    keyboardListener.setKeyReleasedMap(controls2);

    this.addKeyListener(keyboardListener);

    pack();
    setVisible(true);
  }

  /**
   * Updates the view to correspond to the state of the model.
   */
  public void updateView(){
    this.reversiBoard.repaint();
  }

  /**
   * Displays an error message saying that a move is illegal.
   * @param e The error to display
   */
  public void displayError(RuntimeException e){

    JOptionPane.showMessageDialog(this, e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Displays a message stating that the player has won.
   */
  public void displayWin(){
    JOptionPane.showMessageDialog(this, "You Win!", "Game Over",
            JOptionPane.PLAIN_MESSAGE);
    this.repaint();
  }

  /**
   * Displays a message stating that there is a draw.
   */
  public void displayDraw(){
    JOptionPane.showMessageDialog(this, "Draw",
            "Game Over", JOptionPane.PLAIN_MESSAGE);
    this.repaint();
  }

  /**
   * Adds features to the view.
   * @param feature the features to be added.
   */
  public void addObserver(Features feature) {
    features.add(feature);
  }

  /**
   * Notifies the controller that the player wants to move to the currently selected hex.
   */
  public void notifyMove(){
    for (Features f : features){
      f.move(this.reversiBoard.selectedHex.getAxialCoords());
    }
  }

  /**
   * Notifies the controller that it wants to pass.
   */
  public void notifyPass(){
    for (Features f : features){
      f.pass();
    }
  }

  /**
   * Enables making moves for the view.
   */
  public void startView() {
    this.active = true;
    this.reversiBoard.startView();

  }

  /**
   * Disables making moves for the view.
   */
  public void stopView() {
    this.active = false;
    this.reversiBoard.stopView();
  }


  private class KeyboardListener implements KeyListener {

    private Map<Character, Runnable> keyTypedMap;
    private Map<Integer, Runnable> keyPressedMap;
    private Map<Integer, Runnable> keyReleasedMap;


    public void setKeyTypedMap(Map<Character, Runnable> map) {
      keyTypedMap = map;
    }

    public void setKeyPressedMap(Map<Integer, Runnable> map) {
      keyPressedMap = map;
    }

    public void setKeyReleasedMap(Map<Integer, Runnable> map) {
      keyReleasedMap = map;
    }

    /**
     * Runs the command associated with the key if the view is active.
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
      if (keyTypedMap.containsKey(e.getKeyChar()) && active) {
        keyTypedMap.get(e.getKeyChar()).run();
      }
    }

    /**
     * Runs the command associated with the key if the view is active.
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
      if (keyPressedMap.containsKey(e.getKeyCode()) && active) {
        keyPressedMap.get(e.getKeyCode()).run();
      }
    }

    /**
     * Runs the command associated with the key if the view is active.
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
      if (keyReleasedMap.containsKey(e.getKeyCode()) && active) {
        keyReleasedMap.get(e.getKeyCode()).run();
      }
    }


  }

}
