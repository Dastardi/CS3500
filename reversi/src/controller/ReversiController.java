package controller;

import model.Coordinate;
import model.ReversiModel;
import view.gui.ViewEventListener;
import view.gui.ReversiFrame;

import javax.swing.*;

public class ReversiController implements IReversiController, ViewEventListener, ModelEventListener {
  private final ReversiModel model;
  private final Player player;
  private final ReversiFrame view;
  private boolean myTurn;

  public ReversiController(ReversiModel model, Player player, ReversiFrame view) {
    if (model == null || player == null || view == null) {
      throw new IllegalArgumentException("All inputs to controller must be non-null.");
    }
    this.model = model;
    this.player = player;
    this.view = view;
    //add this controller as a listener to the view and model
    this.view.addListener(this);
    this.model.addListener(this);
    myTurn = false;
  }

  @Override
  public String moveMadeAndWasValid(Coordinate coordinate) {
    if (!myTurn) {
      return "not your turn!";
    }
    try {
      model.move(coordinate);
      return "valid";
    } catch(Exception e) {
      return e.getMessage();
    }
  }

  @Override
  public void passed() {
    model.pass();
  }

  @Override
  public void initializeGame() {
    view.setVisible(true);
  }

  @Override
  public void updateTurn() {
    myTurn = !myTurn;
    if (myTurn) {
      JOptionPane.showMessageDialog(null, "It's your turn!");
    }
    AIMove();
  }

  private void AIMove() {
    MoveType type = player.move().getFirst();
    Coordinate coordinate = player.move().getSecond();
    if (type.equals(MoveType.VALID)) {
      model.move(coordinate);
    } else if (type.equals(MoveType.INVALID)) {
      model.pass();
    }
  }
}