package view;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import model.AxialCoord;

public class HexagonImage extends Path2D.Double{
  double sideLength;

  Point.Double centerCoords;
  AxialCoord axialCoords;
  Color color;

  public HexagonImage(double sideLength, Point.Double centerCoords, AxialCoord axialCoords, Color c) {
    this.sideLength = sideLength;
    this.centerCoords = centerCoords;
    this.axialCoords = axialCoords;
    this.getPath();
    this.color = c;
  }
  public void getPath() {
    boolean firstEdge = true;
    Point2D[] pointList = new Point2D[6];
    for(int i = 0; i < 6; i++) {
      int angleDegree = 60 * i - 30;
      double angleRadian = Math.PI / 180 * angleDegree;

      pointList[i] = new Point2D.Double(this.centerCoords.getX() + this.sideLength * Math.cos(angleRadian),
              this.centerCoords.getY() + this.sideLength * Math.sin(angleRadian));
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

  public void setColor(Color c) {
    this.color = c;
  }

  public Point.Double getCenter() {
    return this.centerCoords;

  }
  public AxialCoord getAxialCoords() {
    return this.axialCoords;
  }




}
