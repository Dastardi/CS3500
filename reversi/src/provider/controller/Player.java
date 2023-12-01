package provider.controller;

import provider.model.HexState;

/**
 * Represents a player of a HexagonalReversiModel game.
 */
public interface Player {

  /**
   * Allows the player to place a piece on a ReversiHex cell.
   */
  void placePiece();

  /**
   * Allows the player to pass on their turn.
   */
  void passTurn();

  /**
   * Returns the designated piece color for this player.
   */
  HexState getColor();

  /**
   * Add a listener to this player's actions.
   *
   * @param listener A listener.
   */
  void addPlayerActionListener(PlayerActionListener listener);
}
