package model;

public class Tile implements ReversiTile {
  Coordinate coordinate;
  PlayerColor contents;

  public Tile(int x, int y) {
    this.coordinate = new Coordinate(x, y, -x-y);
    this.contents = null;
  }

  @Override
  public void flip(PlayerColor color) {
    if (this.contents == null) {
      throw new IllegalStateException("Cannot flip a non-existent disc.");
    }
    this.contents = color;
  }

  @Override
  public PlayerColor getColor() {
    if (this.contents == null) {
      throw new IllegalStateException("Cannot get the color of a disc that does not exist.");
    }
    return this.contents;
  }

  @Override
  public void placeDisc(PlayerColor color) {
    if (contents != null) {
      throw new IllegalStateException("Cannot place a disc on a nonempty tile.");
    }
    this.contents = color;
  }
}
