package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the model for the image processing program. The model contains a hashmap of images to
 * store the images that. the user passes in or creates.
 */
public class Model implements IModel {
  private Map<String, Image> images;

  /** Constructor for the model. */
  public Model() {
    this.images = new HashMap<>();
  }

  @Override
  public void addImage(String name, Image image) {
    this.images.put(name, image);
  }

  @Override
  public Image getImage(String name) {
    return this.images.get(name);
  }

  @Override
  public boolean isInModel(String name) {
    return this.images.containsKey(name);
  }

  @Override
  public void removeImage(String name) {
    this.images.remove(name);
  }

  @Override
  public boolean containsImage(String name) {
    return this.images.containsKey(name);
  }

  @Override
  public void sideBySide(
      String leftImageName, String rightImageName, String destImageName, int percent) {
    Image leftImage = getImage(leftImageName).getLeftPercentageOfImage(percent);
    Image rightImage = getImage(rightImageName).getRightPercentageOfImage(100 - percent);
    Image sideBySide = addImagesTogether(leftImage, rightImage);
    addImage(destImageName, sideBySide);
  }

  @Override
  public void previewImage(String name, String destImageName, int percent, String operation) {
    Image og = getImage(name).getLeftPercentageOfImage(percent);
    switch (operation) {
      case "blur":
        Image blurImage = getImage(name).blur().getRightPercentageOfImage(100 - percent);
        Image blurPreview = addImagesTogether(og, blurImage);
        addImage(destImageName, blurPreview);
        break;
      case "sharpen":
        Image sharpenImage = getImage(name).sharpen().getRightPercentageOfImage(100 - percent);
        Image sharpenPreview = addImagesTogether(og, sharpenImage);
        addImage(destImageName, sharpenPreview);
        break;
      case "sepia":
        Image sepiaImage = getImage(name).sepia().getRightPercentageOfImage(100 - percent);
        Image sepiaPreview = addImagesTogether(og, sepiaImage);
        addImage(destImageName, sepiaPreview);
        break;
      case "luma":
        Image lumaImage = getImage(name).calculateValue().getRightPercentageOfImage(100 - percent);
        Image lumaPreview = addImagesTogether(og, lumaImage);
        addImage(destImageName, lumaPreview);
        break;
      case "color-correct":
        Image colorCorrectImage =
            getImage(name).colorCorrect().getRightPercentageOfImage(100 - percent);
        Image colorCorrectPreview = addImagesTogether(og, colorCorrectImage);
        addImage(destImageName, colorCorrectPreview);
        break;
      case "level-adjust":
        break;
      default:
        break;
    }
  }

  @Override
  public void previewImage(
      String name, String destImageName, int percent, String operation, int b, int m, int w) {
    if ("level-adjust".equals(operation)) {
      Image og = getImage(name).getLeftPercentageOfImage(percent);
      Image levelAdjustImage =
          getImage(name).levelAdjust(b, m, w).getRightPercentageOfImage(100 - percent);
      Image levelAdjustPreview = addImagesTogether(og, levelAdjustImage);
      addImage(destImageName, levelAdjustPreview);
    } else {
      previewImage(name, destImageName, percent, operation);
    }
  }

  @Override
  public void histogram(String name, String destImageName) {
    Image image = getImage(name);
    Image histogram = image.getHistogram();
    addImage(destImageName, histogram);
  }

  @Override
  public void colorCorrect(String name, String destImageName) {
    Image image = getImage(name);
    Image colorCorrect = image.colorCorrect();
    addImage(destImageName, colorCorrect);
  }

  @Override
  public void levelAdjust(int b, int m, int w, String name, String destImageName) {
    Image image = getImage(name);
    Image newImage = image.levelAdjust(b, m, w);
    addImage(destImageName, newImage);
  }

  @Override
  public void compressImage(String name, String destImageName, int factor) {
    Image image = getImage(name);
    Image newImage = image.compressImage(factor);
    addImage(destImageName, newImage);
  }

  /**
   * Helper method to add two images together. Adds a white line between the two images as well.
   *
   * @param leftImage image on the left
   * @param rightImage image on the right
   * @return the new image
   */
  private static Image addImagesTogether(Image leftImage, Image rightImage) {
    RGBPixel[][] leftMatrix = leftImage.getRGBPixelMatrix();
    RGBPixel[][] rightMatrix = rightImage.getRGBPixelMatrix();

    int height = leftImage.getHeight();
    int newWidth = leftImage.getWidth() + rightImage.getWidth();

    RGBPixel[][] newMatrix = new RGBPixel[height][newWidth + 1];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < leftImage.getWidth(); j++) {
        newMatrix[i][j] = leftMatrix[i][j];
      }
    }

    for (int i = 0; i < height; i++) {
      if (i % 3 == 0) {
        newMatrix[i][leftImage.getWidth()] = new RGBPixel(255, 255, 255);
      } else {
        newMatrix[i][leftImage.getWidth()] = new RGBPixel(255, 0, 255);
      }
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < rightImage.getWidth(); j++) {
        newMatrix[i][leftImage.getWidth() + 1 + j] = rightMatrix[i][j];
      }
    }

    return new RGBImage(height, newWidth + 1, newMatrix);
  }

  @Override
  public void brightenImage(int amount, String name, String destImageName) {
    Image image = getImage(name);
    Image newImage = image.brighten(amount);
    addImage(destImageName, newImage);
  }

  @Override
  public void blurImage(String name, String destImageName) {
    Image image = getImage(name);
    Image newImage = image.blur();
    addImage(destImageName, newImage);
  }

  @Override
  public void sharpenImage(String name, String destImageName) {
    Image image = getImage(name);
    Image newImage = image.sharpen();
    addImage(destImageName, newImage);
  }

  @Override
  public void sepiaImage(String name, String destImageName) {
    Image image = getImage(name);
    Image newImage = image.sepia();
    addImage(destImageName, newImage);
  }

  @Override
  public void redComponent(String name, String destImageName) {
    Image image = getImage(name);
    Image newImage = image.getRedComponent();
    addImage(destImageName, newImage);
  }

  @Override
  public void greenComponent(String name, String destImageName) {
    Image image = getImage(name);
    Image newImage = image.getGreenComponent();
    addImage(destImageName, newImage);
  }

  @Override
  public void blueComponent(String name, String destImageName) {
    Image image = getImage(name);
    Image newImage = image.getBlueComponent();
    addImage(destImageName, newImage);
  }

  @Override
  public void calculateValue(String name, String destImageName) {
    Image image = getImage(name);
    Image newImage = image.calculateValue();
    addImage(destImageName, newImage);
  }

  @Override
  public void calculateIntensity(String name, String destImageName) {
    Image image = getImage(name);
    Image newImage = image.calculateIntensity();
    addImage(destImageName, newImage);
  }

  @Override
  public void calculateLuma(String name, String destImageName) {
    Image image = getImage(name);
    Image newImage = image.calculateLuma();
    addImage(destImageName, newImage);
  }

  @Override
  public void horizontalFlip(String name, String destImageName) {
    Image image = getImage(name);
    Image newImage = image.horizontalFlip();
    addImage(destImageName, newImage);
  }

  @Override
  public void verticalFlip(String name, String destImageName) {
    Image image = getImage(name);
    Image newImage = image.verticalFlip();
    addImage(destImageName, newImage);
  }

  @Override
  public void splitRGB(
      String name, String redDestImageName, String greenDestImageName, String blueDestImageName) {
    Image image = getImage(name);
    Image redImage = image.getRedComponent();
    Image greenImage = image.getGreenComponent();
    Image blueImage = image.getBlueComponent();
    if (redImage == null || greenImage == null || blueImage == null) {
      System.out.println("Grayscale image cannot be split into RGB components.");
      return;
    }
    addImage(redDestImageName, redImage);
    addImage(greenDestImageName, greenImage);
    addImage(blueDestImageName, blueImage);
  }

  @Override
  public void combineRGB(
      String destImageName, String redImageName, String greenImageName, String blueImageName) {
    Image redImage = getImage(redImageName);
    Image greenImage = getImage(greenImageName);
    Image blueImage = getImage(blueImageName);
    Image newImage = redImage.combineRGBComponents(greenImage, blueImage);
    addImage(destImageName, newImage);
  }

  @Override
  public boolean isGrayScale(String s) {
    Image image = getImage(s);
    return image instanceof GrayScaleImage;
  }

  @Override
  public boolean isSameSize(String s1, String s2) {
    Image image1 = getImage(s1);
    Image image2 = getImage(s2);
    return image1.getHeight() == image2.getHeight() && image1.getWidth() == image2.getWidth();
  }

  @Override
  public void ditherImage(String currImage, String currImage1) {
    Image image = getImage(currImage);
    Image newImage = image.dither();
    addImage(currImage1, newImage);
  }
}
