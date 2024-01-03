package model;

/** Represents a pixel with red, green, and blue components. */
public class RGBPixel {
  /** Components of the pixel. */
  private int redComponent;

  private int greenComponent;
  private int blueComponent;

  /** Default constructor a pixel with all components set to 0. */
  public RGBPixel() {
    this.redComponent = 0;
    this.greenComponent = 0;
    this.blueComponent = 0;
  }

  /**
   * Constructor for a pixel with given components.
   *
   * @param red red component
   * @param green green component
   * @param blue blue component
   */
  public RGBPixel(int red, int green, int blue) {
    red = Math.max(0, Math.min(255, red));
    green = Math.max(0, Math.min(255, green));
    blue = Math.max(0, Math.min(255, blue));
    this.redComponent = red;
    this.greenComponent = green;
    this.blueComponent = blue;
  }

  /**
   * Getter for red component.
   *
   * @return red component
   */
  public int getRedComponent() {
    return redComponent;
  }

  /**
   * Getter for blue component.
   *
   * @return blue component
   */
  public int getBlueComponent() {
    return blueComponent;
  }

  /**
   * Getter for green component.
   *
   * @return green component
   */
  public int getGreenComponent() {
    return greenComponent;
  }
}
