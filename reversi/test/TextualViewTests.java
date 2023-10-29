import model.BasicReversi;
import model.ReversiModel;
import model.Coordinate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import view.ReversiTextualView;

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
        "  _ _ _", smallView.toString());
  }

  /*
  @Test
  public void testFullSmallBoard() {
    Assert.assertEquals("  _ _ _\n" +
        " _ X O _\n" +
        "_ O _ X _\n" +
        " _ X X _\n" +
        "  _ X _", smallView.toString());
  }
   */
}
