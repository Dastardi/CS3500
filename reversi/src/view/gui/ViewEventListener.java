package view.gui;

/**
 * //todo add more
 * This will be implemented by the controller because
 * it is the controller's job to listen to the view and handle view events.
 */
public interface ViewEventListener {
  /**
   * //todo add
   * @return true if the move is valid and false otherwise
   */
  public boolean handleMove();

  /**
   * //todo add
   */
  public void handlePass();
}
