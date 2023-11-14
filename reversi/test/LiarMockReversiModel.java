import model.Coordinate;

/**
 *
 */
public class LiarMockReversiModel extends MockReversiModel {
  public LiarMockReversiModel(StringBuilder log) {
    super(log);
  }

  @Override
  public boolean isMoveLegal(Coordinate coordinate) {
    return coordinate.equals(new Coordinate(3, 3));
  }
}
