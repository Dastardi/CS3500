package model;

/**
 * An interface to represent a tile in a Reversi board. A Tile can either be empty (meaning its
 * PlayerColor value is null) or have a disc in it (its PlayerColor value is non-null).
 */
public interface ReversiTile {
  /**
   * "Places a disc" in an empty tile - essentially, takes it from being null to having a set
   * PlayerColor value. A disc cannot be placed in a non-empty tile.
   * @param color the color of the disc to place.
   * @throws IllegalStateException if this tile already has a disc.
   */
  public void placeDisc(PlayerColor color) throws IllegalStateException;

  /**
   * Flips the tile from the current color to the other color.
   * Only works for a game with two players, but can be easily modified to
   * a set color method that takes in the color to set to instead of flipping.
   * @throws IllegalStateException if this tile does not have a disc placed on it.
   */
  public void flip() throws IllegalStateException;

  /**
   * Returns a boolean based on whether this tile is empty.
   * @return true iff this tile has no disc in it
   */
  public boolean isEmpty();


  /**
   * Returns the current color of this tile, or null if it is empty.
   * @return the current contents of this tile.
   */
  public PlayerColor getContents();

  /**
   * Returns the Coordinate object representing the position of this tile on the board.
   * @return this tile's coordinate
   */
  public Coordinate getCoordinate();
}
