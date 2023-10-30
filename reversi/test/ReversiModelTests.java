import model.BasicReversi;
import model.Coordinate;
import model.PlayerColor;
import model.ReversiModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the methods of the basic reversi model.
 */
public class ReversiModelTests {
  ReversiModel model;
  ReversiModel tinyModel;
  ReversiModel smallModel;
  ReversiModel bigModel;

  @Before
  public void init() {
    this.model = new BasicReversi(6);
    this.tinyModel = new BasicReversi(2);
    this.smallModel = new BasicReversi(4);
    this.bigModel = new BasicReversi(10);

  }

  //move() tests
  @Test
  public void testSomeLegalMoves() {
    this.model.move(new Coordinate(4, 7));
  }

  @Test
  public void testIllegalMoveToCenterAfterStart() {
    Assert.assertThrows(IllegalStateException.class, () -> this.model.move(new Coordinate(5, 5)));
  }

  @Test
  public void testMoveOntoDiscThrowsState() {
    Assert.assertThrows(IllegalStateException.class, () -> this.model.move(new Coordinate(4, 5)));
    Assert.assertThrows(IllegalStateException.class, () -> this.model.move(new Coordinate(5, 6)));
  }

  @Test
  public void testMoveOutsideBoardThrowsArgument() {
    Assert.assertThrows(IllegalArgumentException.class, () -> this.model.move(new Coordinate(20, 20)));
  }

  @Test
  public void testIllegalMoveInCornerThrowsState() {
    Assert.assertThrows("Invalid move.",
        IllegalStateException.class, () -> this.model.move(new Coordinate(10, 0)));
  }

  @Test
  public void testMoveFlipsTiles() {
    Assert.assertNull(this.model.getTileAt(new Coordinate(4, 7)).getContents());
    //this is the first move, so this will be a black disc
    model.move(new Coordinate(4, 7));
    Assert.assertEquals(PlayerColor.BLACK, model.getTileAt(new Coordinate(4, 7)).getContents());
    Assert.assertEquals(PlayerColor.BLACK, model.getTileAt(new Coordinate(5, 6)).getContents());
    Assert.assertEquals(PlayerColor.BLACK, model.getTileAt(new Coordinate(6, 5)).getContents());
  }

  //pass() tests


  //isGameOver() tests
  @Test
  public void testIsGameOver() {

  }

  //getWinner() tests
  @Test
  public void testGetWinnerThrows() {

  }


  //getPlayerScore() tests
  @Test
  public void testStartOfGameScore() {
    //score for both players should be 3 when the game is first started
    //because it starts with both players having 3 discs automatically placed
    Assert.assertEquals(this.model.getPlayerScore(PlayerColor.BLACK), 3);
    Assert.assertEquals(this.model.getPlayerScore(PlayerColor.WHITE), 3);
  }

  @Test
  public void testGetScoreOnDevelopedBoard() {
    model.move(new Coordinate(4, 7));

  }

  //getTileAt() tests
  @Test
  public void testTileAlignmentNull() {
    Assert.assertNull(this.model.getTileAt(new Coordinate(0, 0)));
    Assert.assertNull(this.model.getTileAt(new Coordinate(10, 10)));
  }

  @Test
  public void testGetTileAtThrows() {
    Assert.assertThrows("Coordinate is invalid.", IllegalArgumentException.class,
        () -> this.model.getTileAt(new Coordinate(0, 11)));
    Assert.assertThrows("Coordinate is invalid.", IllegalArgumentException.class,
        () -> this.model.getTileAt(new Coordinate(11, 0)));
  }

  //getTileContentsTests()
  @Test
  public void testTileAlignmentTilesExistButEmpty() {
    Assert.assertNull(this.model.getTileAt(new Coordinate(0, 10)).getContents());
    Assert.assertNull(this.model.getTileAt(new Coordinate(10, 0)).getContents());
  }

  @Test
  public void testTileContentsSimple() {
    Assert.assertEquals(PlayerColor.BLACK, this.model.getTileAt(new Coordinate(6, 5)).getContents());
    Assert.assertEquals(PlayerColor.WHITE, this.model.getTileAt(new Coordinate(5, 6)).getContents());
  }

  @Test
  public void testSizeOfTinyModel() {
    Assert.assertNotNull(this.tinyModel.getTileAt(new Coordinate(0, 2)));
    Assert.assertNotNull(this.tinyModel.getTileAt(new Coordinate(2, 0)));
    Assert.assertNull(this.tinyModel.getTileAt(new Coordinate(0, 0)));
    Assert.assertNull(this.tinyModel.getTileAt(new Coordinate(2, 2)));
    Assert.assertThrows("Coordinate is invalid.", IllegalArgumentException.class,
        () -> this.tinyModel.getTileAt(new Coordinate(0, 3)));
    Assert.assertThrows("Coordinate is invalid.", IllegalArgumentException.class,
        () -> this.tinyModel.getTileAt(new Coordinate(3, 0)));
  }

  @Test
  public void testTinyModelPlacementWorks() {
    Assert.assertEquals(PlayerColor.BLACK, tinyModel.getTileAt(new Coordinate(2, 1)).getContents());
  }

  @Test
  public void testSizeOfSmallModel() {
    Assert.assertNotNull(this.smallModel.getTileAt(new Coordinate(0, 6)));
    Assert.assertNotNull(this.smallModel.getTileAt(new Coordinate(6, 0)));
    Assert.assertNull(this.smallModel.getTileAt(new Coordinate(0, 0)));
    Assert.assertNull(this.smallModel.getTileAt(new Coordinate(6, 6)));
    Assert.assertThrows("Coordinate is invalid.", IllegalArgumentException.class,
        () -> this.smallModel.getTileAt(new Coordinate(0, 7)));
    Assert.assertThrows("Coordinate is invalid.", IllegalArgumentException.class,
        () -> this.smallModel.getTileAt(new Coordinate(7, 0)));
  }

  @Test
  public void testSmallModelPlacementWorks() {
    Assert.assertEquals(PlayerColor.BLACK, smallModel.getTileAt(new Coordinate(4, 3)).getContents());
  }

  @Test
  public void testSizeOfBigModel() {
    Assert.assertNotNull(this.bigModel.getTileAt(new Coordinate(0, 18)));
    Assert.assertNotNull(this.bigModel.getTileAt(new Coordinate(18, 0)));
    Assert.assertNull(this.bigModel.getTileAt(new Coordinate(0, 0)));
    Assert.assertNull(this.bigModel.getTileAt(new Coordinate(18, 18)));
    Assert.assertThrows("Coordinate is invalid.", IllegalArgumentException.class,
        () -> this.bigModel.getTileAt(new Coordinate(0, 19)));
    Assert.assertThrows("Coordinate is invalid.", IllegalArgumentException.class,
        () -> this.bigModel.getTileAt(new Coordinate(19, 0)));
  }

  @Test
  public void testBigModelPlacementWorks() {
    Assert.assertEquals(PlayerColor.BLACK, bigModel.getTileAt(new Coordinate(10, 9)).getContents());
  }
}
