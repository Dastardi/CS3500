import model.Coordinate;
import model.PlayerColor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.Tile;

/**
 * Tests the behavior of a Tile object for a game of Reversi.
 */
public class TileTests {
  Tile tile1;
  Tile tile2;

  @Before
  public void init() {
    this.tile1 = new Tile(5, 5);
    this.tile2 = new Tile(4, 6);
  }

  //constructor tests
  @Test
  public void testConstructorThrowsOnNegativeValues() {
    Assert.assertThrows(IllegalArgumentException.class, () -> new Tile(-10, -2));
  }

  //getContents tests
  @Test
  public void testGetContents() {
    Assert.assertNull(this.tile1.getContents());
    this.tile2.placeDisc(PlayerColor.BLACK);
    Assert.assertEquals(PlayerColor.BLACK, this.tile2.getContents());
  }

  //flip tests
  @Test
  public void testFlipSimple() {
    this.tile1.placeDisc(PlayerColor.BLACK);
    Assert.assertEquals(PlayerColor.BLACK, this.tile1.getContents());
    this.tile1.flip();
    Assert.assertEquals(PlayerColor.WHITE, this.tile1.getContents());
    this.tile1.flip();
    Assert.assertEquals(PlayerColor.BLACK, this.tile1.getContents());
  }

  @Test
  public void testMultipleFlips() {
    this.tile1.placeDisc(PlayerColor.WHITE);
    Assert.assertEquals(PlayerColor.WHITE, this.tile1.getContents());
    this.tile1.flip();
    this.tile1.flip();
    this.tile1.flip();
    Assert.assertEquals(PlayerColor.BLACK, this.tile1.getContents());
  }

  @Test
  public void testFlipOnEmptyTile() {
    Assert.assertThrows(IllegalStateException.class, () -> this.tile1.flip());
  }

  //placeDisc tests
  @Test
  public void testPlaceDiscSimple() {
    Assert.assertNull(this.tile1.getContents());
    this.tile1.placeDisc(PlayerColor.WHITE);
    Assert.assertEquals(PlayerColor.WHITE, this.tile1.getContents());
  }

  @Test
  public void testPlaceDiscOnOccupiedTileThrowsException() {
    this.tile1.placeDisc(PlayerColor.BLACK);
    Assert.assertThrows(IllegalStateException.class,
        () -> this.tile1.placeDisc(PlayerColor.BLACK));
  }

  //getCoordinate tests
  @Test
  public void testGetCoordinateSimple() {
    Assert.assertEquals(new Coordinate(5, 5), this.tile1.getCoordinate());
  }

  //isEmpty tests
  @Test
  public void testIsEmpty() {
    Assert.assertTrue(tile1.isEmpty());
    tile2.placeDisc(PlayerColor.WHITE);
    Assert.assertFalse(tile2.isEmpty());
  }
}