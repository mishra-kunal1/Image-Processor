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
import java.util.Comparator;
import java.util.Iterator;

/**
 * Represents an image with an int pixels making it single channel. Extends Image to implement all
 * the processing methods.
 */
public class GrayScaleImage extends Image {
  private int[][] pixels;

  /**
   * Constructs a GrayScaleImage with the given height, width, and a pre-determined matrix of
   * pixels.
   *
   * @param height the height of the image
   * @param width the width of the image
   * @param pixels the pixels of the image
   */
  public GrayScaleImage(int height, int width, int[][] pixels) {
    super(height, width);
    this.pixels = pixels;
  }

  /**
   * Constructs a GrayScaleImage with the given height and width. Initializes the pixels to be
   * empty.
   *
   * @param height the height of the image
   * @param width the width of the image
   */
  public GrayScaleImage(int height, int width) {
    super(height, width);
    this.pixels = new int[height][width];
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        this.pixels[row][col] = 0;
      }
    }
  }

  @Override
  public Image horizontalFlip() {
    Image newImage = new GrayScaleImage(this.height, this.width);
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < (this.width / 2) + 1; col++) {
        // Swap the pixels on opposite sides of the middle column

        // get the pixels
        int pixel1 = this.pixels[row][col];
        int pixel2 = this.pixels[row][this.width - col - 1];

        // set the pixels

        newImage.setPixel(row, col, pixel2);
        newImage.setPixel(row, this.width - col - 1, pixel1);
      }
    }

    return newImage;
  }

  @Override
  public Image verticalFlip() {
    Image newImage = new GrayScaleImage(this.height, this.width);

    for (int row = 0; row < (this.height / 2) + 1; row++) {
      for (int col = 0; col < this.width; col++) {
        // Swap the rows on opposite sides of the middle row

        // Get the pixels
        int pixel1 = this.pixels[row][col];
        int pixel2 = this.pixels[this.height - row - 1][col];

        // Set the pixels
        newImage.setPixel(row, col, pixel2);
        newImage.setPixel(this.height - row - 1, col, pixel1);
      }
    }

    return newImage;
  }

  @Override
  public Image brighten(int value) {
    Image newImage = new GrayScaleImage(this.height, this.width);
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
        int modifiedPixel = Math.min(255, this.pixels[row][col] + value);
        newImage.setPixel(row, col, modifiedPixel);
      }
    }
  }

  private void darkenImage(int value, Image newImage) {
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        int modifiedPixel = Math.max(0, this.pixels[row][col] + value);
        newImage.setPixel(row, col, modifiedPixel);
      }
    }
  }

  @Override
  public Image blur() {
    Image newImage = new GrayScaleImage(this.height, this.width);
    double[][] kernel = {
      {0.0625, 0.125, 0.0625},
      {0.125, 0.25, 0.125},
      {0.0625, 0.125, 0.0625}
    };

    this.applyFilter(kernel, newImage);
    return newImage;
  }

  @Override
  public Image sharpen() {
    Image newImage = new GrayScaleImage(this.height, this.width);
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
                (0.393 * this.getPixel(row, col)
                    + 0.769 * this.getPixel(row, col)
                    + 0.189 * this.getPixel(row, col));
        int green =
            (int)
                (0.349 * this.getPixel(row, col)
                    + 0.686 * this.getPixel(row, col)
                    + 0.168 * this.getPixel(row, col));
        int blue =
            (int)
                (0.272 * this.getPixel(row, col)
                    + 0.534 * this.getPixel(row, col)
                    + 0.131 * this.getPixel(row, col));

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
    return null;
  }

  @Override
  public Image getGreenComponent() {
    return null;
  }

  @Override
  public Image getBlueComponent() {
    return null;
  }

  @Override
  public Image calculateValue() {
    return null;
  }

  @Override
  public Image calculateIntensity() {
    return null;
  }

  @Override
  public Image calculateLuma() {
    return null;
  }

  @Override
  public Image combineRGBComponents(Image greenImage, Image blueImage) {
    return null;
  }

  /**
   * Helper method to apply a filter of a specific kernel to an image.
   *
   * @param kernel the kernel to apply
   * @param newImage the new image to apply the kernel to
   */
  private void applyFilter(double[][] kernel, Image newImage) {
    // padded image
    int padding = (kernel.length - 1) / 2;
    Image paddedImage = new GrayScaleImage(this.height + 2 * padding, this.width + 2 * padding);

    // copying the contents of original image to padded image
    for (int row = padding; row < this.height + padding; row++) {
      for (int col = padding; col < this.width + padding; col++) {
        paddedImage.setPixel(row, col, this.getPixel(row - padding, col - padding));
      }
    }

    for (int row = 0; row < newImage.height; row++) {
      for (int col = 0; col < newImage.width; col++) {
        double filteredSum = 0;
        for (int kernelRow = 0; kernelRow < kernel.length; kernelRow++) {
          for (int kernelCol = 0; kernelCol < kernel.length; kernelCol++) {
            int currRow = row + kernelRow;
            int currCol = col + kernelCol;
            filteredSum += paddedImage.getPixel(currRow, currCol) * kernel[kernelRow][kernelCol];
          }
        }
        filteredSum = Math.max(0, Math.min(255, filteredSum));
        newImage.setPixel(row, col, (int) filteredSum);
      }
    }
  }

  @Override
  protected void setPixel(int row, int col, int pixel) {
    this.pixels[row][col] = pixel;
  }

  @Override
  protected void setPixelRGB(int row, int col, RGBPixel pixel) {
    return;
  }

  public int getPixel(int row, int col) {
    return this.pixels[row][col];
  }

  @Override
  public RGBPixel getPixelRGB(int row, int col) {
    return new RGBPixel();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true; // Same instance, so they are equal
    }

    if (!(obj instanceof GrayScaleImage)) {
      return false; // Not the same class or obj is null, so they are not equal
    }

    GrayScaleImage other = (GrayScaleImage) obj;

    // Compare width and height
    if (this.width != other.width || this.height != other.height) {
      return false;
    }

    // Compare pixel values
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        if (this.getPixel(i, j) != other.getPixel(i, j)) {
          //          System.out.println("Failed at" + i + " " + j);
          return false;
        }
      }
    }

    // If all comparisons passed, the objects are equal
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.width, this.height, this.pixels);
  }

  @Override
  public Image getLeftPercentageOfImage(int percentage) {
    int height = this.getHeight();
    int newWidth = (int) (this.getWidth() * (percentage / 100.0));
    int[][] newMatrix = new int[height][newWidth];

    for (int i = 0; i < height; i++) {
      System.arraycopy(this.pixels[i], 0, newMatrix[i], 0, newWidth);
    }

    return new GrayScaleImage(height, newWidth, newMatrix);
  }

  @Override
  public Image getRightPercentageOfImage(int percentage) {
    int height = this.getHeight();
    int width = this.getWidth();
    int newWidth = width * percentage / 100;
    int startCol = width - newWidth;
    int[][] newMatrix = new int[height][newWidth];

    for (int i = 0; i < height; i++) {
      System.arraycopy(this.pixels[i], startCol, newMatrix[i], 0, newWidth);
    }

    return new GrayScaleImage(height, newWidth, newMatrix);
  }

  @Override
  public RGBPixel[][] getRGBPixelMatrix() {
    RGBPixel[][] matrix = new RGBPixel[this.height][this.width];
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        int value = this.pixels[i][j];
        matrix[i][j] = new RGBPixel(value, value, value);
      }
    }
    return matrix;
  }

  @Override
  public Image getHistogram() {
    GrayScaleImage[] channels = new GrayScaleImage[3];
    channels[0] = this;
    channels[1] = this;
    channels[2] = this;

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
    GrayScaleImage[] channels = new GrayScaleImage[3];
    channels[0] = this;
    channels[1] = this;
    channels[2] = this;
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

  private static double[] normalizeArray(int[] data, int overallMax) {
    double[] normalized = new double[data.length];
    for (int i = 0; i < data.length; i++) {
      normalized[i] = (double) data[i] / overallMax;
    }
    return normalized;
  }

  @Override
  public Image dither() {
    return null;
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

    Image newImage = new GrayScaleImage(this.getHeight(), this.getWidth());

    for (int row = 0; row < this.getHeight(); row++) {
      for (int col = 0; col < this.getWidth(); col++) {
        int pixel = this.getPixel(row, col);

        int newPixel = (int) (a * Math.pow(pixel, 2) + b * pixel + c);

        newPixel = Math.min(255, Math.max(0, newPixel));

        newImage.setPixel(row, col, newPixel);
      }
    }

    return newImage;
  }

  @Override
  public Image compressImage(int threshold) {
    int height = this.getHeight();
    int width = this.getWidth();
    // applyying the haar wavelet transformation on the image
    double[][] compressedRedChannel = this.haarWaveletTransformer();
    double[][] compressedGreenChannel = this.haarWaveletTransformer();
    double[][] compressedBlueChannel = this.haarWaveletTransformer();

    // removing the threshold percent pixels from the transformed Image
    TreeSet<double[]> thresholdMap =
        thresholdValues(
            compressedRedChannel, compressedGreenChannel, compressedBlueChannel, threshold);
    double[][] thresholdRedChannel = applyThreshold(compressedRedChannel, thresholdMap, 0.0);
    double[][] thresholdGreenChannel = applyThreshold(compressedGreenChannel, thresholdMap, 1.0);
    double[][] thresholdBlueChannel = applyThreshold(compressedBlueChannel, thresholdMap, 2.0);

    // performing the inverse transformation
    GrayScaleImage invertRed = invertTransform(thresholdRedChannel, height, width);
    GrayScaleImage invertGreen = invertTransform(thresholdGreenChannel, height, width);
    GrayScaleImage invertBlue = invertTransform(thresholdBlueChannel, height, width);
    // how to create the RGB image from the compressed channels?
    return invertRed;
  }

  protected static double[][] applyThreshold(
      double[][] compressedChannel, TreeSet<double[]> thresholdMap, double channelValue) {
    for (int row = 0; row < compressedChannel.length; row++) {
      for (int col = 0; col < compressedChannel[0].length; col++) {
        double signValue = Math.abs(compressedChannel[row][col]) / compressedChannel[row][col];
        double[] arr = {Math.abs(compressedChannel[row][col]), row, col, signValue, channelValue};
        if (!(thresholdMap.contains(arr))) {
          compressedChannel[row][col] = 0.0;
        }
      }
    }
    return compressedChannel;
  }

  protected static GrayScaleImage invertTransform(
      double[][] compressedChannel, int height, int width) {
    int m = 2;
    while (m <= compressedChannel.length) {
      // System.out.println("Inverse transforming "+m+" columns ");
      for (int j = 0; j < m; j++) {
        double[] tempCol = new double[m];
        for (int k = 0; k < m; k++) {
          tempCol[k] = compressedChannel[k][j];
        }
        tempCol = inverseTransformSequence(tempCol, m);
        for (int k = 0; k < m; k++) {
          compressedChannel[k][j] = tempCol[k];
        }
      }
      // System.out.println("Inverse transforming "+m+" rows ");
      for (int i = 0; i < m; i++) {
        compressedChannel[i] = inverseTransformSequence(compressedChannel[i], m);
      }
      // write code to transform the columns as well

      m = m * 2;
    }
    // make the size of the compressed channel equal to the size of the original image
    GrayScaleImage finalInvertedChannel = unpadChannel(compressedChannel, height, width);
    return finalInvertedChannel;
  }

  protected static GrayScaleImage unpadChannel(
      double[][] compressedChannel, int originalHeight, int originalWidth) {

    GrayScaleImage unpaddedChannel = new GrayScaleImage(originalHeight, originalWidth);
    for (int i = 0; i < originalHeight; i++) {
      for (int j = 0; j < originalWidth; j++) {

        int roundedPixel = (int) Math.round(compressedChannel[i][j]);
        int finalPixel = Math.min(255, Math.max(0, roundedPixel));

        unpaddedChannel.setPixel(i, j, finalPixel);
      }
    }
    return unpaddedChannel;
  }

  private double[][] padImage() {
    int rows = this.height;
    int columns = this.width;

    int maxSize = Math.max(rows, columns);
    int n = 1;
    while (n < maxSize) {
      n *= 2;
    }

    double[][] paddedImage = new double[n][n];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (i < rows && j < columns) {
          paddedImage[i][j] = this.pixels[i][j];
        } else {
          paddedImage[i][j] = 0; // Fill with zeros for the padded area
        }
      }
    }

    return paddedImage;
  }

  private static double[] transformSequence(double[] row, int m) {

    while (m > 1) {
      List<Double> avg = new ArrayList<>();
      List<Double> diff = new ArrayList<>();
      int avg_index = 0;
      int diff_index = m / 2;
      for (int i = 0; i < m; i += 2) {
        double firstNum = row[i];
        double secondNum = row[i + 1];
        double av = (firstNum + secondNum) / (Math.sqrt(2));
        double dif = (firstNum - secondNum) / (Math.sqrt(2));
        avg.add(av);
        diff.add(dif);
      }

      avg.addAll(diff);

      for (int i = 0; i < m; i++) {
        row[i] = avg.get(i);
      }
      m = m / 2;
    }
    return row;
  }

  protected double[][] haarWaveletTransformer() {
    // pad the image
    double[][] paddedImage = this.padImage();
    int m = paddedImage.length;
    while (m > 1) {
      // transform first m rows
      // System.out.println("transforming "+m+" rows ");
      for (int i = 0; i < m; i++) {
        paddedImage[i] = transformSequence(paddedImage[i], m);
      }
      // transform first m columns
      // System.out.println("transforming "+m+" columns ");
      for (int j = 0; j < m; j++) {
        double[] tempCol = new double[m];
        for (int k = 0; k < m; k++) {
          tempCol[k] = paddedImage[k][j];
        }
        tempCol = transformSequence(tempCol, m);
        for (int k = 0; k < m; k++) {
          paddedImage[k][j] = tempCol[k];
        }
      }
      m = m / 2;
    }
    // remove the values which are very close to zero
    removeValuesCloseToZero(paddedImage);
    return paddedImage;
  }

  private void removeValuesCloseToZero(double[][] paddedImage) {
    for (int i = 0; i < paddedImage.length; i++) {
      for (int j = 0; j < paddedImage[i].length; j++) {
        if (Math.abs(paddedImage[i][j]) < 0.1) {
          paddedImage[i][j] = 0;
        }
      }
    }
  }

  protected static double[] inverseTransformSequence(double[] row, int n) {
    //    List<Double> avg = new ArrayList<>();
    //    List<Double> diff = new ArrayList<>();

    int m = 2;

    while (m <= n) {
      // System.out.print("elements "+m+" , ");
      List<Double> avg = new ArrayList<>();
      int start_index = 0;
      int diff_index = m / 2;
      for (int i = 0; i < m / 2; i++) {
        double firstNum = (row[start_index] + row[diff_index]) / Math.sqrt(2);
        double secondNum = (row[start_index] - row[diff_index]) / Math.sqrt(2);
        avg.add(firstNum);
        avg.add(secondNum);
        start_index++;
        diff_index++;
      }
      for (int j = 0; j < m; j++) {
        row[j] = avg.get(j);
      }
      m *= 2;
    }
    return row;
  }

  protected static TreeSet<double[]> thresholdValues(
      double[][] compressedRedChannel,
      double[][] compressedGreenChannel,
      double[][] compressedBlueChannel,
      int threshold) {
    // Finding non-zero values
    TreeSet<double[]> treeSet =
        findNonZeroValues(compressedRedChannel, compressedGreenChannel, compressedBlueChannel);
    //    System.out.println("The number of non zero pixels before thresholding is " +
    // treeSet.size());
    // Trimming the TreeSet to keep only the first finalNonZeroValues elements
    getFinalValues(treeSet, threshold);
    //    System.out.println("The number of non zero pixels after thresholding is " +
    // treeSet.size());
    // Creating a new matrix to store the thresholded values
    return treeSet;
  }

  private static TreeSet<double[]> findNonZeroValues(
      double[][] compressedRedChannel,
      double[][] compressedGreenChannel,
      double[][] compressedBlueChannel) {
    int rows = compressedRedChannel.length;
    int cols = compressedRedChannel[0].length;

    // Create a TreeSet to store the non-zero values
    TreeSet<double[]> treeSet =
        new TreeSet<>(Comparator.<double[], Double>comparing(arr -> arr[0]).reversed());
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (compressedRedChannel[i][j] != 0) {
          if (compressedRedChannel[i][j] > 0) {
            treeSet.add(new double[] {Math.abs(compressedRedChannel[i][j]), i, j, 1.0, 0.0});
          } else {
            treeSet.add(new double[] {Math.abs(compressedRedChannel[i][j]), i, j, -1.0, 0.0});
          }
        }
        if (compressedGreenChannel[i][j] != 0) {
          if (compressedGreenChannel[i][j] > 0) {
            treeSet.add(new double[] {Math.abs(compressedGreenChannel[i][j]), i, j, 1.0, 1.0});
          } else {
            treeSet.add(new double[] {Math.abs(compressedGreenChannel[i][j]), i, j, -1.0, 1.0});
          }
        }
        if (compressedBlueChannel[i][j] != 0) {
          if (compressedBlueChannel[i][j] > 0) {
            treeSet.add(new double[] {Math.abs(compressedBlueChannel[i][j]), i, j, 1.0, 2.0});
          } else {
            treeSet.add(new double[] {Math.abs(compressedBlueChannel[i][j]), i, j, -1.0, 2.0});
          }
        }
      }
    }

    return treeSet;
  }

  private static void getFinalValues(TreeSet<double[]> treeSet, int threshold) {

    int initialNonZeroValues = treeSet.size();
    int finalNonZeroValues = (int) Math.ceil(initialNonZeroValues * ((100.0 - threshold) / 100.0));

    // Trim the TreeSet to keep only the first finalNonZeroValues elements
    Iterator<double[]> iterator = treeSet.iterator();
    int count = 0;
    while (iterator.hasNext()) {
      if (count < finalNonZeroValues) {
        iterator.next();
        count++;
      } else {
        // remove the element after the threshold
        iterator.next();
        iterator.remove();
      }
    }
  }
}
