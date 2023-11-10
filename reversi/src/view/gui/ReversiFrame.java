package view.gui;

import model.ReadOnlyReversiModel;

import javax.swing.*;
import java.awt.*;

public class ReversiFrame extends JFrame implements ViewFrame, PanelEventListener {
  private final ReversiPanel panel;

  public ReversiFrame(ReadOnlyReversiModel model) {
    int boardSize = model.getBoardSize();
    //TODO come back to this - HAD TO CHANGE TO 710 FROM 680 TO FIT THE BOARD
    setPreferredSize(new Dimension(760, 710));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.panel = new ReversiPanel(boardSize);
    add(this.panel);
    pack();
    setResizable(false);
  }

  @Override
  public void tileClicked(int q, int r) {
    System.out.println("In view: " + q + " " + r);
  }
}