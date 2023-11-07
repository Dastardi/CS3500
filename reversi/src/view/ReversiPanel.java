package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class ReversiPanel extends JPanel implements ViewPanel {
  private final List<ViewableTile> tileList;
  private final int hexSize = 100;

  public ReversiPanel(int boardSize) {
    setPreferredSize(new Dimension(boardSize * hexSize, boardSize * hexSize));
    setBackground(Color.GRAY);

    //create tiles and set their coordinates
    //avoids having to convert pixel to hex
    this.tileList = new ArrayList<>();

    //populate the tile list with tiles
    for (int r = 0; r < boardSize; r++) {
      for (int q = 0; q < boardSize; q++) {
        //if the coordinates are within the hexagonal board
        if (r + q < (boardSize / 2) * 3 && r + q > boardSize / 2) {
          ViewableTile tile = new ViewableTile(Color.GREEN, q * hexSize, r * hexSize,
              hexSize, q, r);
          this.tileList.add(tile);
        }
      }
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (ViewableTile tile : tileList) {
      tile.draw(g);
    }
  }
}