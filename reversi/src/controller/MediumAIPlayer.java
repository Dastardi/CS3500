package controller;

import model.Coordinate;
import model.ReversiModel;
import strategy.*;

import java.util.ArrayList;
import java.util.List;

public class MediumAIPlayer implements Player {
  private final ReversiStrategy strategy;
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
    } else return new Pair<>(MoveType.VALID, moveList.get(0));
  }
}
