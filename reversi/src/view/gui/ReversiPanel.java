package view.gui;

import model.Coordinate;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

/**
 * The GUI panel for a game of Reversi, which is held in the frame. Does the majority of the work
 * to actually draw a game of reversi, including player selection for easier gameplay.
 */
public class ReversiPanel extends JPanel implements ViewPanel, MouseListener, KeyListener {
  private HashMap<Coordinate, ViewableTile> tileList;
  private final double radius;
  private final double tileWidth;
  private final List<PanelEventListener> listeners;
  private final Color baseColor = Color.GRAY;
  private final Color clickedColor = Color.CYAN;
  private final Color backgroundColor = Color.BLACK;
  private ViewableTile selectedTile;

  /**
   * Constructs the Reversi panel. Uses a set radius to perform the calculations for the size
   * of the panel (which the frame uses to size itself around it). It also adds the correct
   * amount of tiles in a hexagonal arrangement and sets up listeners for the panel. Lastly,
   * the constructor draws the six starting tiles at the center of the board in order to
   * create the conditions necessary to play a game of Reversi.
   * @param boardSize the size of the model's board.
   */
  public ReversiPanel(int boardSize) {
    //set up the panel
    this.radius = 40;
    int frameWidth = (int)(Math.sqrt(3) * boardSize * radius);
    int frameHeight = (int)((boardSize / 2 * radius) + ((boardSize / 2 + 1) * 2 * radius));
    setPreferredSize(new Dimension(frameWidth, frameHeight));
    this.tileWidth = Math.sqrt(3) * this.radius;
    setBackground(backgroundColor);

    //the panel needs to be able to take clicks, so it is a mouse listener
    addMouseListener(this);
    setFocusable(true);
    addKeyListener(this);
    this.listeners = new ArrayList<>();

    //set up the tiles and starting discs
    this.tileList = new HashMap<Coordinate, ViewableTile>();
    setTilePositions(boardSize);
    this.selectedTile = null;
    drawStartingDiscs(boardSize);
  }

  //adds all the board tiles to tileList.
  private void setTilePositions(int boardSize) {
    int halfBoard = boardSize / 2;
    for (int row = 0; row >= -halfBoard; row--) {
      for (int index = row; index <= halfBoard; index++) {
        ViewableTile tile = new ViewableTile(baseColor,
            getCenter(boardSize).width + (tileWidth * index) - (tileWidth * (halfBoard + row) / 2),
            getCenter(boardSize).height - radius * (1.5 * (halfBoard + row)),
            radius, halfBoard + index, -row);
        tileList.put(new Coordinate(tile.getQ(), tile.getR()), tile);
        if (row != -halfBoard) {
          ViewableTile tile2 = new ViewableTile(baseColor,
              getCenter(boardSize).width + (tileWidth * index) - (tileWidth * (halfBoard + row) / 2),
              getCenter(boardSize).height + radius * (1.5 * (halfBoard + row)),
              radius,index + Math.abs(row),boardSize + row - 1);
          tileList.put(new Coordinate(tile2.getQ(), tile2.getR()), tile2);
        }
      }
    }
  }

  //draws the six starting discs in the center of the board, alternating black and white.
  private void drawStartingDiscs(int boardSize) {
    int centerTileQR = boardSize / 2;
    ViewableTile centerTile = tileList.get(new Coordinate(centerTileQR, centerTileQR));

    List<ViewableTile> neighbors = new ArrayList<>();
    //tile to the right of center
    neighbors.add(tileList.get(new Coordinate(centerTile.getQ() + 1, centerTile.getR())));
    //tile to the bottom right of center
    neighbors.add(tileList.get(new Coordinate(centerTile.getQ(), centerTile.getR() + 1)));
    //tile to the bottom left of center
    neighbors.add(tileList.get(new Coordinate(centerTile.getQ() - 1, centerTile.getR() + 1)));
    //tile to the left of center
    neighbors.add(tileList.get(new Coordinate(centerTile.getQ() - 1, centerTile.getR())));
    //tile to the top left of center
    neighbors.add(tileList.get(new Coordinate(centerTile.getQ(), centerTile.getR() - 1)));
    //tile to the top right of center
    neighbors.add(tileList.get(new Coordinate(centerTile.getQ() + 1, centerTile.getR() - 1)));

    for (int i = 0; i < neighbors.size(); i++) {
      if (i % 2 == 0) {
        //place a black disc in the top left, bottom left, and right
        neighbors.get(i).setDisc(Color.BLACK);
      } else {
        //place a white disc in the top right, bottom right, and left
        neighbors.get(i).setDisc(Color.WHITE);
      }
    }
  }

  //returns the center-most tile of the board to allow for easier arrangement calculations.
  private Dimension getCenter(int boardSize) {
    int halfBoard = boardSize / 2;
    int frameWidth = (int)(Math.sqrt(3) * boardSize * radius);
    int frameHeight = (int)((halfBoard * radius) + ((halfBoard + 1) * 2 * radius));
    return new Dimension(frameWidth / 2, frameHeight / 2);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    //iterate through the hashmap entries
    for (Map.Entry<Coordinate, ViewableTile> pair : tileList.entrySet()) {
      ViewableTile tile = pair.getValue();
      tile.draw(g);
    }
  }

  //go through all listeners of this panel and notify each of them that the tile
  // at the provided coordinates was clicked
  private boolean notifyMoveMadeAndCheckValidity(Coordinate coordinate) {
    boolean moveIsValid = false;
    for (PanelEventListener listener : listeners) {
      //notify listeners that the move was made
      //and simultaneously ask if the move was valid
      if (listener.moveMadeAndWasValid(coordinate)) {
        moveIsValid = true;
      }
    }
    return moveIsValid;
  }

  //todo add comment
  private void notifyPassed() {
    for (PanelEventListener listener : listeners) {
      listener.passed();
    }
  }

  @Override
  public void addPanelListener(PanelEventListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Cannot provide a null listener to the panel.");
    }
    this.listeners.add(listener);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Point pointClicked = e.getPoint();
    boolean tileClicked = false;

    for (Map.Entry<Coordinate, ViewableTile> pair : tileList.entrySet()) {
      ViewableTile tile = pair.getValue();
      if (tile.containsPoint(pointClicked)) {
        tileClicked = true;
        if (tile.getColor() == this.baseColor) {
          setAllTilesToBase();
          tile.setColor(this.clickedColor);
          this.selectedTile = tile;
          System.out.println("Tile Coordinates: " + tile.getQ() + ", " + tile.getR());
        } else {
          setAllTilesToBase();
          this.selectedTile = null;
        }
        break;
      }
    }
    //deselect all tiles if click is outside the board
    if (!tileClicked) {
      setAllTilesToBase();
      this.selectedTile = null;
    }
    repaint();
  }

  //repaints all tiles to their starting color
  private void setAllTilesToBase() {
    for (Map.Entry<Coordinate, ViewableTile> pair : tileList.entrySet()) {
      ViewableTile tile = pair.getValue();
      tile.setColor(this.baseColor);
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    //enter key = make move on currently selected tile
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      System.out.println("enter key pressed"); //todo keep to check method calls, delete when done
      if (this.selectedTile != null) {
        if(notifyMoveMadeAndCheckValidity(new Coordinate(this.selectedTile.getQ(), this.selectedTile.getR()))) {
          this.selectedTile.setDisc(Color.BLACK);
        }
        repaint();
      }
    }

    if (e.getKeyCode() == KeyEvent.VK_W) {
      if (this.selectedTile != null) {
          this.selectedTile.setDisc(Color.WHITE);
        repaint();
      }
    }

    if (e.getKeyCode() == KeyEvent.VK_B) {
      if (this.selectedTile != null) {
        this.selectedTile.setDisc(Color.BLACK);
        repaint();
      }
    }

    //space bar = pass
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      System.out.println("space bar pressed"); //todo keep to check method calls, delete when done
      notifyPassed();
    }
    //q key = quit game
    if (e.getKeyCode() == KeyEvent.VK_Q) {
      System.out.println("q key pressed"); //todo keep to check method calls, delete when done
      System.exit(0);
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    //unused stub
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    //unused stub
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    //unused stub
  }

  @Override
  public void mouseExited(MouseEvent e) {
    //unused stub
  }

  @Override
  public void keyTyped(KeyEvent e) {
    //unused stub
  }

  @Override
  public void keyReleased(KeyEvent e) {
    //unused stub
  }
}