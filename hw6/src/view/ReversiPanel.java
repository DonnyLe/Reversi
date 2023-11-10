package view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;

import javax.swing.*;

import model.DiscState;
import model.Hexagon;
import model.ReadonlyIReversi;

public class ReversiPanel extends JPanel {
  private ArrayList<HexagonImage> hexImageList;
  private final ReadonlyIReversi model;


  ReversiPanel(ReadonlyIReversi model) {
    this.model = model;
    this.hexImageList = new ArrayList<>();
  }

  public void refresh() {
    this.repaint();

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

    for(int  q= 0; q < this.model.getBoardArrayLength(); q++) {
      for (int r = 0; r < this.model.getBoardArrayLength(); r++) {

        try {
          DiscState disc = this.model.getDiscAt(q, r);
          Point p = convertToCartesianCoords(q, r);
          HexagonImage hexImg = new HexagonImage(0.5,
                  p.getX() - getXCoordHexCorrection(q),p.getY());
          hexImg.getPath();
          g2d.draw(hexImg);
          this.hexImageList.add(hexImg);
        }
        //if getDiscAt throws IllegalArgument exception due to q and r being out of bounds or
        //equalling null
        catch(IllegalArgumentException e) {
          continue;
        }
      }

      }
    }

  /**
   * Hexagons are square root of 3 units apart from each other (not 1 unit apart from eachother).
   * The following math corrects the spacing between hexagons in the x direction.
   * @param q
   * @return
   */
  private double getXCoordHexCorrection(int q) {
    return ((2-Math.sqrt(3)))/2 * (q + 1);

  }

  private Point convertToCartesianCoords(int q, int r) {
        int centerR = (int) Math.floor(this.model.getBoardArrayLength()/ 2);
        return new Point(q - centerR, r - centerR);
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
    ret.scale(1, -1);
    return ret;
  }




}
