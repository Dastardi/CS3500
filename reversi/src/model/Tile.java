package model;

/**
 * Represents a Tile in a hexagonal board for a game of Reversi.
 */
public class Tile implements ReversiTile {
  private final Coordinate coordinate;
  private PlayerColor contents;

  /**
   * Constructs a Tile object that represents a hexagonal space on a hexagonal board.
   * @param q the q-coordinate of this Tile
   * @param r the r-coordinate of this Tile
   * @throws IllegalArgumentException if either of the given values are negative
   */
  public Tile(int q, int r) {
    if (q < 0 || r < 0) {
      throw new IllegalArgumentException("A tile in a game of Reversi cannot"
              + "have a coordinate with values less than 0.");
    }
    this.coordinate = new Coordinate(q, r);
    this.contents = null;
  }

  @Override
  public void flip() {
    if (this.contents == null) {
      throw new IllegalStateException("Cannot flip a nonexistent disc.");
    }
    this.contents = contents == PlayerColor.BLACK ? PlayerColor.WHITE : PlayerColor.BLACK;
  }

  @Override
  public PlayerColor getContents() {
    return this.contents;
  }

  @Override
  public boolean isEmpty() {
    return this.contents == null;
  }

  @Override
  public void placeDisc(PlayerColor color) {
    if (contents != null) {
      throw new IllegalStateException("Cannot place a disc on a nonempty tile.");
    }
    this.contents = color;
  }

  @Override
  public Coordinate getCoordinate() {
    return this.coordinate;
  }
}
