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
    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    //reversi board panel
    reversiBoard = new ReversiPanel(model);
    reversiBoard.setPreferredSize(new Dimension(500, 300));
    JScrollPane scrollPane = new JScrollPane(reversiBoard);
    this.add(reversiBoard, BorderLayout.CENTER);

    pack();
    setVisible(true);
  }
}
