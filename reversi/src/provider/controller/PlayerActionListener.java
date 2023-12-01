package provider.controller;

/**
 * Represents something that listens to a player's actions.
 */
public interface PlayerActionListener {

  /**
   * Do something when a player places a piece.
   *
   * @param q The q coordinate.
   * @param r The r coordinate.
   * @param s The s coordinate.
   */
  void processPiecePlaced(int q, int r, int s);

  /**
   * Do something when a player passes a turn.
   */
  void processTurnPassed();

}
