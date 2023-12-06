package model;

import controller.ModelEventListener;
import controller.ReversiController;
import controller.Translator;
import provider.model.HexPosn;
import provider.model.HexState;
import provider.model.HexagonalReversi;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the model for a game of Reversi.
 * In Reversi, two players, each represented by a color (white or black),
 * place discs on a board with the goal of flipping the opponent's tiles to their color.
 * A player can flip an opponent's tiles if she traps a line of their tiles in a sandwich
 * of her tiles. Players have two options on their turn: make a legal move, or pass.
 * A move is legal if it will flip at least one enemy tile.
 * Passing places no tiles for the turn, and lets the opponent go.
 * If both players pass in sequence, the game ends. The game also ends when the board is full.
 * At the end of the game, the player with the most tiles on the board wins.
 * The BasicReversi class contains all fields and methods necessary for internal gameplay.
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

  //holds the two controllers which listen to the model
  private final List<ModelEventListener> listeners;

  /**
   * Constructs a basic model object for playing a game of Reversi with 6 tiles on each side.
   * This will result in a board that has a longest center row length of 11,
   * which is the standard board size.
   * Default constructor that calls a more robust constructor with the default side length.
   */
  public BasicReversi() {
    //the default side length for a game of Reversi is 6
    //results in a board size of 11
    this(6);
  }

  /**
   * Constructs a model object for playing a game of Reversi, given a side length.
   * Useful for testing to be able to set custom board size, or if the user
   * wants a different size board.
   * Initializes all class fields for the game.
   * @param sideLength the custom side length
   * @throws IllegalArgumentException if the given side length is less than 3
   */
  public BasicReversi(int sideLength) {
    //side lengths of less than 3 result in an invalid/unplayable board
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
    this.listeners = new ArrayList<>();
  }

  /**
   * Constructs a model object for playing a game of Reversi, given a starting
   * board for the game.
   * Helpful for testing to initialize a board under chosen conditions.
   * Could theoretically allow a user to provide starting conditions for a game,
   * though that is not currently implemented.
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
    this.listeners = new ArrayList<>();
  }

  //constructs a deep copy of a Reversi board
  //takes in a given board, made to be used with the constructor that does the same,
  //and returns a deep copy of the board (same data, new reference)
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

  @Override
  public Tile[][] getBoard() {
    return copyBoard(this.board);
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
    if (!tileInBoard(coordinate.getQ(), coordinate.getR())) {
      throw new IllegalArgumentException("Given coordinate out of bounds of board moving.");
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

    //notify all listeners that a move has been made
    notifyTurn();
  }

  @Override
  public boolean isMoveLegal(Coordinate coordinate) {
    if (!tileInBoard(coordinate.getQ(), coordinate.getR())) {
      return false;
    }
    List<List<Tile>> validRows = getValidRows(coordinate, getCurrentPlayer());
    return !validRows.isEmpty() && getTileAt(coordinate).isEmpty();
  }

  @Override
  public boolean playerHasLegalMoves() {
    boolean hasLegalMove = false;
    for (Tile[] row : this.board) {
      for (Tile tile : row) {
        if (tile != null && tile.isEmpty()) {
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
    if (!tileInBoard(coordinate.getQ(), coordinate.getR())) {
      throw new IllegalArgumentException("Given coordinate out of bounds of board"
              + " getting moveScore.");
    }
    if (!getTileAt(new Coordinate(coordinate.getQ(), coordinate.getR())).isEmpty()) {
      return 0;
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
        while (nextTile != null && !nextTile.isEmpty() && nextTile.getContents() != currentColor) {
          row.add(nextTile);
          nextTile = getNextInRow(row.get(row.size() - 2), row.get(row.size() - 1));
        }
        //if it's not empty and would be the correct color to create a sandwich, it's a valid row
        if (nextTile != null && nextTile.getContents() == currentColor) {
          validRows.add(row);
        }
      }
    }
    return validRows;
  }

  //flips all the discs in each of the given rows
  //uses each tile's flip() method, which will flip a black tile to white and vice versa
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
    int deltaQ = neighbor.getCoordinate().getQ() - tile.getCoordinate().getQ();
    int deltaR = neighbor.getCoordinate().getR() - tile.getCoordinate().getR();
    int nextTileQ = neighbor.getCoordinate().getQ() + deltaQ;
    int nextTileR = neighbor.getCoordinate().getR() + deltaR;
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
    addTileIfInBoard(neighbors, coordinate.getQ() + 1, coordinate.getR());
    //tile to the bottom right of center
    addTileIfInBoard(neighbors, coordinate.getQ(), coordinate.getR() + 1);
    //tile to the bottom left of center
    addTileIfInBoard(neighbors, coordinate.getQ() - 1, coordinate.getR() + 1);
    //tile to the left of center
    addTileIfInBoard(neighbors, coordinate.getQ() - 1, coordinate.getR());
    //tile to the top left of center
    addTileIfInBoard(neighbors, coordinate.getQ() , coordinate.getR() - 1);
    //tile to the top right of center
    addTileIfInBoard(neighbors, coordinate.getQ() + 1, coordinate.getR() - 1);

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
    //notify all listeners of the model that a turn has been taken
    notifyTurn();
  }

  //this method comes from our providers' model but relocated it here
  //to avoid an overload method declaration order error
  @Override
  public void pass(HexState color) throws IllegalStateException {
    if (getCurrentPlayer() != Translator.hexStateToPlayerColor(color)) {
      throw new IllegalStateException("Not your turn!");
    }
    this.pass();
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
          break;
        }
      }
    }
    //if two both players have passed consecutively or the board is full, the game is over
    return this.passCount >= 2 || boardFull;
  }

  @Override
  public int getCurrentWinner() {
    //to keep track of the current highest score of all players
    int highestScore = 0;
    //to keep track of the winner
    int winner = 2;
    //go through all player colors
    for (PlayerColor color : PlayerColor.values()) {
      int playerScore = getPlayerScore(color);

      //if this player's score is higher than the current highest
      //if there is a tie, return null
      if (playerScore == highestScore) {
        winner = 2;
      }

      if (playerScore > highestScore) {
        //set their score as the new highest score and make them the winner
        highestScore = playerScore;
        winner = color.ordinal();
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
      return this.board[coordinate.getQ()][coordinate.getR()];
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

  @Override
  public void startGame() {
    for (ModelEventListener listener : listeners) {
      listener.initializeGame();
    }
    activateFirstTurn();
  }

  //finds the first controller in the listener list and activates it as the black player.
  //we do it this way because we have both views and controllers in our listeners, and due to
  //the way they add each other it's finicky to try and do a consistent get from the list.
  private void activateFirstTurn() {
    for (ModelEventListener listener : listeners) {
      if (listener instanceof ReversiController) {
        listener.updateTurn();
        return;
      }
    }
  }

  @Override
  public void addListener(ModelEventListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Cannot provide a null listener to the model.");
    }
    this.listeners.add(listener);
  }

  @Override
  public void notifyTurn() {
    for (ModelEventListener listener : listeners) {
      listener.updateTurn();
    }
  }

  //methods for provider HexagonalReversi
  @Override
  public void placePiece(int q, int r, int s, HexState color) throws IllegalStateException {
    try {
      this.move(Translator.hexPosnToCoordinate(this.getNumLayers(), new HexPosn(q, r, s)));
    } catch (IllegalStateException e) {
      //pass
    }
  }

  //methods for provider ReadOnlyHexagonalReversi
  @Override
  public boolean validateMove(int q, int r, int s, HexState color) {
    if (tileInBoard(this.boardSize / 2 + q, this.boardSize / 2 + r)) {
      return isMoveLegal(Translator.hexPosnToCoordinate(this.getNumLayers(), new HexPosn(q, r, s)));
    }
    else {
      return false;
    }
  }

  @Override
  public HexState getNextTurn() {
    return Translator.playerColorToHexState(getCurrentPlayer());
  }

  @Override
  public HexState getHexState(int q, int r, int s) {
    return Translator.playerColorToHexState(getTileAt(Translator.hexPosnToCoordinate(
            this.getNumLayers(), new HexPosn(q, r, s))).getContents());
  }

  @Override
  public int getScore(HexState color) {
    return getPlayerScore(Translator.hexStateToPlayerColor(color));
  }

  @Override
  public int getNumLayers() {
    return this.boardSize / 2 + 1;
  }

  @Override
  public boolean playableMoveExists(HexState color) {
    return playerHasLegalMoves();
  }

  @Override
  public List<HexPosn> collectAllPossibleMoves(HexState color) {
    //no usages, stub implementation from provider code
    return null;
  }

  @Override
  public HexagonalReversi deepCopy() {
    return new BasicReversi(this.board);
  }

  @Override
  public void addModelEventListener(provider.model.ModelEventListener listener) {
    //no usages, stub implementation from provider code
  }
}