package view.gui;

import model.ReadOnlyReversiModel;

import javax.swing.*;
import java.awt.*;

public class ReversiFrame extends JFrame implements ViewFrame, PanelEventListener {
  private final ReversiPanel panel;

  public ReversiFrame(ReadOnlyReversiModel model) {
    int boardSize = model.getBoardSize();
    setPreferredSize(new Dimension(760, 680));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.panel = new ReversiPanel(boardSize, true);
    add(this.panel);
    pack();
    setVisible(true); //TODO don't need this if it's in main?
    setResizable(false);
  }

  @Override
  public void tileClicked(int q, int r) {
    System.out.println("In view: " + q + " " + r);
  }
}