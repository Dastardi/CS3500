package controller;

/**
 * An interface for an object that listens to the model.
 */
public interface ModelEventListener {
  /**
   * The model needs to be able to inform its listeners when the game has begun.
   */
  void initializeGame();

  /**
   * The model needs to be able to inform its listeners that one of the players took a turn.
   */
  void updateTurn();
}
