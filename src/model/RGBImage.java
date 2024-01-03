package model;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;
import java.util.TreeSet;

/** Represents an image with RGB pixels. Extends Image to implement all the processing methods. */
public class RGBImage extends Image {
  private RGBPixel[][] pixels;

  /**
   * Constructs an RGBImage with the given height, width, and a pre-determined matrix of pixels.
   *
   * @param height the height of the image
   * @param width the width of the image
   * @param pixels the pixels of the image
   */
  public RGBImage(int height, int width, RGBPixel[][] pixels) {
    super(height, width);
    this.pixels = pixels;
  }

  /**
   * Constructs an RGBImage with the given height and width. Initializes the pixels to be empty.
   *
   * @param height the height of the image
   * @param width the width of the image
   */
  public RGBImage(int height, int width) {
    super(height, width);
    this.pixels = new RGBPixel[height][width];
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        this.pixels[row][col] = new RGBPixel();
      }
    }
  }

  /**
   * Additional constructor for RGBImage that takes in 3 Images. These 3 images should be in the
   * order of a red, green, blue image component.
   *
   * @param red red image component
   * @param green green image component
   * @param blue blue image component
   */
  public RGBImage(Image red, Image green, Image blue) {
    super(red.getHeight(), red.getWidth());
    this.pixels = new RGBPixel[red.getHeight()][red.getWidth()];
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        this.pixels[row][col] =
            new RGBPixel(red.getPixel(row, col), green.getPixel(row, col), blue.getPixel(row, col));
      }
    }
  }

  @Override
  public Image horizontalFlip() {
    Image newImage = new RGBImage(this.height, this.width);
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < (this.width / 2) + 1; col++) {
        // Swap the pixels on opposite sides of the middle column

        // get the pixels
        RGBPixel pixel1 = this.pixels[row][col];
        RGBPixel pixel2 = this.pixels[row][this.width - col - 1];

        // set the pixels

        newImage.setPixelRGB(row, col, pixel2);
        newImage.setPixelRGB(row, this.width - col - 1, pixel1);
      }
    }

    return newImage;
  }

  @Override
  public Image verticalFlip() {
    Image newImage = new RGBImage(this.height, this.width);

    for (int row = 0; row < (this.height / 2) + 1; row++) {
      for (int col = 0; col < this.width; col++) {
        // Swap the pixels on opposite sides of the middle row

        // Get the pixels
        RGBPixel pixel1 = this.pixels[row][col];
        RGBPixel pixel2 = this.pixels[this.height - row - 1][col];

        // Set the pixels
        newImage.setPixelRGB(row, col, pixel2);
        newImage.setPixelRGB(this.height - row - 1, col, pixel1);
      }
    }

    return newImage;
  }

  @Override
  protected void setPixel(int row, int col, int pixel) {
    // empty method, since RGBImage does not support this method and only grayscale does.
  }

  @Override
  protected void setPixelRGB(int row, int col, RGBPixel pixel) {
    this.pixels[row][col] = pixel;
  }

  @Override
  public int getPixel(int row, int col) {
    return 0;
  }

  @Override
  public RGBPixel getPixelRGB(int row, int col) {
    return this.pixels[row][col];
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof RGBImage)) {
      return false;
    }
    RGBImage other = (RGBImage) obj;
    if (this.height != other.height || this.width != other.width) {
      return false;
    }
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        RGBPixel thisPixel = this.pixels[row][col];
        RGBPixel otherPixel = other.pixels[row][col];

        // Compare red, green, and blue values for each pixel
        if (thisPixel.getRedComponent() != otherPixel.getRedComponent()
            || thisPixel.getGreenComponent() != otherPixel.getGreenComponent()
            || thisPixel.getBlueComponent() != otherPixel.getBlueComponent()) {
          //          System.out.println("Pixel values not equal at (" + row + ", " + col + ")");
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.height, this.width, this.pixels);
  }

  @Override
  public Image brighten(int value) {
    Image newImage = new RGBImage(this.height, this.width);
    if (value > 0) {
      this.brightenImage(value, newImage);
    } else {
      this.darkenImage(value, newImage);
    }
    return newImage;
  }

  private void brightenImage(int value, Image newImage) {
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        int redComponent = Math.min(255, this.getPixelRGB(row, col).getRedComponent() + value);
        int greenComponent = Math.min(255, this.getPixelRGB(row, col).getGreenComponent() + value);
        int blueComponent = Math.min(255, this.getPixelRGB(row, col).getBlueComponent() + value);
        RGBPixel modifiedPixel = new RGBPixel(redComponent, greenComponent, blueComponent);
        newImage.setPixelRGB(row, col, modifiedPixel);
      }
    }
  }

  private void darkenImage(int value, Image newImage) {
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        int redComponent = Math.max(0, this.getPixelRGB(row, col).getRedComponent() + value);
        int greenComponent = Math.max(0, this.getPixelRGB(row, col).getGreenComponent() + value);
        int blueComponent = Math.max(0, this.getPixelRGB(row, col).getBlueComponent() + value);
        RGBPixel modifiedPixel = new RGBPixel(redComponent, greenComponent, blueComponent);
        newImage.setPixelRGB(row, col, modifiedPixel);
      }
    }
  }

  @Override
  public Image blur() {
    Image newImage = new RGBImage(this.height, this.width);

    double[][] kernel = {
      {0.0625, 0.125, 0.0625},
      {0.125, 0.25, 0.125},
      {0.0625, 0.125, 0.0625}
    };
    this.applyFilter(kernel, newImage);
    return newImage;
  }

  /**
   * Helper method to apply a filter of a specific kernel to an image.
   *
   * @param kernel the kernel to apply
   * @param newImage the new image to apply the kernel to
   */
  private void applyFilter(double[][] kernel, Image newImage) {
    int padding = (kernel.length - 1) / 2;
    Image paddedImage = new RGBImage(this.height + 2 * padding, this.width + 2 * padding);
    for (int row = padding; row < this.height + padding; row++) {
      for (int col = padding; col < this.width + padding; col++) {
        paddedImage.setPixelRGB(row, col, this.getPixelRGB(row - padding, col - padding));
      }
    }

    for (int row = 0; row < newImage.height; row++) {
      for (int col = 0; col < newImage.width; col++) {
        double filteredSumRed = 0;
        double filteredSumGreen = 0;
        double filteredSumBlue = 0;

        for (int kernelRow = 0; kernelRow < kernel.length; kernelRow++) {
          for (int kernelCol = 0; kernelCol < kernel.length; kernelCol++) {
            int currRow = row + kernelRow;
            int currCol = col + kernelCol;

            filteredSumRed +=
                paddedImage.getPixelRGB(currRow, currCol).getRedComponent()
                    * kernel[kernelRow][kernelCol];

            filteredSumGreen +=
                paddedImage.getPixelRGB(currRow, currCol).getGreenComponent()
                    * kernel[kernelRow][kernelCol];

            filteredSumBlue +=
                paddedImage.getPixelRGB(currRow, currCol).getBlueComponent()
                    * kernel[kernelRow][kernelCol];
          }
        }
        filteredSumRed = Math.max(0, Math.min(255, filteredSumRed));
        filteredSumGreen = Math.max(0, Math.min(255, filteredSumGreen));
        filteredSumBlue = Math.max(0, Math.min(255, filteredSumBlue));

        RGBPixel pixel =
            new RGBPixel((int) filteredSumRed, (int) filteredSumGreen, (int) filteredSumBlue);
        newImage.setPixelRGB(row, col, pixel);
      }
    }
  }

  @Override
  public Image sharpen() {
    Image newImage = new RGBImage(this.height, this.width);

    double[][] kernel = {
      {-0.125, -0.125, -0.125, -0.125, -0.125},
      {-0.125, 0.25, 0.25, 0.25, -0.125},
      {-0.125, 0.25, 1.0, 0.25, -0.125},
      {-0.125, 0.25, 0.25, 0.25, -0.125},
      {-0.125, -0.125, -0.125, -0.125, -0.125}
    };
    this.applyFilter(kernel, newImage);
    return newImage;
  }

  @Override
  public Image sepia() {
    Image sepiaImage = new RGBImage(this.getHeight(), this.getWidth());

    for (int row = 0; row < this.getHeight(); row++) {
      for (int col = 0; col < this.getWidth(); col++) {
        int red =
            (int)
                (0.393 * this.getPixelRGB(row, col).getRedComponent()
                    + 0.769 * this.getPixelRGB(row, col).getGreenComponent()
                    + 0.189 * this.getPixelRGB(row, col).getBlueComponent());
        int green =
            (int)
                (0.349 * this.getPixelRGB(row, col).getRedComponent()
                    + 0.686 * this.getPixelRGB(row, col).getGreenComponent()
                    + 0.168 * this.getPixelRGB(row, col).getBlueComponent());
        int blue =
            (int)
                (0.272 * this.getPixelRGB(row, col).getRedComponent()
                    + 0.534 * this.getPixelRGB(row, col).getGreenComponent()
                    + 0.131 * this.getPixelRGB(row, col).getBlueComponent());

        red = Math.min(255, red);
        green = Math.min(255, green);
        blue = Math.min(255, blue);

        RGBPixel pixel = new RGBPixel(red, green, blue);
        sepiaImage.setPixelRGB(row, col, pixel);
      }
    }
    return sepiaImage;
  }

  @Override
  public Image getRedComponent() {
    Image redImage = new RGBImage(this.getHeight(), this.getWidth());

    for (int row = 0; row < this.getHeight(); row++) {
      for (int col = 0; col < this.getWidth(); col++) {
        int red = this.getPixelRGB(row, col).getRedComponent();
        int green = 0;
        int blue = 0;

        RGBPixel pixel = new RGBPixel(red, green, blue);
        redImage.setPixelRGB(row, col, pixel);
      }
    }
    return redImage;
  }

  @Override
  public Image getGreenComponent() {
    Image greenImage = new RGBImage(this.getHeight(), this.getWidth());

    for (int row = 0; row < this.getHeight(); row++) {
      for (int col = 0; col < this.getWidth(); col++) {
        int red = 0;
        int green = this.getPixelRGB(row, col).getGreenComponent();
        int blue = 0;

        RGBPixel pixel = new RGBPixel(red, green, blue);
        greenImage.setPixelRGB(row, col, pixel);
      }
    }
    return greenImage;
  }

  @Override
  public Image getBlueComponent() {
    Image blueImage = new RGBImage(this.getHeight(), this.getWidth());

    for (int row = 0; row < this.getHeight(); row++) {
      for (int col = 0; col < this.getWidth(); col++) {
        int red = 0;
        int green = 0;
        int blue = this.getPixelRGB(row, col).getBlueComponent();

        RGBPixel pixel = new RGBPixel(red, green, blue);
        blueImage.setPixelRGB(row, col, pixel);
      }
    }
    return blueImage;
  }

  @Override
  public Image calculateValue() {
    Image valueImage = new GrayScaleImage(this.getHeight(), this.getWidth());
    for (int row = 0; row < this.getHeight(); row++) {
      for (int col = 0; col < this.getWidth(); col++) {
        int red = this.getPixelRGB(row, col).getRedComponent();
        int green = this.getPixelRGB(row, col).getGreenComponent();
        int blue = this.getPixelRGB(row, col).getBlueComponent();
        int value = Math.max(red, Math.max(green, blue));

        valueImage.setPixel(row, col, value);
      }
    }
    return valueImage;
  }

  @Override
  public Image calculateIntensity() {
    Image intensityImage = new GrayScaleImage(this.getHeight(), this.getWidth());
    for (int row = 0; row < this.getHeight(); row++) {
      for (int col = 0; col < this.getWidth(); col++) {
        int red = this.getPixelRGB(row, col).getRedComponent();
        int green = this.getPixelRGB(row, col).getGreenComponent();
        int blue = this.getPixelRGB(row, col).getBlueComponent();
        int value = (int) (red + green + blue) / 3;

        intensityImage.setPixel(row, col, value);
      }
    }
    return intensityImage;
  }

  @Override
  public Image calculateLuma() {
    Image lumaImage = new GrayScaleImage(this.getHeight(), this.getWidth());
    for (int row = 0; row < this.getHeight(); row++) {
      for (int col = 0; col < this.getWidth(); col++) {
        int red = this.getPixelRGB(row, col).getRedComponent();
        int green = this.getPixelRGB(row, col).getGreenComponent();
        int blue = this.getPixelRGB(row, col).getBlueComponent();
        int value = (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);

        lumaImage.setPixel(row, col, value);
      }
    }
    return lumaImage;
  }

  @Override
  public Image combineRGBComponents(Image greenImage, Image blueImage) {
    Image newImage = new RGBImage(this.getHeight(), this.getWidth());
    for (int row = 0; row < this.getHeight(); row++) {
      for (int col = 0; col < this.getWidth(); col++) {

        int red = this.getPixelRGB(row, col).getRedComponent();
        int green = greenImage.getPixelRGB(row, col).getGreenComponent();
        int blue = blueImage.getPixelRGB(row, col).getBlueComponent();

        RGBPixel pixel = new RGBPixel(red, green, blue);
        newImage.setPixelRGB(row, col, pixel);
      }
    }
    return newImage;
  }

  @Override
  public Image getLeftPercentageOfImage(int percentage) {
    int height = this.getHeight();
    int newWidth = (int) (this.getWidth() * (percentage / 100.0));
    RGBPixel[][] newMatrix = new RGBPixel[height][newWidth];

    for (int i = 0; i < height; i++) {
      System.arraycopy(this.pixels[i], 0, newMatrix[i], 0, newWidth);
    }

    return new RGBImage(height, newWidth, newMatrix);
  }

  @Override
  public Image getRightPercentageOfImage(int percentage) {
    int height = this.getHeight();
    int width = this.getWidth();
    int newWidth = width * percentage / 100;
    int startCol = width - newWidth;
    RGBPixel[][] newMatrix = new RGBPixel[height][newWidth];

    for (int i = 0; i < height; i++) {
      System.arraycopy(this.pixels[i], startCol, newMatrix[i], 0, newWidth);
    }

    return new RGBImage(height, newWidth, newMatrix);
  }

  @Override
  public RGBPixel[][] getRGBPixelMatrix() {
    return this.pixels;
  }

  private GrayScaleImage[] splitChannel() {
    GrayScaleImage redChannel = new GrayScaleImage(this.getHeight(), this.getWidth());
    GrayScaleImage greenChannel = new GrayScaleImage(this.getHeight(), this.getWidth());
    GrayScaleImage blueChannel = new GrayScaleImage(this.getHeight(), this.getWidth());

    for (int row = 0; row < this.getHeight(); row++) {
      for (int col = 0; col < this.getWidth(); col++) {
        int red = this.getPixelRGB(row, col).getRedComponent();
        int green = this.getPixelRGB(row, col).getGreenComponent();
        int blue = this.getPixelRGB(row, col).getBlueComponent();

        redChannel.setPixel(row, col, red);
        greenChannel.setPixel(row, col, green);
        blueChannel.setPixel(row, col, blue);
      }
    }
    GrayScaleImage[] channels = {redChannel, greenChannel, blueChannel};
    return channels;
  }

  @Override
  public Image getHistogram() {
    GrayScaleImage[] channels = this.splitChannel();
    List<Integer> redValuesList = new ArrayList<>();
    List<Integer> greenValuesList = new ArrayList<>();
    List<Integer> blueValuesList = new ArrayList<>();

    for (int ch = 0; ch < 3; ch++) {
      for (int i = 0; i < channels[ch].getHeight(); i++) {
        for (int j = 0; j < channels[ch].getWidth(); j++) {
          if (ch == 0) {
            redValuesList.add(channels[ch].getPixel(i, j));
          } else if (ch == 1) {
            greenValuesList.add(channels[ch].getPixel(i, j));
          } else {
            blueValuesList.add(channels[ch].getPixel(i, j));
          }
        }
      }
    }

    Map<Integer, Integer> redFrequencyMap = getFrequency(redValuesList);
    Map<Integer, Integer> greenFrequencyMap = getFrequency(greenValuesList);
    Map<Integer, Integer> blueFrequencyMap = getFrequency(blueValuesList);

    double[][] normalizedData =
        getNormalizedColorData(redFrequencyMap, greenFrequencyMap, blueFrequencyMap);

    double[] normalizedRed = normalizedData[0];
    double[] normalizedGreen = normalizedData[1];
    double[] normalizedBlue = normalizedData[2];

    int width = 256;
    int height = 256;
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    Graphics2D g2d = image.createGraphics();
    drawGrid(g2d, width, height);
    plotNormalizedData(g2d, normalizedRed, Color.RED, width, height);
    plotNormalizedData(g2d, normalizedGreen, Color.GREEN, width, height);
    plotNormalizedData(g2d, normalizedBlue, Color.BLUE, width, height);

    g2d.dispose();

    Image newImage = new RGBImage(image.getHeight(), image.getWidth());

    for (int i = 0; i < newImage.getHeight(); i++) {
      for (int j = 0; j < newImage.getWidth(); j++) {
        Color color = new Color(image.getRGB(j, i));
        newImage.setPixelRGB(i, j, new RGBPixel(color.getRed(), color.getGreen(), color.getBlue()));
      }
    }

    return newImage;
  }

  @Override
  public Image colorCorrect() {
    GrayScaleImage[] channels = this.splitChannel();
    List<Integer> redValuesList = new ArrayList<>();
    List<Integer> greenValuesList = new ArrayList<>();
    List<Integer> blueValuesList = new ArrayList<>();

    for (int ch = 0; ch < 3; ch++) {
      for (int i = 0; i < channels[ch].getHeight(); i++) {
        for (int j = 0; j < channels[ch].getWidth(); j++) {
          if (ch == 0) {
            redValuesList.add(channels[ch].getPixel(i, j));
          } else if (ch == 1) {
            greenValuesList.add(channels[ch].getPixel(i, j));
          } else {
            blueValuesList.add(channels[ch].getPixel(i, j));
          }
        }
      }
    }

    Map<Integer, Integer> redFrequencyMap = getFrequency(redValuesList);
    Map<Integer, Integer> greenFrequencyMap = getFrequency(greenValuesList);
    Map<Integer, Integer> blueFrequencyMap = getFrequency(blueValuesList);

    int[] dataRed = new int[256];
    int[] dataGreen = new int[256];
    int[] dataBlue = new int[256];

    for (int i = 0; i < 256; i++) {
      dataRed[i] = redFrequencyMap.get(i);
      dataGreen[i] = greenFrequencyMap.get(i);
      dataBlue[i] = blueFrequencyMap.get(i);
    }

    int redMax = 0;
    int redIndex = 0;
    int greenMax = 0;
    int greenIndex = 0;
    int blueMax = 0;
    int blueIndex = 0;

    for (int i = 0; i < dataRed.length; i++) {
      if (dataRed[i] > redMax) {
        redMax = dataRed[i];
        redIndex = i;
      }
    }

    for (int i = 0; i < dataGreen.length; i++) {
      if (dataGreen[i] > greenMax) {
        greenMax = dataGreen[i];
        greenIndex = i;
      }
    }

    for (int i = 0; i < dataBlue.length; i++) {
      if (dataBlue[i] > blueMax) {
        blueMax = dataBlue[i];
        blueIndex = i;
      }
    }

    int average = (redIndex + greenIndex + blueIndex) / 3;
    int[] corrections = {redIndex - average, greenIndex - average, blueIndex - average};

    for (int ch = 0; ch < 3; ch++) {
      for (int i = 0; i < channels[ch].getHeight(); i++) {
        for (int j = 0; j < channels[ch].getWidth(); j++) {
          if (ch == 0) {
            int redValue = channels[ch].getPixel(i, j);
            redValue -= corrections[0];
            channels[ch].setPixel(i, j, redValue);
          } else if (ch == 1) {
            int greenValue = channels[ch].getPixel(i, j);
            greenValue -= corrections[1];
            channels[ch].setPixel(i, j, greenValue);
          } else {
            int blueValue = channels[ch].getPixel(i, j);
            blueValue -= corrections[2];
            channels[ch].setPixel(i, j, blueValue);
          }
        }
      }
    }

    RGBPixel[][] newImage = new RGBPixel[channels[0].getHeight()][channels[0].getWidth()];
    for (int i = 0; i < channels[0].getHeight(); i++) {
      for (int j = 0; j < channels[0].getWidth(); j++) {
        newImage[i][j] =
            new RGBPixel(
                channels[0].getPixel(i, j), channels[1].getPixel(i, j), channels[2].getPixel(i, j));
      }
    }

    return new RGBImage(channels[0].getHeight(), channels[0].getWidth(), newImage);
  }

  private static Map<Integer, Integer> getFrequency(List<Integer> valuesList) {
    Map<Integer, Integer> frequencyMap = new HashMap<>();

    for (int i = 0; i <= 255; i++) {
      frequencyMap.put(i, 0);
    }

    for (Integer redValue : valuesList) {
      frequencyMap.put(redValue, frequencyMap.get(redValue) + 1);
    }

    return frequencyMap;
  }

  @Override
  public Image levelAdjust(int black, int mid, int white) {
    double aA =
        black * black * (mid - white)
            - black * ((mid * mid) - (white * white))
            + (white * mid * mid)
            - (mid * white * white);
    double aAa = -black * (128 - 255) + 128 * white - 255 * mid;
    double aAb = black * black * (128 - 255) + 255 * mid * mid - 128 * white * white;
    double aAc =
        black * black * (((255 * mid) - (128 * white)))
            - black * (((255 * mid * mid) - (128 * white * white)));

    double a = aAa / aA;
    double b = aAb / aA;
    double c = aAc / aA;

    Image newImage = new RGBImage(this.getHeight(), this.getWidth());

    for (int row = 0; row < this.getHeight(); row++) {
      for (int col = 0; col < this.getWidth(); col++) {
        RGBPixel rgb = this.getPixelRGB(row, col);

        int red = (int) (a * Math.pow(rgb.getRedComponent(), 2) + b * rgb.getRedComponent() + c);
        int green =
            (int) (a * Math.pow(rgb.getGreenComponent(), 2) + b * rgb.getGreenComponent() + c);
        int blue = (int) (a * Math.pow(rgb.getBlueComponent(), 2) + b * rgb.getBlueComponent() + c);

        red = Math.min(255, Math.max(0, red));
        green = Math.min(255, Math.max(0, green));
        blue = Math.min(255, Math.max(0, blue));

        newImage.setPixelRGB(row, col, new RGBPixel(red, green, blue));
      }
    }

    return newImage;
  }

  @Override
  public Image dither() {
    Image grayScaleImage = this.calculateIntensity();
    for (int row = 0; row < this.getHeight(); row++) {
      for (int col = 0; col < this.getWidth(); col++) {

        int oldPixel = grayScaleImage.getPixel(row, col);
        int newPixel = oldPixel < 128 ? 0 : 255;
        grayScaleImage.setPixel(row, col, newPixel);

        int error = oldPixel - newPixel;

        if (col + 1 < this.getWidth()) {
          int pixel = grayScaleImage.getPixel(row, col + 1);
          pixel += (int) (error * (7.0 / 16.0));
          pixel = Math.min(255, Math.max(0, pixel));
          grayScaleImage.setPixel(row, col + 1, pixel);
        }

        if (row + 1 < this.getHeight() && col - 1 >= 0) {
          int pixel = grayScaleImage.getPixel(row + 1, col - 1);
          pixel += (int) (error * (3.0 / 16.0));
          pixel = Math.min(255, Math.max(0, pixel));
          grayScaleImage.setPixel(row + 1, col - 1, pixel);
        }

        if (row + 1 < this.getHeight()) {
          int pixel = grayScaleImage.getPixel(row + 1, col);
          pixel += (int) (error * (5.0 / 16.0));
          pixel = Math.min(255, Math.max(0, pixel));
          grayScaleImage.setPixel(row + 1, col, pixel);
        }

        if (row + 1 < this.getHeight() && col + 1 < this.getWidth()) {
          int pixel = grayScaleImage.getPixel(row + 1, col + 1);
          pixel += (int) (error * (1.0 / 16.0));
          pixel = Math.min(255, Math.max(0, pixel));
          grayScaleImage.setPixel(row + 1, col + 1, pixel);
        }
      }


    }
    return grayScaleImage;
  }

  @Override
  public Image compressImage(int threshold) {
    GrayScaleImage[] channels = this.splitChannel();
    int height = this.getHeight();
    int width = this.getWidth();
    // applyying the haar wavelet transformation on the image
    double[][] compressedRedChannel = channels[0].haarWaveletTransformer();
    double[][] compressedGreenChannel = channels[1].haarWaveletTransformer();
    double[][] compressedBlueChannel = channels[2].haarWaveletTransformer();

    // removing the threshold percent pixels from the transformed Image
    TreeSet<double[]> thresholdMap =
        GrayScaleImage.thresholdValues(
            compressedRedChannel, compressedGreenChannel, compressedBlueChannel, threshold);
    double[][] thresholdRedChannel =
        GrayScaleImage.applyThreshold(compressedRedChannel, thresholdMap, 0.0);
    double[][] thresholdGreenChannel =
        GrayScaleImage.applyThreshold(compressedGreenChannel, thresholdMap, 1.0);
    double[][] thresholdBlueChannel =
        GrayScaleImage.applyThreshold(compressedBlueChannel, thresholdMap, 2.0);

    // performing the inverse transformation
    GrayScaleImage invertRed = GrayScaleImage.invertTransform(thresholdRedChannel, height, width);
    GrayScaleImage invertGreen =
        GrayScaleImage.invertTransform(thresholdGreenChannel, height, width);
    GrayScaleImage invertBlue = GrayScaleImage.invertTransform(thresholdBlueChannel, height, width);
    // how to create the RGB image from the compressed channels?
    return new RGBImage(invertRed, invertGreen, invertBlue);
  }
}
