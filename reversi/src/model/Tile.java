package model;

/**
 * Represents a Tile in a hexagonal board for a game of Reversi.
 */
public class Tile implements ReversiTile {
  private final Coordinate coordinate;
  private PlayerColor contents;

  public Tile(int q, int r) {
    this.coordinate = new Coordinate(q, r);
    this.contents = null;
  }

  @Override
  public void flip(PlayerColor color) {
    if (this.contents == null) {
      throw new IllegalStateException("Cannot flip a non-existent disc.");
    }
    //not necessary to do anything different if trying to flip to existing color
    this.contents = color;
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
