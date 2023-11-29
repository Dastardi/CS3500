package controller;

import model.Coordinate;
import model.ReversiModel;
import view.gui.ViewEventListener;
import view.gui.ReversiFrame;

public class ReversiController implements ViewEventListener, ModelEventListener {
  private final ReversiModel model;
  private final Player player;
  private final ReversiFrame view;
  private boolean myTurn;

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
    this.myTurn = !this.myTurn;
    if (this.myTurn) {
      this.view.displayPopup("It's your turn!");
    }
  }

  private void AIMove() {
    Pair<MoveType, Coordinate> pair = player.move();
    MoveType type = pair.getFirst();
    Coordinate coordinate = pair.getSecond();
    if (type.equals(MoveType.HUMAN)) {
      return;
    } else if (type.equals(MoveType.VALID)) {
      this.model.move(coordinate);
    } else if (type.equals(MoveType.NOVALID)) {
      this.model.pass();
    }
  }
}