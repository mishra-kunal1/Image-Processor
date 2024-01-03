package model;

/**
 * The IME (Image Manipulation and Enhancement) interface defines a set of image processing
 * operations that can be applied to an image. Implementing classes are expected to provide the
 * functionality to perform these operations on an image.
 */
public interface IME {
  /**
   * Performs a horizontal flip on the image.
   *
   * @return A new image after the horizontal flip.
   */
  Image horizontalFlip();

  /**
   * Performs a vertical flip on the image.
   *
   * @return A new image after the vertical flip.
   */
  Image verticalFlip();

  /**
   * Increases the brightness of the image by the specified value.
   *
   * @param value The amount by which to increase the brightness.
   * @return A new image with increased brightness.
   */
  Image brighten(int value);

  /**
   * Applies a blur effect to the image.
   *
   * @return A new image with a blur effect applied.
   */
  Image blur();

  /**
   * Applies a sepia tone effect to the image.
   *
   * @return A new image with a sepia tone effect applied.
   */
  Image sepia();

  /**
   * Applies a sharpening effect to the image.
   *
   * @return A new image with a sharpening effect applied.
   */
  Image sharpen();

  /**
   * Returns the red component of the image.
   *
   * @return A new image with only the red component
   */
  Image getRedComponent();

  /**
   * Returns the green component of the image.
   *
   * @return A new image with only the green component
   */
  Image getGreenComponent();

  /**
   * Returns the blue component of the image.
   *
   * @return A new image with only the blue component
   */
  Image getBlueComponent();

  /**
   * Returns the value component(max value of RGB) of the image.
   *
   * @return A new image with only the value component
   */
  Image calculateValue();

  /**
   * Returns the intensity (average of all values) component of the image.
   *
   * @return A new image with only the intensity component
   */
  Image calculateIntensity();

  /**
   * Returns the luma component of the image.
   *
   * @return A new image with only the luma component
   */
  Image calculateLuma();

  /**
   * Returns the image with the green and blue components combined.
   *
   * @return A new image with the green and blue components combined
   */
  Image combineRGBComponents(Image greenImage, Image blueImage);

  /**
   * Gets the left percentage of the image. Means the percentage of the image from the left side.
   * 50% means the left half of the image.
   *
   * @param percentage the percentage of the image to get.
   * @return the left percentage of the image.
   */
  Image getLeftPercentageOfImage(int percentage);

  /**
   * Gets the right percentage of the image. Means the percentage of the image from the right side.
   * 50% means the right half of the image.
   *
   * @param percentage the percentage of the image to get.
   * @return the right percentage of the image.
   */
  Image getRightPercentageOfImage(int percentage);

  /**
   * Gets the Pixel matrix for the image. Specifically RGBPixel Matrix.
   *
   * @return the RGBPixel matrix for the image.
   */
  RGBPixel[][] getRGBPixelMatrix();

  /**
   * Compresses the image by the given percentage.
   *
   * @param compressionPercentage the percentage by which to compress the image.
   * @return the compressed image.
   */
  Image compressImage(int compressionPercentage);

  /**
   * Gets the histogram for the color distribution of an image.
   *
   * @return the histogram graph.
   */
  Image getHistogram();

  /**
   * Color corrects the image.
   *
   * @return the color corrected image.
   */
  Image colorCorrect();

  /**
   * Applies level adjustment using given values.
   *
   * @param b black (shadow)
   * @param m mid
   * @param w white (highlight)
   * @return the image after level adjustment
   */
  Image levelAdjust(int b, int m, int w);

  Image dither();
}
