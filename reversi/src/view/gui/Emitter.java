package view.gui;

import controller.ViewEventListener;

/**
 * A representation of the panel used to display a game of Reversi. Most methods other than the
 * constructor are private, but objects (specifically, the frame) need to be able to add
 * themselves as listeners to the panel.
 */
public interface Emitter {
  /**
   * Adds a listener. Primarily used to connect the frame to the panel.
   * @param listener the listener to add
   */
  void addListener(ViewEventListener listener);
}