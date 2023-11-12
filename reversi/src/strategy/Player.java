package strategy;

import model.ReversiModel;

/**
 * todo
 */
public interface Player {
  /**
   * todo
   * @param model
   */
  void move(ReversiModel model);

  /**
   * todo
   * @param model
   */
  void pass(ReversiModel model);
}
