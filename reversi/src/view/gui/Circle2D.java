package view.gui;

import java.awt.geom.Path2D;

/**
 * A simple circle drawing which is created from the center rather than the top left corner,
 * as it is for Ellipse2D.Double. This makes it easier to draw discs inside of tiles on a
 * Reversi board.
 */
public class Circle2D extends Path2D.Double{
  /**
   * Constructs the circle using the crateCircle method from the center.
   */
  public Circle2D(double centerX, double centerY, double radius) {
      super();
      createCircle(centerX, centerY, radius);
  }

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
