package strategy;

import model.Coordinate;
import model.ReadOnlyReversiModel;

import java.util.List;

/**
 * A strategy for a computer player for a game of Reversi.
 * This strategy picks the move that captures the maximum possible number of the opponent's pieces.
 * If there are multiple moves that capture the same number of pieces,
 * break ties by choosing the move with the uppermost-leftmost coordinate.
 * This is not a good real-life strategy, but it makes testing substantially easier
 * to have a deterministic way of breaking ties.
 * If there are no valid moves, the strategy returns an empty list,
 * meaning either the next strategy should be used, if this strategy has been composed,
 * or the user should pass.
 */
public class SquareMaxCaptureStrategy implements ReversiStrategy {
  private final ReadOnlyReversiModel model;

  /**
   * Constructs the MaxCaptureStrategy using a model.
   * @param model the model of the game that this strategy is playing.
   */
  public SquareMaxCaptureStrategy(ReadOnlyReversiModel model) {
    this.model = model;
  }

  @Override
  public List<Coordinate> chooseMove(List<Coordinate> moveList) {
    int topScore = 0;
    int boardSize = model.getBoardSize();

    for (int r = 0; r < boardSize; r++) {
      for (int q = 0; q < boardSize; q++) {
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