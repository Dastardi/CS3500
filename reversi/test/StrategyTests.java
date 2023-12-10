import model.BasicReversi;
import model.Coordinate;
import model.PlayerColor;
import model.ReversiModel;
import org.junit.Before;
import org.junit.Test;
import squaretests.LiarMockReversiModel;
import squaretests.MockReversiModel;
import strategy.ReversiStrategy;
import strategy.CompositeStrategy;
import strategy.MaxCaptureStrategy;
import strategy.CornerStrategy;
import strategy.AvoidCornerAdjacentStrategy;
import strategy.UpperLeftMostStrategy;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


/**
 * Tests the AI strategies in this Reversi game.
 */
public class StrategyTests {
  StringBuilder log;
  ReversiModel basicStartModel;
  ReversiModel cornerAndUpperLeftOpenModel;
  ReversiModel cornerAdjacentCornerOpenModel;
  MockReversiModel basicMock;
  MockReversiModel liarMock;

  //three strategies that use the basic model, which has only the starting tiles
  ReversiStrategy basicMaxCapture;
  ReversiStrategy basicAvoidCornerAdjacent;
  ReversiStrategy basicCornerCapture;

  //three strategies that use a model set up with a corner available to capture in
  //the bottom right
  ReversiStrategy cornerMaxCapture;
  ReversiStrategy cornerAvoidCornerAdjacent;
  ReversiStrategy cornerCornerCapture;

  //three strategies that use a model with a corner open in the bottom right, a corner-adjacent
  //tile open in the top left, and a non-corner adjacent tile open in the middle
  ReversiStrategy adjacentMaxCapture;
  ReversiStrategy adjacentAvoidCornerAdjacent;
  ReversiStrategy adjacentCornerCapture;

  //max capture strategies to test using the mocks
  ReversiStrategy mockMaxCapture;
  ReversiStrategy liarMockMaxCapture;

  @Before
  public void init() {
    //model with just the starting tiles in place
    this.basicStartModel = new BasicReversi(8);

    //model with an opening to take two tiles in the upper left (upper left is preferred by
    // the strategies as a tiebreaker) or two tiles in the bottom right corner.
    this.cornerAndUpperLeftOpenModel = new BasicReversi(8);
    cornerAndUpperLeftOpenModel.getTileAt(new Coordinate(6,7)).placeDisc(PlayerColor.WHITE);
    cornerAndUpperLeftOpenModel.getTileAt(new Coordinate(5,7)).placeDisc(PlayerColor.WHITE);
    cornerAndUpperLeftOpenModel.getTileAt(new Coordinate(4,7)).placeDisc(PlayerColor.BLACK);
    cornerAndUpperLeftOpenModel.getTileAt(new Coordinate(3,5)).placeDisc(PlayerColor.WHITE);

    //model with an opening to take two tiles by moving next to a corner in the upper left,
    //take two tiles by moving further away from a corner in the bottom right,
    //or take two tiles by moving to the bottom right corner.
    this.cornerAdjacentCornerOpenModel = new BasicReversi(8);
    cornerAdjacentCornerOpenModel.getTileAt(new Coordinate(6,7)).placeDisc(PlayerColor.WHITE);
    cornerAdjacentCornerOpenModel.getTileAt(new Coordinate(5,7)).placeDisc(PlayerColor.WHITE);
    cornerAdjacentCornerOpenModel.getTileAt(new Coordinate(4,7)).placeDisc(PlayerColor.BLACK);
    cornerAdjacentCornerOpenModel.getTileAt(new Coordinate(3,5)).placeDisc(PlayerColor.WHITE);
    cornerAdjacentCornerOpenModel.getTileAt(new Coordinate(2,0)).placeDisc(PlayerColor.WHITE);
    cornerAdjacentCornerOpenModel.getTileAt(new Coordinate(3,0)).placeDisc(PlayerColor.WHITE);
    cornerAdjacentCornerOpenModel.getTileAt(new Coordinate(4,5)).placeDisc(PlayerColor.BLACK);

    this.log = new StringBuilder();

    this.basicMock = new MockReversiModel(log);
    this.liarMock = new LiarMockReversiModel(log);

    this.basicMaxCapture =
        new CompositeStrategy(new MaxCaptureStrategy(basicStartModel),
        new UpperLeftMostStrategy());
    this.basicAvoidCornerAdjacent =
        new CompositeStrategy(new MaxCaptureStrategy(basicStartModel),
        new CompositeStrategy(new AvoidCornerAdjacentStrategy(basicStartModel),
            new UpperLeftMostStrategy()));
    this.basicCornerCapture =
        new CompositeStrategy(new CornerStrategy(basicStartModel),
        new CompositeStrategy(new MaxCaptureStrategy(basicStartModel),
            new UpperLeftMostStrategy()));


    this.cornerMaxCapture =
        new CompositeStrategy(new MaxCaptureStrategy(cornerAndUpperLeftOpenModel),
        new UpperLeftMostStrategy());
    this.cornerAvoidCornerAdjacent =
        new CompositeStrategy(new MaxCaptureStrategy(cornerAndUpperLeftOpenModel),
        new CompositeStrategy(new AvoidCornerAdjacentStrategy(cornerAndUpperLeftOpenModel),
            new UpperLeftMostStrategy()));
    this.cornerCornerCapture =
        new CompositeStrategy(new MaxCaptureStrategy(cornerAndUpperLeftOpenModel),
        new CompositeStrategy(new CornerStrategy(cornerAndUpperLeftOpenModel),
            new UpperLeftMostStrategy()));

    this.adjacentMaxCapture =
        new CompositeStrategy(new MaxCaptureStrategy(cornerAdjacentCornerOpenModel),
            new UpperLeftMostStrategy());
    this.adjacentAvoidCornerAdjacent =
        new CompositeStrategy(new MaxCaptureStrategy(cornerAdjacentCornerOpenModel),
            new CompositeStrategy(new AvoidCornerAdjacentStrategy(cornerAdjacentCornerOpenModel),
                new UpperLeftMostStrategy()));
    this.adjacentCornerCapture =
        new CompositeStrategy(new MaxCaptureStrategy(cornerAdjacentCornerOpenModel),
            new CompositeStrategy(new CornerStrategy(cornerAdjacentCornerOpenModel),
                new UpperLeftMostStrategy()));

    this.mockMaxCapture =
        new CompositeStrategy(new MaxCaptureStrategy(basicMock),
            new UpperLeftMostStrategy());
    this.liarMockMaxCapture =
        new CompositeStrategy(new MaxCaptureStrategy(liarMock),
            new UpperLeftMostStrategy());
  }

  //tests that all strategies break ties correctly by moving in the top left.
  @Test
  public void testAllStrategiesHaveSameStart() {
    //check that all three strategies have the same starting move
    assertEquals(new Coordinate(4,2),
        basicMaxCapture.chooseMove(new ArrayList<>()).get(0));
    assertEquals(new Coordinate(4,2),
        basicAvoidCornerAdjacent.chooseMove(new ArrayList<>()).get(0));
    assertEquals(new Coordinate(4,2),
        basicCornerCapture.chooseMove(new ArrayList<>()).get(0));
  }

  //tests that all three strategies will prioritize the higher-value move
  @Test
  public void testStrategiesTakeHigherValueMove() {
    basicStartModel.move(new Coordinate(4, 2));
    basicStartModel.move(new Coordinate(3, 2));
    basicStartModel.move(basicMaxCapture.chooseMove(new ArrayList<>()).get(0));
    assertEquals(new Coordinate(3,1),
        basicMaxCapture.chooseMove(new ArrayList<>()).get(0));
    assertEquals(new Coordinate(3,1),
        basicAvoidCornerAdjacent.chooseMove(new ArrayList<>()).get(0));
    assertEquals(new Coordinate(3,1),
        basicCornerCapture.chooseMove(new ArrayList<>()).get(0));
  }

  //tests that corner strategy prefers corners despite the move losing the top left tiebreaker,
  //that avoid corner strategy doesn't move next to a corner despite it being in the top left,
  //and that max capture moves in the top left regardless of strategic implications
  @Test
  public void testDifferencesBetweenAllThree() {
    assertEquals(new Coordinate(3,6),
        adjacentMaxCapture.chooseMove(new ArrayList<>()).get(0));
    assertEquals(new Coordinate(3,6),
        adjacentAvoidCornerAdjacent.chooseMove(new ArrayList<>()).get(0));
    assertEquals(new Coordinate(3,6),
        adjacentCornerCapture.chooseMove(new ArrayList<>()).get(0));
  }

  //tests that corner goes to the corner and that when it's not worrying about being next to
  //a corner, avoid corner capture strategy has the same behavior as max capture strategy
  @Test
  public void testCornerStrategyFocusesOnCorner() {
    assertEquals(new Coordinate(1,4),
        cornerMaxCapture.chooseMove(new ArrayList<>()).get(0));
    assertEquals(new Coordinate(1,4),
        cornerAvoidCornerAdjacent.chooseMove(new ArrayList<>()).get(0));
    assertEquals(new Coordinate(3,6),
        cornerCornerCapture.chooseMove(new ArrayList<>()).get(0));
  }

  //the mock model appends messages to the log. We want to ensure that all eight potential
  //moves are checked, as well as that the strategy checks every tile in the board.
  //in order to see the entire message, check strategy-transcript.txt.
  @Test
  public void testMaxCaptureMock() {
    mockMaxCapture.chooseMove(new ArrayList<>());
    assertTrue(log.toString().contains("Getting score at 6, 3"));
    assertTrue(log.toString().contains("Getting score at 4, 4"));
    assertTrue(log.toString().contains("Getting score at 7, 4"));
    assertTrue(log.toString().contains("Getting score at 3, 6"));
    assertTrue(log.toString().contains("Getting score at 6, 6"));
    assertTrue(log.toString().contains("Getting score at 4, 7"));
    assertEquals(new Coordinate(6,3), mockMaxCapture.chooseMove(new ArrayList<>()).get(0));
  }

  //liar mock alters the validity check to ensure that the strategy follows the model in
  //determining its choices for best moves.
  @Test
  public void testLiarMock() {
    assertEquals(new Coordinate(3,3), liarMockMaxCapture.chooseMove(new ArrayList<>()).get(0));
  }
}
