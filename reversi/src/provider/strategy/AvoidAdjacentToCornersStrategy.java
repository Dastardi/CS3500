package provider.strategy;

import java.util.HashSet;
import java.util.Set;

import provider.model.HexPosn;
import provider.model.HexState;
import provider.model.ReadOnlyHexagonalReversi;

/**
 * Represents a strategy that prioritizes avoiding hexes adjacent to corners.
 */
public class AvoidAdjacentToCornersStrategy extends AbstractStrategy implements Strategy {

  /**
   * The move value of a hex that is adjacent to a corner is 0. The move value of a hex
   * that is not adjacent to a corner is 1.
   *
   * @param model The model
   * @param color The player to play
   * @param move The move to be made
   * @return The value of the given move
   */
  @Override
  public double getMoveValue(ReadOnlyHexagonalReversi model, HexState color, HexPosn move) {

    int layers = model.getNumLayers();

    Set<Integer> coordinates = new HashSet<>();
    coordinates.add(Math.abs(move.q));
    coordinates.add(Math.abs(move.r));
    coordinates.add(Math.abs(move.s));

    if ((coordinates.contains(layers - 2) && coordinates.contains(0)) ||
            (coordinates.contains(layers - 1) && coordinates.contains(layers - 2))) {
      return 0;
    }

    else {
      return 1;
    }
  }
}
