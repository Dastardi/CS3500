package provider.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;

import provider.controller.PlayerActionListener;
import provider.model.CoordinateUtils;
import provider.model.HexPosn;
import provider.model.ReadOnlyHexagonalReversi;

/**
 * Represents a JPanel for Reversi.
 */
class HexReversiPanel extends JPanel {

  List<PlayerActionListener> playerActionListeners = new ArrayList<>();

  private final ReadOnlyHexagonalReversi model;

  private final int originalWidth;

  private final int originalHeight;

  private double physicalScale = 1.0;

  private final List<HexTile> tiles = new ArrayList<>();

  private final List<CanvasEventListener> listeners = new ArrayList<>();

  /**
   * Constructor for a Reversi Panel.
   *
   * @param model The Read-Only model for the game of Reversi
   * @param hexCellSideLen The side length in pixels of a single hex cell
   * @param width The width of the window
   * @param height The height of the window
   */
  HexReversiPanel(ReadOnlyHexagonalReversi model, int hexCellSideLen, int width, int height) {

    PanelInputHandler inputHandler = new PanelInputHandler();
    addMouseListener(inputHandler);
    addKeyListener(inputHandler);
    addComponentListener(inputHandler);
    setFocusable(true);
    requestFocus();

    this.model = model;
    int modelLayers = model.getNumLayers();
    this.originalWidth = width;
    this.originalHeight = height;

    for (int q = -modelLayers + 1; q < modelLayers; q++) {
      for (int r = -modelLayers + 1; r < modelLayers; r++) {
        for (int s = -modelLayers + 1; s < modelLayers; s++) {
          HexPosn posn = new HexPosn(q, r, s);
          if (posn.validHexPosn()) {

            List<Integer> rowAndIndex = CoordinateUtils.qrsToRowAndIndex(posn, modelLayers);

            try {
              HexTile hexTile = new HexTile(model.getHexState(q, r, s), hexCellSideLen,
                  rowAndIndex.get(0), rowAndIndex.get(1), modelLayers, physicalScale);

              tiles.add(hexTile);
              addPanelListener(hexTile);
            } catch (Exception e) {
              //ignore
            }

          }
        }
      }
    }

    updateTileStates();
  }

  /**
   * Paints all tiles of the board.
   *
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    for (HexTile tile : tiles) {
      tile.draw(g2d);
    }
  }

  /**
   * Add an event listener to the list of event listeners.
   *
   * @param eventListener A new event listener
   */
  protected void addPanelListener(CanvasEventListener eventListener) {
    listeners.add(Objects.requireNonNull(eventListener));
  }

  /**
   * Update the state of all tiles in the board.
   */
  private void updateTileStates() {
    for (HexTile tile : tiles) {
      tile.updateState(model, physicalScale);
      tile.repaint();
    }
  }

  void refreshView() {
    updateTileStates();
    repaint();
  }

  /**
   * Update the window scale depending on a given width.
   *
   * @param width Window width
   * @param height Window height
   */
  private void updateTransform(int width, int height) {

    double scaleX = (double) width / (double) originalWidth;
    double scaleY = (double) height / (double) originalHeight;

    double universalScale = Math.min(scaleX,  scaleY);

    physicalScale = universalScale;
    updateTileStates();
  }

  //TODO: should i have each of my tiles have player action listener list
  //TODO do the feature interface methods need to know the color
  void addPlayerActionListener(PlayerActionListener listener) {
    this.playerActionListeners.add(listener);
    for (HexTile tile : tiles) {
      tile.addPlayerActionListener(listener);
    }
  }

  /**
   * Class for handling panel inputs.
   */
  private class PanelInputHandler implements MouseListener, KeyListener, ComponentListener {

    /**
     * Handle a mouse click event.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {

      for (CanvasEventListener eventListener : listeners) {
        eventListener.processMouseClicked(e);
      }
      refreshView();
    }

    /**
     * Handle a mouse hold event.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
//      System.out.println(
//              "Nothing happens, but I'm forced to implement this method," +
//              "and if I don't leave something here I lose Javadoc points"
//      );
    }

    /**
     * Handle a mouse release event.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
//      System.out.println(
//              "Nothing happens, but I'm forced to implement this method," +
//                      "and if I don't leave something here I lose Javadoc points"
//      );
    }

    /**
     * Handle a mouse enter event.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
//      System.out.println(
//              "Nothing happens, but I'm forced to implement this method," +
//                      "and if I don't leave something here I lose Javadoc points"
//      );
    }

    /**
     * Handle a mouse exit event.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
//      System.out.println(
//              "Nothing happens, but I'm forced to implement this method," +
//                      "and if I don't leave something here I lose Javadoc points"
//      );
    }

    /**
     * Handle a key input event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {

      char key = e.getKeyChar();
      System.out.println(key);

      if (key == 'P' || key == 'p') {

        for (PlayerActionListener listener : playerActionListeners) {
          listener.processTurnPassed();
        }

        return;
      }

      for (CanvasEventListener eventListener : listeners) {
        eventListener.processKeyTyped(e);
      }
      updateTileStates();
      repaint();
    }

    /**
     * Handle a key hold event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
//      System.out.println(
//              "Nothing happens, but I'm forced to implement this method," +
//                      "and if I don't leave something here I lose Javadoc points"
//      );
    }

    /**
     * Handle a key release event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
//      System.out.println(
//              "Nothing happens, but I'm forced to implement this method," +
//                      "and if I don't leave something here I lose Javadoc points"
//      );
    }

    /**
     * Handle a window resize event.
     *
     * @param e the event to be processed
     */
    @Override
    public void componentResized(ComponentEvent e) {
      updateTransform(HexReversiPanel.this.getWidth(), HexReversiPanel.this.getHeight());
      repaint();
    }

    /**
     * Handle a window move event.
     *
     * @param e the event to be processed
     */
    @Override
    public void componentMoved(ComponentEvent e) {
//      System.out.println(
//              "Nothing happens, but I'm forced to implement this method," +
//                      "and if I don't leave something here I lose Javadoc points"
//      );
    }

    /**
     * Handle a window show event.
     *
     * @param e the event to be processed
     */
    @Override
    public void componentShown(ComponentEvent e) {
//      System.out.println(
//              "Nothing happens, but I'm forced to implement this method," +
//                      "and if I don't leave something here I lose Javadoc points"
//      );
    }

    /**
     * Handle a window hide event.
     *
     * @param e the event to be processed
     */
    @Override
    public void componentHidden(ComponentEvent e) {
//      System.out.println(
//              "Nothing happens, but I'm forced to implement this method," +
//                      "and if I don't leave something here I lose Javadoc points"
//      );
    }
  }
}
