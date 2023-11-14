package strategy;

import model.Coordinate;
import model.ReadOnlyReversiModel;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * a strategy that captures the most advantageous corner
 */
public class CornerStrategy implements ReversiStrategy {
  @Override
  public Optional<Coordinate> chooseMove(ReadOnlyReversiModel model) {
    int boardSize = model.getBoardSize();
    int halfBoard = boardSize / 2;
    int topScore = 0;
    List<Coordinate> bestMoves = new ArrayList<>();

    for (int r = 0; r < boardSize; r += halfBoard) {
      for (int q = 0; q < boardSize; q += halfBoard) {
        if (q != r) {
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
    if (bestMoves.isEmpty()) {
      return Optional.empty();
    } else return Optional.of(bestMoves.get(0));
  }
}