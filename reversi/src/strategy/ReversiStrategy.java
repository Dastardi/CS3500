package strategy;

import model.Coordinate;
import model.ReadOnlyReversiModel;

import java.util.List;

/**
 * Represents a strategy for a game of Reversi, either human or AI.
 */
public interface ReversiStrategy {
  /**
   * Returns a list of potential moves
   * @param model
   * @return
   */
  Coordinate chooseMove(ReadOnlyReversiModel model);
}
