package view;

import java.awt.*;
import java.awt.geom.Path2D;

public class HexagonImage extends Path2D.Double{
  double sideLength;

  double centerX;
  double centerY;

  public HexagonImage(double sideLength, double centerX, double centerY) {
      this.sideLength = sideLength;
      this.centerX = centerX;
      this.centerY = centerY;
  }



}
