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
   * Returns the winner if the game is over. If the game is not over, throws an error.
   * @return the PlayerColor of the player with more discs on the board.
   * @throws IllegalStateException if the game is not over.
   */
  public PlayerColor getWinner() throws IllegalStateException;

  /**
   * Returns the score of the given player.
   * @param color the color of the player whose score the method is checking.
   * @return the number of tiles the player with the given color has on the board.
   */
  public int getPlayerScore(PlayerColor color);

  /**
   * Checks if the provided tile has a disc in it.
   * @param coordinate the coordinate of the tile to check.
   * @return true iff the tile's contents are null.
   */
  public boolean isTileEmpty(Coordinate coordinate);

  /**
   * Returns the contents of a given tile.
   * @return the color of the disc in the given tile.
   */
  public PlayerColor getTileContents(Coordinate coordinate);
}
