package model;

import controller.ModelEventListener;

public interface TurnTaker {
  void addListener(ModelEventListener listener);
  void notifyTurn();
}
