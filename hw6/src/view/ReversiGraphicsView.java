package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

import model.IReversi;
import model.ReadonlyIReversi;

public class ReversiGraphicsView extends JFrame implements IGraphicsView{
  ReversiPanel reversiBoard;
  ReadonlyIReversi model;
  public ReversiGraphicsView(IReversi model) {
    super("Reversi");
    this.model = model;
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setBackground(Color.DARK_GRAY);

    reversiBoard = new ReversiPanel(model);
    this.add(reversiBoard);
    pack();
    setVisible(true);
  }

}
