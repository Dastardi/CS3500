import model.Coordinate;
import model.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CoordinateTests {
    private Coordinate coord1;
    private Coordinate coord2;
    private Coordinate coord3;
    private Tile tile;

    @Before
    public void init() {
        this.coord1 = new Coordinate (0, 0);
        this.coord2 = new Coordinate(0, 0);
        this.coord3 = new Coordinate(0, 1);
        this.tile = new Tile(coord1.q, coord1.r);
    }

    @Test
    public void testCoordinateEquals() {
        Assert.assertTrue(coord1.equals(coord2));
        Assert.assertTrue(coord2.equals(coord1));
        Assert.assertFalse(coord3.equals(coord1));
        Assert.assertFalse(coord1.equals(coord3));
        Assert.assertFalse(coord1.equals(tile));
    }

    @Test
    public void testCoordinateHashCode() {
        Assert.assertTrue(this.coord1.hashCode() == this.coord2.hashCode());
        Assert.assertTrue(this.coord1.hashCode() != this.coord3.hashCode());
        Assert.assertTrue(this.coord1.hashCode() != this.tile.hashCode());
    }
}
