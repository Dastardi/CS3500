package provider.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JOptionPane;

import cs3500.reversi.controller.PlayerActionListener;
import cs3500.reversi.model.ReadOnlyHexagonalReversi;

/**
 * A JFrame for Reversi.
 */
public class HexReversiFrame extends JFrame implements HexReversiGUI {

  private final HexReversiPanel canvas;

  /**
   * Constructor for a Reversi JFrame.
   *
   * @param model A Read-Only model for the JFrame to display.
   */
  public HexReversiFrame(ReadOnlyHexagonalReversi model) {

    // calculate the width of the board, then the appropriate side lengths of hexes, then the height
    int defaultWindowWidth = 800;
    int boardWidth = defaultWindowWidth;
    int hexCellSideLen = (int) (boardWidth / (1.7321 * (2 * model.getNumLayers() - 1)));
    int boardHeight = (int) (1.5 * hexCellSideLen *
            (2 * model.getNumLayers() - 1)
            + hexCellSideLen / 2);

    // calculate the frame width and height
    int frameWidth = boardWidth;
    int frameHeight = boardHeight;

    // configure frame details
    setSize(new Dimension(frameWidth, frameHeight));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setResizable(true);

    // initialize the panel
    canvas = new HexReversiPanel(model, hexCellSideLen, frameWidth, frameHeight);
    canvas.setBackground(Color.lightGray);
    //canvas.addPanelListener(this);
    add(canvas);
  }

  @Override
  public void addPlayerActionListener(PlayerActionListener listener) {
    canvas.addPlayerActionListener(listener);
  }

  @Override
  public void setVisible() {
    this.setVisible(true);
  }

  @Override
  public boolean isVisible() {
    return super.isVisible();
  }

  @Override
  public void refreshView() {
    this.canvas.refreshView();
    this.repaint();
  }

  @Override
  public void showMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }
}
