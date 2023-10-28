package model;

/**
 *
 */
public interface PlayerActions {
  /**
   * Places a reversi disk of the given color in the provided space.
   * @throws IllegalArgumentException if the coordinate does not exist
   * @throws IllegalStateException if the chosen tile already has a disc in it or if the move
   * is illegal.
   */
  public void move(PlayerColor color, Coordinate coordinate)
          throws IllegalArgumentException, IllegalStateException;

  /**
   * Passes the current player's turn without placing a disc.
   */
  public void pass();
}
