package model;

/**
 * Represents a Tile in a square board for a game of Reversi.
 * A Tile is a square shape that makes up the larger square that is the board.
 * A Tile is the space where a disc, which represents a "piece" in the game, can be played.
 * Tile objects contain information about what, if anything, is placed on them.
 * They also know their own position within the board, represented by a simple coordinate system.
 */
public class Tile implements ReversiTile {
  private final Coordinate coordinate;
  private PlayerColor contents;

  /**
   * Constructs a Tile object that represents a space on a square board.
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
