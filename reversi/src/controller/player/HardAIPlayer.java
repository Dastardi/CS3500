package controller.player;

import controller.MoveType;
import controller.Pair;
import controller.Player;
import model.Coordinate;
import model.ReversiModel;
import strategy.CompositeStrategy;
import strategy.CornerStrategy;
import strategy.MaxCaptureStrategy;
import strategy.ReversiStrategy;
import strategy.UpperLeftMostStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * A hard AI to give a proper challenge in a Reversi game. Understands the strength of the corner
 * and uses it to its advantage, playing in corners when possible and attempting to prevent its
 * opponent from playing in the corner.
 */
public class HardAIPlayer implements Player {
  private final ReversiStrategy strategy;

  /**
   * Constructs the AI and its composite strategy.
   * @param model the model from which the strategy reads.
   */
  public HardAIPlayer(ReversiModel model) {
    this.strategy = new CompositeStrategy(new CornerStrategy(model),
        new CompositeStrategy(new MaxCaptureStrategy(model),
            new UpperLeftMostStrategy()));
  }

  @Override
  public Pair<MoveType, Coordinate> move() {
    List<Coordinate> moveList = strategy.chooseMove(new ArrayList<>());
    if (moveList.isEmpty()) {
      return new Pair<>(MoveType.NOVALID, null);
    } else {
      return new Pair<>(MoveType.VALID, moveList.get(0));
    }
  }
}