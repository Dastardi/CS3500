package model;

import controller.ModelEventListener;

/**
 * An interface for a model with listeners to pass on instructions.
 */
public interface TurnTaker {
  /**
   * Adds a listener to this TurnTaker.
   * @param listener the listener to add.
   */
  void addListener(ModelEventListener listener);

  /**
   * Notifies all listeners that a turn has been taken.
   */
  void notifyTurn();
}
