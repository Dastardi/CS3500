package model;

/**
 * An interface to represent a tile in a Reversi board. A Tile can either be empty (meaning its
 * PlayerColor value is null) or have a disc on it (its PlayerColor value is non-null).
 */
public interface ReversiTile {
  /**
   * "Places a disc" in an empty tile - essentially, sets its contents from null to a set
   * PlayerColor value. A disc cannot be placed on a non-empty tile
   * (a tile with a disc already on it).
   * @param color the color, from PlayerColor, of the disc to place.
   * @throws IllegalStateException if this tile already has a disc.
   */
  void placeDisc(PlayerColor color) throws IllegalStateException;

  /**
   * Flips the tile from the current color to the other color.
   * Our game model is currently designed for only two players, but the whole model,
   * including this method, can be easily modified to include 3 or more players.
   * In this case, this method would change to a set color method that
   * takes in the color to set to instead of flipping.
   * @throws IllegalStateException if this tile does not have a disc placed on it.
   */
  void flip() throws IllegalStateException;

  /**
   * Returns a boolean indicating whether this tile is empty, meaning its contents are null.
   * @return true iff this tile has no disc in it
   */
  boolean isEmpty();


  /**
   * Returns the current color of this tile, or null if it is empty.
   * @return the current contents of this tile.
   */
  PlayerColor getContents();

  /**
   * Returns the Coordinate object representing the position of this tile on the board.
   * The Coordinate object contains a q and r value,
   * the two components of the axial coordinate system the board uses.
   * @return this tile's coordinate
   */
  Coordinate getCoordinate();
}