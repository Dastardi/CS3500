package controller;

import model.Coordinate;
import model.PlayerColor;
import provider.model.HexPosn;
import provider.model.HexState;

/**
 * A class with a number of static methods used to translate between our code and
 * the provider's code.
 */
public class Translator {
  /**
   * Translates a provider HexPosn into a Coordinate
   * @param numLayers the size of the board in provider terms, so we can get the center coordinate
   * @param posn the HexPosn to translate into a Coordinate
   * @return a matching Coordinate for the given HexPosn
   */
  public static Coordinate hexPosnToCoordinate(int numLayers, HexPosn posn) {
    int centerCoord = numLayers - 1;
    return new Coordinate(centerCoord + posn.q, centerCoord + posn.r);
  }

  /**
   * Translates a Coordinate into a provider HexPosn
   * @param boardSize the size of the board, so that we can get the center coordinate
   * @param coordinate the Coordinate to translate into a HexPosn
   * @return a matching HexPosn for the given Coordinate
   */
  public static HexPosn coordinateToHexPosn(int boardSize, Coordinate coordinate) {
    int halfBoard = boardSize / 2;
    return new HexPosn(coordinate.getQ() - halfBoard,
        coordinate.getR() - halfBoard,
        - coordinate.getQ() - coordinate.getR());
  }

  /**
   * Translates a provider HexState into a PlayerColor
   * @param color the color that should be given in PlayerColor form
   * @return BLACK iff BLACK, WHITE iff WHITE, null if EMPTY
   */
  public static PlayerColor hexStateToPlayerColor(HexState color) {
    switch (color) {
      case BLACK:
        return PlayerColor.BLACK;
      case WHITE:
        return PlayerColor.WHITE;
      default:
        return null;
    }
  }

  /**
   * Translates a PlayerColor into a provider HexState
   * @param color the color that should be given in HexState form
   * @return BLACK iff BLACK, WHITE iff WHITE, EMPTY if null
   */
  public static HexState playerColorToHexState(PlayerColor color) {
    if (color == PlayerColor.BLACK) {
      return HexState.BLACK;
    }
    else if (color == PlayerColor.WHITE) {
      return HexState.WHITE;
    }
    else {
      return HexState.EMPTY;
    }
  }
}