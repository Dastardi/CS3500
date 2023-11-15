package strategy;

import model.Coordinate;
import model.ReadOnlyReversiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A strategy that avoids placing discs next to the corner so that their opponent can't
 * get into the corner.
 */
public class AvoidCornerAdjacentStrategy implements ReversiStrategy {
  private final ReadOnlyReversiModel model;

  /**
   * Constructs the AvoidCornerAdjacentStrategy from a given model.
   * @param model the model of the game that this strategy is playing.
   */
  public AvoidCornerAdjacentStrategy(ReadOnlyReversiModel model) {
    this.model = model;
  }

  @Override
  public List<Coordinate> chooseMove(List<Coordinate> moveList) {
    //make a list of Coordinates to remove, then re-add to the list
    List<Coordinate> reAddList = new ArrayList<>();
    //get a list of all coordinates next to a corner
    List<Coordinate> nextToCornerList = new ArrayList<>();
    for (Coordinate corner : getCorners()) {
      nextToCornerList.addAll(getNeighbors(corner));
    }

    //if a coordinate is next to a corner, it's undesirable for this strategy.
    //Therefore, if a coordinate is in the next-to-corner-list, move it to the
    //back of the moveList. This ensures that any coordinates that aren't next
    //to corners end up being prioritized.
    for (Coordinate coordinate : moveList) {
      if (nextToCornerList.contains(coordinate)) {
        reAddList.add(coordinate);
      }
    }
    moveList.removeAll(reAddList);
    moveList.addAll(reAddList);

    return moveList;
  }

  //gets the existing corners next to a given coordinate.
  List<Coordinate> getCorners() {
    int boardSize = model.getBoardSize();
    int halfBoard = boardSize / 2;
    List<Coordinate> coordList = new ArrayList<>();
    for (int r = 0; r < boardSize; r += halfBoard) {
      for (int q = 0; q < boardSize; q += halfBoard) {
        if (q != r) {
          coordList.add(new Coordinate(q,r));
        }
      }
    }
    return coordList;
  }

  //returns the existing neighbors of a given tile.
  private List<Coordinate> getNeighbors(Coordinate coordinate) {
    List<Coordinate> neighbors = new ArrayList<>();

    addCoordIfInBoard(neighbors, coordinate.getQ() + 1, coordinate.getR());
    addCoordIfInBoard(neighbors, coordinate.getQ(), coordinate.getR() + 1);
    addCoordIfInBoard(neighbors, coordinate.getQ() - 1, coordinate.getR() + 1);
    addCoordIfInBoard(neighbors, coordinate.getQ() - 1, coordinate.getR());
    addCoordIfInBoard(neighbors, coordinate.getQ() , coordinate.getR() - 1);
    addCoordIfInBoard(neighbors, coordinate.getQ() + 1, coordinate.getR() - 1);

    return neighbors;
  }

  //tries to add a tile to a list of neighbors
  private void addCoordIfInBoard(List<Coordinate> neighbors, int q, int r) {
    if (tileInBoard(q, r)) {
      neighbors.add(new Coordinate(q, r));
    }
  }

  //returns whether the given coordinates correspond to a tile on the game board.
  //checks for a null value because in our 2d array there are values in the top left
  //and bottom right which act as null placeholder values.
  private boolean tileInBoard(int q, int r) {
    if (q >= 0 && q < this.model.getBoardSize() && r >= 0 && r < this.model.getBoardSize()) {
      return model.getTileAt(new Coordinate(q, r)) != null;
    }
    return false;
  }
}