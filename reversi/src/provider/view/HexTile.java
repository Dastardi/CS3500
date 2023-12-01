package provider.view;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import provider.controller.PlayerActionListener;
import provider.model.HexPosn;
import provider.model.HexState;
import provider.model.ReadOnlyHexagonalReversi;

/**
 * Represents a Hex as a JComponent.
 */
class HexTile extends JComponent implements CanvasEventListener {

  private HexState state;

  private final int sideLen;


  private final int row;

  private final int index;

  private final int modelLayers;

  /**
   * Center pixel x coordinate of the hex tile.
   */
  private int xCoordinate;

  /**
   * Center pixel y coordinate of the hex tile.
   */
  private int yCoordinate;

  private double scale;


  private Polygon hexShape;

  private boolean isSelected = false;

  private List<PlayerActionListener> playerActionListeners = new ArrayList<>();

  /**
   * Constructor for a hex tile for a Reversi GUI.
   *
   * @param state The state of the hex.
   * @param sideLen The side length of the hex.
   * @param row The row of the hex.
   * @param index The index in the row of the hex.
   * @param modelLayers The number of layers of the model.
   * @param scale The scale of the length to the window.
   */
  HexTile(HexState state, int sideLen, int row, int index, int modelLayers, double scale) {
    this.state = state;
    this.sideLen = sideLen;
    this.row = row;
    this.index = index;
    this.modelLayers = modelLayers;
    this.scale = scale;

    calculateAndSetXY();
  }

  /**
   * Figure out the actual x and y position of the hex based on the model.
   */
  void calculateAndSetXY() {

    // calculate the physical sideLen
    int physicalSideLen = (int) (sideLen * scale);

    // distances that will affect where the hex tile is located on the panel
    int leftToRightLen = (int) (1.7321 * physicalSideLen);
    int subVerticalHeight = physicalSideLen / 2;
    int startingLeftPixelOffset = Math.abs((modelLayers - 1) - row) * leftToRightLen / 2;

    // calculate the x and y coords of the hex tile
    xCoordinate =
            index * leftToRightLen + leftToRightLen / 2 + startingLeftPixelOffset;
    yCoordinate =
            row * (subVerticalHeight + physicalSideLen) + subVerticalHeight + physicalSideLen / 2;
  }

  /**
   * Draw this hex tile on a 2D grid.
   *
   * @param g2d The 2D grid.
   */
  void draw(Graphics2D g2d) {

    // calculate the physical sideLen
    int physicalSideLen = (int) (sideLen * scale);

    int[] xPoints = new int[6];
    int[] yPoints = new int[6];

    for (int i = 0; i < 6; i++) {
      double angle = 2 * Math.PI / 6 * i + Math.PI / 6;
      xPoints[i] = (int) (xCoordinate + physicalSideLen * Math.cos(angle));
      yPoints[i] = (int) (yCoordinate + physicalSideLen * Math.sin(angle));
    }

    hexShape = new Polygon(xPoints, yPoints, 6);

    Path2D hexagon = new Path2D.Double();
    hexagon.moveTo(xPoints[0], yPoints[0]);
    for (int i = 1; i < 6; i++) {
      hexagon.lineTo(xPoints[i], yPoints[i]);
    }
    hexagon.closePath();

    g2d.setColor(new Color(72, 93, 63));

    if (isSelected) {
      g2d.setColor(Color.cyan);
    }

    g2d.fill(hexagon);
    g2d.setColor(Color.BLACK);
    g2d.draw(hexagon);


    if (state == HexState.WHITE) {
      g2d.setColor(Color.WHITE);
      g2d.fillOval(xCoordinate - physicalSideLen / 2,
              yCoordinate - physicalSideLen / 2,
              physicalSideLen, physicalSideLen);
    } else if (state == HexState.BLACK) {
      g2d.setColor(Color.BLACK);
      g2d.fillOval(xCoordinate - physicalSideLen / 2,
              yCoordinate - physicalSideLen / 2,
              physicalSideLen, physicalSideLen);
    }
  }

  /**
   * If this hex tile is clicked, mark as selected.
   *
   * @param e A mouse click event
   */
  @Override
  public void processMouseClicked(MouseEvent e) {

    if (hexShape.contains(e.getX(), e.getY())) {
      isSelected = !isSelected;

    } else {
      isSelected = false;
    }

  }

  /**
   * If this hex is clicked and played, play a move.
   *
   * @param e A key event
   */
  @Override
  public void processKeyTyped(KeyEvent e) {

    if (isSelected) {
      if (e.getKeyChar() == '\n') {

        HexPosn qrs = CoordinateUtils.rowAndIndexToQRS(row, index, modelLayers);

        // callback to player action listener list
        for (PlayerActionListener listener : playerActionListeners) {
          listener.processPiecePlaced(qrs.q, qrs.r, qrs.s);
        }
      }
    }
  }

  /**
   * Given a model, update the state of this hex tile.
   *
   * @param model The model for Reversi
   * @param scale The scale for the window
   */
  protected void updateState(ReadOnlyHexagonalReversi model, double scale) {

    HexPosn qrs = CoordinateUtils.rowAndIndexToQRS(row, index, modelLayers);
    state = model.getHexState(qrs.q, qrs.r, qrs.s);

    if (row == 3 && index == 3) {
      int a = 2;
    }

    this.scale = scale;
    calculateAndSetXY();
  }

  void addPlayerActionListener(PlayerActionListener listener) {
    this.playerActionListeners.add(listener);
  }


}
