import controller.EasyAIPlayer;
import controller.HumanPlayer;
import controller.Player;
import controller.ReversiController;

import model.BasicReversi;
import model.Coordinate;
import model.ReversiModel;

import view.gui.ReversiFrame;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ControllerTests {
  ReversiModel model;
  Player humanPlayer;
  Player AIPlayer;
  ReversiFrame view;
  ReversiController controller;

  @Before
  public void init() {
    model = new BasicReversi();
    humanPlayer = new HumanPlayer();
    AIPlayer = new EasyAIPlayer(model);
    view = new ReversiFrame(model);
    controller = new ReversiController(model, humanPlayer, view);
  }

  @Test
  public void testConstructorThrowing() {
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new ReversiController(null, humanPlayer, view));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new ReversiController(model, humanPlayer, null));
  }

  @Test
  public void testInvalidMoveMade() {
    Assert.assertEquals("It is not your turn :(",
        controller.moveMade(new Coordinate(0, 0)));
  }

  @Test
  public void testValidMoveMade() {
    Assert.assertEquals("valid",
        controller.moveMade(new Coordinate(0, 0)));
  }

  @Test
  public void testPassed() {

  }

  @Test
  public void testUpdateTurnEndsGame() {

  }

  @Test
  public void testUpdateTurnCallsCheckTurn() {

  }

  @Test
  public void testHumanCheckTurnReturns() {

  }

  @Test
  public void testAICheckTurnMoves() {

  }

  @Test
  public void testAICheckTurnPasses() {

  }

  @Test
  public void testHandleGameOver() {

  }
}