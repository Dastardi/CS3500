package squaretests;

import model.Coordinate;
import squaretests.MockReversiModel;

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