package strategy;

import model.Coordinate;
import model.ReadOnlyReversiModel;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * a strategy that aims to leave the opponent with the worst options possible
 */
public class MinimaxStrategy implements ReversiStrategy {
  @Override
  public Optional<Coordinate> chooseMove(ReadOnlyReversiModel model) {
    return null;
  }
}
