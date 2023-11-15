import model.BasicReversi;
import model.ReversiModel;
import view.gui.ReversiFrame;

/**
 * Runs a game of reversi via a GUI.
 */
public final class Reversi {
  /**
   * Runs the game using command line arguments - a user will be able to set the
   * hexagon radius size using args.
   * @param args command line arguments for the GUI to use.
   */
  public static void main(String[] args) {
    ReversiModel model = new BasicReversi();
    ReversiFrame view = new ReversiFrame(model);
    view.setVisible(true);
  }
}