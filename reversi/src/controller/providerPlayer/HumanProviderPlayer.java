package controller.providerPlayer;

import controller.MoveType;
import controller.Pair;
import controller.Player;
import model.Coordinate;

public class HumanProviderPlayer implements Player {
  @Override
  public Pair<MoveType, Coordinate> move() {
    return new Pair<>(MoveType.HUMAN, null);
  }
}
