package controller;

import model.Coordinate;
import model.ReversiModel;
import provider.controller.PlayerActionListener;
import provider.model.HexPosn;
import view.gui.AdapterReversiFrame;
import view.gui.ReversiView;

/**
 * A controller for a player to interact with the model and view in a game of Reversi.
 * There is a controller for each player, and a unique view for each controller.
 * The controller facilitates between human players (who interact with the view) and
 * the model, as well as AI players (who interface directly with the controller) the view
 * and model in order to ensure that the game is represented correctly in the view as
 * dictated by the model.
 */
public class ReversiController
    implements ViewEventListener, ModelEventListener, PlayerActionListener {
  //represents the game's model
  private final ReversiModel model;
  //represents the player that interacts with this controller, either human or AI
  private final Player player;
  //represents the view that is associated with and communicates with this controller
  private final ReversiView view;
  //represents whether it is this controller's turn, as opposed to the other player's controller
  //not final because whose turn it is obviously changes every turn
  private boolean myTurn;

  /**
   * Constructs a controller. Inputs must be non-null - if the view or model is null,
   * an exception is thrown and the game is prevented from starting. If a player is
   * null, we want to enable the other player to see what went wrong, and so we give them
   * a message using the view before quitting.
   * When it constructs itself, the controller must add itself as a listener to both the
   * model and its respective view.
   */
  public ReversiController(ReversiModel model, Player player, ReversiView view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Model and view inputs to controller must be non-null.");
    }
    this.view = view;
    if (player == null) {
      this.view.displayPopup("You provided an invalid player. LOL!");
      System.exit(0);
    }
    this.model = model;
    this.player = player;
    //add this controller as a listener to the view and model
    this.view.addListener(this);
    if (this.view instanceof AdapterReversiFrame) { ((AdapterReversiFrame) this.view).addPlayerActionListener(this); }
    this.model.addListener(this);
    this.myTurn = false;
  }

  @Override
  public String moveMade(Coordinate coordinate) {
    if (!this.myTurn) {
      return "It is not your turn :(";
    }
    try {
      this.model.move(coordinate);
      return "valid";
    } catch (Exception e) {
      return e.getMessage();
    }
  }

  @Override
  public void passed() {
    this.model.pass();
  }

  @Override
  public void initializeGame() {
    this.view.showBoard();
  }

  @Override
  public void updateTurn() {
    if (this.model.isGameOver()) {
      this.handleGameOver();
      return;
    }
    this.myTurn = !this.myTurn;
    if (this.myTurn) {
      this.view.displayPopup("It's your turn!");
      checkTurn();
    }
  }

  //contains turn-taking logic for human and AI players.
  //if the player is a human, the controller should just sit and wait for the human to interact
  //with the view, and so the method simply returns if it detects that its player is a human on
  //its turn. If the player is an AI, it needs to either move or pass. To do so, we check the Pair
  //to see whether the AI has found a valid move. If it has, the model moves there.
  //otherwise, it passes.
  private void checkTurn() {
    if (!myTurn) {
      return;
    }
    Pair<MoveType, Coordinate> pair = player.move();
    MoveType type = pair.getFirst();
    Coordinate coordinate = pair.getSecond();
    if (type.equals(MoveType.HUMAN)) {
      return;
    }
    if (type.equals(MoveType.VALID)) {
      this.model.move(coordinate);
    }
    if (type.equals(MoveType.NOVALID)) {
      this.model.pass();
    }
  }

  //if the game is over, this method is called to ensure that the correct message is sent
  //and that the window is closed. using the integer return value of getCurrentWinner(),
  //we can determine who won and inform both players accordingly. Closes the windows
  //independently of one another using dispose so that both players are notified that
  //the game is over.
  private void handleGameOver() {
    switch (model.getCurrentWinner()) {
      case 0:
        this.view.displayPopup("Game ended!\nWinner: Black");
        break;
      case 1:
        this.view.displayPopup("Game ended!\nWinner: White");
        break;
      case 2:
        this.view.displayPopup("Game ended in a tie!");
        break;
      default:
        //getCurrentWinner always returns one of 0, 1, or 2.
    }
    this.view.removeView();
  }

  @Override
  public void processPiecePlaced(int q, int r, int s) {
    Coordinate coordinate = Translator.hexPosnToCoordinate(model.getNumLayers(), new HexPosn(q, r, s));
    this.moveMade(coordinate);
  }

  @Override
  public void processTurnPassed() {
    this.passed();
  }
}