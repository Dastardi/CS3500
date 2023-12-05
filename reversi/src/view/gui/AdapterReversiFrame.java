package view.gui;

import controller.ViewEventListener;
import model.ReadOnlyReversiModel;
import provider.view.HexReversiFrame;

import java.util.Objects;

public class AdapterReversiFrame implements ReversiView {
  private final HexReversiFrame delegate;

  public AdapterReversiFrame (ReadOnlyReversiModel model, HexReversiFrame delegate) {
    this.delegate = Objects.requireNonNull(delegate);
  }

  public AdapterReversiFrame (ReadOnlyReversiModel model) {
    this.delegate = new HexReversiFrame(model);
  }

  @Override
  public void displayPopup(String messageToDisplay) {
    delegate.showMessage(messageToDisplay);
  }

  @Override
  public void showBoard() {
    delegate.setVisible();
  }

  @Override
  public void addListener(ViewEventListener listener) {
    //todo add the listener, needs to be a playerActionListener - extend interface?
  }

  @Override
  public void removeView() {
    delegate.dispose();
  }
}
