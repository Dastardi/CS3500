package provider.model;

import java.util.Arrays;
import java.util.List;

/**
 * Class for utility actions relating to hex coordinates.
 */
public class CoordinateUtils {

  /**
   * Converts from 2D cartesian coordinates to cubic coordinates.
   *
   * @param row The row number
   * @param index The index in the row
   * @param modelLayers The number of layers in the model
   * @return A list of integers that represents the cubic coordinate equivalent
   */
  public static HexPosn rowAndIndexToQRS(int row, int index, int modelLayers) {

    int q;
    int r = row - modelLayers + 1;
    int s;

    if (r <= 0) {
      s = modelLayers - 1 - index;
      q = -s - r;
    } else {
      q = -modelLayers + 1 + index;
      s = -q - r;
    }

    return new HexPosn(q, r, s);
  }

  /**
   * Converts from cubic coordinates to 2D cartesian coordinates.
   *
   * @param posn position of the hex
   * @param modelLayers The number of layers in the model
   * @return A list of integers that represents the cartesian coordinate equivalent
   */
  public static List<Integer> qrsToRowAndIndex(HexPosn posn, int modelLayers) {

    int index;
    int row = posn.r + modelLayers - 1;

    if (row >= modelLayers - 1) {
      index = posn.q + modelLayers - 1;
    } else {
      index = -posn.s + modelLayers - 1;
    }

    return Arrays.asList(row, index);
  }
}
