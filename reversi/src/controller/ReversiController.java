package controller;

import strategy.Player;

public interface ReversiController {
  void addPlayer(Player player);
  void play();
}
