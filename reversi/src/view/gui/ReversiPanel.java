package view.gui;

import controller.ModelEventListener;
import controller.ViewEventListener;
import model.Coordinate;
import model.PlayerColor;
import model.ReadOnlyReversiModel;
import model.Tile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * The panel of a graphical user interface (GUI) for a game of Reversi.
 * The panel is held inside the frame. This panel does the majority of the work
 * to visually draw and display the game.
 * The panel implements MouseListener and KeyListener to be able to take user input
 * in the form of clicks and key presses in order for the user to interact with the model.
 * Uses ViewableTile objects to represent each tile and to reduce panel workload,
 * as each tile contains its personal drawing information.
 */
public class ReversiPanel extends JPanel
    implements Emitter, MouseListener, KeyListener, ModelEventListener {
  //represents the model of the Reversi game played and displayed on this panel
  private final ReadOnlyReversiModel model;
  //represents all the tiles to be displayed
  //effectively the GUI counterpart of the board in the model
  private final HashMap<Coordinate, ViewableTile> tileList;
  //represents the radius of each hexagonal tile
  //the radius is the distance from the center to a corner
  private final int sideLength;
  //holds all listeners to this panel, which handle moves and passes
  //not final because it needs to be able to be set from empty to a given ViewEventListener
  private Optional<ViewEventListener> listener;
  //when not selected, all tiles are gray
  private final Color baseColor = Color.GRAY;
  //when selected (via mouse click), tiles are cyan
  private final Color clickedColor = Color.CYAN;
  //the panel behind the board is black
  private final Color backgroundColor = Color.BLACK;
  //represents the tile that is selected at any given time, if one exists
  private ViewableTile selectedTile;
  private boolean hinting;

  /**
   * Constructs the Reversi panel.
   * Uses a set radius to perform the calculations for the size of the panel
   * (which the frame uses to size itself around it). See note in README file regarding radius.
   * Adds the correct amount of tiles in a hexagonal arrangement.
   * Adds itself as a MouseListener and KeyListener to be able to take mouse and key input.
   * Lastly, draws the six tiles around the center of the board
   * that are always displayed at the start of a game of Reversi.
   * @param model the Reversi game model to be displayed on the panel
   */
  public ReversiPanel(ReadOnlyReversiModel model) {
    this.model = model;
    int boardSize = this.model.getBoardSize();
    //set up the panel
    this.sideLength = 60;
    int frameWidth = boardSize * this.sideLength;
    setPreferredSize(new Dimension(frameWidth, frameWidth));
    setBackground(backgroundColor);

    //the panel needs to be able to take clicks, so it is a mouse listener
    //the panel needs to be able to take key presses, so it is a key listener
    addMouseListener(this);
    //must be focusable in order to take key presses
    setFocusable(true);
    addKeyListener(this);
    this.listener = Optional.empty();
    //add this as a listener to the model for repainting
    model.addListener(this);
    //set up the tiles and starting discs
    this.tileList = new HashMap<Coordinate, ViewableTile>();
    setTilePositions(boardSize);
    this.selectedTile = null;
    drawStartingDiscs(boardSize);
  }

  //adds all the board tiles to tileList
  private void setTilePositions(int boardSize) {
    for (int row = 0; row < boardSize; row++) {
      for (int col = 0; col < boardSize; col++) {
        ViewableTile tile = new ViewableTile(baseColor, col * this.sideLength,
            row * this.sideLength, this.sideLength, col, row);
        tileList.put(new Coordinate(tile.getQ(), tile.getR()), tile);
      }
    }
  }

  //draws the six starting discs in the center of the board, alternating black and white.
  private void drawStartingDiscs(int boardSize) {
    int centerTileQR = boardSize / 2;
    ViewableTile centerTile = tileList.get(new Coordinate(centerTileQR, centerTileQR));

    List<ViewableTile> neighbors = new ArrayList<>();
    //tile to the top left
    neighbors.add(tileList.get(new Coordinate(boardSize / 2 - 1, boardSize / 2 - 1)));
    //tile to the bottom left
    neighbors.add(tileList.get(new Coordinate(boardSize / 2, boardSize / 2 - 1)));
    //tile to the bottom right
    neighbors.add(tileList.get(new Coordinate(boardSize / 2, boardSize / 2)));
    //tile to the top right
    neighbors.add(tileList.get(new Coordinate(boardSize / 2 - 1, boardSize / 2)));

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

  //we customized paintComponent in order to ensure that we draw the board correctly
  //whenever a change is made.
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Tile[][] board = model.getBoard();
    //we iterate over the model's board, because going the other way created issues with
    //trying to fetch before the board was populated with tiles.
    for (Tile[] row : board) {
      for (Tile tile : row) {
        ViewableTile viewTile = tileList.get(tile.getCoordinate());
        //we check the contents of the BOARD tile and use a pair of if statements to
        //change the VIEW tile accordingly.
        if (!tile.isEmpty()) {
          PlayerColor tileContents = tile.getContents();
          if (tileContents == PlayerColor.BLACK) {
            viewTile.setDisc(Color.BLACK);
          }
          else if (tileContents == PlayerColor.WHITE) {
            viewTile.setDisc(Color.WHITE);
          }
        }
        //finally, we tell the view tile to draw itself.
        viewTile.draw(g);
      }
    }
  }

  /**
   * Notifies the first listener of this panel that a move was made at the given coordinates.
   * Since the view should not place a tile or otherwise make any changes to itself
   * without the model approving those changes, this method returns a boolean so that
   * information can flow from model to controller to view to panel about whether
   * the move the user attempted to make is a valid move and should show on the panel.
   * This method works in conjunction with the moveMadeAndWasValid method in the frame,
   * and both will work in conjunction with the equivalent move listening method
   * in the controller once the controller is implemented.
   * @param coordinate the coordinate of the tile the user indicated a move to
   */
  private String notifyMoveMade(Coordinate coordinate) {
    if (this.listener.isPresent()) {
      return this.listener.get().moveMade(coordinate);
    }
    else {
      return "Could not make move; the panel is disconnected from the rest of the system.";
    }
  }

  /**
   * Notifies the listener to this panel that the user is passing their turn.
   * A pass is always a valid move, as long as the game is in progress,
   * therefore no information must be passed regarding validity.
   */
  private void notifyPassed() {
    this.listener.ifPresent(ViewEventListener::passed);
  }

  @Override
  public void addListener(ViewEventListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Cannot provide a null listener to the panel.");
    }
    this.listener = Optional.of(listener);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Point pointClicked = e.getPoint();
    boolean tileClicked = false;
    //for each tile in the tileList
    for (Map.Entry<Coordinate, ViewableTile> pair : tileList.entrySet()) {
      ViewableTile tile = pair.getValue();
      //if this is the tile the click happened in
      if (tile.containsPoint(pointClicked)) {
        tileClicked = true;
        //note: still printing this as requested in the previous assignment,
        //as instructions were very unclear as to whether we should keep this functionality,
        //so we are erring on the side of caution
        System.out.println("Clicked tile coordinates: " + tile.getQ() + ", " + tile.getR());
        //if the tile is not currently selected, it becomes the selected tile and changes color
        if (tile.getColor() == this.baseColor) {
          setAllTilesToBase();
          tile.setColor(this.clickedColor);
          this.selectedTile = tile;
        }
        //else, i.e. if the clicked tile is already selected, all tiles are reset to base color
        //and there is no currently selected tile
        else {
          setAllTilesToBase();
          this.selectedTile = null;
        }
        //once the clicked tile has been found, there's no need to continue the loop
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
      if (this.selectedTile != null) {
        String moveMessage = notifyMoveMade(new Coordinate(this.selectedTile.getQ(),
            this.selectedTile.getR()));
        if (!moveMessage.equals("valid")) {
          URL catImageUrl = getClass().getResource("/view/gui/img/cat.png");
          ImageIcon catIcon = new ImageIcon(catImageUrl);
          JOptionPane.showMessageDialog(this, moveMessage,
              "Game Status", JOptionPane.INFORMATION_MESSAGE, catIcon);
        }
      }
    }

    //space bar = pass
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      URL oranguImageURL = getClass().getResource("/view/gui/img/orangutan.png");
      ImageIcon oranguImage = new ImageIcon(oranguImageURL);
      int passed = JOptionPane.showConfirmDialog(this, "Pass your turn?",
          "Game Status", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, oranguImage);
      if (passed == 0) {
        notifyPassed();
      }
    }

    //q key = quit game (and close both frames)
    if (e.getKeyCode() == KeyEvent.VK_Q) {
      URL dawgImageURL = getClass().getResource("/view/gui/img/dawg.png");
      ImageIcon dawgImage = new ImageIcon(dawgImageURL);
      int quit = JOptionPane.showConfirmDialog(this, "Quit the game?",
          "Game Status", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, dawgImage);
      if (quit == 0) {
        JOptionPane.showMessageDialog(this, "Game quit.",
            "Game Status", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
      }
    }

    //h key - basic help method, tells you if you have any valid moves
    if (e.getKeyCode() == KeyEvent.VK_H) {
      this.hinting = !this.hinting;
    }
  }

  @Override
  public void updateTurn() {
    this.paintComponent(getGraphics());
  }

  @Override
  public void mousePressed(MouseEvent e) {
    //unused stub; required override
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    //unused stub; required override
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    //unused stub; required override
  }

  @Override
  public void mouseExited(MouseEvent e) {
    //unused stub; required override
  }

  @Override
  public void keyTyped(KeyEvent e) {
    //unused stub; required override
  }

  @Override
  public void keyReleased(KeyEvent e) {
    //unused stub; required override
  }

  @Override
  public void initializeGame() {
    //unused stub; required override
  }
}