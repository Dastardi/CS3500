package provider.model;

/**
 * Represents something that listens to changes in the model.
 */
public interface ModelEventListener {

  /**
   * Do something when the model changes turn.
   *
   * @param color The color that is the new turn.
   */
  void processNotifyOfTurn(HexState color);

  /**
   * Do something when the model's game is over.
   */
  void processGameIsOver();

  /**
   * Do something when the model's board state is updated.
   */
  void processModelStateUpdated();
}
