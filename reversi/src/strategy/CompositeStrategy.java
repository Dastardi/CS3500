package strategy;

import model.Coordinate;
import model.ReadOnlyReversiModel;

import java.util.List;
import java.util.Objects;

/**
 * Represents a composite strategy allowing for nesting of various different strategies.
 * When asked to return a move, it prioritizes the first strategy, then tries the rest if the
 * first has no viable options.
 */
public class CompositeStrategy implements ReversiStrategy {
  private final ReversiStrategy first;
  private final ReversiStrategy rest;

  /**
   * Constructs a composite strategy from two ReversiStrategies.
   * @param first the first strategy, which is looked to first to choose a list of moves.
   * @param rest the second strategy, which is a secondary option.
   */
  public CompositeStrategy(ReversiStrategy first, ReversiStrategy rest) {
    this.first = Objects.requireNonNull(first);
    this.rest = Objects.requireNonNull(rest);
  }

  @Override
  public Coordinate chooseMove(ReadOnlyReversiModel model) { //todo just make sure this works
    Coordinate firstStrategyBestMove = first.chooseMove(model);
    if (firstStrategyBestMove != null) {
      return firstStrategyBestMove;
    }
    return rest.chooseMove(model);
  }
}