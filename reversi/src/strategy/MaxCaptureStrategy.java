package strategy;

import model.Coordinate;
import model.ReadOnlyReversiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A strategy for a computer player for a game of Reversi.
 * This strategy picks the move that captures the maximum possible number of the opponent's pieces.
 *
 * Implement the first of these strategies. If there are multiple moves that capture the same number of pieces,
 * break ties by choosing the move with the uppermost-leftmost coordinate. (Note: this is not a good real-life strategy!
 * But it will make your testing substantially easier to have a deterministic way of breaking ties.)
 * If there are no valid moves, your player should pass.
 * todo make a note for all strategies that if they return null, there are no valid moves, so pass
 *
 */
public class MaxCaptureStrategy implements ReversiStrategy {
  @Override
  public Coordinate chooseMove(ReadOnlyReversiModel model) {
    int topScore = 0;
    List<Coordinate> bestMoves = new ArrayList<>();
    int boardSize = model.getBoardSize();

    for (int r = 0; r < boardSize; r++) {
      for (int q = 0; q < boardSize; q++) {
        Coordinate coordinate = new Coordinate(q, r);
        if (model.isMoveLegal(coordinate)) {
          int moveScore = model.getMoveScore(coordinate);
          if (moveScore > topScore) {
            topScore = moveScore;
            bestMoves.clear();
            bestMoves.add(coordinate);
          } else if (moveScore == topScore) {
            bestMoves.add(coordinate);
          }
        }
      }
    }
    return breakTies(bestMoves);
  }

  private Coordinate breakTies(List<Coordinate> moves) {
    if (moves.isEmpty()) {
      return null;
    }
    Coordinate upperLeftMostMove = moves.get(0);
    for (Coordinate move : moves) {
      if ((move.r < upperLeftMostMove.r && move.q + move.r <= upperLeftMostMove.q + upperLeftMostMove.r)
          || (move.r == upperLeftMostMove.r && move.q < upperLeftMostMove.q)) {
        upperLeftMostMove = move;
      }
    }
    return upperLeftMostMove;
  }
}