package view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

import javax.swing.*;

import model.ReadonlyIReversi;

public class ReversiPanel extends JPanel {
  ReadonlyIReversi model;
  ReversiPanel(ReadonlyIReversi model) {
    this.model = model;
  }

  @Override
  protected void paintComponent(Graphics g){
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g.create();
    for(int q = 0; q < this.model.getBoardArrayLength(); q++) {
      for(int r = 0; r < this.model.getBoardArrayLength(); r++ ) {
        this.model.getDiscAt(q, r);
      }
    }
//    Path2D.Double p = new Path2D.Double();




  }



}
