package controller;

import model.Coordinate;
import model.ReversiModel;

public class HumanPlayer implements Player {
  public HumanPlayer(ReversiModel model) {

  }

  @Override
  public Pair<MoveType, Coordinate> move() {
    return new Pair<>(MoveType.HUMAN, null);
  }
}
