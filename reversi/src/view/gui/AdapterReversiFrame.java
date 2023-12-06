package view.gui;

import controller.ViewEventListener;
import model.ReadOnlyReversiModel;
import provider.controller.PlayerActionListener;
import provider.view.HexReversiFrame;
import provider.view.HexReversiGUI;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents the frame, otherwise called the window, of the GUI for a reversi game.
 * This frame is an adapter of our providers' code by using a HexReversiFrame, the provided view class, as a delegate.
 * It implements our view interface and the given view interface and therefore has the functionality of
 * both types. This allows it to be a functional view in conjunction with our controller, thus allowing us to use
 * our controller for both view types.
 */
public class AdapterReversiFrame implements ReversiView, HexReversiGUI {
  //holds the delegate object to use the provided view
  private final HexReversiFrame delegate;
  //holds the listener to this panel, which handles moves and passes
  //not final because it needs to be able to be set from empty to a given ViewEventListener
  //providers use a PlayerActionListener, but since this view adapts theirs into one of ours
  //and our controller uses it and our controller is a ViewEventListener, the listener
  //to this view is also a ViewEventListener
  private Optional<ViewEventListener> listener;

  /**
   * Constructs the adapter frame of the graphical user interface (GUI).
   * Uses a HexReversiFrame as the delegate so that it can adapt the providers' view.
   * @param delegate an object of the providers' view to adapt within this class
   */
  public AdapterReversiFrame (HexReversiFrame delegate) {
    this.delegate = Objects.requireNonNull(delegate);
    this.listener = Optional.empty();
  }

  /**
   * Alternate constructor that takes in the model and constructs its own delegate as a new HexReversiFrame object.
   * Uses the other constructor to initialize fields.
   * @param model the model for this game of Reversi
   */
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