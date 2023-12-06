import controller.player.Player;
import controller.ReversiController;
import model.Coordinate;
import model.ReversiModel;
import view.gui.ReversiFrame;

/**
 * Mock version of a Reversi controller to help test that it receives information correctly.
 * Contains stub implementations of necessary methods and uses a log
 * to return the inputs being passed in.
 */
public class MockReversiController extends ReversiController {
  private StringBuilder log;

  /**
   * Constructs the mock controller.
   * @param model the Reversi model to use with the controller
   * @param player the player using this controller
   * @param view the view that interacts with this controller
   * @param log the StringBuilder to append messages to
   */
  public MockReversiController(ReversiModel model, Player player,
                               ReversiFrame view, StringBuilder log) {
    super(model, player, view);
    this.log = log;
  }

  @Override
  public String moveMade(Coordinate coordinate) {
    return "Received move at: " + coordinate.toString();
  }

  @Override
  public void passed() {
    this.log.append("Received pass");
  }
}