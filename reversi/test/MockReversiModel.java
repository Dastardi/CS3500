import model.Coordinate;
import model.PlayerColor;
import model.ReversiModel;
import model.Tile;

import java.util.Objects;

/**
 * Mock version of a Reversi model to help test the controller.
 * Contains stub implementations of all methods and uses a log
 * to return the inputs being passed to each method in the model.
 */
public class MockReversiModel implements ReversiModel {
  protected final StringBuilder log;

  /**
   * Constructs the mock model.
   * @param log the StringBuilder to append our validation messages to.
   */
  public MockReversiModel(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public PlayerColor getCurrentPlayer() throws IllegalStateException {
    return null;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public PlayerColor getCurrentWinner() {
    return null;
  }

  @Override
  public int getPlayerScore(PlayerColor color) {
    log.append("Getting score for the " + color + " player.");
    return 0;
  }

  @Override
  public Tile getTileAt(Coordinate coordinate) throws IllegalArgumentException {
    log.append("Getting tile at coordinate: " + coordinate);
    return null;
  }

  @Override
  public int getBoardSize() {
    return 0;
  }

  @Override
  public boolean isMoveLegal(Coordinate coordinate) {
    log.append("Checking that move at " + coordinate + " is legal.");
    return false;
  }

  @Override
  public int getMoveScore(Coordinate coordinate) {
    log.append("Getting score at " + coordinate);
    return 0;
  }

  @Override
  public boolean playerHasLegalMoves() {
    return false;
  }

  @Override
  public void move(Coordinate coordinate) throws IllegalArgumentException, IllegalStateException {
    log.append("Making a move to " + coordinate);
  }

  @Override
  public void pass() throws IllegalStateException {
    //stub
  }
}