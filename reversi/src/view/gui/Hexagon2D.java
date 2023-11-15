package view.gui;

import java.awt.geom.Path2D;

/**
 * A class to represent a hexagon on a Reversi game board.
 */
public class Hexagon2D extends Path2D.Double {
  /**
   * Constructs the hexagon from the center using a passed radius value by calling the
   * createHexagon method, which uses angle calculations to place and draw the correct paths
   * from one vertex to another.
   */
  public Hexagon2D(double centerX, double centerY, double radius) {
    super();
    createHexagon(centerX, centerY, radius);
  }

  //creates a hexagon from the center by finding the point at 30 degrees right and down
  //one radius distance away and then finding points at increments of 60 degrees, drawing
  //a line between each point. Once complete it returns the full hexagon for later use
  //in the GUI panel.
  private void createHexagon(double centerX, double centerY, double radius) {
    // To make a pointy-topped hexagon, we start at 30 degrees, which is π/6 radians
    double angleStep = Math.PI / 3;
    double startAngle = Math.PI / 6;

    //calculates the coordinates of each vertex - for each angle:
    for (int i = 0; i < 6; i++) {
      // Add the starting angle to rotate the hexagon for a pointy top
      double angle = i * angleStep + startAngle;
      double x = centerX + radius * Math.cos(angle);
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