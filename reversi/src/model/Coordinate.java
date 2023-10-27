package model;

/**
 * A class to represent hexagonal cube coordinates in Reversi.
 */
public class Coordinate {
  public final int x, y, z;

  /**
   * Constructs the coordinate.
   * @param x the x-value of the cube coordinate.
   * @param y the y-value of the cube coordinate.
   * @param z the z-value of the cube coordinate.
   */
  public Coordinate(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
}
