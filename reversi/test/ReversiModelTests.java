import model.BasicReversi;
import model.Coordinate;
import model.PlayerColor;
import model.ReversiModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the methods of the square Reversi model.
 */
public class ReversiModelTests {
  ReversiModel model;
  ReversiModel smallModel;
  ReversiModel bigModel;

  @Before
  public void init() {
    this.model = new BasicReversi(8);
    this.smallModel = new BasicReversi(4);
    this.bigModel = new BasicReversi(10);
  }

  //constructor tests
  @Test
  public void testInvalidSideLengths() {
    Assert.assertThrows(IllegalArgumentException.class, () -> new BasicReversi(-1));
    Assert.assertThrows(IllegalArgumentException.class, () -> new BasicReversi(0));
    Assert.assertThrows(IllegalArgumentException.class, () -> new BasicReversi(3));
  }

  //move() tests
  @Test
  public void testMovePlacesTiles() {
    this.model.move(new Coordinate(5, 3));
    Assert.assertEquals(PlayerColor.BLACK, model.getTileAt(new Coordinate(5, 3)).getContents());
    this.model.move(new Coordinate(3, 2));
    Assert.assertEquals(PlayerColor.WHITE, model.getTileAt(new Coordinate(3, 2)).getContents());
    this.model.move(new Coordinate(2, 4));
    Assert.assertEquals(PlayerColor.BLACK, model.getTileAt(new Coordinate(2, 4)).getContents());
  }

  @Test
  public void testIllegalMoveInCornerThrowsState() {
    Assert.assertThrows("Invalid move.",
        IllegalStateException.class, () -> this.model.move(new Coordinate(7, 0)));
  }

  @Test
  public void testMoveOntoDiscThrowsState() {
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.move(new Coordinate(4, 4)));
    Assert.assertThrows(IllegalStateException.class,
        () -> this.model.move(new Coordinate(3, 3)));
  }

  @Test
  public void testMoveOutsideBoardThrowsArgument() {
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.move(new Coordinate(-1, 0)));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> this.model.move(new Coordinate(10, 10)));
  }

  @Test
  public void testMoveFlipsTiles() {
    Assert.assertNull(this.model.getTileAt(new Coordinate(5, 3)).getContents());
    //this is the first move, so this will be a black disc
    this.model.move(new Coordinate(5, 3));
    Assert.assertEquals(PlayerColor.BLACK,
        this.model.getTileAt(new Coordinate(5, 3)).getContents());
    Assert.assertEquals(PlayerColor.BLACK,
        this.model.getTileAt(new Coordinate(4, 3)).getContents());
    Assert.assertEquals(PlayerColor.BLACK,
        this.model.getTileAt(new Coordinate(3, 3)).getContents());
  }

  @Test
  public void testMoveThrowsIfGameOver() {
    this.model.pass();
    this.model.pass();
    Assert.assertTrue(model.isGameOver());
    Assert.assertThrows(IllegalStateException.class, () -> this.model.move(new Coordinate(5, 3)));
  }

  @Test
  public void testMoveOnlyFlipsFirstSetOfTiles() {
    this.model.getTileAt(new Coordinate(5, 4)).placeDisc(PlayerColor.WHITE);
    this.model.getTileAt(new Coordinate(6, 4)).placeDisc(PlayerColor.BLACK);
    //make a valid move as black to a tile that has a row that goes white, black, white, black
    //the first white tile should flip but the next white tile in the row should not
    //first check that the player that's about to move is still black
    Assert.assertEquals(PlayerColor.BLACK, this.model.getCurrentPlayer());
    this.model.move(new Coordinate(2, 4));
    Assert.assertEquals(PlayerColor.BLACK,
        this.model.getTileAt(new Coordinate(2, 4)).getContents());
    Assert.assertEquals(PlayerColor.BLACK,
        this.model.getTileAt(new Coordinate(3, 4)).getContents());
    Assert.assertEquals(PlayerColor.BLACK,
        this.model.getTileAt(new Coordinate(4, 4)).getContents());
    Assert.assertEquals(PlayerColor.WHITE,
        this.model.getTileAt(new Coordinate(5, 4)).getContents());
    Assert.assertEquals(PlayerColor.BLACK,
        this.model.getTileAt(new Coordinate(6, 4)).getContents());
  }

  //pass() tests
  @Test
  public void testPass() {
    Assert.assertEquals(PlayerColor.BLACK, this.model.getCurrentPlayer());
    this.model.pass();
    Assert.assertEquals(PlayerColor.WHITE, this.model.getCurrentPlayer());
    this.model.move(new Coordinate(5, 4));
    Assert.assertEquals(PlayerColor.BLACK, this.model.getCurrentPlayer());
    this.model.pass();
    Assert.assertEquals(PlayerColor.WHITE, this.model.getCurrentPlayer());
    this.model.pass();
    Assert.assertThrows(IllegalStateException.class, () -> this.model.getCurrentPlayer());
  }

  @Test
  public void testPassThrowsIfGameOver() {
    this.model.pass();
    this.model.pass();
    Assert.assertTrue(model.isGameOver());
    Assert.assertThrows(IllegalStateException.class, () -> this.model.pass());
  }

  //getCurrentPlayer() tests
  @Test
  public void testGetCurrentPlayerSimple() {
    Assert.assertEquals(PlayerColor.BLACK, this.model.getCurrentPlayer());
    this.model.pass();
    Assert.assertEquals(PlayerColor.WHITE, this.model.getCurrentPlayer());
  }

  @Test
  public void testGetCurrentPlayerThrowsExceptionAfterGameEnds() {
    this.model.pass();
    this.model.pass();
    Assert.assertTrue(this.model.isGameOver());
    Assert.assertThrows(IllegalStateException.class, () -> this.model.getCurrentPlayer());
  }

  //isGameOver() tests
  @Test
  public void testIsGameOverPassing() {
    Assert.assertFalse(this.model.isGameOver());
    this.model.pass();
    Assert.assertFalse(this.model.isGameOver());
    this.model.pass();
    Assert.assertTrue(this.model.isGameOver());
  }

  //getCurrentWinner() tests
  @Test
  public void testGetCurrentWinnerAtStart() {
    //when the game starts, black and white are tied, which should result in a null value
    Assert.assertEquals(2, this.model.getCurrentWinner());
  }

  @Test
  public void testGetCurrentWinnerAfterOneMove() {
    Assert.assertEquals(2, this.model.getCurrentWinner());
    this.model.move(new Coordinate(5, 3));
    Assert.assertEquals(0, this.model.getCurrentWinner());
  }

  @Test
  public void testGetCurrentWinnerWhenGameOver() {
    this.model.move(new Coordinate(4, 2));
    this.model.pass();
    this.model.pass();
    Assert.assertEquals(0, this.model.getCurrentWinner());
  }

  @Test
  public void testGetCurrentWinnerMultipleMoves() {
    this.model.getTileAt(new Coordinate(5, 4)).placeDisc(PlayerColor.WHITE);
    Assert.assertEquals(1, this.model.getCurrentWinner());
    this.model.getTileAt(new Coordinate(4, 5)).placeDisc(PlayerColor.BLACK);
    this.model.getTileAt(new Coordinate(4, 6)).placeDisc(PlayerColor.BLACK);
    Assert.assertEquals(0, this.model.getCurrentWinner());
    this.model.getTileAt(new Coordinate(2, 4)).placeDisc(PlayerColor.WHITE);
    Assert.assertEquals(2, this.model.getCurrentWinner());
    this.model.getTileAt(new Coordinate(1, 4)).placeDisc(PlayerColor.WHITE);
    Assert.assertEquals(1, this.model.getCurrentWinner());
  }

  //getPlayerScore() tests
  @Test
  public void testStartOfGameScore() {
    //score for both players should be 2 when the game is first started
    //because it starts with both players having 2 discs automatically placed
    Assert.assertEquals(2, this.model.getPlayerScore(PlayerColor.BLACK));
    Assert.assertEquals(2, this.model.getPlayerScore(PlayerColor.WHITE));
  }

  @Test
  public void testGetScoreAfterSomeMoves() {
    this.model.move(new Coordinate(5, 3));
    Assert.assertEquals(4, this.model.getPlayerScore(PlayerColor.BLACK));
    Assert.assertEquals(1, this.model.getPlayerScore(PlayerColor.WHITE));
    this.model.move(new Coordinate(3, 2));
    Assert.assertEquals(3, this.model.getPlayerScore(PlayerColor.BLACK));
    Assert.assertEquals(3, this.model.getPlayerScore(PlayerColor.WHITE));
  }

  //getTileAt() test

  @Test
  public void testGetTileAtThrows() {
    Assert.assertThrows("Coordinate is invalid.", IllegalArgumentException.class,
        () -> this.model.getTileAt(new Coordinate(0, 8)));
    Assert.assertThrows("Coordinate is invalid.", IllegalArgumentException.class,
        () -> this.model.getTileAt(new Coordinate(8, 0)));
  }

  @Test
  public void testTileAlignmentTilesExistButEmpty() {
    Assert.assertNull(this.model.getTileAt(new Coordinate(0, 7)).getContents());
    Assert.assertNull(this.model.getTileAt(new Coordinate(7, 0)).getContents());
  }

  @Test
  public void testTileContentsSimple() {
    Assert.assertEquals(PlayerColor.BLACK,
        this.model.getTileAt(new Coordinate(3, 3)).getContents());
    Assert.assertEquals(PlayerColor.WHITE,
        this.model.getTileAt(new Coordinate(4, 3)).getContents());
  }

  //getBoardSize tests
  @Test
  public void testBoardSize() {
    Assert.assertEquals(8, model.getBoardSize());
    Assert.assertEquals(4, smallModel.getBoardSize());
    //Assert.assertEquals(5, threeModel.getBoardSize());
    Assert.assertEquals(10, bigModel.getBoardSize());
  }

  @Test
  public void testBoardSizeLegalAfterGameOver() {
    Assert.assertEquals(8, this.model.getBoardSize());
    this.model.pass();
    this.model.pass();
    Assert.assertTrue(this.model.isGameOver());
    Assert.assertEquals(8, this.model.getBoardSize());
  }

  //variable model size tests
  @Test
  public void testSizeOfSmallModel() {
    Assert.assertNotNull(this.smallModel.getTileAt(new Coordinate(0, 3)));
    Assert.assertNotNull(this.smallModel.getTileAt(new Coordinate(3, 0)));
    Assert.assertThrows("Coordinate is invalid.", IllegalArgumentException.class,
        () -> this.smallModel.getTileAt(new Coordinate(0, 4)));
    Assert.assertThrows("Coordinate is invalid.", IllegalArgumentException.class,
        () -> this.smallModel.getTileAt(new Coordinate(4, 0)));
  }

  @Test
  public void testSmallModelPlacementWorks() {
    Assert.assertEquals(PlayerColor.BLACK,
        this.smallModel.getTileAt(new Coordinate(2, 2)).getContents());
  }

  @Test
  public void testSizeOfBigModel() {
    Assert.assertNotNull(this.bigModel.getTileAt(new Coordinate(0, 9)));
    Assert.assertNotNull(this.bigModel.getTileAt(new Coordinate(9, 0)));
    Assert.assertThrows("Coordinate is invalid.", IllegalArgumentException.class,
        () -> this.bigModel.getTileAt(new Coordinate(0, 10)));
    Assert.assertThrows("Coordinate is invalid.", IllegalArgumentException.class,
        () -> this.bigModel.getTileAt(new Coordinate(10, 0)));
  }

  @Test
  public void testBigModelPlacementWorks() {
    Assert.assertEquals(PlayerColor.BLACK,
        bigModel.getTileAt(new Coordinate(5, 5)).getContents());
  }
}