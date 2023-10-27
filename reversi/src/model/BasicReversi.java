package model;

public class BasicReversi implements ReversiModel, PlayerActions {
  private final int boardSize;
  private final Tile[][] board;
  private int passCount;

  public BasicReversi() {
    this.boardSize = 11;
    this.board = new Tile[this.boardSize][this.boardSize];
    this.passCount = 0;
    fillBoard(this.boardSize);
  }

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
  public void move(PlayerColor color, Coordinate coordinate) throws IllegalArgumentException, IllegalStateException {
    //TODO
    //after every move, the most recent action was a move so pass count should be zero
    passCount = 0;
  }

  @Override
  public void pass() {
    //TODO
    //after every pass, the most recent action was a pass so pass count increments
    passCount++;
  }

  @Override
  public boolean isGameOver() {
    // TODO: Implement boolean to check if the board is full
    boolean boardFull = false;
    return passCount >= 2 || boardFull;
  }

  @Override
  public PlayerColor getWinner() {
    return null;
  }

  @Override
  public int getPlayerScore(PlayerColor color) {
    return 0;
  }

  @Override
  public boolean isTileEmpty(Coordinate coordinate) {
    return false;
  }

  @Override
  public PlayerColor getTileContents() {
    return null;
  }
}
