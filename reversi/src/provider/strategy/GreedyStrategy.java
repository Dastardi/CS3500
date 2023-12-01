package provider.strategy;

import provider.model.HexPosn;
import provider.model.HexState;
import provider.model.HexagonalReversi;
import provider.model.ReadOnlyHexagonalReversi;

/**
 * Represents a strategy that values moves based on the number of pieces
 * they flip.
 */
public class GreedyStrategy extends AbstractStrategy implements Strategy {

  /**
   * A move that flips x pieces has a value of x / totalNumberOfHexes.
   *
   * @param model The model
   * @param color The player to play
   * @param move The move to be made
   * @return The value of the given move
   */
  @Override
  public double getMoveValue(ReadOnlyHexagonalReversi model, HexState color, HexPosn move) {
    int layers = model.getNumLayers();
    int curScore = model.getScore(color);

    HexagonalReversi experimentationModel = model.deepCopy();
    experimentationModel.placePiece(move.q, move.r, move.s, color);

    double totalNumberOfHexes = 1 + (6.0 * (layers * (layers - 1))) / 2;

    return (experimentationModel.getScore(color) - curScore) / totalNumberOfHexes;
  }
}
