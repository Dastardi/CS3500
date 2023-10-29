import model.BasicReversi;
import model.PlayerColor;
import model.ReversiModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReversiModelTests {
  ReversiModel modelStartingBoard;
  ReversiModel smallBoard;

  @Before
  public void init() {
    this.modelStartingBoard = new BasicReversi();
    this.smallBoard = new BasicReversi(3);
  }

  //move() tests




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
    Assert.assertEquals(this.modelStartingBoard.getPlayerScore(PlayerColor.BLACK), 3);
    Assert.assertEquals(this.modelStartingBoard.getPlayerScore(PlayerColor.WHITE), 3);
  }

  @Test
  public void testGetScoreOnNonEmptyBoard() {

  }

  @Test
  public void test() {

  }

  //isTileEmpty() tests




  //getTileContentsTests()
}
