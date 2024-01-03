package controller;

import javax.imageio.ImageIO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;

import java.awt.Color;
import java.awt.image.BufferedImage;

import java.util.Scanner;

import model.GrayScaleImage;
import model.Image;
import model.RGBImage;
import model.RGBPixel;

/** This class helps in loading and saving images. */
public class ImageUtil {
  /**
   * Loads image from an image file into an Image matrix for processing.
   *
   * @param fileName name of the file.
   * @param ext extension of the file.
   * @return the image matrix.
   * @throws IOException if the file cannot be found.
   */
  public static Image loadImage(String fileName, String ext) throws IOException {
    File file = new File(fileName);

    BufferedImage image;
    if (ext.equals("ppm")) {
      image = loadPPMFile(fileName);
      if (image == null) {
        return null;
      }
    } else {
      image = ImageIO.read(file);
    }

    if (isGrayscale(image)) {
      Color[][] colors = new Color[image.getHeight()][image.getWidth()];

      for (int row = 0; row < colors.length; row++) {
        for (int col = 0; col < colors[0].length; col++) {
          colors[row][col] = new Color(image.getRGB(col, row));
        }
      }

      int[][] pixels = new int[image.getHeight()][image.getWidth()];

      for (int row = 0; row < pixels.length; row++) {
        for (int col = 0; col < pixels[0].length; col++) {
          int r = clamp(colors[row][col].getRed(), 0, 255);
          pixels[row][col] = r;
        }
      }

      return new GrayScaleImage(pixels.length, pixels[0].length, pixels);
    } else {
      Color[][] colors = new Color[image.getHeight()][image.getWidth()];

      for (int row = 0; row < colors.length; row++) {
        for (int col = 0; col < colors[0].length; col++) {
          colors[row][col] = new Color(image.getRGB(col, row));
        }
      }

      RGBPixel[][] pixels = new RGBPixel[image.getHeight()][image.getWidth()];

      for (int row = 0; row < pixels.length; row++) {
        for (int col = 0; col < pixels[0].length; col++) {
          int r = clamp(colors[row][col].getRed(), 0, 255);
          int g = clamp(colors[row][col].getGreen(), 0, 255);
          int b = clamp(colors[row][col].getBlue(), 0, 255);
          pixels[row][col] = new RGBPixel(r, g, b);
        }
      }

      return new RGBImage(pixels.length, pixels[0].length, pixels);
    }
  }

  /**
   * Helper method to load PPM file by reading the file and converting it into an image matrix.
   *
   * @param fileName name of the file.
   * @return the image matrix.
   */
  private static BufferedImage loadPPMFile(String fileName) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(fileName));
    } catch (FileNotFoundException e) {
      return null;
    }
    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      return null;
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    if (maxValue > 255) {
      return null;
    }

    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int r = sc.nextInt();
        r = clamp(r, 0, 255);
        int g = sc.nextInt();
        g = clamp(g, 0, 255);
        int b = sc.nextInt();
        b = clamp(b, 0, 255);
        int rgb = new Color(r, g, b).getRGB();
        bufferedImage.setRGB(col, row, rgb);
      }
    }

    return bufferedImage;
  }

  /**
   * Saves the image matrix into a ppm file.
   *
   * @param image the image matrix.
   * @param fileName name of the file.
   * @param ext extension of the file.
   * @throws IOException if the file cannot be found.
   */
  public static void convertToPPMImage(Image image, String fileName, String ext)
      throws IOException {
    FileWriter writer = new FileWriter(fileName);
    writer.write("P3\n");
    writer.write(image.getWidth() + " " + image.getHeight() + "\n");
    writer.write("255\n");
    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        if (image instanceof GrayScaleImage) {
          writer.write(image.getPixel(row, col) + "\n");
          writer.write(image.getPixel(row, col) + "\n");
          writer.write(image.getPixel(row, col) + "\n");
        } else {
          writer.write(image.getPixelRGB(row, col).getRedComponent() + "\n");
          writer.write(image.getPixelRGB(row, col).getGreenComponent() + "\n");
          writer.write(image.getPixelRGB(row, col).getBlueComponent() + "\n");
        }
      }
    }
    writer.close();
  }

  /**
   * Saves the image matrix into a Buffered Image. For saving and viewing image by GUI.
   *
   * @param matrix the image matrix.
   * @return BufferedImage of the given matrix. To be used by GUI. Or in saving by ImageIO.
   * @throws IOException if the file cannot be found.
   */
  public static BufferedImage convertToBuffered(Image matrix) throws IOException {
    BufferedImage image =
        new BufferedImage(matrix.getWidth(), matrix.getHeight(), BufferedImage.TYPE_INT_RGB);

    if (matrix instanceof GrayScaleImage) {
      for (int row = 0; row < matrix.getHeight(); row++) {
        for (int col = 0; col < matrix.getWidth(); col++) {

          int rgb =
              new Color(
                      matrix.getPixel(row, col),
                      matrix.getPixel(row, col),
                      matrix.getPixel(row, col))
                  .getRGB();
          image.setRGB(col, row, rgb);
        }
      }
    } else {
      for (int row = 0; row < matrix.getHeight(); row++) {
        for (int col = 0; col < matrix.getWidth(); col++) {
          int rgb =
              new Color(
                      matrix.getPixelRGB(row, col).getRedComponent(),
                      matrix.getPixelRGB(row, col).getGreenComponent(),
                      matrix.getPixelRGB(row, col).getBlueComponent())
                  .getRGB();
          image.setRGB(col, row, rgb);
        }
      }
    }
    return image;
  }

  /**
   * Saves the image matrix into an image file.
   *
   * @param matrix the image matrix.
   * @param fileName name of the file.
   * @param ext extension of the file.
   * @throws IOException if the file cannot be found.
   */
  public static void convertToImage(Image matrix, String fileName, String ext) throws IOException {
    if (matrix == null) {
      return;
    }
    BufferedImage image = convertToBuffered(matrix);

    File outputFile = new File(fileName);
    ImageIO.write(image, ext, outputFile);
  }

  /**
   * Helper method to check if the image is grayscale. Image is grayscale if {R,G,B} components of
   * image are equal.
   *
   * @param image the image matrix.
   * @return true if the image is grayscale, false otherwise.
   */
  private static boolean isGrayscale(BufferedImage image) {
    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        Color c = new Color(image.getRGB(col, row));
        if (c.getRed() != c.getGreen() || c.getRed() != c.getBlue()) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Helper method to clamp the value between min and max between 0-255.
   *
   * @param val value to be clamped.
   * @param min minimum value.
   * @param max maximum value.
   * @return the clamped value.
   */
  private static int clamp(int val, int min, int max) {
    return Math.max(min, Math.min(max, val));
  }
}
