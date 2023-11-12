package view.text;

import model.Coordinate;
import model.PlayerColor;

import model.ReadOnlyReversiModel;
import model.Tile;

import java.io.IOException;

/**
 * A textual representation, via String or Appendable, of a Reversi game.
 * The easiest way to create a textual view of a game state is to call
 * toString() on the view, given an existing model.
 * The render method will be used in coordination
 * with the controller to make calls from the command line.
 */
public class ReversiTextualView implements TextualView {
  private final ReadOnlyReversiModel model;
  private final Appendable appendable;

  /**
   * constructs a textual view from a model, creating its own appendable.
   * @param model the model to be displayed.
   */
  public ReversiTextualView(ReadOnlyReversiModel model) {
    this.model = model;
    this.appendable = new StringBuilder();
  }

  /**
   * constructs a textual view from a model and a given appendable.
   * @param model the model to be displayed.
   * @param appendable the appendable to write to.
   */
  public ReversiTextualView(ReadOnlyReversiModel model, Appendable appendable) {
    this.model = model;
    this.appendable = appendable;
  }

  @Override
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