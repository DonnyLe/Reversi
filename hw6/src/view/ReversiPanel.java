package view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import javax.swing.*;

public class ReversiPanel extends JPanel {
  private int squareX = 50;
  private int squareY = 50;
  private int squareW = 20;
  private int squareH = 20;
  ReversiPanel() {
  }

  @Override
  protected void paintComponent(Graphics g){
    super.paintComponent(g);

    //placeholder
    g.drawString("This is my custom Panel!",10,20);
    g.setColor(Color.RED);
    Graphics2D g2d = (Graphics2D) g;




  }



}
