import model.BasicReversi;
import model.ReversiModel;
import model.Coordinate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import view.ReversiTextualView;

import java.io.IOException;

/**
 * Testing class for the implementation of a text-based view for a game of Reversi.
 */
public class TextualViewTests {
  ReversiModel model;
  ReversiModel smallModel;
  ReversiTextualView view;
  ReversiTextualView smallView;

  @Before
  public void init() {
    this.model = new BasicReversi();
    this.smallModel = new BasicReversi(3);
    this.view = new ReversiTextualView(this.model);
    this.smallView = new ReversiTextualView(this.smallModel);
  }

  @Test
  public void testBasicTextualView() {
    Assert.assertEquals("     _ _ _ _ _ _\n" +
        "    _ _ _ _ _ _ _\n" +
        "   _ _ _ _ _ _ _ _\n" +
        "  _ _ _ _ _ _ _ _ _\n" +
        " _ _ _ _ X O _ _ _ _\n" +
        "_ _ _ _ O _ X _ _ _ _\n" +
        " _ _ _ _ X O _ _ _ _\n" +
        "  _ _ _ _ _ _ _ _ _\n" +
        "   _ _ _ _ _ _ _ _\n" +
        "    _ _ _ _ _ _ _\n" +
        "     _ _ _ _ _ _\n", view.toString());
  }

  @Test
  public void testSingleMove() {
    model.move(new Coordinate(4, 7));
    Assert.assertEquals("     _ _ _ _ _ _\n" +
        "    _ _ _ _ _ _ _\n" +
        "   _ _ _ _ _ _ _ _\n" +
        "  _ _ _ _ _ _ _ _ _\n" +
        " _ _ _ _ X O _ _ _ _\n" +
        "_ _ _ _ O _ X _ _ _ _\n" +
        " _ _ _ _ X X _ _ _ _\n" +
        "  _ _ _ _ X _ _ _ _\n" +
        "   _ _ _ _ _ _ _ _\n" +
        "    _ _ _ _ _ _ _\n" +
        "     _ _ _ _ _ _\n", view.toString());
  }

  @Test
  public void testSmallBoard() {
    Assert.assertEquals("  _ _ _\n" +
        " _ X O _\n" +
        "_ O _ X _\n" +
        " _ X O _\n" +
        "  _ _ _\n", smallView.toString());
  }


  @Test
  public void testMoveOnSmallBoard() {
    smallModel.move(new Coordinate(1,4));
    Assert.assertEquals("  _ _ _\n" +
        " _ X O _\n" +
        "_ O _ X _\n" +
        " _ X X _\n" +
        "  _ X _\n", smallView.toString());
  }

  @Test
  public void testWithAppendable() {
    StringBuilder stringBuilder = new StringBuilder();
    ReversiTextualView customAppendableView = new ReversiTextualView(model, stringBuilder);
    try {
      customAppendableView.render();
    } catch(IOException e) {
      throw new IllegalStateException("IO");
    }
    Assert.assertEquals("     _ _ _ _ _ _\n" +
        "    _ _ _ _ _ _ _\n" +
        "   _ _ _ _ _ _ _ _\n" +
        "  _ _ _ _ _ _ _ _ _\n" +
        " _ _ _ _ X O _ _ _ _\n" +
        "_ _ _ _ O _ X _ _ _ _\n" +
        " _ _ _ _ X O _ _ _ _\n" +
        "  _ _ _ _ _ _ _ _ _\n" +
        "   _ _ _ _ _ _ _ _\n" +
        "    _ _ _ _ _ _ _\n" +
        "     _ _ _ _ _ _\n", stringBuilder.toString());
  }

  @Test
  public void testFullBoard() {
    smallModel.move(new Coordinate(1, 4));
    smallModel.move(new Coordinate(3, 0));
    smallModel.move(new Coordinate(1, 1));
    smallModel.move(new Coordinate(0, 3));
    smallModel.move(new Coordinate(4, 1));
    smallModel.move(new Coordinate(3, 3));
  }
}
