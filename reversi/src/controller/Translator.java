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
   * @param posn the HexPosn to translate into a Coordinate
   * @return a matching Coordinate for the given HexPosn
   */
  public static Coordinate hexPosnToCoordinate(HexPosn posn) {
    return null;
  }

  /**
   * Translates a Coordinate into a provider HexPosn
   * @param coordinate the Coordinate to translate into a HexPosn
   * @return a matching HexPosn for the given Coordinate
   */
  public static Coordinate hexPosnToCoordinate(Coordinate coordinate) {
    return null;
  }

  /**
   * Translates a provider HexState into a PlayerColor
   * @param color the color that should be given in PlayerColor form
   * @return BLACK iff BLACK, WHITE iff WHITE, null if EMPTY
   */
  public static PlayerColor hexStateToPlayerColor(HexState color) {
    return null;
  }

  /**
   * Translates a PlayerColor into a provider HexState
   * @param color the color that should be given in HexState form
   * @return BLACK iff BLACK, WHITE iff WHITE, EMPTY if null
   */
  public static HexState playerColorToHexState(PlayerColor color) {
    return null;
  }
}
