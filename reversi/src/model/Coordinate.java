package model;

import java.util.Objects;

/**
 * A class to represent hexagonal cube coordinates in Reversi.
 */
public class Coordinate {
  public final int q;
  public final int r;
  public final int s; //TODO delete field if never used

  /**
   * Constructs the coordinate. Creates the s value from the r and q values.
   * @param q the x-value of the cube coordinate.
   * @param r the y-value of the cube coordinate.
   */
  public Coordinate(int q, int r) {
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
    if (other == this) {
      return true;
    }
    if (!(other instanceof Coordinate that)) {
      return false;
    }
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
