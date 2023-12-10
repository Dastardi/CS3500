package squaretests;

import model.BasicReversi;
import model.PlayerColor;
import model.Coordinate;

/**
 * Mock version of a Reversi model to help test the controller.
 * Contains stub implementations of all methods and uses a log
 * to return the inputs being passed to each method in the model.
 */
public class MockReversiModel extends BasicReversi {
  protected final StringBuilder log;

  /**
   * Constructs the mock model.
   * @param log the StringBuilder to append our validation messages to.
   */
  public MockReversiModel(StringBuilder log) {
    super();
    if (log == null) {
      throw new IllegalArgumentException("Passed log cannot be null!");
    }
    this.log = log;
  }

  @Override
  public int getPlayerScore(PlayerColor color) {
    if (log != null) {
      log.append("Getting score for the " + color + " player.\n");
    }
    return super.getPlayerScore(color);
  }

  @Override
  public boolean isMoveLegal(Coordinate coordinate) {
    if (log != null) {
      log.append("Checking that move at " + coordinate + " is legal.\n");
    }
    return super.isMoveLegal(coordinate);
  }

  @Override
  public int getMoveScore(Coordinate coordinate) {
    if (log != null) {
      log.append("Getting score at " + coordinate + "\n");
    }
    return super.getMoveScore(coordinate);
  }

  @Override
  public void move(Coordinate coordinate) throws IllegalArgumentException, IllegalStateException {
    if (log != null) {
      log.append("Making a move to " + coordinate + "\n");
    }
    super.move(coordinate);
  }
}