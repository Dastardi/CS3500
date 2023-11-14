import model.Coordinate;
import model.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Fpor testing the methods of the Coordinate enum.
 */
public class CoordinateTests {
  private Coordinate coord1;
  private Coordinate coord2;
  private Coordinate coord3;
  private Tile tile;

  @Before
  public void init() {
    this.coord1 = new Coordinate(0, 0);
    this.coord2 = new Coordinate(0, 0);
    this.coord3 = new Coordinate(0, 1);
    this.tile = new Tile(coord1.getQ(), coord1.getR());
  }

  @Test
  public void testConstructorThrowsOnNegativeValues() {
    Assert.assertThrows(IllegalArgumentException.class, () -> new Coordinate(-1, -1));
  }

  @Test
  public void testCoordinateEquals() {
    Assert.assertEquals(coord1, coord2);
    Assert.assertEquals(coord2, coord1);
    Assert.assertNotEquals(coord3, coord1);
    Assert.assertNotEquals(coord1, coord3);
  }

  @Test
  public void testCoordinateHashCode() {
    Assert.assertEquals(this.coord1.hashCode(), this.coord2.hashCode());
    Assert.assertNotEquals(this.coord1.hashCode(), this.coord3.hashCode());
    Assert.assertNotEquals(this.coord1.hashCode(), this.tile.hashCode());
  }

  @Test
  public void testCoordinateString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Coordinate 1: " + coord1);
    Assert.assertEquals("", sb.toString());
  }
}
