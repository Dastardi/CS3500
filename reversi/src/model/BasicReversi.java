package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the model for a game of Reversi.
 * Contains all fields and methods necessary for internal gameplay.
 */
public class BasicReversi implements ReversiModel {
  //represents the size of the game board (the number of tiles in the longest row)
  private final int boardSize;

  //represents the game board in a 2D array
  private final Tile[][] board;

  //represents the number of recent player actions that were a pass
  private int passCount;

  //represents the index in the PlayerColor enum of the player whose turn it is
  //INVARIANT: currentPlayerIndex is less than 2
  private int currentPlayerIndex;

  /**
   * Constructs a basic model object for playing a game of Reversi with 6 tiles on each side.
   * Default constructor that calls a more robust constructor with the default side length.
   */
  public BasicReversi() {
    //the default side length for a game of Reversi is 6
    //results in a board size of 11
    this(6);
  }

  /**
   * Constructs a model object for playing a game of Reversi, given a side length.
   * Useful for testing to be able to set custom board size.
   * Initializes all class fields for the game.
   * @param sideLength the custom side length
   * @throws IllegalArgumentException if the given side length is less than 3
   */
  public BasicReversi(int sideLength) {
    if (sideLength < 3) {
      throw new IllegalArgumentException("Minimum side length of 3 required"
              + "for a playable game of Reversi.");
    }
    //the size of the board holds the size of the longest row,
    //which is double each side length minus 1
    this.boardSize = (sideLength * 2) - 1;
    this.board = new Tile[this.boardSize][this.boardSize];
    //no moves or passes have been made, so set the number of previous passes to 0
    this.passCount = 0;
    //create all tiles for the board
    fillBoard(this.boardSize);
    //place the six starting tiles around the center
    placeStartingTiles();
    //black moves first, and their i
    this.currentPlayerIndex = 0;
  }

  /**
   * Constructs a model object for playing a game of Reversi, given a starting
   * board for the game.
   * @param givenBoard the board with which to start the game
   */
  public BasicReversi(Tile[][] givenBoard) {
    if (givenBoard == null || givenBoard.length < 5) {
      throw new IllegalArgumentException("Minimum side length of 3 required"
          + "for a playable game of Reversi.");
    }
    this.boardSize = givenBoard.length;
    this.board = copyBoard(givenBoard);
    this.passCount = 0;
    this.currentPlayerIndex = 0;
  }

  private Tile[][] copyBoard(Tile[][] givenBoard) {
    int boardSize = givenBoard.length;
    Tile[][] board = new Tile[boardSize][boardSize];
    for (int r = 0; r < boardSize; r++) {
      for (int q = 0; q < boardSize; q++) {
        if (r + q > (boardSize / 2) * 3 || r + q < boardSize / 2) {
          board[r][q] = null;
        } else {
          board[r][q] = new Tile(r, q);
          if (!givenBoard[r][q].isEmpty()) {
            board[r][q].placeDisc(givenBoard[r][q].getContents());
          }
        }
      }
    }
    return board;
  }

  //helps set up game state by filling the 2D array representing the game board
  //with Tile objects representing each tile on the board
  private void fillBoard(int size) {
    for (int r = 0; r < size; r++) {
      for (int q = 0; q < size; q++) {
        //using a 2D array to represent the board
        //since the board is hexagonal, non-used array spaces are null
        //any coordinates that add to less than the board size * (3/2)
        //or greater than the board size / 2 are null
        //for a standard board size of 11, this will be less than 5 or greater than 15
        if (r + q > (boardSize / 2) * 3 || r + q < boardSize / 2) {
          this.board[r][q] = null;
        } else {
          this.board[r][q] = new Tile(r, q);
        }
      }
    }
  }

  //sets up game state by placing alternating discs in the tiles around the center of the board
  private void placeStartingTiles() {
    int centerTileQR = this.boardSize / 2;
    //get all the neighbors of the center tile
    List<Tile> neighborsOfCenter = getNeighbors(new Coordinate(centerTileQR, centerTileQR));
    for (int i = 0; i < neighborsOfCenter.size(); i++) {
      if (i % 2 == 0) {
        //place a black disc in the top left, bottom left, and right
        neighborsOfCenter.get(i).placeDisc(PlayerColor.BLACK);
      } else {
        //place a white disc in the top right, bottom right, and left
        neighborsOfCenter.get(i).placeDisc(PlayerColor.WHITE);
      }
    }
  }

  @Override
  public void move(Coordinate coordinate) {
    throwIfGameOver();
    //cannot make a move to a coordinate that is not a part of the board
    if (!tileInBoard(coordinate.q, coordinate.r)) {
      throw new IllegalArgumentException("Given coordinate out of bounds of board.");
    }
    //get the current player and increment the player index
    PlayerColor currentColor = getCurrentPlayer();
    //get list of all rows that render this move legal
    List<List<Tile>> validRows = getValidRows(coordinate, currentColor);
    //as long as at least one row provides a valid sandwich:
    if (!validRows.isEmpty()) {
      //place a disc of the color of the current player at the requested coordinate,
      getTileAt(coordinate).placeDisc(currentColor);
      //and flip all non-current player's discs in the row
      flipDiscs(validRows);
    }
    //if no rows fulfill valid move criteria, throw an exception
    else {
      throw new IllegalStateException("Invalid move.");
    }

    //after every move, the most recent action was a move so pass count should be zero
    this.passCount = 0;

    //update the player color
    updatePlayer();
  }

  //TODO: ask TA about repetitive code and checking move legality externally
  // i.e. is it bad to call this public method inside move()
  public boolean isMoveLegal(Coordinate coordinate) {
    if (!tileInBoard(coordinate.q, coordinate.r)) {
      throw new IllegalArgumentException("Given coordinate out of bounds of board.");
    }
    List<List<Tile>> validRows = getValidRows(coordinate, getCurrentPlayer());
    return !validRows.isEmpty();
  }

  @Override
  public boolean playerHasLegalMoves() {
    boolean hasLegalMove = false;
    for (Tile[] row : this.board) {
      for (Tile tile : row) {
        if (tile.isEmpty()) {
          if (isMoveLegal(tile.getCoordinate())) {
            hasLegalMove = true;
          }
        }
      }
    }
    return hasLegalMove;
  }

  @Override
  public int getMoveScore(Coordinate coordinate) {
    if (!tileInBoard(coordinate.q, coordinate.r)) {
      throw new IllegalArgumentException("Given coordinate out of bounds of board.");
    }
    int moveScore = 0;
    List<List<Tile>> validRows = getValidRows(coordinate, getCurrentPlayer());
    if (!validRows.isEmpty()) {
      for (List<Tile> row : validRows) {
        moveScore += row.size();
      }
    }
    return moveScore;
  }

  //returns a list of all rows emanating out from a coordinate
  //that would render a move there legal
  private List<List<Tile>> getValidRows(Coordinate coordinate, PlayerColor currentColor) {
    //initialize a variable to return at the end of the function
    List<List<Tile>> validRows = new ArrayList<>();
    //get the neighbors of the given tile
    List<Tile> neighbors = getNeighbors(coordinate);
    //iterate over all six potential valid rows
    for (Tile neighbor : neighbors) {
      //if the neighbor is empty or the same as the color, it doesn't count - ignore it
      if (!neighbor.isEmpty() && neighbor.getContents() != currentColor) {
        //initialize a list to represent this specific row
        List<Tile> row = new ArrayList<>();
        //add the neighbor to the row
        row.add(neighbor);
        //get the next tile in the row
        Tile nextTile = getNextInRow(getTileAt(coordinate), neighbor);
        //while the next row is the opposite color, keep iterating and adding to the row
        while (nextTile != null && nextTile.getContents() != currentColor) {
          row.add(nextTile);
          nextTile = getNextInRow(neighbor, nextTile);
        }
        //if it's not empty and would be the correct color to create a sandwich, it's a valid row
        if (nextTile != null && nextTile.getContents() == currentColor) {
          validRows.add(row);
        }
      }
    }
    return validRows;
  }

  //flips all the discs in each of the given rows.
  private void flipDiscs(List<List<Tile>> validRows) {
    for (List<Tile> row : validRows) {
      for (Tile tile : row) {
        tile.flip();
      }
    }
  }

  //given two tiles, this method finds the pattern that connects them and applies that pattern
  // to the board to get the next tile along the row
  private Tile getNextInRow(Tile tile, Tile neighbor) {
    //get the changes in axial coordinates for finding the next tile in the sequence
    int deltaQ = neighbor.getCoordinate().q - tile.getCoordinate().q;
    int deltaR = neighbor.getCoordinate().r - tile.getCoordinate().r;
    int nextTileQ = neighbor.getCoordinate().q + deltaQ;
    int nextTileR = neighbor.getCoordinate().r + deltaR;
    //make sure this tile exists in the board before trying to access it
    //to avoid index out of bounds exceptions
    if (tileInBoard(nextTileQ, nextTileR)) {
      return this.board[nextTileQ][nextTileR];
    }
    //if the tile is not on the board, return null
    return null;
  }

  //gets all six neighbors of a tile at a given coordinate
  private List<Tile> getNeighbors(Coordinate coordinate) {
    List<Tile> neighbors = new ArrayList<>();

    //tile to the right of center
    addTileIfInBoard(neighbors, coordinate.q + 1, coordinate.r);
    //tile to the bottom right of center
    addTileIfInBoard(neighbors, coordinate.q, coordinate.r + 1);
    //tile to the bottom left of center
    addTileIfInBoard(neighbors, coordinate.q - 1, coordinate.r + 1);
    //tile to the left of center
    addTileIfInBoard(neighbors, coordinate.q - 1, coordinate.r);
    //tile to the top left of center
    addTileIfInBoard(neighbors, coordinate.q , coordinate.r - 1);
    //tile to the top right of center
    addTileIfInBoard(neighbors, coordinate.q + 1, coordinate.r - 1);

    return neighbors;
  }

  //tries to add a tile to a list of neighbors
  private void addTileIfInBoard(List<Tile> neighbors, int q, int r) {
    if (tileInBoard(q, r)) {
      neighbors.add(board[q][r]);
    }
  }

  //returns whether the given coordinates correspond to a tile on the game board.
  //checks for a null value because in our 2d array there are values in the top left
  //and bottom right which act as null placeholder values.
  private boolean tileInBoard(int q, int r) {
    if (q >= 0 && q < this.boardSize && r >= 0 && r < this.boardSize) {
      return getTileAt(new Coordinate(q, r)) != null;
    }
    return false;
  }

  @Override
  public void pass() {
    throwIfGameOver();
    //increment the next player index without moving
    updatePlayer();
    //after every pass, the most recent action was a pass so pass count increments
    this.passCount++;
  }

  @Override
  public PlayerColor getCurrentPlayer() {
    throwIfGameOver();
    PlayerColor[] playerColors = PlayerColor.values();
    return playerColors[this.currentPlayerIndex];
  }

  //updates the player index so that the next move is made by the next player
  private void updatePlayer() {
    PlayerColor[] playerColors = PlayerColor.values();
    //modulo will set the index back to 0 once the length of the index is reached
    this.currentPlayerIndex = (this.currentPlayerIndex + 1) % playerColors.length;
  }

  @Override
  public boolean isGameOver() {
    //assume the board is full
    boolean boardFull = true;
    //iterate over the entire board, setting the boolean to false if any of the tiles are empty
    for (int r = 0; r < this.boardSize; r++) {
      for (int q = 0; q < this.boardSize; q++) {
        //only iterate over tiles that are actually on the board (skip null values in the array)
        if (tileInBoard(q, r) && getTileAt(new Coordinate(r, q)).isEmpty()) {
          boardFull = false;
        }
      }
    }
    //if two both players have passed consecutively or the board is full, the game is over
    return this.passCount >= 2 || boardFull;
  }

  @Override
  public PlayerColor getCurrentWinner() {
    //to keep track of the current highest score of all players
    int highestScore = 0;
    //to keep track of the winner
    PlayerColor winner = null;
    //go through all player colors
    for (PlayerColor color : PlayerColor.values()) {
      int playerScore = getPlayerScore(color);

      //if this player's score is higher than the current highest
      //if there is a tie, return null
      if (playerScore == highestScore) {
        winner = null;
      }

      if (playerScore > highestScore) {
        //set their score as the new highest score and make them the winner
        highestScore = playerScore;
        winner = color;
      }
    }
    return winner;
  }

  @Override
  public int getPlayerScore(PlayerColor color) {
    int score = 0;
    //go through every index in the board (null or tile)
    for (int r = 0; r < this.boardSize; r++) {
      for (int q = 0; q < this.boardSize; q++) {
        Tile tile = getTileAt(new Coordinate(q, r));
        //if it holds a disc belonging to the given player
        if (tile != null && tile.getContents() == color) {
          score++;
        }
      }
    }
    return score;
  }

  @Override
  public Tile getTileAt(Coordinate coordinate) {
    try {
      return this.board[coordinate.q][coordinate.r];
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Coordinate is invalid.");
    }
  }

  @Override
  public int getBoardSize() {
    return this.boardSize;
  }

  //throws an exception if it is called once the game is over
  private void throwIfGameOver() {
    if (isGameOver()) {
      throw new IllegalStateException("The game is over.");
    }
  }
}