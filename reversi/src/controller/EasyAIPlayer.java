package controller;

import model.Coordinate;
import model.ReversiModel;
import strategy.CompositeStrategy;
import strategy.MaxCaptureStrategy;
import strategy.UpperLeftMostStrategy;

import java.util.ArrayList;
import java.util.List;

public class EasyAIPlayer implements Player {
  private final CompositeStrategy strategy;
  public EasyAIPlayer(ReversiModel model) {
    this.strategy = new CompositeStrategy(new MaxCaptureStrategy(model), new UpperLeftMostStrategy());
  }

  @Override
  public Pair<MoveType, Coordinate> move() {
    List<Coordinate> moveList = strategy.chooseMove(new ArrayList<>());
    if (moveList.isEmpty()) {
      return new Pair<>(MoveType.INVALID, null);
    } else return new Pair<>(MoveType.VALID, moveList.get(0));
  }
}
