package controller;

/**
 * NOTE: This interface is not necessary for the GUI part of this assignment -
 * we are thinking ahead to the controller.
 * This interface is a parallel of the PanelEventListener interface.
 * It will be implemented by the controller, when a controller exists,
 * because it is the controller's job to listen to the view and handle view events.
 */
public interface ViewEventListener {
  /**
   * Listens to the view and receives notifications that a move was made at the given coordinates.
   * Since the view should not place a tile or otherwise make any changes to itself
   * without the model approving those changes, this method returns a boolean so that
   * information can flow from model to controller to view to panel about whether
   * the move the user attempted to make is a valid move and should show on the panel.
   * This method works in conjunction with the moveMadeAndWasValid method in the frame
   * and the notifyMoveMadeAndCheckValidity method in the panel.
   * @return true iff this move is valid according to the model
   */
  boolean handleMove();

  /**
   * Listens to the view and receives notifications that the user has passed their turn.
   * A pass is always a valid move, as long as the game is in progress,
   * therefore no information must be passed regarding validity.
   */
  void handlePass();
}