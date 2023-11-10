package view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class HexagonImage extends Path2D.Double{
  double sideLength;

  double centerX;
  double centerY;

  public HexagonImage(double sideLength, double centerX, double centerY) {
      this.sideLength = sideLength;
      this.centerX = centerX;
      this.centerY = centerY;
  }
  public void getPath() {
    boolean firstEdge = true;
    Point2D[] pointList = new Point2D[6];
    for(int i = 0; i < 6; i++) {
      int angleDegree = 60 * i - 30;
      double angleRadian = Math.PI / 180 * angleDegree;

      pointList[i] = new Point2D.Double(centerX + this.sideLength * Math.cos(angleRadian),
              centerY + this.sideLength * Math.sin(angleRadian));
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




  }
