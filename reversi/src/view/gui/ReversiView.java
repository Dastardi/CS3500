package view.gui;

import controller.ViewEventListener;

/**
 * This interface allows the controller to interact with the view by telling it to send
 * any message to the player via JOptionPanels.
 */
public interface ReversiView {
  /**
   * Sends a notification to the view that it should display a given message.
   * @param messageToDisplay the text of the message. This text also determines the image shown.
   */
  void displayPopup(String messageToDisplay);

  /**
   * Displays the board by setting the visibility to true.
   */
  void showBoard();

  /**
   * Adds a listener. Primarily used to connect the controller to the view.
   * @param listener the listener to add
   */
  void addListener(ViewEventListener listener);

  /**
   * Destroys and releases this view and all its subcomponents.
   */
  void removeView();
}