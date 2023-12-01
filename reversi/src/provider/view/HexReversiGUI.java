package provider.view;

import cs3500.reversi.controller.PlayerActionListener;

/**
 * Represents a GUI for Reversi.
 */
public interface HexReversiGUI {

  /**
   * Add a listener for player actions.
   *
   * @param listener The listener.
   */
  void addPlayerActionListener(PlayerActionListener listener);

  /**
   * Set the GUI to be visible.
   */
  void setVisible();

  /**
   * Whether the GUI is currently visible.
   *
   * @return Whether the GUI is currently visible.
   */
  boolean isVisible();

  /**
   * Refresh the view displayed by the GUI.
   */
  void refreshView();

  /**
   * Display a message on the GUI.
   *
   * @param message The content of the message.
   */
  void showMessage(String message);

}
