package squaretests;

import model.ReadOnlyReversiModel;
import view.gui.ReversiFrame;

/**
 * Mock version of a Reversi view to help test that it receives information correctly.
 * Contains stub implementations of the displayPopup method and uses a log
 * to return the inputs being passed in.
 */
public class MockView extends ReversiFrame {
  private StringBuilder log;

  /**
   * Constructs the mock view.
   * @param model the Reversi model to use for the view
   * @param log the StringBuilder to append validation messages to
   */
  public MockView(ReadOnlyReversiModel model, StringBuilder log) {
    super(model);
    this.log = log;
  }

  @Override
  public void displayPopup(String messageToDisplay) {
    this.log.append(messageToDisplay);
  }
}