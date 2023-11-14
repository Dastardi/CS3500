import model.BasicReversi;
import model.Coordinate;
import model.PlayerColor;
import model.ReversiModel;
import org.junit.Before;
import org.junit.Test;
import strategy.*;

import static org.junit.Assert.assertEquals;

public class StrategyTests {
  StringBuilder log;
  ReversiStrategy maxCaptureStrategy;
  ReversiStrategy avoidCornerAdjacentStrategy;
  ReversiStrategy cornerCaptureStrategy;
  ReversiModel basicStartModel;
  ReversiModel cornerAndUpperLeftOpenModel;
  ReversiModel cornerAdjacentAndCornerOpenModel;
  MockReversiModel basicMock;

  @Before
  public void init() {
    this.log = new StringBuilder();

    this.maxCaptureStrategy = new MaxCaptureStrategy();
    this.avoidCornerAdjacentStrategy = new CompositeStrategy(new AvoidCornerAdjacentStrategy(),
        new MaxCaptureStrategy());
    this.cornerCaptureStrategy = new CompositeStrategy(new CornerStrategy(),
        new CompositeStrategy(new AvoidCornerAdjacentStrategy(), new MaxCaptureStrategy()));

    //model with just the starting tiles in place
    this.basicStartModel = new BasicReversi(4);

    //model with an opening to take two tiles in the upper left (upper left is preferred by
    // the strategies as a tiebreaker) or two tiles in the bottom right corner.
    this.cornerAndUpperLeftOpenModel = new BasicReversi(4);
    cornerAndUpperLeftOpenModel.getTileAt(new Coordinate(1,6)).placeDisc(PlayerColor.WHITE);
    cornerAndUpperLeftOpenModel.getTileAt(new Coordinate(2,6)).placeDisc(PlayerColor.WHITE);
    cornerAndUpperLeftOpenModel.getTileAt(new Coordinate(0,6)).placeDisc(PlayerColor.BLACK);
    cornerAndUpperLeftOpenModel.getTileAt(new Coordinate(4,1)).placeDisc(PlayerColor.BLACK);
    cornerAndUpperLeftOpenModel.getTileAt(new Coordinate(3,2)).flip();

    //model with an opening to take two tiles by moving next to a corner in the upper left,
    //take two tiles by moving further away from a corner in the bottom right,
    //or take two tiles by moving to the bottom right corner.
    this.cornerAdjacentAndCornerOpenModel = new BasicReversi(4);
    cornerAdjacentAndCornerOpenModel.getTileAt(new Coordinate(1,6)).placeDisc(PlayerColor.WHITE);
    cornerAdjacentAndCornerOpenModel.getTileAt(new Coordinate(2,6)).placeDisc(PlayerColor.WHITE);
    cornerAdjacentAndCornerOpenModel.getTileAt(new Coordinate(0,6)).placeDisc(PlayerColor.BLACK);
    cornerAdjacentAndCornerOpenModel.getTileAt(new Coordinate(4,4)).placeDisc(PlayerColor.BLACK);
    cornerAdjacentAndCornerOpenModel.getTileAt(new Coordinate(2,2)).placeDisc(PlayerColor.WHITE);
    cornerAdjacentAndCornerOpenModel.getTileAt(new Coordinate(4,3)).flip();

    this.basicMock = new MockReversiModel(log);
  }

  //tests that all strategies break ties correctly by moving in the top left.
  @Test
  public void testAllStrategiesHaveSameStart() {
    assertEquals(new Coordinate(4,1), maxCaptureStrategy.chooseMove(basicStartModel));
    //assertEquals(new Coordinate(3,2), avoidCornerAdjacentStrategy.chooseMove(basicStartModel));
    assertEquals(new Coordinate(3,2), cornerCaptureStrategy.chooseMove(basicStartModel));
  }

  //tests that all three strategies will prioritize the higher-value move
  @Test
  public void testStrategiesTakeHigherValueMove() {
    //basicStartModel.move(maxCaptureStrategy.chooseMove(basicStartModel));
    assertEquals(new Coordinate(5, 0), maxCaptureStrategy.chooseMove(basicStartModel));
    //assertEquals(new Coordinate(5, 0), avoidCornerAdjacentStrategy.chooseMove(basicStartModel));
    //assertEquals(new Coordinate(5, 0), cornerCaptureStrategy.chooseMove(basicStartModel));
  }

  //tests that corner strategy prefers corners despite the move losing the top left tiebreaker,
  //that avoid corner strategy doesn't move next to a corner despite it being in the top left,
  //and that max capture moves in the top left regardless of strategic implications
  @Test
  public void testDifferencesBetweenAllThree() {
    assertEquals(new Coordinate(2,1), maxCaptureStrategy.chooseMove(cornerAdjacentAndCornerOpenModel));
    assertEquals(new Coordinate(4,1), avoidCornerAdjacentStrategy.chooseMove(cornerAdjacentAndCornerOpenModel));
    assertEquals(new Coordinate(3,6), cornerCaptureStrategy.chooseMove(cornerAdjacentAndCornerOpenModel));
  }

  //tests that corner goes to the corner and that when it's not worrying about being next to
  //a corner, avoid corner capture strategy has the same behavior as max capture strategy
  @Test
  public void testCornerStrategyFocusesOnCorner() {
    assertEquals(new Coordinate(1,4), maxCaptureStrategy.chooseMove(cornerAndUpperLeftOpenModel));
    //assertEquals(new Coordinate(1,4), avoidCornerAdjacentStrategy.chooseMove(cornerAndUpperLeftOpenModel));
    assertEquals(new Coordinate(3,6), cornerCaptureStrategy.chooseMove(cornerAndUpperLeftOpenModel));
  }

  @Test
  public void testMaxCaptureMock() {
    maxCaptureStrategy.chooseMove(basicMock);
    assertEquals("", log.toString());
  }
}
