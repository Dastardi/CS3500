package provider.strategy;

import java.util.Optional;

import provider.model.HexPosn;
import provider.model.HexState;
import provider.model.ReadOnlyHexagonalReversi;

/**
 * An interface representing various strategies for a game of Hex Reversi.
 */
public interface Strategy {

  /**
   * Get the best move based on the current strategy or aggregate strategy.
   *
   * @param model The model
   * @param color The player to play
   * @return The best move
   */
  Optional<HexPosn> bestMove(ReadOnlyHexagonalReversi model, HexState color);
}
