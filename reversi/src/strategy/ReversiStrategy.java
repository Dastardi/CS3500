package strategy;

import model.Coordinate;

import java.util.List;

/**
 * Represents a strategy for a game of Reversi, either human or AI.
 */
public interface ReversiStrategy {
  /**
   * Returns a list of potential move locations.
   * @param moveList the list of moves which this will operate on.
   * @return a list of coordinates that represent the possible moves this strategy suggests.
   */
  List<Coordinate> chooseMove(List<Coordinate> moveList);
}