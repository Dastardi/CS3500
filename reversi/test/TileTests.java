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
  Tile tile3;

  @Before
  public void init() {
    this.tile1 = new Tile(5, 5);
    this.tile2 = new Tile(4, 6);
    this.tile3 = new Tile(0, 8);
  }

  @Test
  public void testGetContents() {
    this.tile1.placeDisc(PlayerColor.BLACK);
    Assert.assertEquals(PlayerColor.BLACK, this.tile1.getContents());
    this.tile2.placeDisc(PlayerColor.WHITE);
    Assert.assertEquals(PlayerColor.WHITE, this.tile2.getContents());
    Assert.assertNull(this.tile3.getContents());
  }

  @Test
  public void testFlipSimple() {
    this.tile1.placeDisc(PlayerColor.BLACK);
    Assert.assertEquals(this.tile1.getContents(), PlayerColor.BLACK);
    this.tile1.flip(PlayerColor.WHITE);
    Assert.assertEquals(this.tile1.getContents(), PlayerColor.WHITE);
    this.tile1.flip(PlayerColor.WHITE);
    Assert.assertEquals(PlayerColor.WHITE, this.tile1.getContents());
  }

  @Test
  public void testFlipOnEmptyTile() {
    Assert.assertThrows(IllegalStateException.class, () -> this.tile1.flip(PlayerColor.WHITE));
  }

  @Test
  public void testPlaceDiscSimple() {
    Assert.assertNull(this.tile1.getContents());
    this.tile1.placeDisc(PlayerColor.WHITE);
    Assert.assertEquals(PlayerColor.WHITE, this.tile1.getContents());
  }

  @Test
  public void testPlaceDiscOnOccupiedTile() {
    this.tile1.placeDisc(PlayerColor.BLACK);
    Assert.assertThrows(IllegalStateException.class, () -> this.tile1.placeDisc(PlayerColor.BLACK));
  }

  @Test
  public void testGetCoordinateSimple() {
    Assert.assertEquals(new Coordinate(5, 5), this.tile1.getCoordinate());
  }
}