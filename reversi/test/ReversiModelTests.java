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
  ReversiModel smallModel;

  @Before
  public void init() {
    this.model = new BasicReversi();
    this.smallModel = new BasicReversi(3);
  }

  //move() tests
  @Test
  public void testSomeLegalMoves() {
    this.model.move(new Coordinate(5, 5));
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
  public void testIllegalMoveInNarniaThrowsState() {
    Assert.assertThrows("Invalid move.",
            IllegalStateException.class, () -> this.model.move(new Coordinate (10, 0)));
  }

  @Test
  public void testMoveFlipsTiles() {
    model.move(new Coordinate(4, 7));
    Assert.assertEquals(PlayerColor.BLACK, model.getTileAt(new Coordinate(4, 7)).getContents());
    Assert.assertEquals(PlayerColor.BLACK, model.getTileAt(new Coordinate(5, 6)).getContents());
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
}
