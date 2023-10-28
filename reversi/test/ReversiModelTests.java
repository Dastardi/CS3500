import model.BasicReversi;
import model.PlayerColor;
import model.ReversiModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReversiModelTests {
  ReversiModel modelEmptyBoard;

  @Before
  public void init() {
    this.modelEmptyBoard = new BasicReversi();
  }

  //isGameOver() tests
  @Test
  public void testSomething() {
    Assert.assertEquals(5, 5);
  }

  //getWinner() tests
  @Test
  public void testGetWinnerThrowing() {

  }



  //getPlayerScore() tests
  @Test
  public void testZeroScoreEmptyBoard() {
    Assert.assertEquals(this.modelEmptyBoard.getPlayerScore(PlayerColor.BLACK), 0);
    Assert.assertEquals(this.modelEmptyBoard.getPlayerScore(PlayerColor.WHITE), 0);
  }

  @Test
  public void test() {

  }

  //isTileEmpty() tests




  //getTileContentsTests()
}
