package provider.strategy;

import java.util.HashSet;
import java.util.Set;

import provider.model.HexPosn;
import provider.model.HexState;
import provider.model.ReadOnlyHexagonalReversi;

/**
 * Represents a strategy that prioritizes corners.
 */
public class PrioritizeCornersStrategy extends AbstractStrategy implements Strategy {

  /**
   * The value of a move that fills a corner hex is 1. The value of a move that fills a non-corner
   * hex is 0.
   *
   * @param model The model
   * @param color The player to play
   * @param move The move to be made
   * @return The absolute value of the given move.
   */
  @Override
  public double getMoveValue(ReadOnlyHexagonalReversi model, HexState color, HexPosn move) {

    int layers = model.getNumLayers();

    Set<Integer> coordinates = new HashSet<>();
    coordinates.add(Math.abs(move.q));
    coordinates.add(Math.abs(move.r));
    coordinates.add(Math.abs(move.s));

    if (coordinates.contains(layers - 1) && coordinates.contains(0)) {
      return 1;
    }

    else {
      return 0;
    }
  }
}
