package model;

/**
 * Represents the actions that a player can take in Reversi.
 * These methods are implemented in a model for the game.
 */
public interface PlayerActions {
  /**
   * Makes a move in a game of Reversi by placing a disc of the given color in the provided space.
   * @throws IllegalArgumentException if the given coordinate is not on the game board.
   * @throws IllegalStateException if the chosen tile already has a disc in it,
   *     if the move is illegal, or if the game is over.
   */
  public void move(Coordinate coordinate) throws IllegalArgumentException, IllegalStateException;

  /**
   * Passes the current player's turn without placing a disc, skipping to the next player's turn.
   * @throws IllegalStateException if the game is over.
   */
  public void pass() throws IllegalStateException;
}