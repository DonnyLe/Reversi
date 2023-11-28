package view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

//import commands.MoveCommand;
//import commands.PassCommand;
import controller.Player;
import controller.PlayerActions;
import model.ReadonlyIReversi;

/**
 * Graphical view for Reversi. Implements IView interface.
 */
public class ReversiGraphicsView extends JFrame implements IView {
  ReversiPanel reversiBoard;
  ReadonlyIReversi model;

  /**
   * Reversi graphics view that takes in a ReadonlyIReversi model.
   * @param model Reversi model
   */
  public ReversiGraphicsView(ReadonlyIReversi model) {
    super("Reversi");
    this.model = model;

  }

  public void addFeatures(PlayerActions playerActions, Player player) {
    this.reversiBoard.addFeatures(playerActions, player);
  }





  /**
   * Initializes the graphical view by adding a ReversiPanel,
   * adding Keyboard and mouse listeners, and setting the view
   * as visible.
   */
  public void render() {
    this.setBackground(Color.DARK_GRAY);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    reversiBoard = new ReversiPanel(model);
    this.add(reversiBoard);

    KeyboardListener keyboardListener = new KeyboardListener();
    HashMap<Character, Runnable> controls = new HashMap<Character, Runnable>();
    HashMap<Integer, Runnable> controls2 = new HashMap<Integer, Runnable>();
//    controls.put('m', new MoveCommand());
//    controls.put('p', new PassCommand());
    keyboardListener.setKeyTypedMap(controls);
    keyboardListener.setKeyPressedMap(controls2);
    keyboardListener.setKeyReleasedMap(controls2);

    this.addKeyListener(keyboardListener);

    pack();
    setVisible(true);
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

    @Override
    public void keyTyped(KeyEvent e) {
      if (keyTypedMap.containsKey(e.getKeyChar())) {
        keyTypedMap.get(e.getKeyChar()).run();
      }
    }

    @Override
    public void keyPressed(KeyEvent e) {
      if (keyPressedMap.containsKey(e.getKeyCode())) {
        keyPressedMap.get(e.getKeyCode()).run();
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
      if (keyReleasedMap.containsKey(e.getKeyCode())) {
        keyReleasedMap.get(e.getKeyCode()).run();
      }
    }
  }

}
