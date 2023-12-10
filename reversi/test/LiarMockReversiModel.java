import model.Coordinate;

/**
 * Represents a mock Reversi model that lies to a strategy trying to play it,
 * telling the strategy that only certain move(s) are valid.
 */
public class LiarMockReversiModel extends MockReversiModel {
  public LiarMockReversiModel(StringBuilder log) {
    super(log);
  }

  @Override
  public boolean isMoveLegal(Coordinate coordinate) {
    return coordinate.equals(new Coordinate(0, 0));
  }
}
