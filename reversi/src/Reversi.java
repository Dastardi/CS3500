import controller.*;
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
    ReversiFrame viewPlayer1 = new ReversiFrame(model);
    ReversiFrame viewPlayer2 = new ReversiFrame(model);
    Player player1 = setPlayer(args[0], model);
    Player player2 = setPlayer(args[1], model);
    ReversiController controller1 = new ReversiController(model, player1, viewPlayer1);
    ReversiController controller2 = new ReversiController(model, player2, viewPlayer2);
    model.startGame();
  }

  //TODO can this be static?
  private static Player setPlayer(String arg, ReversiModel model) {
    switch (arg) {
      case "human":
        return new HumanPlayer();
      case "easy":
        return new EasyAIPlayer(model);
      case "hard":
        return new HardAIPlayer(model);
      default:
        System.out.println("The three available players are human, easy, and hard.");
        //the controller ensures the game cannot start with a null player
        return null;
    }
  }
}