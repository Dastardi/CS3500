package controller;

import model.Coordinate;

public class CornerProviderPlayer implements Player {
  //if the optional is empty, return NOVALID and null
  //if the optional has a value, return VALID and the translation of the HexPosn into a Coordinate
  @Override
  public Pair<MoveType, Coordinate> move() {
    return null;
  }
}
