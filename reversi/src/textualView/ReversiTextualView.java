package textualView;

import model.Coordinate;
import model.PlayerColor;

import model.ReversiModel;
import model.Tile;

import java.io.IOException;


public class ReversiTextualView implements TextualView {
  private final ReversiModel model;
  private final Appendable appendable;

  public ReversiTextualView(ReversiModel model) {
    this.model = model;
    this.appendable = new StringBuilder();
  }

  public ReversiTextualView(ReversiModel model, Appendable appendable) {
    this.model = model;
    this.appendable = appendable;
  }

  public void render() throws IOException {
    this.appendable.append(this.toString());
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    int boardSize = this.model.getBoardSize();

    for (int r = 0; r < boardSize; r++) {
      //get number of leading spaces
      int spaces = Math.abs(boardSize / 2 - r);
      stringBuilder.append(" ".repeat(spaces));

      for (int q = 0; q < boardSize; q++) {
        //add each existing tile in the row
        Tile tile = this.model.getTileAt(new Coordinate(q, r));
        if (tile != null) {
          if (tile.getContents() == PlayerColor.BLACK) {
            stringBuilder.append("X ");
          } else if (tile.getContents() == PlayerColor.WHITE) {
            stringBuilder.append("O ");
          } else {
            stringBuilder.append("_ ");
          }
        }
      }
      stringBuilder.setLength(stringBuilder.length() - 1);
      stringBuilder.append("\n");
    }
    return stringBuilder.toString();
  }
}
