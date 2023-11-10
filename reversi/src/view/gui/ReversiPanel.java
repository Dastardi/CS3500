package view.gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;

public class ReversiPanel extends JPanel implements ViewPanel, MouseListener {
  private final List<ViewableTile> tileList;
  private final double radius;
  private final double tileWidth;
  private final double tileHeight;
  private final int panelWidth; //TODO these might not be able to be resizable based on how resizing works
  private final int panelHeight;
  private PanelEventListener listener; //TODO careful - not initialized in constructor; also, change to list later?

  public ReversiPanel(int boardSize) {
    //setPreferredSize(new Dimension(boardSize * (int)Math.sqrt(3) * radius, 17 * radius));
    this.radius = 40;
    this.tileWidth = Math.sqrt(3) * this.radius;
    this.tileHeight = 2 * this.radius;
    this.panelWidth = 760; //TODO prob can't leave these hardcoded
    this.panelHeight = 680;
    setPreferredSize(new Dimension(this.panelWidth, this.panelHeight));
    setBackground(Color.BLACK);
    //the panel needs to be able to take clicks, so it is a mouse listener
    addMouseListener(this);

    //TODO after making listeners a list, initialize it here
    //this.listeners = new ArrayList<>();

    this.tileList = new ArrayList<>();
    int halfBoard = boardSize / 2;

    //create and add the board to tileList.
    //row moves from 0 to the negative board size, in order to get represent the full board
    //row-by-row on a 0 index
    for (int row = 0; row >= -halfBoard; row--) {
      //index is based on row, and moves from the value of row to half the size of the board.
      //this will always generate the correct amount of tiles for that row.
      for (int index = row; index <= halfBoard; index++) {
        //for adding the top rows, including the center row.
        ViewableTile tile = new ViewableTile(Color.GRAY,
            //based on the horizontal center of the board, offset by the correct amount based on
            //the index, the tile width, and the row number.
            getCenter().width + (tileWidth * index) - (tileWidth * (halfBoard + row) / 2),
            //based on the vertical center of the board, offset by the correct amount based on
            //the row number. As we don't need to do the offset calculations, this is a lot
            //simpler than calculating x.
            getCenter().height - radius * (1.5 * (halfBoard + row)),
            //set the radius
            radius,
            //since q is offset to the right in the top half of the axial array,
            //the range of values starts at its smallest at the top and increases
            //as we go down. since index gets more negative over time, this does that.
            halfBoard + index,
            //r starts at 0 and goes up, whereas row starts at 0 and goes down.
            -row);
        tileList.add(tile);

        //for adding the bottom rows - does not include the middle row, hence the if
        if (row != -halfBoard) {
            ViewableTile tile2 = new ViewableTile(Color.GRAY,
                //same horizontal calculations as above.
                getCenter().width + (tileWidth * index) - (tileWidth * (halfBoard + row) / 2),
                //slightly different y - adds the calculation rather than subtracting it to place
                //the row further down the frame, as (0,0) is in the top left.
                getCenter().height + radius * (1.5 * (halfBoard + row)),
                //set the radius
                radius,
                //since q is offset to the left in the bottom half of the axial array,
                //the range of values must stay consistent on the left and increase
                //as we go up. index and row are always going to be the same on the first
                //run of the inner for loop, but as it continues index will increase and the
                //offset from row will stay the same.
                index + Math.abs(row),
                //in this call, r has to start at boardSize-1 and go down - as the row loop
                //continues, it will decrease over time, so adding it allows us to build from
                //the bottom up.
                boardSize + row - 1);
            tileList.add(tile2);
        }
      }
    }

    //drawDiscs method call here to redraw each disc on the board on top of the tiles
  }

  private Dimension getCenter() {
    return new Dimension(this.panelWidth / 2, this.panelHeight / 2);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    //recalculate radius and tileWidth based on the frame
    for (ViewableTile tile : tileList) {
      tile.draw(g);
    }
  }

  //TODO deal with encapsulation
  public void addPanelListener(PanelEventListener listener) {
    this.listener = Objects.requireNonNull(listener);
  }

  //go through all listeners of this panel
  //and notify each of them that the tile at the provided coordinates was clicked
  //TODO for now I'm only making one listener, which is the view, so commenting out the loop
  private void notifyTileClicked(int q, int r) {
    /*
    for (ICanvasEvent listener : listeners) {
      listener.tileClicked(q, r);
    }
     */
    this.listener.tileClicked(q, r);
  }

  //TODO set all other tiles to the base color
  @Override
  public void mouseClicked(MouseEvent e) {
    Point pointClicked = e.getPoint();
    //which tile was this point in?
    for (ViewableTile tile : this.tileList) {
      if (tile.containsPoint(pointClicked)) {
        //right now we're just printing which one we clicked in to confirm we did it right
        System.out.println(tile.getQ() + ", " + tile.getR() + "\n");
        //we can do other stuff too
        tile.setColor(Color.red);
        //this is how we register that the tile was clicked!
        notifyTileClicked(tile.getQ(), tile.getR());
      }
      repaint(); //need this to redraw
      //I think he put this outside the for each actually
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    //currently does nothing
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    //currently does nothing
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    //currently does nothing
  }

  @Override
  public void mouseExited(MouseEvent e) {
    //currently does nothing
  }
}