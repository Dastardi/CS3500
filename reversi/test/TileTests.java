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
  public void testGetColor() {
    this.tile1.placeDisc(PlayerColor.BLACK);
    Assert.assertEquals(this.tile1.getColor(), PlayerColor.BLACK);
    this.tile2.placeDisc(PlayerColor.WHITE);
    Assert.assertEquals(this.tile2.getColor(), PlayerColor.WHITE);
  }

  @Test
  public void testGetColorThrows() {
    this.tile1.placeDisc(PlayerColor.BLACK);
    Assert.assertEquals(this.tile1.getColor(), PlayerColor.BLACK);
    Assert.assertThrows(IllegalStateException.class, () -> this.tile2.getColor());
    Assert.assertThrows(IllegalStateException.class, () -> this.tile3.getColor());
  }

  @Test
  public void testFlipSimple() {
    this.tile1.placeDisc(PlayerColor.BLACK);
    Assert.assertEquals(this.tile1.getColor(), PlayerColor.BLACK);
    this.tile1.flip(PlayerColor.WHITE);
    Assert.assertEquals(this.tile1.getColor(), PlayerColor.WHITE);
    this.tile1.flip(PlayerColor.WHITE);
    Assert.assertEquals(PlayerColor.WHITE, this.tile1.getColor());
  }

  @Test
  public void testFlipOnEmptyTile() {
    Assert.assertThrows(IllegalStateException.class, () -> this.tile1.flip(PlayerColor.WHITE));
  }

  @Test
  public void testPlaceDiscSimple() {
    Assert.assertThrows(IllegalStateException.class, () -> this.tile1.getColor());
    this.tile1.placeDisc(PlayerColor.WHITE);
    Assert.assertEquals(PlayerColor.WHITE, this.tile1.getColor());
  }

  @Test
  public void testPlaceDiscOnOccupiedTile() {
    this.tile1.placeDisc(PlayerColor.BLACK);
    Assert.assertThrows(IllegalStateException.class, () -> this.tile1.placeDisc(PlayerColor.BLACK));
  }
}