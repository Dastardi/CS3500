import model.BasicReversi;
import model.ReversiModel;
import view.gui.ReversiFrame;

public final class Reversi {
  public static void main(String[] args) {
    ReversiModel model = new BasicReversi(4);
    ReversiFrame view = new ReversiFrame(model);
    view.setVisible(true);
    //TODO doesn't like this because ViewFrame doesn't have anything to do with JFrame
    // but works fine as ReversiFrame
  }
}