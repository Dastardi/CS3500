import model.BasicReversi;
import model.Coordinate;
import model.PlayerColor;
import model.ReversiModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for the model for a game of Reversi.
 * Holds example tests to illustrate the main functionality of the model.
 */
public class ExampleModelTests {
    ReversiModel model;

    @Before
    public void init() {
        this.model = new BasicReversi();
    }

    @Test
    public void testMakeAMove() {
        this.model.move(new Coordinate(4, 7));
        Assert.assertEquals(PlayerColor.BLACK, model.getTileAt(new Coordinate(4, 7)).getContents());
    }

    @Test
    public void testMoveOntoDiscThrowsState() {
        Assert.assertThrows(IllegalStateException.class,
                () -> this.model.move(new Coordinate(4, 5)));
    }

    @Test
    public void testMoveFlipsTiles() {
        Assert.assertNull(this.model.getTileAt(new Coordinate(4, 7)).getContents());
        this.model.move(new Coordinate(4, 7));
        Assert.assertEquals(PlayerColor.BLACK,
                this.model.getTileAt(new Coordinate(4, 7)).getContents());
        Assert.assertEquals(PlayerColor.BLACK,
                this.model.getTileAt(new Coordinate(5, 6)).getContents());
        Assert.assertEquals(PlayerColor.BLACK,
                this.model.getTileAt(new Coordinate(6, 5)).getContents());
    }

    @Test
    public void testPassSkipsCurrentPlayer() {
        Assert.assertEquals(PlayerColor.BLACK, this.model.getCurrentPlayer());
        this.model.pass();
        Assert.assertEquals(PlayerColor.WHITE, this.model.getCurrentPlayer());
    }

    @Test
    public void testGetPlayerScore() {
        Assert.assertEquals(3, model.getPlayerScore(PlayerColor.BLACK));
        Assert.assertEquals(3, model.getPlayerScore(PlayerColor.WHITE));
        model.move(new Coordinate(4, 7));
        Assert.assertEquals(5, model.getPlayerScore(PlayerColor.BLACK));
        Assert.assertEquals(2, model.getPlayerScore(PlayerColor.WHITE));
    }

    @Test
    public void testTwoPassesEndsGame() {
        Assert.assertFalse(model.isGameOver());
        model.pass();
        model.pass();
        Assert.assertTrue(model.isGameOver());
    }
}