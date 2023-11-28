package controller;

import view.gui.ReversiFrame;

public class ReversiController implements IReversiController, ViewEventListener {
  ReversiController(Player player, ReversiFrame view) {

  }

  @Override
  public boolean handleMove() {
    return false;
  }

  @Override
  public void handlePass() {

  }
}
