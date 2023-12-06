package model;

import controller.ModelEventListener;

/**
 * An interface for a model with listeners to pass on instructions.
 * This interface is a more specific implementation of the Emitter interface.
 * It gives the model the ability to add listeners, except that its listeners are
 * ModelEventListeners as opposed to ViewEventListeners.
 * It also provides a way for the model to notify its listeners that a turn has been taken.
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