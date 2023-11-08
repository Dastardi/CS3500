package view.gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JPanel;

public class ReversiPanel extends JPanel implements ViewPanel, MouseListener {
  private final List<ViewableTile> tileList;
  private final int radius = 40;
  private PanelEventListener listener; //TODO careful - not initialized in constructor; also, change to list later?

  public ReversiPanel(int boardSize, boolean bingBong) {
    setPreferredSize(new Dimension(boardSize * (int)Math.sqrt(3) * radius, 17 * radius));
    setBackground(Color.GRAY);
    //the panel needs to be able to take clicks, so it is a mouse listener
    addMouseListener(this);
    //TODO after making listeners a list, initialize it here
    //this.listeners = new ArrayList<>();

    this.tileList = new ArrayList<>();
    this.tileList.add(new ViewableTile(Color.BLUE, 60 * Math.sqrt(3), 40.0, radius, 0, 5));
    this.tileList.add(new ViewableTile(Color.BLUE, 5.5 * (Math.sqrt(3)/2) * 40, 340, radius, 5, 5));

    //populate the tile list with tiles
//    for (int r = 0; r < boardSize; r++) {
//      for (int q = 0; q < boardSize; q++) {
//        //if the coordinates are within the hexagonal board
//        if (r + q <= (boardSize / 2) * 3 && r + q >= boardSize / 2) {
//          ViewableTile tile = new ViewableTile(Color.BLUE, q * radius, r * radius, radius, q, r);
//          this.tileList.add(tile);
//        }
//      }
//    }



    //drawDiscs method call here to redraw each disc on the board on top of the tiles
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
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