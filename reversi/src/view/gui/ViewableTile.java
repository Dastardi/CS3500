package view.gui;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Objects;

/**
 * The implementation of a view tile for a Reversi GUI, to be arranged in a panel. Makes life
 * easier in terms of placing discs, drawing the board, selecting tiles, and listening to inputs.
 */
public class ViewableTile implements ViewableReversiTile{
  //x and y represent the position of the tile in the window in which it will be drawn
  private final double x;
  private final double y;
  //q and r represent the logical axial coordinates of the Tile object in the model
  //that this ViewableTile corresponds to
  private final int q;
  private final int r;
  private final double radius;
  private Color color;
  private Color discColor;
  private final Hexagon2D hexagon;
  private Path2D.Double disc;

  /**
   * Constructs the ViewableTile from a number of inputs, described below.
   * @param color the initial color of the tile.
   * @param x the x coordinate of the center of the tile.
   * @param y the y coordinate of the center of the tile.
   * @param radius the radius of the tile, for drawing its hexagon and later its disc.
   * @param q the q value of the tile in the axial coordinate array.
   * @param r the r value of the tile in the axial coordinate array.
   */
  public ViewableTile(Color color, double x, double y, double radius, int q, int r) {
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Coordinates cannot be negative.");
    }
    this.color = Objects.requireNonNull(color);
    this.x = x;
    this.y = y;
    this.q = q;
    this.r = r;
    this.radius = radius;

    this.hexagon = new Hexagon2D(x, y, radius);
    this.disc = new Circle2D(x, y, radius/2);
  }

  @Override
  public void draw(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g.setColor(Color.BLACK);
    g2d.draw(hexagon);
    g.setColor(this.color);
    g2d.fill(hexagon);
    if (discColor != null) {
      g.setColor(discColor);
      g2d.fill(disc);
      System.out.println(g.getColor() + "-colored disc drawn!");
    }
  }

  @Override
  public void setDisc(Color color) {
    this.discColor = color;
  }

  @Override
  public void setColor(Color color) {
    this.color = Objects.requireNonNull(color);
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public int getQ() {
    return this.q;
  }

  @Override
  public int getR() {
    return this.r;
  }

  @Override
  public boolean containsPoint(Point2D point) {
    return this.hexagon.contains(point);
  }
}