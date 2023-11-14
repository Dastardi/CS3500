package strategy;

import model.Coordinate;

import java.util.List;

public interface Tiebreaker {
  /**
   * todo
   * @return
   */
  Coordinate breakTies(List<Coordinate> moves);
}
