package controller;

import model.Coordinate;
import provider.controller.PlayerActionListener;

/**
 * A ReversiEventListener provides the capability for objects to listen to the ReversiPanel
 * (or any such class that notifies its listeners about moves and passes).
 * Implementing a custom interface eliminates the need for classes such as EventListener,
 * which could be swing dependent, and could result in too much coupling on technologies
 * our code may not always want to rely on.
 * An object that is a ReversiEventListener will receive information from the panel when a user
 * who is interacting with the panel indicates that they want to either move or pass.
 * Listeners of the panel do not need to care about what causes these events to occur;
 * they only care that they have happened so that they can respond.
 * In the case of Reversi, this interface is implemented by the view, and therefore
 * the methods of this interface should, in turn, pass the information they receive along
 * to the ViewEventListener class that listens to the view.
 * This is how information will pass from panel to view to controller to model and back.
 */
public interface ViewEventListener {
  /**
   * Listens to the panel and receives notifications that a move was made at the given coordinates.
   * In turn, notifies all listeners to the view (or other object implementing this interface),
   * of the move that was made.
   * Since the view should not place a tile or otherwise make any changes to itself
   * without the model approving those changes, this method returns a boolean so that
   * information can flow from model to controller to view to panel about whether
   * the move the user attempted to make is a valid move and should show on the panel.
   * This method works in conjunction with the notifyMoveMadeAndCheckValidity method in the panel,
   * and both will work in conjunction with the equivalent move listening method
   * in the controller once the controller is implemented.
   * @param coordinate the coordinate of the tile the user indicated a move to
   */
  String moveMade(Coordinate coordinate);

  /**
   * Listens to the panel and receives notifications that the user has passed their turn.
   * In turn, notifies all listeners to the view
   * (or other object implementing this interface) of the pass.
   * A pass is always a valid move, as long as the game is in progress,
   * therefore no information must be passed regarding validity.
   */
  void passed();
}