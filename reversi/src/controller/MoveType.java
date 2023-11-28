package controller;

/**
 * An enum describing the possible return values for a move from a Reversi Player.
 * A Human player wants to inform the controller
 */
public enum MoveType {
  VALID,
  NOVALID,
  HUMAN;
}