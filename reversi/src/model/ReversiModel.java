package model;

import provider.model.HexagonalReversi;

/**
 * Represents the actions that a player can make in Reversi.
 * A player's only actions are making a move and passing their turn.
 * These methods are implemented in a model for the game,
 * as extensions of basic observational functionality held in the main model.
 * This interface provides the functionality for interacting with the model.
 */
public interface ReversiModel extends ReadOnlyReversiModel, HexagonalReversi {
  /**
   * Makes a move in a game of Reversi by placing a disc of the given color in the provided space.
   * @throws IllegalArgumentException if the given coordinate is not on the game board.
   * @throws IllegalStateException if the chosen tile already has a disc in it,
   *     if the move is illegal, or if the game is over.
   */
  void move(Coordinate coordinate) throws IllegalArgumentException, IllegalStateException;

  /**
   * Passes the current player's turn without placing a disc, skipping to the next player's turn.
   * @throws IllegalStateException if the game is over.
   */
  void pass() throws IllegalStateException;

  /**
   * Informs the listeners of this model that the game has begun.
   */
  void startGame();
}