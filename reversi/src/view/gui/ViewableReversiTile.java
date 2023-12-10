package view.gui;

import controller.Pair;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * The interface for a viewable tile in a game of Reversi's visual representation.
 * Tiles implementing this interface are made to assist the panel in the display of the game,
 * therefore they have the ability to draw themselves, draw the discs on them,
 * change their color, and provide information about their location and state.
 */
public interface ViewableReversiTile {
  /**
   * Draws this tile using the rect and disc fields. Because it holds all the values it
   * needs within itself, drawing a tile is easy - it only needs a Graphics object.
   * @param g we have to pass in Graphics in order to draw things
   */
  void draw(Graphics g);

  /**
   * Sets a disc on this tile.
   * @param color the color of the disc to be drawn.
   */
  void setDisc(Color color);

  /**
   * Sets the color of this tile's rect to a given color.
   * Used to highlight tiles when clicked.
   * @param color the color to change to.
   */
  void setColor(Color color);

  /**
   * Returns the current color of the tile.
   * @return this tile's general color.
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
   * Determines whether a clicked point was within this tile.
   * @param point a Point2D object representing a spot on the panel.
   * @return true iff this tile's rect contains the given point.
   */
  boolean containsPoint(Point2D point);

  void setHint(Pair<Boolean, Integer> hint);
}