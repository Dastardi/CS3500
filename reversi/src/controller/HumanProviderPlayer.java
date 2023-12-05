package controller;

import model.Coordinate;

public class HumanProviderPlayer implements Player {
  @Override
  public Pair<MoveType, Coordinate> move() {
    return new Pair<>(MoveType.HUMAN, null);
  }
}
