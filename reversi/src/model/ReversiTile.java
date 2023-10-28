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
   * Flips the tile from the current color to the given color.
   * @param color the color to switch to.
   * @throws IllegalStateException if this tile does not have a disc placed on it.
   */
  public void flip(PlayerColor color) throws IllegalStateException;

  /**
   * Returns a boolean based on whether or not this tile is empty
   * @return true iff this tile has no disc in it
   */
  public boolean isEmpty();


    /**
     * Returns the current color of this tile.
     * @return the current contents of this disc.
     * @throws IllegalStateException if this tile does not have a disc placed in it.
     */
  public PlayerColor getColor() throws IllegalStateException;

  /**
   *
   * @return
   */
  public Coordinate getCoordinate();
}
