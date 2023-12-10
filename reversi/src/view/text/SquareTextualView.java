package view.text;

import java.io.IOException;

/**
 * Represents a flexible view for the Reversi textual representation.
 */
public interface SquareTextualView {
  /**
   * Writes the toString method to an appendable.
   * @throws IOException if writing throws an IOException.
   */
  void render() throws IOException;

  /**
   * Creates a string-based representation of the board of this view's model.
   * @return a string representation of the view's model.
   */
  String toString();
}
