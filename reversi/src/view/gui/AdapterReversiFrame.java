package view.gui;

import controller.ViewEventListener;
import model.ReadOnlyReversiModel;
import provider.model.ReadOnlyHexagonalReversi;
import provider.view.HexReversiFrame;

public class AdapterReversiFrame implements ReversiView {
  private HexReversiFrame delegate;

  public AdapterReversiFrame (ReadOnlyReversiModel model, HexReversiFrame delegate) {

  }

  public AdapterReversiFrame (ReadOnlyReversiModel model) {
    //this.delegate = new HexReversiFrame(wut);
  }

  @Override
  public void displayPopup(String messageToDisplay) {

  }

  @Override
  public void showBoard() {

  }

  @Override
  public void addListener(ViewEventListener listener) {

  }

  @Override
  public void removeView() {

  }
}
