package model;

/**
 * Represents the primary interface for playing a game of Reversi.
 * Holds all methods necessary for internal gameplay.
 */
public interface ReversiModel extends PlayerActions {

  /**
   * Gets the color of the player that is making the current turn.
   * @return the color of the current player
   * @throws IllegalStateException if the game is over.
   */
  public PlayerColor getCurrentPlayer() throws IllegalStateException;

  /**
   * Returns a boolean based on the state of the game.
   * The game ends when two players pass in a row, or when the board is full.
   * @return true iff the game has ended.
   */
  public boolean isGameOver();

  /**
   * Returns the color of the player that has the highest score when the method is called.
   * Highest score refers to highest number of discs of that color on the board,
   * as given by {@link ReversiModel#getPlayerScore}.
   * Does not depend on the game being over and therefore can be called at any point.
   * Does not return the overall winner - returns the winner at the moment it is called.
   * If the players have the exact same score (same number of tiles on the board), returns null.
   * @return the PlayerColor of the player with more discs on the board, or null.
   */
  public PlayerColor getCurrentWinner();

  /**
   * Returns the score of the given player.
   * @param color the color of the player whose score the method is checking.
   * @return the number of tiles the player with the given color has on the board.
   */
  public int getPlayerScore(PlayerColor color);

  /**
   * Returns the tile at the given coordinate.
   * @param coordinate the coordinate of the tile to check.
   * @throws IllegalArgumentException if the given coordinate is invalid.
   */
  public Tile getTileAt(Coordinate coordinate) throws IllegalArgumentException;

  /**
   * Returns the size of the board for this game of Reversi.
   * @return the board size field.
   */
  public int getBoardSize();
}
