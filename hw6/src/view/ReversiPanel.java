package view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import model.DiscState;
import model.Hexagon;
import model.ReadonlyIReversi;

public class ReversiPanel extends JPanel {
  private static final double CIRCLE_RADIUS = 0.2;
  private ArrayList<HexagonImage> hexImageList;
  private final ReadonlyIReversi model;


  ReversiPanel(ReadonlyIReversi model) {
    this.model = model;
    this.hexImageList = new ArrayList<>();
    MouseEventsListener listener = new MouseEventsListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
  }
  /**
   * This method tells Swing what the "natural" size should be
   * for this panel.  Here, we set it to 400x400 pixels.
   * @return  Our preferred *physical* size.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(350, 350);
  }


  @Override
  protected void paintComponent(Graphics g){
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.transform(transformLogicalToPhysical());
    g2d.setStroke(new BasicStroke((float) 0.03));
    drawMiddleRow(g2d);
    drawAllRowsExceptMiddle(g2d);
  }
  private void drawMiddleRow(Graphics2D g2d) {
    for(int q = 0; q < this.model.getBoardArrayLength(); q++) {
      DiscState disc = this.model.getDiscAt(q, this.model.getBoardArrayLength()/2);

      HexagonImage hex = new HexagonImage(0.5,
              formattedPoint.getX(),formattedPoint.getY());
      drawHex(g2d, hex);
      drawDisc(g2d, formattedPoint, disc);


      this.hexImageList.add(hex);
    }
  }

  private void drawAllRowsExceptMiddle(Graphics2D g2d) {
    for(int  r = 0; r < this.model.getBoardArrayLength();r++) {
      if(r != this.model.getBoardArrayLength()/2) {
        for (int q = 0; q < this.model.getBoardArrayLength(); q++) {
          try {
            DiscState disc = this.model.getDiscAt(q, r);
            Point.Double formattedPoint = getHexagonCenterCoords(q, r);

            Point.Double shiftedFormattedPoint = new Point.Double(formattedPoint.getX()
                    + Math.sqrt(3) / 2 * 0.5 * (this.model.getBoardArrayLength() / 2 - r),
                    formattedPoint.getY());

            HexagonImage hex = new HexagonImage(0.5,
                    shiftedFormattedPoint.getX(), shiftedFormattedPoint.getY() );
            drawHex(g2d, hex);
            drawDisc(g2d, shiftedFormattedPoint, disc);

            this.hexImageList.add(hex);
          }
          //if getDiscAt throws IllegalArgument exception due to q and r being out of bounds or
          //equalling null
          catch (IllegalArgumentException e) {
            continue;
          }
        }
      }
    }
  }

  private void drawHex(Graphics2D g2d, HexagonImage hex) {
    g2d.setColor(Color.lightGray);
    g2d.fill(hex);
    g2d.setColor(Color.BLACK);
    g2d.draw(hex);
  }

  private void drawDisc(Graphics2D g2d, Point.Double center, DiscState disc) {
    AffineTransform oldTransform = g2d.getTransform();
    if(disc != DiscState.NONE) {
      if(disc == DiscState.WHITE) {
        g2d.setColor(Color.WHITE);
      }
      else {
        g2d.setColor(Color.BLACK);
      }

      g2d.translate(center.getX(), center.getY());
      Shape circle = new Ellipse2D.Double(
              -CIRCLE_RADIUS,     // left
              -CIRCLE_RADIUS,     // top
              2 * CIRCLE_RADIUS,  // width
              2 * CIRCLE_RADIUS); // height
      g2d.fill(circle);

      g2d.setTransform(oldTransform);
    }
  }


  /**
   * Computes the transformation that converts screen coordinates
   * (with (0,0) in upper-left, width and height in pixels)
   * into board coordinates (with (0,0) in center, width and height
   * our logical size).
   * <p>
   * @return The necessary transformation
   */
  private AffineTransform transformPhysicalToLogical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = new Dimension(this.model.getBoardArrayLength()/2, this.model.getBoardArrayLength()/2);
    ret.scale(1, -1);
    ret.scale(preferred.getWidth() / getWidth(), preferred.getHeight() / getHeight());
    ret.translate(-getWidth() / 2., -getHeight() / 2.);
    return ret;
  }

  /**
   * Computes the transformation that converts board coordinates
   * (with (0,0) in center, width and height our logical size)
   * into screen coordinates (with (0,0) in upper-left,
   * width and height in pixels).
   * <p>
   * @return The necessary transformation
   */
  private AffineTransform transformLogicalToPhysical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = new Dimension(this.model.getBoardArrayLength(), this.model.getBoardArrayLength());
    ret.translate(getWidth() / 2., getHeight() / 2.);
    ret.scale(getWidth() / preferred.getWidth(), getHeight() / preferred.getHeight());
    ret.scale(1, 1);
    return ret;
  }
  private class MouseEventsListener extends MouseInputAdapter {
//    @Override
//    public void mousePressed(MouseEvent e) {
//      for(HexagonImage hex: hexImageList) {
//        if(hex.contains(e.getPoint())) {
//            if(ReversiPanel.this.model.moveAllowedCheck())
//            ReversiPanel.this.drawHex();
//        }
//      }
//      this.mouseDragged(e);
//    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
      // This point is measured in actual physical pixels
      Point physicalP = e.getPoint();
      // For us to figure out which circle it belongs to, we need to transform it
      // into logical coordinates
      Point2D logicalP = transformPhysicalToLogical().transform(physicalP, null);

      // TODO: Figure out whether this location is inside a circle, and if so, which one
    }
  }






}
