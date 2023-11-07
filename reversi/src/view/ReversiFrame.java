package view;

import model.ReadOnlyReversiModel;

import javax.swing.*;
import java.awt.*;

public class ReversiFrame extends JFrame implements ViewFrame {
  private final ReversiPanel panel;
  private final int hexSize = 100;

  public ReversiFrame(ReadOnlyReversiModel model) {
    int boardSize = model.getBoardSize();
    setPreferredSize(new Dimension(boardSize * hexSize, boardSize * hexSize));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.panel = new ReversiPanel(boardSize);
    add(this.panel);
    pack();
    setVisible(true); //TODO don't need this if it's in main?
  }
}