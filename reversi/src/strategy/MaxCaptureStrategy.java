package strategy;

import model.Coordinate;
import model.ReadOnlyReversiModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
  public Optional<Coordinate> chooseMove(ReadOnlyReversiModel model) {
    int topScore = 0;
    List<Coordinate> bestMoves = new ArrayList<>();
    int boardSize = model.getBoardSize();

    for (int r = 0; r < boardSize; r++) {
      for (int q = 0; q < boardSize; q++) {
        if (r + q <= (boardSize / 2) * 3 && r + q >= boardSize / 2) {
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
    }
    //for loops are indexed from the top left, so the first coordinate in the list of tied
    //coordinates will automatically be the upperleftmost coordinate with that highest score
    if (bestMoves.isEmpty()) {
      return Optional.empty();
    } else return Optional.of(bestMoves.get(0));
  }

  //todo don't delete this yet but DO DELETE before turn in if not used by then
  protected Coordinate breakTies(List<Coordinate> moves) {
    if (moves.isEmpty()) {
      return null;
    }
    Coordinate upperLeftMostMove = moves.get(0);
    for (Coordinate move : moves) {
      if ((move.getR() < upperLeftMostMove.getR()
          && move.getQ() + move.getR() <= upperLeftMostMove.getQ() + upperLeftMostMove.getR())
          || (move.getR() == upperLeftMostMove.getR()
          && move.getQ() < upperLeftMostMove.getQ())) {
        upperLeftMostMove = move;
      }
    }
    return upperLeftMostMove;
  }
}