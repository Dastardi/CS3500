package controller;

import model.Coordinate;

/**
 * A representation of a human Reversi player.
 */
public class HumanPlayer implements Player {
  @Override
  public Pair<MoveType, Coordinate> move() {
    return new Pair<>(MoveType.HUMAN, null);
  }
}