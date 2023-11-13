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
    this.setBackground(Color.DARK_GRAY);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    reversiBoard = new ReversiPanel(model);
    this.add(reversiBoard);
    pack();
    setVisible(true);
  }

}
