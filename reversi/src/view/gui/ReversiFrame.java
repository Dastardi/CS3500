package view.gui;

import model.Coordinate;
import model.ReadOnlyReversiModel;

import javax.swing.*;
import java.awt.*;

/**
 * The frame of the GUI for a reversi game. The frame is a container for the panel,
 * which does the majority of the heavy lifting when it comes to actually
 * displaying the game.
 */
public class ReversiFrame extends JFrame implements PanelEventListener {
  private final ReversiPanel panel;
  private final int boardSize;

  /**
   * Constructs the frame of the GUI.
   * @param model the read-only model to be created in the view
   */
  public ReversiFrame(ReadOnlyReversiModel model) {
    this.boardSize = model.getBoardSize();
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.panel = new ReversiPanel(this.boardSize);
    //make the frame able to listen to the panel
    this.panel.addPanelListener(this);
    add(this.panel, BorderLayout.CENTER);
    setResizable(false);
    pack();
  }

  @Override
  public boolean moveMadeAndWasValid(Coordinate coordinate) {
    System.out.println("Hello I am the frame and you're trying to move here: " + coordinate.q + " " + coordinate.r);
    //todo stub method - fix this
    return true; //todo this should only be true if the controller says the model says the move is valid
  }

  @Override
  public void passed() {
    System.out.println("Hello I am the frame and the user passed"); //todo stub method - fix this
  }
}