package view;

import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Shape;




import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import java.util.ArrayList;


import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import model.AxialCoord;
import model.DiscState;
import model.ReadonlyIReversi;

/**
 * JPanel object that is passed into ReversiGraphicsView. Displays the main board.
 */
public class ReversiPanel extends JPanel {
  private static final double CIRCLE_RADIUS = 0.2;
  private final ArrayList<HexagonImage> hexImageList;
  private final ReadonlyIReversi model;

  protected HexagonImage selectedHex;
  private boolean active;




  /**
   * Constructor for a ReversiPanel. Takes in a ReadOnlyIReversi model.
   * @param model ReadOnlyIReversi model.
   */
  ReversiPanel(ReadonlyIReversi model) {
    this.model = model;
    this.hexImageList = new ArrayList<>();

    MouseEventsListener listener = new MouseEventsListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
    this.active = false;
    selectedHex = null;
  }


  public void initializeHexImageList() {
    initializeMiddleRow();
    initializeAllRowsExceptMiddle();
    this.setBackground(Color.DARK_GRAY);
  }

  /**
   * This method tells Swing what the "natural" size should be
   * for this panel.  Here, we set it to 400x400 pixels.
   *
   * @return Our preferred *physical* size.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(350, 350);
  }


  @Override
  protected void paintComponent(Graphics g) {

    Graphics2D g2d = (Graphics2D) g.create();
    g2d.transform(transformLogicalToPhysical());
    g2d.setStroke(new BasicStroke((float) 0.03));

    for (HexagonImage hex : this.hexImageList) {
      drawHex(g2d, hex);
      AxialCoord axialCoords = hex.getAxialCoords();
      drawDisc(g2d, hex.getCenter(), this.model.getDiscAt(axialCoords.q, axialCoords.r));
    }

  }

  private void initializeMiddleRow() {
    for (int q = 0; q < this.model.getBoardArrayLength(); q++) {
      int r = this.model.getBoardArrayLength() / 2;
      DiscState disc = this.model.getDiscAt(q, r);

      Point.Double formattedPoint = getHexagonCenterCoords(q,
              this.model.getBoardArrayLength() / 2);
      HexagonImage hex = new HexagonImage(0.5, new Point.Double(
              formattedPoint.getX(), formattedPoint.getY()),
              new AxialCoord(q, r), Color.lightGray);
      this.hexImageList.add(hex);
    }
  }

  private void initializeAllRowsExceptMiddle() {
    for (int r = 0; r < this.model.getBoardArrayLength(); r++) {
      if (r != this.model.getBoardArrayLength() / 2) {
        for (int q = 0; q < this.model.getBoardArrayLength(); q++) {
          try {
            DiscState disc = this.model.getDiscAt(q, r);
            Point.Double formattedPoint = getHexagonCenterCoords(q, r);

            Point.Double shiftedFormattedPoint = new Point.Double(formattedPoint.getX()
                    + Math.sqrt(3) / 2 * 0.5 * (this.model.getBoardArrayLength() / 2 - r),
                    formattedPoint.getY());
            HexagonImage hex = new HexagonImage(0.5, shiftedFormattedPoint,
                    new AxialCoord(q, r), Color.lightGray);

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

  /**
   * Draws a hexagon based on the inputted HexagonImage (made using Path.2D).
   * Hexagon image
   *
   * @param g2d graphics
   * @param hex hexagonImage object
   */
  private void drawHex(Graphics2D g2d, HexagonImage hex) {
    g2d.setColor(hex.getColor());
    g2d.fill(hex);
    g2d.setColor(Color.BLACK);
    g2d.draw(hex);
  }

  /**
   * Draws a disc based on the inputted center point (usually the center point of
   * hexagon image) and the state of the disc.
   *
   * @param g2d graphics
   * @param center center of the disc object (center of hexagonImage)
   * @param disc disc state
   */
  private void drawDisc(Graphics2D g2d, Point.Double center, DiscState disc) {
    AffineTransform oldTransform = g2d.getTransform();
    if (disc != DiscState.NONE) {
      if (disc == DiscState.WHITE) {
        g2d.setColor(Color.WHITE);
      } else {
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
   * Helper method that calls other method to transform a q and r (that is compatible with the
   * model where q and r are non-negative) to logical coordinates that correctly
   * place the hexagon images in the desired hexagon board graphic.
   * @param q original axial q coord
   * @param r original axial r coord
   * @return a point object for the center of a hexagon.
   */
  private Point.Double getHexagonCenterCoords(int q, int r) {
    //translates q and r such that it works with the graphics transformation
    Point transformedPoint = translateAxialCoords(q, r);
    //adjusts the center point such that there are no gaps between hexagons
    return this.adjustAxialCoords((int) transformedPoint.getX(),
            (int) transformedPoint.getY());
  }


  /**
   * Translates the axial coords to coordinates that work with AffineTransform from
   * the method transformPhysicalToLogical. In other words, the inputs, q and r, use a coordinate
   * system where the origin, (0, 0), is the top left, unused space in the hexagon board array
   * (will be null based on invariant). The output will be a Point object where the origin,
   * (0, 0), is the center space of the hexagon board.
   *
   * @param q q coordinate
   * @param r r coordinate
   * @return translated axial coords
   */
  private Point translateAxialCoords(int q, int r) {
    int centerR = (int) Math.floor(this.model.getBoardArrayLength() / 2);
    return new Point(q - centerR, r - centerR);

  }

  /**
   * Adjust the return value of translateAxialCoords to produce a center coord for the hexagons
   * such that there are no gaps between hexagons (this function is mainly for formatting).
   * Uses modified code from <a href="https://www.redblobgames.com/grids/hexagons/#basics">
   * Hexagon tutorial</a>.
   *
   * @param q x value of the returned point from translateAxialCoords.
   * @param r y value of the returned point from translateAxialCoords.
   * @return center point for hexagons.
   */
  private Point.Double adjustAxialCoords(int q, int r) {
    double x = 0.5 * (Math.sqrt(3)) * q + Math.sqrt(3) / 2 * r;
    double y = 0.75 * (3 / 2) * r;
    return new Point.Double(x, y);
  }


  /**
   * Computes the transformation that converts screen coordinates
   * (with (0,0) in upper-left, width and height in pixels)
   * into board coordinates (with (0,0) in center, width and height
   * our logical size).
   *
   * @return The necessary transformation
   */
  private AffineTransform transformPhysicalToLogical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = new Dimension(this.model.getBoardArrayLength() - 1,
            this.model.getBoardArrayLength() - 1);
    ret.scale(1, 1);
    ret.scale(preferred.getWidth() / getWidth(), preferred.getHeight() / getHeight());
    ret.translate(-getWidth() / 2., -getHeight() / 2.);
    return ret;
  }

  /**
   * Computes the transformation that converts board coordinates
   * (with (0,0) in center, width and height our logical size)
   * into screen coordinates (with (0,0) in upper-left,
   * width and height in pixels).
   *
   * @return The necessary transformation
   */
  private AffineTransform transformLogicalToPhysical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = new Dimension(this.model.getBoardArrayLength() - 1,
            this.model.getBoardArrayLength() - 1);
    ret.translate(getWidth() / 2., getHeight() / 2.);
    ret.scale(getWidth() / preferred.getWidth(), getHeight() / preferred.getHeight());
    ret.scale(1, 1);
    return ret;
  }

  public void stopView() {
    this.active = false;

  }

  public void startView() {
    this.active = true;
  }

  /**
   * Handles mouse events (temporarily in ReversiPanel).
   */
  private class MouseEventsListener extends MouseInputAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
      boolean foundHex = false;
      if(active) {
        for (HexagonImage hex : hexImageList) {
          // This point is measured in actual physical pixels
          Point physicalP = e.getPoint();
          // For us to figure out which circle it belongs to, we need to transform it
          // into logical coordinates
          Point2D logicalP = transformPhysicalToLogical().transform(physicalP, null);
          if (hex.contains(logicalP) && ReversiPanel.this.selectedHex == null) {
            hex.setColor(Color.GREEN);
            ReversiPanel.this.selectedHex = hex;
            ReversiPanel.this.repaint();

            foundHex = true;
          } else if (hex.contains(logicalP) && ReversiPanel.this.selectedHex == hex) {
            ReversiPanel.this.selectedHex.setColor(Color.LIGHT_GRAY);
            ReversiPanel.this.selectedHex = null;
            ReversiPanel.this.repaint();
          } else if (hex.contains(logicalP) && ReversiPanel.this.selectedHex != null) {
            ReversiPanel.this.selectedHex.setColor(Color.LIGHT_GRAY);
            hex.setColor(Color.GREEN);
            ReversiPanel.this.selectedHex = hex;
            ReversiPanel.this.repaint();
            foundHex = true;

          }

        }
        if (!foundHex) {
          ReversiPanel.this.selectedHex.setColor(Color.LIGHT_GRAY);
          ReversiPanel.this.selectedHex = null;
          ReversiPanel.this.repaint();
        }

        if (foundHex) {
          System.out.println(ReversiPanel.this.selectedHex.getAxialCoords().r
                  + ", " + ReversiPanel.this.selectedHex.getAxialCoords().q);

        }
      }
    }
  }
}