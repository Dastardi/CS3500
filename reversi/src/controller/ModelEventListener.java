package controller;

/**
 * An interface for an object that listens to the model.
 * Effectively the model's counterpart to the ViewEventListener interface;
 * these are the features a class needs if it wants to review notifications from the model.
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
