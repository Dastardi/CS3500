package controller;

/**
 * An enum describing the possible return values for a move from a Reversi Player.
 * A human player's move will always come from the view, so the HUMAN value tells the controller
 * to handle those separately.
 * An AI player will either return a move, in which case its type is VALID,
 * or it will have no possible moves, in which case its time is NOVALID.
 */
public enum MoveType {
  VALID,
  NOVALID,
  HUMAN;
}