import model.BasicReversi;
import model.PlayerColor;
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
  ReversiModel fourModel;
  ReversiTextualView fourView;
  ReversiTextualView view;
  ReversiTextualView smallView;

  @Before
  public void init() {
    this.model = new BasicReversi();
    this.smallModel = new BasicReversi(3);
    this.fourModel = new BasicReversi(4);
    this.view = new ReversiTextualView(this.model);
    this.smallView = new ReversiTextualView(this.smallModel);
    this.fourView = new ReversiTextualView(this.fourModel);
  }

  @Test
  public void testBasicTextualViewAtStart() {
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
  public void testSomeMoves() {
    this.model.move(new Coordinate(4, 7));
    this.model.move(new Coordinate(6, 3));
    this.model.move(new Coordinate(6, 2));
    Assert.assertEquals("     _ _ _ _ _ _\n" +
        "    _ _ _ _ _ _ _\n" +
        "   _ _ _ X _ _ _ _\n" +
        "  _ _ _ _ X _ _ _ _\n" +
        " _ _ _ _ O X _ _ _ _\n" +
        "_ _ _ _ O _ X _ _ _ _\n" +
        " _ _ _ _ X X _ _ _ _\n" +
        "  _ _ _ _ X _ _ _ _\n" +
        "   _ _ _ _ _ _ _ _\n" +
        "    _ _ _ _ _ _ _\n" +
        "     _ _ _ _ _ _\n", view.toString());
  }

  @Test
  public void testSmallBoardAtStart() {
    Assert.assertEquals("  _ _ _\n" +
        " _ X O _\n" +
        "_ O _ X _\n" +
        " _ X O _\n" +
        "  _ _ _\n", smallView.toString());

  }

  @Test
  public void testMoveOnSmallBoard() {
    smallModel.move(new Coordinate(1,1));
    Assert.assertEquals("  _ _ _\n" +
        " X X O _\n" +
        "_ X _ X _\n" +
        " _ X O _\n" +
        "  _ _ _\n", smallView.toString());
  }

  @Test
  public void testFullBoardRigged() {
    this.smallModel.getTileAt(new Coordinate(2, 0)).placeDisc(PlayerColor.BLACK);
    this.smallModel.getTileAt(new Coordinate(3, 0)).placeDisc(PlayerColor.BLACK);
    this.smallModel.getTileAt(new Coordinate(4, 0)).placeDisc(PlayerColor.BLACK);
    this.smallModel.getTileAt(new Coordinate(1, 1)).placeDisc(PlayerColor.BLACK);
    this.smallModel.getTileAt(new Coordinate(4, 1)).placeDisc(PlayerColor.BLACK);
    this.smallModel.getTileAt(new Coordinate(0, 2)).placeDisc(PlayerColor.BLACK);
    this.smallModel.getTileAt(new Coordinate(2, 2)).placeDisc(PlayerColor.BLACK);
    this.smallModel.getTileAt(new Coordinate(4, 2)).placeDisc(PlayerColor.BLACK);
    this.smallModel.getTileAt(new Coordinate(0, 3)).placeDisc(PlayerColor.BLACK);
    this.smallModel.getTileAt(new Coordinate(3, 3)).placeDisc(PlayerColor.BLACK);
    this.smallModel.getTileAt(new Coordinate(0, 4)).placeDisc(PlayerColor.BLACK);
    this.smallModel.getTileAt(new Coordinate(1, 4)).placeDisc(PlayerColor.BLACK);
    this.smallModel.getTileAt(new Coordinate(2, 4)).placeDisc(PlayerColor.BLACK);
    Assert.assertEquals("  X X X\n" +
            " X X O X\n" +
            "X O X X X\n" +
            " X X O X\n" +
            "  X X X\n", smallView.toString());
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
}