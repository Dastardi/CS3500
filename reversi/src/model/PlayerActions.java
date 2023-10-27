package model;

/**
 *
 */
public interface PlayerActions {
  /**
   *
   * @throws IllegalArgumentException
   * @throws IllegalStateException
   */
  public void move(PlayerColor color, Coordinate coordinate)
          throws IllegalArgumentException, IllegalStateException;

  /**
   *
   */
  public void pass();
}
