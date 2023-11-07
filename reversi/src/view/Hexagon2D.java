package view;

import java.awt.geom.Path2D;

public class Hexagon2D extends Path2D.Double {
  public Hexagon2D(double centerX, double centerY, double radius) {
    super();
    createHexagon(centerX, centerY, radius);
  }

  private void createHexagon(double centerX, double centerY, double radius) {
    //calculates the angle needed for each side - a circle has 2 * math.PI degrees,
    //so dividing math.PI by three is dividing 360 by 6, resulting in 60 degrees.
    double angleStep = Math.PI / 3;

    //calculates the coordinates of each vertex - for each angle:
    for (int i = 0; i < 6; i++) {
      //the angle is i times 60, giving us 60, 120, 180, 240, 300, or 360
      double angle = i * angleStep;
      //the angle x coordinate is radius far away from the center at the angle determined above
      double x = centerX + radius * Math.cos(angle);
      //the angle y coordinate is radius far away from the center at the angle determined above
      double y = centerY + radius * Math.sin(angle);

      //moves the pen to the first angle at 0 degrees (to the right), then draws six lines around
      // a hexagonal shape.
      if (i == 0) {
        moveTo(x, y);
      } else {
        lineTo(x, y);
      }
    }
    //close the path, as it is now a complete hexagon.
    closePath();
  }
}