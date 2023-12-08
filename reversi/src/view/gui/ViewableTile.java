package view.gui;

import controller.Pair;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Objects;

/**
 * The implementation of a view tile for a Reversi GUI, to be arranged in a panel. Makes life
 * easier in terms of placing discs, drawing the board, selecting tiles, and listening to inputs.
 * A ViewableTile holds all of its important personal information -
 * where it should be drawn, where (in terms of coordinate) it is on the board,
 * how big it is, what color it is at any given time,
 * and the color of the disc on the tile, if one exists.
 * A ViewableTile holds two shape objects - a hexagon, which is the tile,
 * and a circle, which is the disc that is drawn if one is placed in the tile.
 */
public class ViewableTile implements ViewableReversiTile {
  //q and r represent the logical axial coordinates of the Tile object in the model
  //that this ViewableTile corresponds to
  private final int q;
  private final int r;
  //x and y represent the pixel location of the hexagon
  private final double x;
  private final double y;
  //represents the current color of this tile
  private Color color;
  //represents the color of the disc on this tile, if one exists
  private Color discColor;
  private final Hexagon2D hexagon;
  private final Path2D.Double disc;
  //represents whether this tile should currently be displaying a hint.
  private Pair<Boolean, Integer> hinting;

  /**
   * Constructs the ViewableTile from a number of inputs, described below. Radius, x, and y are
   * not fields of ViewableTile because they are only used to construct the hexagon and circle
   * fields and don't need to be accessed later.
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
    this.q = q;
    this.r = r;
    this.x = x;
    this.y = y;

    this.hexagon = new Hexagon2D(x, y, radius);
    this.disc = new Circle2D(x, y, radius / 1.5);

    this.hinting = new Pair<>(false, null);
  }

  @Override
  public void draw(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g.setColor(Color.BLACK);
    g2d.draw(hexagon);
    g.setColor(this.color);
    g2d.fill(hexagon);
    g.setColor(Color.BLACK);
    if (hinting.getFirst()) {
      String hintNumber = "" + hinting.getSecond();
      Font font = new Font("ComicSans", Font.PLAIN, 21);
      g2d.setFont(font);
      g2d.drawString(hintNumber, (int)this.x, (int)this.y);
    }
    if (discColor != null) {
      g.setColor(discColor);
      g2d.fill(disc);
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

  @Override
  public void setHint(Pair<Boolean, Integer> hint) {
    this.hinting = hint;
  }
}