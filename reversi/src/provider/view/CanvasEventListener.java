package provider.view;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Event listeners for the canvas.
 */
interface CanvasEventListener {

  /**
   * Process a mouse click event.
   *
   * @param e A mouse click event
   */
  void processMouseClicked(MouseEvent e);

  /**
   * Process a key event.
   *
   * @param e A key event
   */
  void processKeyTyped(KeyEvent e);
}
