package view.gui;

import controller.ModelEventListener;
import model.Coordinate;
import model.ReadOnlyReversiModel;

import javax.swing.*;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * The frame, otherwise called the window, of the GUI for a reversi game.
 * The frame constitutes the view, though it is effectively a wrapper for the panel.
 * The panel does the work of displaying the board and directly interacting with the user.
 * The frame's job is to construct and accurately display the panel, as well as to
 * pass information from user input, via the panel, to the controller.
 * Note for this hw06: there is currently no controller in the code, so there is a disconnect
 * between the view and the model. Once the controller is implemented, it will be a listener
 * of this view.
 */
public class ReversiFrame extends JFrame implements ReversiView, ViewEventListener, Emitter, ModelEventListener {
  //holds all listeners to this panel, which handle moves and passes
  private final List<ViewEventListener> listeners;
  private final ReversiPanel panel;

  /**
   * Constructs the frame of the graphical user interface (GUI).
   * Uses a read-only version of the model because the view is not allowed to change
   * the model directly or interact with it other than observing its state.
   * @param model the read-only version of the model to be displayed in the view
   */
  public ReversiFrame(ReadOnlyReversiModel model) {
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.panel = new ReversiPanel(model);
    //the frame is a listener of the panel, allowing it to get user interaction information
    this.panel.addListener(this);
    //model.addListener(this); //TODO
    this.listeners = new ArrayList<>();
    //the panel is the only object in this frame, therefore it is placed in the center
    //of a simple border layout
    add(this.panel, BorderLayout.CENTER);
    setResizable(false);
    pack();
  }

  @Override
  public void addListener(ViewEventListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Cannot provide a null listener to the frame.");
    }
    this.listeners.add(listener);
  }

  @Override
  public String moveMade(Coordinate coordinate) {
    //see PanelEventListener interface

    //this method is currently a stub
    //when we add a controller to this code, this method will pass information
    //from the panel through here and through to the controller.
    //the controller, in turn, will pass information back (from the model)
    //about whether the move is valid so that the view knows whether it is okay
    //to display the requested move
    //this method should only return true if the controller says the model says the move is valid
    return listeners.get(0).moveMade(coordinate);
  }

  @Override
  public void passed() {
    //see PanelEventListener interface

    //this method is currently a stub
    //when we add a controller to this code, this method will pass information
    //from the panel through here and through to the controller
    listeners.get(0).passed();
  }

  @Override
  public void displayPopup(String messageToDisplay) {
    JOptionPane.showMessageDialog(this, messageToDisplay);
  }

  @Override
  public void updateTurn() {
    this.panel.repaint();
  }

  @Override
  public void initializeGame() {
    //stub implementation
  }
}