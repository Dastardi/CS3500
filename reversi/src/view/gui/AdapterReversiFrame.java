package view.gui;

import controller.ViewEventListener;
import model.ReadOnlyReversiModel;
import provider.controller.PlayerActionListener;
import provider.view.HexReversiFrame;
import provider.view.HexReversiGUI;

import java.util.Objects;
import java.util.Optional;

public class AdapterReversiFrame implements ReversiView, HexReversiGUI {
  //holds the delegate object to use the provided view
  private final HexReversiFrame delegate;
  //holds the listener to this panel, which handles moves and passes
  //not final because it needs to be able to be set from empty to a given ViewEventListener
  //providers use a PlayerActionListener, but since this view adapts theirs into one of ours
  //and our controller uses it and our controller is a ViewEventListener, the listener
  //to this view is also a ViewEventListener
  private Optional<ViewEventListener> listener;

  public AdapterReversiFrame (HexReversiFrame delegate) {
    this.delegate = Objects.requireNonNull(delegate);
    this.listener = Optional.empty();
  }

  public AdapterReversiFrame (ReadOnlyReversiModel model) {
    this(new HexReversiFrame(model));
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
    if (listener == null) {
      throw new IllegalArgumentException("Cannot provide a null listener to the frame.");
    }
    this.listener = Optional.of(listener);
  }

  @Override
  public void removeView() {
    delegate.dispose();
  }

  @Override
  public void addPlayerActionListener(PlayerActionListener listener) {
    delegate.addPlayerActionListener(listener);
  }

  @Override
  public void setVisible() {
    delegate.setVisible();
  }

  @Override
  public boolean isVisible() {
    return delegate.isVisible();
  }

  @Override
  public void refreshView() {
    delegate.refreshView();
  }

  @Override
  public void showMessage(String message) {
    delegate.showMessage(message);
  }
}
