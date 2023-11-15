package view.gui;

import java.awt.geom.Path2D;

/**
 * A simple circle drawing which is created from the center rather than the top left corner,
 * as it is for Ellipse2D.Double. This makes it easier to draw discs inside of tiles on a
 * Reversi board.
 */
public class Circle2D extends Path2D.Double {
  /**
   * Constructs the circle using the createCircle method from the center.
   */
  public Circle2D(double centerX, double centerY, double radius) {
    super();
    createCircle(centerX, centerY, radius);
  }

  //constructs the circle from the center by getting the center position and finding a
  //position of radius distance away. It then goes around 360 degrees, getting a point at
  //each degree and drawing a line between the two points. Once completed, it returns
  //a complete circle for use down the line as a disc.
  private void createCircle(double centerX, double centerY, double radius) {
    for (int i = 0; i <= 360; i++) {
      double angle = Math.toRadians(i);
      double x = centerX + radius * Math.cos(angle);
      double y = centerY + radius * Math.sin(angle);

      if (i == 0) {
        moveTo(x, y);
      } else {
        lineTo(x, y);
      }
    }
    closePath();
  }
}