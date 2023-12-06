package controller.player;

import controller.MoveType;
import controller.Pair;
import controller.Player;
import model.Coordinate;
import model.ReversiModel;
import strategy.CompositeStrategy;
import strategy.MaxCaptureStrategy;
import strategy.UpperLeftMostStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * An easy AI to play Reversi against. Always selects the tile with the highest possible score.
 */
public class EasyAIPlayer implements Player {
  private final CompositeStrategy strategy;

  /**
   * Constructs this AI and sets its strategy.
   * @param model the model from which the strategy reads.
   */
  public EasyAIPlayer(ReversiModel model) {
    this.strategy = new CompositeStrategy(new MaxCaptureStrategy(model),
        new UpperLeftMostStrategy());
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
