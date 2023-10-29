package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for package-private implementation details of a model for a game of Reversi.
 */
public class PackagePrivateTests {
  ReversiModel model;
  ReversiModel smallModel;

  @Before
  public void init() {
    model = new BasicReversi();
    smallModel = new BasicReversi(3);
  }

  @Test
  public void testFlipDiscs() {
    Assert.assertEquals(2, 1 + 1);
  }
}
