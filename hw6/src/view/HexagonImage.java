package view;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import model.AxialCoord;

/**
 * A Path2D.Double object that is drawn into the shape of a hexagon.
 */
public class HexagonImage extends Path2D.Double {
  private final double sideLength;

  private final Point.Double centerCoords;
  private final AxialCoord axialCoords;
  private Color color;

  /**
   * Constructor for a hexagon graphical image.
   * @param sideLength side length of the hexagon (length is in the view's logical coordinate
   *                   system). As a result, 1 does not equal 1 pixel.
   * @param centerCoords where the center of the hexagon should be placed (in logical, not physical
   *                     coordinate system).
   * @param axialCoords the original axial coordinates of hexagon (before transformations
   *                    that resulted in the centerCoords). axialCoords are non-negative, meaning
   *                    that (0, 0) is not the center hexagon. Used in order to get original Hexagon
   *                    object from the model.
   * @param c fill color of the hexagon graphical shape (not the disc color).
   */
  public HexagonImage(double sideLength, Point.Double centerCoords,
                      AxialCoord axialCoords, Color c) {
    this.sideLength = sideLength;
    this.centerCoords = centerCoords;
    this.axialCoords = axialCoords;
    this.getPath();
    this.color = c;
  }

  /**
   * Draws/traces the shape of the hexagon with respect
   * to the center coordinate. Math found from
   * https://www.redblobgames.com/grids/hexagons/.
   */
  private void getPath() {

    boolean firstEdge = true;
    Point2D[] pointList = new Point2D[6];
    for (int i = 0; i < 6; i++) {
      int angleDegree = 60 * i - 30;
      double angleRadian = Math.PI / 180 * angleDegree;

      pointList[i] = new Point2D.Double(this.centerCoords.getX()
              + this.sideLength * Math.cos(angleRadian),
              this.centerCoords.getY() + this.sideLength * Math.sin(angleRadian));
    }
    for (Point2D point : pointList) {
      if (firstEdge) {
        this.moveTo(point.getX(), point.getY());
        firstEdge = false;

      } else {
        this.lineTo(point.getX(), point.getY());
      }
    }
    this.closePath();
  }

  public void setColor(Color c) {
    this.color = c;
  }

  /**
   * Returns the center coordinates that are used to draw
   * the hexagon.
   * @return Point object
   */
  public Point.Double getCenter() {
    return this.centerCoords;

  }

  /**
   * Gets the color (not disc state) of the hexagon object.
   * @return
   */
  public Color getColor() {
    return this.color;
  }

  /**
   * Returns the axial coord of hexagon image object.
   * @return AxialCoord object
   */
  public AxialCoord getAxialCoords() {
    return this.axialCoords;
  }


}
