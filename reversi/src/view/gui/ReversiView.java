package view.gui;

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
}