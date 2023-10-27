package model;

/**
 * Represents the primary interface for playing a game of Reversi.
 */
public interface ReversiModel {
  /**
   * Returns the state of the game.
   * @return A GameStatus based on the current state of the game.
   */
  public boolean isGameOver();

  /**
   *
   * @return
   */
  public PlayerColor getWinner();

  /**
   *
   * @param color
   * @return
   */
  public int getPlayerScore(PlayerColor color);

  /**
   *
   * @param coordinate
   * @return
   */
  public boolean isTileEmpty(Coordinate coordinate);

  /**
   *
   * @return
   */
  public PlayerColor getTileContents();
}
