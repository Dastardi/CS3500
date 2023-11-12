package view.gui;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * The interface for a viewable tile in a game of Reversi's visual representation.
 * A ViewableTile holds in its fields a number of important pieces of information -
 * x and y represent its center coordinates, q and r represent its position in the axial
 * coordinate array, radius represents the hexagon size, color represents the current color
 * of the hexagon, and discColor represents the color of the disc currently in this tile (if
 * there is one). The hexagon and disc fields inform the tile of what to draw when it is
 * asked to display itself.
 */
public interface ViewableReversiTile {
  /**
   * Draws this tile using the hexagon and disc fields. Because it holds all the values it
   * needs within itself, drawing a tile is easy! We just have to pass in a Graphics object
   * and we're off to the races.
   * @param g we have to pass in Graphics in order to draw things
   */
  void draw(Graphics g);

  /**
   * sets the color of this tile's reversi disc.
   * @param color the color of the disc to be drawn.
   */
  void setDisc(Color color);

  /**
   * Sets the color of this tile's hexagon to a given color. Used to highlight tiles when clicked.
   * @param color the color to change to.
   */
  void setColor(Color color);

  /**
   * Returns the current color of the hexagon.
   * @return this tile's hexagon color.
   */
  Color getColor();

  /**
   * Returns the private q value of this tile.
   * @return the q value of this tile.
   */
  int getQ();

  /**
   * Returns the private r value of this tile.
   * @return the r value of this tile.
   */
  int getR();

  /**
   * A boolean to determine whether a clicked point was within this tile.
   * @param point a Point2D object representing a spot on the panel.
   * @return true iff this tile's hexagon contains the given point.
   */
  boolean containsPoint(Point2D point);
}
