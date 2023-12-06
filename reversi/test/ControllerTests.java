import controller.player.EasyAIPlayer;
import controller.player.HumanPlayer;
import controller.Player;
import controller.ReversiController;

import model.BasicReversi;
import model.Coordinate;
import model.PlayerColor;
import model.ReversiModel;

import view.gui.ReversiFrame;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for controller functionality.
 */
public class ControllerTests {
  ReversiModel model;
  Player playerHuman;
  Player playerAI;
  ReversiFrame view;
  ReversiFrame mockView;
  ReversiController controller;
  ReversiController controllerWithMockView;
  ReversiController mockController;
  StringBuilder log;

  @Before
  public void init() {
    this.log = new StringBuilder();
    this.model = new BasicReversi();
    this.playerHuman = new HumanPlayer();
    this.playerAI = new EasyAIPlayer(this.model);
    this.view = new ReversiFrame(this.model);
    this.controller = new ReversiController(this.model, this.playerHuman, this.view);
    this.mockView = new MockView(this.model, this.log);
    this.controllerWithMockView =
        new ReversiController(this.model, this.playerHuman, this.mockView);
    this.mockController =
        new MockReversiController(this.model, this.playerHuman, this.view, this.log);
  }

  @Test
  public void testConstructorThrowing() {
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new ReversiController(null, this.playerHuman, this.view));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new ReversiController(this.model, this.playerHuman, null));
  }

  @Test
  public void testInvalidTurn() {
    Assert.assertEquals("It is not your turn :(",
        this.controller.moveMade(new Coordinate(0, 0)));
  }

  @Test
  public void testInvalidMove() {
    this.model.pass();
    Assert.assertEquals("Invalid move.", this.controller.moveMade(new Coordinate(5, 6)));
  }

  @Test
  public void testValidMoveMade() {
    this.model.pass();
    Assert.assertEquals("valid",
        this.controller.moveMade(new Coordinate(6, 3)));
  }

  @Test
  public void testPassed() {
    Assert.assertEquals(this.model.getCurrentPlayer(), PlayerColor.BLACK);
    this.controller.passed();
    Assert.assertEquals(this.model.getCurrentPlayer(), PlayerColor.WHITE);
  }

  @Test
  public void testUpdateTurnEndsGame() {
    this.model.pass();
    this.model.pass();
    this.controllerWithMockView.updateTurn();
    Assert.assertTrue(this.log.toString().contains("Game ended in a tie!"));
  }

  @Test
  public void testMoveCoordinateProperlyReceived() {
    Assert.assertTrue(this.mockController.moveMade(new Coordinate(6, 3))
        .contains("Received move at: 6, 3"));
  }

  @Test
  public void testPassProperlyReceived() {
    this.view.passed();
    //also makes sure the view and controller are properly linked
    Assert.assertTrue(this.log.toString().contains("Received pass"));
  }
}