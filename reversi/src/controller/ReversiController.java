package controller;

import model.Coordinate;
import model.ReversiModel;
import view.gui.ViewEventListener;
import view.gui.ReversiFrame;
import view.text.ReversiTextualView;

/**
 * TODO WRITE A JAVADOC HERE!!!!!!!!!!!!!!!!
 */
public class ReversiController implements ViewEventListener, ModelEventListener {
  private final ReversiModel model;
  private final Player player;
  private final ReversiFrame view;
  private boolean myTurn;

  /**
   * TODO WRITE A JAVADOC HERE!!!!!!!!!!!!!!!!
   */
  public ReversiController(ReversiModel model, Player player, ReversiFrame view) {
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
    } catch(Exception e) {
      return e.getMessage();
    }
  }

  @Override
  public void passed() {
    this.model.pass();
  }

  @Override
  public void initializeGame() {
    this.view.setVisible(true);
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
      System.out.println(new ReversiTextualView(model));
      checkTurn();
    }
  }

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
        this.view.displayPopup("Game ended!\n" +
            "Winner: Black");
        break;
      case 1:
        this.view.displayPopup("Game ended!\n" +
            "Winner: White");
        break;
      case 2:
        this.view.displayPopup("Game ended in a tie!");
        break;
    }

    this.view.dispose();
  }
}