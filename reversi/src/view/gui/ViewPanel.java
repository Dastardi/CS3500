package view.gui;

/**
 * A representation of the panel used to display a game of Reversi. Most methods other than the
 * constructor are private, but objects (specifically, the frame) need to be able to add
 * themselves as listeners to the panel.
 */
public interface ViewPanel {
  /**
   * Adds a listener to the panel. Primarily used to connect the frame to the panel.
   * @param listener the listener to add to the panel
   */
  void addPanelListener(PanelEventListener listener);
}