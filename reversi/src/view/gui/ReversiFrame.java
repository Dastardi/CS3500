package view.gui;

import controller.ModelEventListener;
import controller.ViewEventListener;
import model.Coordinate;
import model.ReadOnlyReversiModel;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.net.URL;
import java.util.Optional;

/**
 * The frame, otherwise called the window, of the GUI for a reversi game.
 * The frame constitutes the view, though it is effectively a wrapper for the panel.
 * The panel does the work of displaying the board and directly interacting with the user.
 * The frame's job is to construct and accurately display the panel, as well as to
 * pass information from user input, via the panel, to the controller.
 */
public class ReversiFrame extends JFrame
    implements ReversiView, ViewEventListener, Emitter, ModelEventListener {
  //holds the listener to this panel, which handles moves and passes
  //not final because it needs to be able to be set from empty to a given ViewEventListener
  private Optional<ViewEventListener> listener;
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
    model.addListener(this);
    this.listener = Optional.empty();
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
    this.listener = Optional.of(listener);
  }

  @Override
  public void removeView() {
    this.dispose();
  }

  @Override
  public String moveMade(Coordinate coordinate) {
    if (this.listener.isPresent()) {
      return this.listener.get().moveMade(coordinate);
    } else {
      return "Could not make move; the view is disconnected from the rest of the system.";
    }
  }

  @Override
  public void passed() {
    this.listener.ifPresent(ViewEventListener::passed);
  }

  //we decided to have some fun with it and add some images to our popups. this is by no means
  //the most efficient way to do this - we briefly considered making an alternate constructor to
  //pass in images - but seeing as it's all in the name of having a good time we hope it's
  //not a problem :)
  @Override
  public void displayPopup(String messageToDisplay) {
    switch (messageToDisplay) {
      case "It's your turn!":
        URL thumbImageUrl = getClass().getResource("/view/gui/img/thumb.png");
        ImageIcon thumbIcon = new ImageIcon(thumbImageUrl);
        JOptionPane.showMessageDialog(this, messageToDisplay,
            "Game Status", JOptionPane.INFORMATION_MESSAGE, thumbIcon);
        break;
      case "You provided an invalid player. LOL!":
        URL koalaImageURL = getClass().getResource("/view/gui/img/koala.png");
        ImageIcon koalaIcon = new ImageIcon(koalaImageURL);
        JOptionPane.showMessageDialog(this, messageToDisplay,
            "Game Status", JOptionPane.INFORMATION_MESSAGE, koalaIcon);
        break;
      case "Game ended!\nWinner: Black":
        URL blackBearImageURL = getClass().getResource("/view/gui/img/blackbear.png");
        ImageIcon blackBearIcon = new ImageIcon(blackBearImageURL);
        JOptionPane.showMessageDialog(this, messageToDisplay,
            "Game Status", JOptionPane.INFORMATION_MESSAGE, blackBearIcon);
        break;
      case "Game ended!\nWinner: White":
        URL polarImageIcon = getClass().getResource("/view/gui/img/polar.png");
        ImageIcon polarIcon = new ImageIcon(polarImageIcon);
        JOptionPane.showMessageDialog(this, messageToDisplay,
            "Game Status", JOptionPane.INFORMATION_MESSAGE, polarIcon);
        break;
      case "Game ended in a tie!":
        URL penguinImageIcon = getClass().getResource("/view/gui/img/penguins.png");
        ImageIcon penguinIcon = new ImageIcon(penguinImageIcon);
        JOptionPane.showMessageDialog(this, messageToDisplay,
            "Game Status", JOptionPane.INFORMATION_MESSAGE, penguinIcon);
        break;
      default:
        JOptionPane.showMessageDialog(this, messageToDisplay,
            "Game Status", JOptionPane.INFORMATION_MESSAGE);
        break;
    }
  }

  @Override
  public void showBoard() {
    this.setVisible(true);
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