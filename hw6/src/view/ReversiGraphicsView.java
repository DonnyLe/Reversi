package view;

import java.awt.*;
import javax.swing.*;

import model.IReversi;
import model.ReadonlyIReversi;

public class ReversiGraphicsView extends JFrame implements IView{
  ReversiPanel reversiBoard;
  ReadonlyIReversi model;
  public ReversiGraphicsView(IReversi model) {
    super("Reversi");
    this.model = model;

  }

  public void render() {
    this.setBackground(Color.DARK_GRAY);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    reversiBoard = new ReversiPanel(model);
    this.add(reversiBoard);
    pack();
    setVisible(true);
  }

}
