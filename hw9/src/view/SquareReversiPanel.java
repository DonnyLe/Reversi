package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import model.DiscState;
import model.ReadonlyIReversi;

/**
 * JPanel object that is passed into ReversiGraphicsView. Displays the main board.
 */
public class SquareReversiPanel extends JPanel implements IPanel {
  private static final double CIRCLE_RADIUS = 0.2;
  private final ArrayList<SquareImage> squareImageList;
  private final ReadonlyIReversi model;

  protected SquareImage selectedSquare;
  private boolean active;
  private JLabel passMessage;




  /**
   * Constructor for a ReversiPanel. Takes in a ReadOnlyIReversi model.
   * @param model ReadOnlyIReversi model.
   */
  SquareReversiPanel(ReadonlyIReversi model) {
    super(null);
    this.model = model;
    this.squareImageList = new ArrayList<>();

    MouseEventsListener listener = new MouseEventsListener();
    this.addMouseListener(listener);
    this.addMouseMotionListener(listener);
    this.active = false;
    selectedSquare = null;
    this.passMessage = new JLabel("Passed move!");
    this.add(passMessage);
    Dimension d = this.passMessage.getPreferredSize();
    this.passMessage.setBounds(0, 0, d.width, d.height);
    this.passMessage.setForeground(Color.BLACK);

    this.passMessage.setVisible(false);

  }

  /**
   * Passes message.
   */
  public void passMessage() {
    int delay = 2000;
    this.passMessage.setVisible(true);


    Timer timer = new Timer(delay, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SquareReversiPanel.this.passMessage.setVisible(false);
      }
    });
    timer.setRepeats(false); // Set to false to run the timer only once
    timer.start();
  }



  /**
   * Initializes hex image list.
   */
  @Override
  public void initializeShapeImageList() {

    for(int x = 0; x < this.model.getSideLength(); x++) {
      for(int y = 0; y < this.model.getSideLength(); y++) {
        SquareImage s = new SquareImage(1, new Point2D.Double(x, y), Color.lightGray);
        this.squareImageList.add(s);
      }
    }

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

    for (SquareImage square : this.squareImageList) {
      drawSquare(g2d, square);
      Point2D coord = square.getCoords();
      drawDisc(g2d, square.getCenter(), this.model.getDiscAt((int) coord.getX(), (int) coord.getY()));
    }

  }



  /**
   * Draws a hexagon based on the inputted HexagonImage (made using Path.2D).
   * Hexagon image
   *
   * @param g2d graphics
   * @param square SquareImage object
   */
  private void drawSquare(Graphics2D g2d, SquareImage square) {
    g2d.setColor(square.getColor());
    g2d.fill(square);

    g2d.setColor(Color.BLACK);
    g2d.draw(square);

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
   * Computes the transformation that converts screen coordinates
   * (with (0,0) in upper-left, width and height in pixels)
   * into board coordinates (with (0,0) in center, width and height
   * our logical size).
   *
   * @return The necessary transformation
   */
  private AffineTransform transformPhysicalToLogical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = new Dimension(this.model.getBoardArrayLength() ,
            this.model.getBoardArrayLength());
    ret.scale(1, 1);
    ret.scale(preferred.getWidth() / getWidth(), preferred.getHeight() / getHeight());
//    ret.translate(-getWidth() / 2., -getHeight() / 2.);
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
    Dimension preferred = new Dimension(this.model.getBoardArrayLength() ,
            this.model.getBoardArrayLength() );
//    ret.translate(getWidth() / 2., getHeight() / 2.);
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
      Point physicalP = e.getPoint();
      Point2D logicalP = transformPhysicalToLogical().transform(physicalP, null);
      System.out.println(logicalP.getX()+ ", " + logicalP.getY());

      if (active) {
        for (SquareImage square : squareImageList) {
          // This point is measured in actual physical pixels
          physicalP = e.getPoint();
          // For us to figure out which circle it belongs to, we need to transform it
          // into logical coordinates
          logicalP = transformPhysicalToLogical().transform(physicalP, null);
          if (square.contains(logicalP) && SquareReversiPanel.this.selectedSquare == null) {
            square.setColor(Color.GREEN);
            SquareReversiPanel.this.selectedSquare = square;
            SquareReversiPanel.this.repaint();

            foundHex = true;
          } else if (square.contains(logicalP) && SquareReversiPanel.this.selectedSquare == square) {
            SquareReversiPanel.this.selectedSquare.setColor(Color.LIGHT_GRAY);
            SquareReversiPanel.this.selectedSquare = null;
            SquareReversiPanel.this.repaint();
          } else if (square.contains(logicalP) && SquareReversiPanel.this.selectedSquare != null) {
            SquareReversiPanel.this.selectedSquare.setColor(Color.LIGHT_GRAY);
            square.setColor(Color.GREEN);
            SquareReversiPanel.this.selectedSquare = square;
            SquareReversiPanel.this.repaint();
            foundHex = true;

          }

        }
        if (!foundHex) {
          SquareReversiPanel.this.selectedSquare.setColor(Color.LIGHT_GRAY);
          SquareReversiPanel.this.selectedSquare = null;
          SquareReversiPanel.this.repaint();
        }

        if (foundHex) {
          System.out.println(SquareReversiPanel.this.selectedSquare.getCoords().getX()
                  + ", " + SquareReversiPanel.this.selectedSquare.getCoords().getY());

        }
      }
    }
  }
}