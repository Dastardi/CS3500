package model;

import java.util.Objects;

/**
 * A class to represent coordinates in Reversi. Q and R are values that
 * represent the index and row of the array.
 */
public class Coordinate {
  private final int q;
  private final int r;

  /**
   * Constructs the coordinate.
   * @param q the x-value of the coordinate.
   * @param r the y-value of the coordinate.
   * @throws IllegalArgumentException if either of the given values are negative
   */
  public Coordinate(int q, int r) {
    if (q < 0 || r < 0) {
      throw new IllegalArgumentException("Coordinate values in a game of Reversi"
              + "must be at least 0.");
    }
    this.q = q;
    this.r = r;
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
    if (!(other instanceof Coordinate)) {
      return false;
    }
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

  //a Coordinate's toString is represented by its q and r values, separated by a comma

  /**
   * Overrides Object's toString method.
   * A Coordinate's toString is represented by its q and r values, separated by a comma.
   * @return the string representation of this Coordinate
   */
  @Override
  public String toString() {
    return this.q + ", " + this.r;
  }

  /**
   * Returns the q value of this Coordinate.
   * @return this Coordinate's q value
   */
  public int getQ() {
    return this.q;
  }

  /**
   * Returns the r value of this Coordinate.
   * @return this Coordinate's r value
   */
  public int getR() {
    return this.r;
  }
}