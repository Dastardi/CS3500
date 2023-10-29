package model;

import java.util.Objects;

/**
 * A class to represent hexagonal cube coordinates in Reversi.
 */
public class Coordinate {
  public final int q;
  public final int r;
  public final int s;

  /**
   * Constructs the coordinate. Creates the s value from the r and q values.
   * @param r the x-value of the cube coordinate.
   * @param q the y-value of the cube coordinate.
   */
  public Coordinate(int r, int q) {
    this.q = q;
    this.r = r;
    this.s = - q - r;
  }

  /**
   * Overrides Object's equals method. Determines if two Coordinates are equal
   * based on if their q and r values are both equal.
   * @param other the other object to compare
   * @return true if the Coordinates are equal and false otherwise
   */
  @Override
  public boolean equals(Object other) {
    Coordinate that = (Coordinate) other;
    return this.q == that.q && this.r == that.r;
  }

  /**
   * Overrides Object's hashcode method.
   * @return the hash code of this Coordinate as an int
   */
  @Override
  public int hashCode() {
    return Objects.hash(q, r);
  }
}
