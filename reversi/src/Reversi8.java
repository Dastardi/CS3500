import controller.*;
import model.BasicReversi;
import model.ReversiModel;
import provider.view.HexReversiFrame;
import view.gui.AdapterReversiFrame;
import view.gui.ReversiFrame;
import view.gui.ReversiView;

/**
 * Runs a two-player game of hexagonal reversi with full GUI and AI capabilities.
 * Designed for assignment eight in which we use a provider's code in addition to
 * our own, one player and one view are from our original code, and the other player
 * and view are adapted from the provider's code.
 */
public class Reversi8 {
  /**
   * Runs the game using command line arguments. Sets up a model, then two each of views,
   * players, and controllers according to the args. Does not allow less than two players,
   * and any text after the first two players will be ignored.
   * @param args command line arguments for the GUI to use.
   */
  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("Must provide at least two player types.");
      return;
    }

    ReversiModel model = new BasicReversi();
    ReversiView viewPlayer1 = new ReversiFrame(model);
    ReversiView viewPlayer2 = new AdapterReversiFrame(model);
    Player player1 = setPlayer(args[0], model);
    Player player2 = setProviderPlayer(args[1], model);
    ReversiController controller1 = new ReversiController(model, player1, viewPlayer1);
    ReversiController controller2 = new ReversiController(model, player2, viewPlayer2);
    model.startGame();

  }

  //helps the main method construct the game by parsing a given argument for the requested game
  //type. takes in a string and the model and constructs a player object, using the model if
  //necessary, as indicated by the provided string
  private static Player setPlayer(String arg, ReversiModel model) {
    //the controller ensures the game cannot start with a null player
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

  private static Player setProviderPlayer(String arg, ReversiModel model) {
    return null;
  }
}