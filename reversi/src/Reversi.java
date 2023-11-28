import controller.EasyAIPlayer;
import controller.HumanPlayer;
import controller.Player;
import controller.ReversiController;
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
//  public static void main(String[] args) {
//    ReversiModel model = new BasicReversi();
//    ReversiFrame view = new ReversiFrame(model);
//    view.setVisible(true);
//  }


  public static void main(String[] args) {
    ReversiModel model = new BasicReversi();
    ReversiFrame viewPlayer1 = new ReversiFrame(model);
    ReversiFrame viewPlayer2 = new ReversiFrame(model);
    Player player1 = new HumanPlayer(model);
    Player player2 = new HumanPlayer(model);
    ReversiController controller1 = new ReversiController(model, player1, viewPlayer1);
    ReversiController controller2 = new ReversiController(model, player2, viewPlayer2);
    model.startGame();
  }
}