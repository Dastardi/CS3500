package strategy;

import model.Coordinate;

/**
 * todo
 */
public class ScoredMove {
  private final Coordinate move;
  private final int score;

  /**
   * todo
   * @param move
   * @param score
   */
  public ScoredMove(Coordinate move, int score) {
    if (move == null || score < 0) {
      throw new IllegalArgumentException("A scored move cannot be null or have a negative score.");
    }
    this.move = move;
    this.score = score;
  }

  public Coordinate getMove() {
    return new Coordinate(this.move.q, this.move.r);
  }

  private int getScore() {
    return this.score;
  }
}