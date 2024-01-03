package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/** Test class for RGBPixel. */
public class RGBPixelTest {
  private RGBPixel rgbPixel;

  @Before
  public void setup() {
    this.rgbPixel = new RGBPixel(10, 15, 20);
  }

  @Test
  public void testGetRedComponent() {
    assertEquals(10, rgbPixel.getRedComponent());
  }

  @Test
  public void testGetGreenComponent() {
    assertEquals(15, rgbPixel.getGreenComponent());
  }

  @Test
  public void testGetBlueComponent() {
    assertEquals(20, rgbPixel.getBlueComponent());
  }

  @Test
  public void testDefaultConstructor() {
    RGBPixel defaultPixel = new RGBPixel();
    assertEquals(0, defaultPixel.getRedComponent());
    assertEquals(0, defaultPixel.getGreenComponent());
    assertEquals(0, defaultPixel.getBlueComponent());
  }
}
