package view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class HexagonImage extends Path2D.Double{
  double sideLength;

  int q;
  int r;


  public HexagonImage(double sideLength, int q, int r) {
      this.sideLength = sideLength;
      this.q = q;
      this.r = r;
  }
  public void getPath( int centerOfHexagonBoard) {
    Point.Double center = getHexagonCenterCoords(q, r, centerOfHexagonBoard);

    boolean firstEdge = true;
    Point2D[] pointList = new Point2D[6];
    for(int i = 0; i < 6; i++) {
      int angleDegree = 60 * i - 30;
      double angleRadian = Math.PI / 180 * angleDegree;

      pointList[i] = new Point2D.Double(center.getX() + this.sideLength * Math.cos(angleRadian),
              center.getY() + this.sideLength * Math.sin(angleRadian));
    }
    for(Point2D point: pointList) {
      if(firstEdge) {
        this.moveTo(point.getX(), point.getY());
        firstEdge = false;

      }
      else {
        this.lineTo(point.getX(), point.getY());
      }
    }
    this.closePath();
  }
  private Point.Double getHexagonCenterCoords(int q, int r, int centerOfHexagonBoard) {
    //translates q and r such that it works with the graphics transformation
    Point transformedPoint = translateAxialCoords(q, r, centerOfHexagonBoard);
    //adjusts the center point such that there are no gaps between hexagons
    Point.Double formattedPoint = this.adjustAxialCoords((int) transformedPoint.getX(),
            (int) transformedPoint.getY());
    return formattedPoint;
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
  private Point translateAxialCoords(int q, int r, int centerOfHexagonBoard) {
    int centerR = (int) Math.floor(centerOfHexagonBoard/2);
    return new Point(q - centerR, r - centerR);
  }

  /**
   * Adjust the return value of translateAxialCoords to produce a center coord for the hexagons
   * such that there are no gaps between hexagons (this function is mainly for formatting).
   * Uses modified code from https://www.redblobgames.com/grids/hexagons/#basics.
   * @param q x value of the returned point from translatAxialCoords.
   * @param r y value of the returned point from translateAxialCoords.
   * @return center point for hexagons.
   */
  private Point.Double adjustAxialCoords(int q, int r) {
    double x = 0.5 * (Math.sqrt(3)) * q + Math.sqrt(3)/2 * r;
    double y = 0.75 * (3/2) * r;
    return new Point.Double(x, y);
  }




  }
