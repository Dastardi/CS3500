package provider.model;

/**
 * A value object representing coordinates in the board.
 */
public class HexPosn {

  public final int q;
  public final int r;
  public final int s;

  /**
   * Constructor for 3D coordinates.
   *
   * @param q The q coordinate
   * @param r The r coordinate
   * @param s The s coordinate
   */
  public HexPosn(int q, int r, int s) {
    this.q = q;
    this.r = r;
    this.s = s;
  }

  /**
   * The absolute distance between two 3D positions.
   *
   * @param other The other 3D position
   * @return The absolute distance between this position and that position.
   */
  public int distanceTo(HexPosn other) {
    return Math.abs(this.q - other.q)
            + Math.abs(this.r - other.r)
            + Math.abs(this.s - other.s);
  }


  /**
   * Returns a HexPosn representing the distance, as a vector, from this hex position
   * to that hex position.
   *
   * @param that - The other hex position.
   * @return The distance from this hex position to that hex position.
   */
  public HexPosn vectorDifference(HexPosn that) {
    return new HexPosn(that.q - q, that.r - r, that.s - s);
  }


  /**
   * Check if the coordinates are valid (they must sum to 0).
   *
   * @return Whether the coordinates are valid.
   */
  public boolean validHexPosn() {
    return q + r + s == 0;
  }

  /**
   * Is this HexPosn equal to that object.
   *
   * @param other The other object to be compared.
   * @return Whether the two are equal
   */
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof HexPosn)) {
      return false;
    }

    HexPosn that = (HexPosn) other;

    return
            this.q == that.q &&
            this.r == that.r &&
            this.s == that.s;
  }

  /**
   * Get the hash code of this hex coordinates. Uses cantor's pairing function
   * to ensure that all hash codes are unique.
   *
   * @return The hash code of this hex coordinates.
   */
  @Override
  public int hashCode() {

    // we need a bijective function from N x N x N -> N to achieve unique hashing
    // use the cantor pairing function
    return ((q + r + s) * (q + r + s + 1) + q + r + s);
  }

}