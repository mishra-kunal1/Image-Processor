package model;

/** Interface for the image processing model. */
public interface IModel {
  /**
   * Adds an image to the model.
   *
   * @param name the name of the image
   * @param image the image
   */
  void addImage(String name, Image image);

  /**
   * Gets an image from the model.
   *
   * @param name the name of the image
   * @return the image
   */
  Image getImage(String name);

  /**
   * Checks if an image is in the model.
   *
   * @param name the name of the image
   * @return true if the image is in the model, false otherwise
   */
  boolean isInModel(String name);

  /**
   * Removes an image from the model.
   *
   * @param name the name of the image
   */
  void removeImage(String name);

  /**
   * Checks if the model contains an image.
   *
   * @param name the name of the image
   * @return true if the model contains the image, false otherwise
   */
  boolean containsImage(String name);

  /**
   * Shows two images side by side.
   *
   * @param leftImageName left image name
   * @param rightImageName right image name
   * @param destImageName destination image name
   * @param percent percentage of the image to show
   */
  void sideBySide(String leftImageName, String rightImageName, String destImageName, int percent);

  /**
   * Previews an image to a given percentage and applied operation on an image.
   *
   * @param name the name of the image
   * @param destImageName the name of the destination image
   * @param percent the percentage of the image to preview
   * @param operation the operation to apply to the image
   */
  void previewImage(String name, String destImageName, int percent, String operation);

  /**
   * Overloaded PreviewImage method that takes in a black, mid, and white value. Specifically for
   * level-adjust.
   *
   * @param name the name of the image
   * @param destImageName the name of the destination image
   * @param percent the percentage of the image to preview
   * @param operation the operation to apply to the image
   * @param b black value
   * @param m mid value
   * @param w white value
   */
  void previewImage(
      String name, String destImageName, int percent, String operation, int b, int m, int w);

  /**
   * Gets the histogram color values of a given image.
   *
   * @param name the name of the image
   * @param destImageName the name of the destination histogram image
   */
  void histogram(String name, String destImageName);

  /**
   * Color corrects the image to the correct color values. (Matching peak color values).
   *
   * @param name the name of the image
   * @param destImageName the name of the destination image
   */
  void colorCorrect(String name, String destImageName);

  /**
   * Adjusts the color levels of an image to the given black, mid, and white values.
   *
   * @param b black value
   * @param m mid value
   * @param w white value
   * @param name the name of the image
   * @param destImageName the name of the destination image
   */
  void levelAdjust(int b, int m, int w, String name, String destImageName);

  /**
   * Compresses an image to a given factor.
   *
   * @param name the name of the image
   * @param destImageName the name of the destination image
   * @param factor the factor to compress the image by
   */
  void compressImage(String name, String destImageName, int factor);

  /**
   * Brightens an image.
   *
   * @param amount the amount to brighten the image by
   * @param name the name of the image
   * @param destImageName the name of the new image
   */
  void brightenImage(int amount, String name, String destImageName);

  /**
   * Blurs an image.
   *
   * @param name the name of the image
   * @param destImageName the name of the new image
   */
  void blurImage(String name, String destImageName);

  /**
   * Darkens an image.
   *
   * @param name the name of the image
   * @param destImageName the name of the new image
   */
  void sharpenImage(String name, String destImageName);

  /**
   * Creates sepia image from given image.
   *
   * @param name the name of the image
   * @param destImageName the name of the new image
   */
  void sepiaImage(String name, String destImageName);

  /**
   * Creates red component of an image.
   *
   * @param name the name of the image
   * @param destImageName the name of the new image
   */
  void redComponent(String name, String destImageName);

  /**
   * Creates green component of an image.
   *
   * @param name the name of the image
   * @param destImageName the name of the new image
   */
  void greenComponent(String name, String destImageName);

  /**
   * Creates blue component of an image.
   *
   * @param name the name of the image
   * @param destImageName the name of the new image
   */
  void blueComponent(String name, String destImageName);

  /**
   * Creates an image with only the value component.
   *
   * @param name the name of the image
   * @param destImageName the name of the new image
   */
  void calculateValue(String name, String destImageName);

  /**
   * Creates an image with only the intensity component.
   *
   * @param name the name of the image
   * @param destImageName the name of the new image
   */
  void calculateIntensity(String name, String destImageName);

  /**
   * Creates an image with only the luma component.
   *
   * @param name the name of the image
   * @param destImageName the name of the new image
   */
  void calculateLuma(String name, String destImageName);

  /**
   * Flips the image horizontally.
   *
   * @param name the name of the image
   * @param destImageName the name of the new image
   */
  void horizontalFlip(String name, String destImageName);

  /**
   * FLips the image vertically.
   *
   * @param name the name of the image
   * @param destImageName the name of the new image
   */
  void verticalFlip(String name, String destImageName);

  /**
   * Rotates the image 90 degrees clockwise.
   *
   * @param name the name of the image
   * @param redDestImageName the name of the red image
   * @param greenDestImageName the name of the green image
   * @param blueDestImageName the name of the blue image
   */
  void splitRGB(
      String name, String redDestImageName, String greenDestImageName, String blueDestImageName);

  /**
   * Combines the RGB components of an image.
   *
   * @param destImageName the name of the new image
   * @param redImageName the name of the red image
   * @param greenImageName the name of the green image
   * @param blueImageName the name of the blue image
   */
  void combineRGB(
      String destImageName, String redImageName, String greenImageName, String blueImageName);

  /**
   * Checks if the image is a gray scale image.
   *
   * @param s the name of the image
   * @return true if the image is a gray scale image, false otherwise
   */
  boolean isGrayScale(String s);

  /**
   * Check if two images are the same size.
   *
   * @param s1 the name of the first image
   * @param s2 the name of the second image
   * @return true if the images are the same size, false otherwise
   */
  boolean isSameSize(String s1, String s2);

  void ditherImage(String currImage, String currImage1);
}
