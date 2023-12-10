import model.BasicReversi;
import model.ReversiModel;
import model.Coordinate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import view.text.ReversiTextualView;

import java.io.IOException;

/**
 * Testing class for the implementation of a text-based view for a game of Reversi.
 */
public class TextualViewTests {
  ReversiModel model;
  ReversiModel smallModel;
  ReversiModel fullModel;
  ReversiTextualView fourView;
  ReversiTextualView view;
  ReversiTextualView smallView;

  @Before
  public void init() {
    this.model = new BasicReversi();
    this.smallModel = new BasicReversi(4);
    this.fullModel = new BasicReversi(8);
    this.view = new ReversiTextualView(this.model);
    this.smallView = new ReversiTextualView(this.smallModel);
    this.fourView = new ReversiTextualView(this.fullModel);
  }

  @Test
  public void testBasicTextualViewAtStart() {
    Assert.assertEquals("_ _ _ _ _ _ _ _\n" +
        "_ _ _ _ _ _ _ _\n" +
        "_ _ _ _ _ _ _ _\n" +
        "_ _ _ X O _ _ _\n" +
        "_ _ _ O X _ _ _\n" +
        "_ _ _ _ _ _ _ _\n" +
        "_ _ _ _ _ _ _ _\n" +
        "_ _ _ _ _ _ _ _\n", view.toString());
  }

  @Test
  public void testSomeMoves() {
    this.model.move(new Coordinate(5, 3));
    this.model.move(new Coordinate(5, 2));
    Assert.assertEquals("_ _ _ _ _ _ _ _\n" +
        "_ _ _ _ _ _ _ _\n" +
        "_ _ _ _ _ O _ _\n" +
        "_ _ _ X O X _ _\n" +
        "_ _ _ O X _ _ _\n" +
        "_ _ _ _ _ _ _ _\n" +
        "_ _ _ _ _ _ _ _\n" +
        "_ _ _ _ _ _ _ _\n", view.toString());
  }

  @Test
  public void testSmallBoardAtStart() {
    Assert.assertEquals("_ _ _ _\n" +
        "_ X O _\n" +
        "_ O X _\n" +
        "_ _ _ _\n", smallView.toString());

  }

  @Test
  public void testMoveOnSmallBoard() {
    smallModel.move(new Coordinate(1,3));
    Assert.assertEquals("_ _ _ _\n" +
        "_ X O _\n" +
        "_ X X _\n" +
        "_ X _ _\n", smallView.toString());
  }

  @Test
  public void testWithAppendable() {
    StringBuilder stringBuilder = new StringBuilder();
    ReversiTextualView customAppendableView = new ReversiTextualView(this.model, stringBuilder);
    try {
      customAppendableView.render();
    } catch (IOException e) {
      throw new IllegalStateException("IO exception thrown.");
    }
    Assert.assertEquals("_ _ _ _ _ _ _ _\n" +
        "_ _ _ _ _ _ _ _\n" +
        "_ _ _ _ _ _ _ _\n" +
        "_ _ _ X O _ _ _\n" +
        "_ _ _ O X _ _ _\n" +
        "_ _ _ _ _ _ _ _\n" +
        "_ _ _ _ _ _ _ _\n" +
        "_ _ _ _ _ _ _ _\n", stringBuilder.toString());
  }
}