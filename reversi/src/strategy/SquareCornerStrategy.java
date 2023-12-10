package strategy;

import model.Coordinate;
import model.ReadOnlyReversiModel;

import java.util.List;
import java.util.Objects;

/**
 * A strategy that captures the most advantageous corner.
 */
public class SquareCornerStrategy implements ReversiStrategy {
  private ReadOnlyReversiModel model;

  /**
   * Constructs the CornerStrategy using the given model.
   * @param model the model of the game that this strategy is playing.
   */
  public SquareCornerStrategy(ReadOnlyReversiModel model) {
    this.model = Objects.requireNonNull(model);
  }

  @Override
  public List<Coordinate> chooseMove(List<Coordinate> moveList) {
    int boardSize = this.model.getBoardSize();
    int topScore = 0;

    for (int r = 0; r < boardSize; r += boardSize - 1) {
      for (int q = 0; q < boardSize; q += boardSize - 1) {
        Coordinate coordinate = new Coordinate(q, r);
        if (model.isMoveLegal(coordinate)) {
          int moveScore = model.getMoveScore(coordinate);
          if (moveScore > topScore) {
            topScore = moveScore;
            moveList.clear();
            moveList.add(coordinate);
          } else if (moveScore == topScore) {
            moveList.add(coordinate);
          }
        }
      }
    }
    return moveList;
  }
}