package model;

import java.awt.Graphics2D;
import java.awt.Color;
import java.util.Map;

/**
 * Abstract class for image. That implements IME to provide the methods that all images must have.
 */
public abstract class Image implements IME {
  protected int height;
  protected int width;

  public Image(int height, int width) {
    this.height = height;
    this.width = width;
  }

  public int getWidth() {
    return this.width;
  }

  public int getHeight() {
    return this.height;
  }

  public abstract Image horizontalFlip();

  protected abstract void setPixel(int row, int col, int pixel);

  protected abstract void setPixelRGB(int row, int col, RGBPixel pixel);

  public abstract int getPixel(int row, int col);

  public abstract RGBPixel getPixelRGB(int row, int col);

  public abstract boolean equals(Object other);

  public abstract int hashCode();

  public abstract Image verticalFlip();

  public abstract Image brighten(int value);

  public abstract Image blur();

  public abstract Image sharpen();

  public abstract Image sepia();

  public abstract Image getRedComponent();

  public abstract Image getGreenComponent();

  public abstract Image getBlueComponent();

  public abstract Image calculateValue();

  public abstract Image calculateIntensity();

  public abstract Image calculateLuma();

  public abstract Image combineRGBComponents(Image greenImage, Image blueImage);

  public abstract Image getLeftPercentageOfImage(int percentage);

  public abstract Image getRightPercentageOfImage(int percentage);

  public abstract RGBPixel[][] getRGBPixelMatrix();

  public abstract Image compressImage(int compressionPercentage);

  public abstract Image getHistogram();

  public abstract Image colorCorrect();

  public abstract Image levelAdjust(int b, int m, int w);

  static double[][] getNormalizedColorData(
      Map<Integer, Integer> redFrequencyMap,
      Map<Integer, Integer> greenFrequencyMap,
      Map<Integer, Integer> blueFrequencyMap) {
    int[] dataRed = new int[256];
    int[] dataGreen = new int[256];
    int[] dataBlue = new int[256];

    for (int i = 0; i < 256; i++) {
      dataRed[i] = redFrequencyMap.getOrDefault(i, 0);
      dataGreen[i] = greenFrequencyMap.getOrDefault(i, 0);
      dataBlue[i] = blueFrequencyMap.getOrDefault(i, 0);
    }

    int redMax = findMax(dataRed);
    int greenMax = findMax(dataGreen);
    int blueMax = findMax(dataBlue);

    int maxFrequency = Math.max(redMax, Math.max(greenMax, blueMax));

    double[] normalizedRed = normalizeArray(dataRed, maxFrequency);
    double[] normalizedGreen = normalizeArray(dataGreen, maxFrequency);
    double[] normalizedBlue = normalizeArray(dataBlue, maxFrequency);

    return new double[][] {normalizedRed, normalizedGreen, normalizedBlue};
  }

  private static int findMax(int[] data) {
    int max = 0;
    for (int value : data) {
      if (value > max) {
        max = value;
      }
    }
    return max;
  }

  private static double[] normalizeArray(int[] data, int overallMax) {
    double[] normalized = new double[data.length];
    for (int i = 0; i < data.length; i++) {
      normalized[i] = (double) data[i] / overallMax;
    }
    return normalized;
  }

  static void drawGrid(Graphics2D g2d, int width, int height) {
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, width, height);

    int[] cellSizes = {11, 12, 12, 12, 12};

    int lineThickness = 1;

    g2d.setColor(new Color(220, 220, 220));

    int xPos = 0;
    for (int x = 0; x < 20; x++) {
      int cellSize = cellSizes[x % cellSizes.length];
      xPos += cellSize;
      g2d.drawLine(xPos, 0, xPos, 256);
      xPos += lineThickness;
    }

    int yPos = 255;
    for (int y = 0; y < 20; y++) {
      int cellSize = cellSizes[y % cellSizes.length];
      yPos -= cellSize;
      g2d.drawLine(0, yPos, 255, yPos);
      yPos -= lineThickness;
    }
  }

  static void plotNormalizedData(
      Graphics2D g2d, double[] normalizedData, Color color, int width, int height) {
    g2d.setColor(color);
    int prevX = 0;
    int prevY = height - (int) (normalizedData[0] * (height)) - 1;

    for (int i = 1; i < normalizedData.length; i++) {
      int x = i;
      int y = height - (int) (normalizedData[i] * (height)) - 1;
      g2d.drawLine(prevX, prevY, x, y);
      prevX = x;
      prevY = y;
    }
  }

  public abstract Image dither();

}
