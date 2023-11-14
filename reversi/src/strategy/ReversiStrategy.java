package strategy;

import model.Coordinate;
import model.ReadOnlyReversiModel;

import java.util.List;
import java.util.Optional;

/**
 * Represents a strategy for a game of Reversi, either human or AI.
 */
public interface ReversiStrategy {
  /**
   * Returns a list of potential moves
   * @param model
   * @return
   */
  Optional<Coordinate> chooseMove(ReadOnlyReversiModel model);
}
