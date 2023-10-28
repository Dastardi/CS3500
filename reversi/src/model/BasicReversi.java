package model;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds the model for a game of Reversi. weeeeeeeee.
 * Contains all fields and methods necessary for internal gameplay.
 */
public class BasicReversi implements ReversiModel, PlayerActions {
  //represents the size of the game board (the number of tiles in the longest row)
  private final int boardSize;

  //represents the game board in a 2D array
  private final Tile[][] board;

  //represents the number of recent player actions that were a pass
  private int passCount;

  //represents the index in the PlayerColor enum of the player whose turn it is
  private int currentPlayerIndex;

  /**
   * Constructs a model object for playing a game of Reversi.
   * Initializes all class fields for the game.
   */
  public BasicReversi() {
    this.boardSize = 11;
    this.board = new Tile[this.boardSize][this.boardSize];
    this.passCount = 0;
    fillBoard(this.boardSize);
    this.currentPlayerIndex = 0;
  }

  //helper method called in the constructor that fills the 2D array representing the game board
  //with Tile objects representing each tile on the board
  private void fillBoard(int size) {
    for (int r = 0; r < size; r++) {
      for (int q = 0; q < size; q++) {
        //using a 2D array to represent the board
        //since the board is hexagonal, non-used array spaces are null
        //any coordinates that add to less than 5 or greater than 15 are null
        if (r + q > 15 || r + q < 5) {
          this.board[r][q] = null;
        }
        else {
          this.board[r][q] = new Tile(r, q);
        }
      }
    }
  }

  @Override
  public void move(Coordinate coordinate) {
    //TODO throw exception if coordinates are invalid
    //get the current player and increment the player index
    PlayerColor currentColor = getCurrentPlayer();

    //get list of all rows that render this move legal
    List<List<Tile>> validRows = getValidRows(coordinate, currentColor);
    //as long as at least one row is valid
    if (!validRows.isEmpty()) {
      //place a disc of the color of the current player at the requested coordinate
      getTileAt(coordinate).placeDisc(currentColor);
      //flip all non-current player's discs in the row (up to the next instance of current player's disc)
      flipDiscs(validRows, currentColor);
    }
    //if no rows fulfill valid move criteria, throw an exception
    else {
      throw new IllegalStateException("Invalid move.");
    }

    //after every move, the most recent action was a move so pass count should be zero
    this.passCount = 0;
  }

  //returns a list of all rows emanating out from a coordinate that would render a move there legal
  private List<List<Tile>> getValidRows(Coordinate coordinate, PlayerColor currentColor) {
    //initialize a variable to return at the end of the function
    List<List<Tile>> validRows = new ArrayList<>();
    //get the neighbors of the given tile
    List<Tile> neighbors = getNeighbors(coordinate);
    //iterate over all six potential valid rows
    for (Tile neighbor : neighbors) {
      //if the color of the neighbor is empty or the same as the color, it doesn't count - ignore it
      if (neighbor.isEmpty() || neighbor.getColor() != currentColor) {
        //initialize a list to represent this specific row
        List<Tile> row = new ArrayList<>();
        //get the next tile in the row
        Tile nextTile = getNextInRow(getTileAt(coordinate), neighbor);
        //while the next row is the opposite color, keep iterating and adding to the row
        while (nextTile != null && nextTile.getColor() != currentColor) {
          row.add(nextTile);
          nextTile = getNextInRow(neighbor, nextTile);
        }
        //if it's not empty and would be the correct color to create a sandwich, it's a valid row
        if (nextTile != null && nextTile.getColor() == currentColor) {
          validRows.add(row);
        }
      }
    }
    return validRows;
  }

  //flips all the discs in each of the given rows that are a different color from the given one.
  private void flipDiscs(List<List<Tile>> validRows, PlayerColor color) {
    for (List<Tile> row : validRows) {
      for (Tile tile : row) {
        tile.flip(color);
      }
    }
  }

  //given two tiles, finds the pattern that connects them and applies that pattern to the board
  //to get the next tile along the row
  private Tile getNextInRow(Tile tile, Tile neighbor) {
    //get the changes in axial coordinates for finding the next tile in the sequence
    int deltaQ = neighbor.getCoordinate().q - tile.getCoordinate().q;
    int deltaR = neighbor.getCoordinate().r - tile.getCoordinate().r;
    return this.board[neighbor.getCoordinate().q + deltaQ][neighbor.getCoordinate().r + deltaR];
  }

  //gets all 6 neighbors of a tile at a given coordinate
  private List<Tile> getNeighbors(Coordinate coordinate) {
    List<Tile> neighbors = new ArrayList<>();

    //tile to the right of center
    neighbors.add(board[coordinate.q + 1][coordinate.r]);
    //tile to the bottom right of center
    neighbors.add(board[coordinate.q][coordinate.r + 1]);
    //tile to the bottom left of center
    neighbors.add(board[coordinate.q - 1][coordinate.r + 1]);
    //tile to the left of center
    neighbors.add(board[coordinate.q - 1][coordinate.r]);
    //tile to the top left of center
    neighbors.add(board[coordinate.q][coordinate.r - 1]);
    //tile to the top right of center
    neighbors.add(board[coordinate.q + 1][coordinate.r - 1]);

    return neighbors;
  }

  @Override
  public void pass() {
    //increment the next player index without using the next player to make a move
    getCurrentPlayer();
    //after every pass, the most recent action was a pass so pass count increments
    this.passCount++;
  }

  //helper method that uses the index field to get the color
  //of the player that is making this current turn
  //and increment the index to provide the next player on the next turn
  private PlayerColor getCurrentPlayer() {
    PlayerColor[] playerColors = PlayerColor.values();
    PlayerColor currentPlayer = playerColors[this.currentPlayerIndex];
    //modulo will set the index back to 0 once the length of the index is reached
    this.currentPlayerIndex = (this.currentPlayerIndex + 1) % playerColors.length;
    return currentPlayer;
  }

  @Override
  public boolean isGameOver() {
    //assume the board is full
    boolean boardFull = true;
    //iterate over the entire board, setting the boolean to false if any of the tiles are empty
    for (int r = 0; r < this.boardSize; r++) {
      for (int q = 0; q < this.boardSize; q++) {
        if (getTileAt(new Coordinate(r, q)).isEmpty()) {
          boardFull = false;
        }
      }
    }
    //if two both players have passed consecutively or the board is full, the game is over
    return this.passCount >= 2 || boardFull;
  }

  @Override
  public PlayerColor getWinner() {
    if (!isGameOver()) {
      throw new IllegalStateException("Game is not over, so we cannot have a winner yet!");
    }
    int highestScore = 0;
    PlayerColor winner = null;
    //go through all player colors
    for (PlayerColor color : PlayerColor.values()) {
      //if this player's score is higher than the current highest
      if (getPlayerScore(color) > highestScore) {
        //set their score as the new highest score and make them the winner
        highestScore = getPlayerScore(color);
        winner = color;
      }
    }
    return winner;
  }

  @Override
  public int getPlayerScore(PlayerColor color) {
    int score = 0;

    for (int r = 0; r < this.boardSize; r++) {
      for (int q = 0; q < this.boardSize; q++) {
        if (this.board[r][q].getColor() == color) {
          score++;
        }
      }
    }
    return score;
  }

  @Override
  public Tile getTileAt(Coordinate coordinate) {
    try {
      return this.board[coordinate.r][coordinate.q];
    } catch (IndexOutOfBoundsException e) { //TODO test to make sure this is correct exception
      throw new IllegalArgumentException("Coordinate is invalid.");
    }
  }
}