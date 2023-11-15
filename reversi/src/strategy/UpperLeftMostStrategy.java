package strategy;

import model.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * This strategy provides a way to break ties, to be added on to other Reversi strategies.
 * It takes in a list provided by another strategy and returns the first element of the list,
 * or an empty list if the list is empty.
 * If the list is empty, there are no valid moves and the user should pass.
 * If there are one or more elements in the list, the first element
 * will always be the upper-left-most because of the way the loops in all strategies are indexed.
 */
public class UpperLeftMostStrategy implements ReversiStrategy {
  @Override
  public List<Coordinate> chooseMove(List<Coordinate> moveList) {
    if (moveList.isEmpty()) {
      return new ArrayList<>();
    }
    Coordinate upperLeftMostMove = moveList.get(0);
    List<Coordinate> moveListToReturn = new ArrayList<>();
    moveListToReturn.add(upperLeftMostMove);
    return moveListToReturn;
  }
}