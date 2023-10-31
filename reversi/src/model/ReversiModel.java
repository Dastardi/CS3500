package model;

/**
 * Represents the primary interface for playing a game of Reversi.
 */
public interface ReversiModel extends PlayerActions {

  /**
   * Gets the color of the player that is making the current turn.
   * @return the color of the current player
   */
  public PlayerColor getCurrentPlayer() throws IllegalArgumentException;

  /**
   * Returns the state of the game.
   * @return A GameStatus based on the current state of the game.
   */
  public boolean isGameOver() throws IllegalStateException;

  /**
   * Returns the winner if the game is over. If the game is not over, returns null instead.
   * @return the PlayerColor of the player with more discs on the board, or null.
   */
  public PlayerColor getWinner();

  /**
   * Returns the score of the given player.
   * @param color the color of the player whose score the method is checking.
   * @return the number of tiles the player with the given color has on the board.
   */
  public int getPlayerScore(PlayerColor color) throws IllegalStateException;

  /**
   * Returns the tile at the given coordinate.
   * @param coordinate the coordinate of the tile to check.
   * @throws IllegalArgumentException if the given coordinate is invalid.
   */
  public Tile getTileAt(Coordinate coordinate)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns the size of the board for this game of Reversi.
   * @return the board size field.
   */
  public int getBoardSize() throws IllegalStateException;

  /**
   * Returns the current status of the game
   * from a choice of Playing, Stalemate, or Game Won.
   * @return the status of the game
   */
  public GameStatus getGameStatus();
}
