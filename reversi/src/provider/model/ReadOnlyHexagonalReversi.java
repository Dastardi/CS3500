package provider.model;

import java.util.List;

/**
 * Represents the user interactions for a game of Hex Reversi.
 */
public interface ReadOnlyHexagonalReversi {

  /**
   * Checks if a game piece of the given color can be placed at the specified coordinates (q, r, s)
   * on the game board.
   *
   * @param q     The q-coordinate of the position.
   * @param r     The r-coordinate of the position.
   * @param s     The s-coordinate of the position.
   * @param color The color of the game piece to be placed.
   */
  boolean validateMove(int q, int r, int s, HexState color);


  /**
   * Checks if the game is over.
   *
   * @return true if the game is over, false otherwise.
   */
  boolean isGameOver();

  /**
   * Gets the color of the player whose turn is next.
   *
   * @return The color of the player with the next turn.
   */
  HexState getNextTurn();

  /**
   * Retrieves the state of the game piece at the specified coordinates (q, r, s).
   *
   * @param q The q-coordinate of the position.
   * @param r The r-coordinate of the position.
   * @param s The s-coordinate of the position.
   * @return The state of the game piece at the given position.
   */
  HexState getHexState(int q, int r, int s);

  /**
   * Gets the current score of the given player.
   *
   * @return The score of the white player.
   */
  int getScore(HexState color);

  /**
   * Gets the number of layers on the game board.
   *
   * @return The number of layers in the game board.
   */
  int getNumLayers();

  /**
   * Returns true if there exists a playable move for the given hex state color, false otherwise.
   *
   * @param color - The player to play.
   * @return Whether a playable move exists for the given color.
   */
  boolean playableMoveExists(HexState color);


  List<HexPosn> collectAllPossibleMoves(HexState color);


  /**
   * Create a deep copy of this HexagonalReversiModel.
   *
   * @return a deep copy of this ReadOnlyHexagonalReversi as a mutable HexagonalReversi
   */
  HexagonalReversi deepCopy();

  void addModelEventListener(ModelEventListener listener);

}

