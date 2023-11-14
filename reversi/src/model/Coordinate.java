package model;

import java.util.Objects;

/**
 * A class to represent axial coordinates in Reversi, using the system described at
 * <a href="https://www.redblobgames.com/grids/hexagons/">this link</a>. Q and R are values that
 * represent the index and row of the array. Within the array, the top left and bottom right
 * corners are left empty, and then the list is transposed diagonally in order to form
 * a hexagon shape.
 */
public class Coordinate {
  private final int q;
  private final int r;

  /**
   * Constructs the coordinate.
   * @param q the x-value of the axial coordinate.
   * @param r the y-value of the axial coordinate.
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

  @Override
  public String toString() {
    return this.q + ", " + this.r;
  }

  public int getQ() {
    return this.q;
  }

  public int getR() {
    return this.r;
  }
}