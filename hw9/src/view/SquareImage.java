package view;

import java.awt.Point;
import java.awt.Color;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * A Path2D.Double object that is drawn into the shape of a square.
 */
public class SquareImage extends Path2D.Double {
  private final double sideLength;

  private  Point.Double centerCoord;

  private Point.Double coordinate;

  private Color color;


  /**
   * Constructor for square image.
   * @param sideLength side length
   * @param coordinate coordinates
   * @param color color
   */
  public SquareImage(double sideLength, Point2D.Double coordinate, Color color) {
    this.color = color;
    this.coordinate = coordinate;
    this.sideLength = sideLength;
    getPath();
  }


  private void getPath() {
    Point2D[] pointList = new Point2D[4];
    Point2D coord = this.coordinate;
    pointList[0] = new Point2D.Double(coord.getX(), coord.getY());
    pointList[1] = new Point2D.Double(coord.getX() + this.sideLength, coord.getY());
    pointList[2] = new Point2D.Double(coord.getX()  + this.sideLength , coord.getY()  +
            this.sideLength);
    pointList[3] = new Point2D.Double(coord.getX(), coord.getY()  + this.sideLength);
    this.moveTo(pointList[0].getX(), pointList[0].getY());
    for (int i = 1; i < pointList.length; i++) {
      this.lineTo(pointList[i].getX(), pointList[i].getY());
    }
    this.closePath();
  }


  public void setColor(Color c) {
    this.color = c;
  }

  public Color getColor() {
    return this.color;
  }

  public Point2D.Double getCenter() {
    return new Point2D.Double(this.coordinate.getX() + this.sideLength / 2,
            this.coordinate.getY() + this.sideLength / 2);
  }

  public Point2D.Double getCoords() {
    return this.coordinate;
  }



}

