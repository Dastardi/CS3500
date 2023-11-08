package view.text;

import java.io.IOException;

/**
 * Represents a flexible view for the Reversi textual representation.
 */
public interface TextualView {
  /**
   * Writes the toString method to an appendable.
   * @throws IOException if writing throws an IOException.
   */
  public void render() throws IOException;
}
