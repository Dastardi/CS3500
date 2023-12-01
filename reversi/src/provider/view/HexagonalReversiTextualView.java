package provider.view;


import provider.model.HexPosn;
import provider.model.HexState;
import provider.model.ReadOnlyHexagonalReversi;

/**
 * Represents a method of viewing a game of Reversi via a String.
 */
public class HexagonalReversiTextualView implements TextualView {

  private final ReadOnlyHexagonalReversi model;

  /**
   * Constructor for a textual view of Reversi.
   *
   * @param model The model for the game of Reversi.
   */
  public HexagonalReversiTextualView(ReadOnlyHexagonalReversi model) {
    this.model = model;
  }

  /**
   * Return a string representation of the model.
   *
   * @return A string representation of the model.
   */
  @Override
  public String toString() {

    String output = "";

    int totalWidthAndHeight = model.getNumLayers() * 2 - 1;
    int lineCharacterWidth = totalWidthAndHeight * 2 - 1;
    int hexesInLayer = model.getNumLayers();

    for (int row = 0; row < totalWidthAndHeight; row += 1) {

      int spaceHexesTakeUp = hexesInLayer * 2 - 1;

      // odd number - odd number = even number, which is divisible by 2
      int totalWhitespaceEachSide = (lineCharacterWidth - spaceHexesTakeUp) / 2;

      // left whitespace
      for (int i = 0; i < totalWhitespaceEachSide; i += 1) {
        output += " ";
      }

      // add all hexes and space between
      for (int i = 0; i < hexesInLayer; i += 1) {

        // figure out our coordinates
        HexPosn qrs = CoordinateUtils.rowAndIndexToQRS(row, i, model.getNumLayers());

        HexState state = model.getHexState(qrs.q, qrs.r, qrs.s);

        String hexState;

        if (state == HexState.EMPTY) {
          hexState = "_";
        } else if (state == HexState.WHITE) {
          hexState = "0";
        } else {
          hexState = "X";
        }

        output += hexState + " ";
      }
      // remove last extra space
      output = output.substring(0, output.length() - 1);

      // right whitespace
      for (int i = 0; i < totalWhitespaceEachSide; i += 1) {
        output += " ";
      }

      // line break
      output += "\n";


      // either increment or decrement hexesInLayer
      // totalWidthAndHeight is odd number, when divided by 2 gives X.5, which floors to X
      if (row < totalWidthAndHeight / 2) {
        hexesInLayer += 1;
      } else {
        hexesInLayer -= 1;
      }
    }

    if (model.getNextTurn() == HexState.WHITE) {
      output += "White (0) to move.";
    } else {
      output += "Black (X) to move.";
    }

    return output;
  }
}
