package model;

/**
 * A class to represent hexagonal cube coordinates in Reversi.
 */
public class Coordinate {
  public final int r, q, s;

  /**
   * Constructs the coordinate. Creates the s value from the r and q values.
   * @param r the x-value of the cube coordinate.
   * @param q the y-value of the cube coordinate.
   */
  public Coordinate(int r, int q) {
    this.r = r;
    this.q = q;
    this.s = -r-q;
  }
}
