package model;

/**
 * Represents the primary interface for playing a game of Reversi.
 * Holds all methods necessary for internal gameplay.
 */
public interface ReadOnlyReversiModel extends TurnTaker {
  /**
   * Gets the color of the player that is making the current turn.
   * @return the color of the current player
   * @throws IllegalStateException if the game is over
   */
  PlayerColor getCurrentPlayer() throws IllegalStateException;

  /**
   * Returns a boolean based on the state of the game.
   * The game ends when two players pass in a row, or when the board is full.
   * @return true iff the game has ended
   */
  boolean isGameOver();

  /**
   * Returns the color of the player that has the highest score when the method is called.
   * Highest score refers to highest number of discs of that color on the board,
   * as given by {@link ReadOnlyReversiModel#getPlayerScore}.
   * Does not depend on the game being over and therefore can be called at any point.
   * Does not return the overall winner - returns the winner at the moment it is called.
   * If the players have the exact same score (same number of tiles on the board), returns null.
   * @return the PlayerColor of the player with more discs on the board, or null
   */
  PlayerColor getCurrentWinner();

  /**
   * Returns the score of the given player where the score represents
   * the number of discs with the given player's tile that are currently on the board.
   * @param color the color of the player whose score the method is checking
   * @return the number of tiles the player with the given color has on the board
   */
  int getPlayerScore(PlayerColor color);

  /**
   * Returns the Tile at the given coordinate.
   * @param coordinate the coordinate of the tile to check
   * @throws IllegalArgumentException if the given coordinate is invalid
   */
  Tile getTileAt(Coordinate coordinate) throws IllegalArgumentException;

  /**
   * Returns the size of the board for this game of Reversi.
   * @return the board size field
   */
  int getBoardSize();

  /**
   * Determines if a move at a certain spot is legal.
   * @param coordinate the coordinate to check.
   * @return true iff the move is legal.
   */
  boolean isMoveLegal(Coordinate coordinate);

  /**
   * Gets the score of a potential move. Is able to determine the number of tiles that would
   * be flipped by a move, and returns that value.
   * @param coordinate the coordinate to check the score for.
   * @return an integer value representing the number of tiles that would be flipped by that move.
   */
  int getMoveScore(Coordinate coordinate);

  /**
   * Returns a copy of the board stored in the model. Uses the private helper copyBoard()
   * to construct a deep copy of the model's board, which it then returns.
   * This copy, like the field, is a 2D array of Tile objects.
   * @return a copy of the board
   */
  Tile[][] getBoard();

  /**
   * Determines if a player has any legal moves. This is important in reversi because if a player
   * is unable to move, the game should force them to pass.
   * Note: Not currently accessed but could be helpful for future use, so we're keeping it for now.
   * @return true iff the player has at least one legal move.
   */
  boolean playerHasLegalMoves();
}