package strategy;

import model.Coordinate;
import model.ReadOnlyReversiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A strategy that avoids placing discs next to the corner
 * so that their opponent can't get into the corner.
 */
public class AvoidCornerAdjacentStrategy implements ReversiStrategy {
  @Override
  public Coordinate chooseMove(ReadOnlyReversiModel model) {
    List<Coordinate> bestMoves = new ArrayList<>();
    return bestMoves.get(0);
  }
}
