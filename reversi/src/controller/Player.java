package controller;

import model.Coordinate;

/**
 *
 */
public interface Player {
  //returns a Pair of an enum (VALID, NOVALID, HUMAN)
  //and a Coordinate (which will be null in any case that is not VALID).
  Pair<MoveType, Coordinate> move();
}
