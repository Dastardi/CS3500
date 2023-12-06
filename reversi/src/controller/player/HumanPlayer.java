package controller.player;

import controller.MoveType;
import controller.Pair;
import model.Coordinate;

/**
 * A representation of a human Reversi player.
 * Mostly a stub, because the human player will never interact with the controller
 * through this class; all human player inputs come from Reversi's view.
 */
public class HumanPlayer implements Player {
  @Override
  public Pair<MoveType, Coordinate> move() {
    return new Pair<>(MoveType.HUMAN, null);
  }
}