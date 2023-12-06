package provider.strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import provider.model.HexPosn;
import provider.model.HexState;
import provider.model.ReadOnlyHexagonalReversi;

/**
 * Represents a template for an abstract strategy. All strategies share the same bestMove method,
 * which is determined by comparing the absolute "value" of all legal moves. Different strategies
 * determine how to evaluate different moves.
 */
public abstract class AbstractStrategy implements Strategy {

  /**
   * Get the best move based on the current strategy or aggregate strategy. If a tie is encountered,
   * defers first to the move that is closest to the top right, and then the move that has the
   * smallest s coordinate.
   *
   * @param model The model
   * @param color The player to play
   * @return The best move
   */
  @Override
  public Optional<HexPosn> bestMove(ReadOnlyHexagonalReversi model, HexState color) {

    List<HexPosn> bestMoves = getAllLegalMoves(model, color);

    if (bestMoves.isEmpty()) {
      System.out.println("No moves found.");
      return Optional.empty();
    }

    Comparator<HexPosn> compareByValue = (first, second) ->
            Double.compare(
                    this.getMoveValue(model, color, first),
                    this.getMoveValue(model, color, second)
            );

    compareByValue = compareByValue.thenComparing(determineMostUpperLeft(model));
    compareByValue = compareByValue.thenComparing(determineSmallestSCoordinate(model));

    bestMoves.sort(compareByValue);

    return Optional.of(bestMoves.get(bestMoves.size() - 1));
  }

  /**
   * Get the value of a given move based on the current strategy. Move values are between 0 and 1
   *
   * @param model The model
   * @param color The player to play
   * @param move The move to be made
   * @return The value of a given move
   */
  abstract double getMoveValue(ReadOnlyHexagonalReversi model, HexState color, HexPosn move);

  /**
   * Return a list of moves that are currently legal.
   *
   * @param model The model
   * @param color The player to play
   * @return All currently legal moves
   */
  private List<HexPosn> getAllLegalMoves(ReadOnlyHexagonalReversi model, HexState color) {

    if (model == null) {
      throw new IllegalArgumentException("Invalid Model");
    }

    if (color == HexState.EMPTY) {
      throw new IllegalArgumentException("Empty is not a color");
    }

    int layers = model.getNumLayers();

    List<HexPosn> allLegalMoves = new ArrayList<>();

    for (int q = -layers + 1; q < layers; q++) {
      for (int r = -layers + 1; r < layers; r++) {
        for (int s = -layers + 1; s < layers; s++) {
          if (q + r + s == 0) {
            if (model.validateMove(q, r, s, color)) {
              allLegalMoves.add(new HexPosn(q, r, s));
            }
          }
        }
      }
    }
    return allLegalMoves;
  }

  /**
   * Determine the closeness of a hex to the upper left.
   *
   * @param model The model
   * @return A comparator that determines which hex is closer to the upper left
   */
  private Comparator<HexPosn> determineMostUpperLeft(ReadOnlyHexagonalReversi model) {
    return (first, second) -> {
      int layers = model.getNumLayers();
      HexPosn upperLeft = new HexPosn(0, -layers + 1, layers - 1);

      int firstUpperLeftDistance = first.distanceTo(upperLeft);
      int secondUpperLeftDistance = second.distanceTo(upperLeft);

      return Integer.compare(secondUpperLeftDistance, firstUpperLeftDistance);
    };
  }

  /**
   * Determine the smallness of a hex's s coordinate.
   *
   * @param model The model
   * @return A comparator that determines which hex has a smaller s coordinate
   */
  private Comparator<HexPosn> determineSmallestSCoordinate(ReadOnlyHexagonalReversi model) {
    return (first, second) -> Integer.compare(second.s, first.s);
  }
}
