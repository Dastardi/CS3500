package provider.model;

/**
 * Represents the user interactions for a game of Hex Reversi.
 */
public interface HexagonalReversi extends ReadOnlyHexagonalReversi {

  /**
   * Starts the game.
   */
  void startGame();

  /**
   * Places a game piece of the given color at the specified coordinates (q, r, s) on the game
   * board.
   *
   * @param q     The q-coordinate of the position.
   * @param r     The r-coordinate of the position.
   * @param s     The s-coordinate of the position.
   * @param color The color of the game piece to be placed.
   * @throws IllegalStateException if it's not a player's turn or the position is already occupied.
   */
  void placePiece(int q, int r, int s, HexState color) throws IllegalStateException;

  /**
   * Passes the turn of the given color player.
   *
   * @param color The color of the player passing the turn.
   * @throws IllegalStateException if it's not a player's turn to pass or the game is already over.
   */
  void pass(HexState color) throws IllegalStateException;
}

