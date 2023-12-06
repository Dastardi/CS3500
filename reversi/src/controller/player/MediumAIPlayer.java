package controller.player;

import controller.MoveType;
import controller.Pair;
import model.Coordinate;
import model.ReversiModel;
import strategy.ReversiStrategy;
import strategy.CompositeStrategy;
import strategy.MaxCaptureStrategy;
import strategy.AvoidCornerAdjacentStrategy;
import strategy.UpperLeftMostStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * A medium-difficulty AI to play Reversi against. Always selects the tile with the highest
 * possible score, and avoids playing next to corners to prevents its opponent from taking them.
 */
public class MediumAIPlayer implements Player {
  private final ReversiStrategy strategy;

  /**
   * Constructs the AI and its composite strategy.
   * @param model the model from which the strategy reads.
   */
  public MediumAIPlayer(ReversiModel model) {
    this.strategy = new CompositeStrategy(new MaxCaptureStrategy(model),
        new CompositeStrategy(new AvoidCornerAdjacentStrategy(model),
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