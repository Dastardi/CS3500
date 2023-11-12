package view.gui;

import model.Coordinate;

/**
 * add javadoc
 */
public interface PanelEventListener {
  /**
   * add javadoc
   * @param coordinate
   */
  public boolean moveMadeAndWasValid(Coordinate coordinate);

  /**
   * add javadoc
   */
  public void passed();
}