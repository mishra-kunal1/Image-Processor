package model;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static controller.ImageUtil.convertToImage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/** Test class for RGBImage. */
public class RGBImageTest {
  private Image rgbScaleSampleImage;
  private Image boundedImage;

  private static GrayScaleImage createFromIntArray(Integer[][] grayscaleValues) {
    int[][] grayscalePixels = new int[grayscaleValues.length][grayscaleValues[0].length];

    for (int i = 0; i < grayscaleValues.length; i++) {
      for (int j = 0; j < grayscaleValues[i].length; j++) {
        grayscalePixels[i][j] = grayscaleValues[i][j];
      }
    }

    return new GrayScaleImage(grayscaleValues.length, grayscaleValues[0].length, grayscalePixels);
  }

  private static RGBImage createRGBFromPixelArray(int[][][] pixelArray) {
    int height = pixelArray.length;
    int width = pixelArray[0].length;
    RGBPixel[][] pixels = new RGBPixel[height][width];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int[] rgb = pixelArray[row][col];
        RGBPixel rgbPixel = new RGBPixel(rgb[0], rgb[1], rgb[2]);
        pixels[row][col] = rgbPixel;
      }
    }

    return new RGBImage(height, width, pixels);
  }

  @Before
  public void setup() {

    int[][][] pixels = {
      {{200, 0, 0}, {180, 0, 0}, {0, 0, 0}, {160, 0, 0}, {140, 0, 0}},
      {{160, 0, 0}, {140, 0, 0}, {0, 0, 0}, {120, 0, 0}, {100, 0, 0}},
      {{120, 0, 0}, {100, 0, 0}, {0, 0, 0}, {80, 0, 0}, {60, 0, 0}},
      {{80, 0, 0}, {60, 0, 0}, {0, 0, 0}, {40, 0, 0}, {20, 0, 0}},
      {{40, 0, 0}, {20, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    // this image is created for plotting histograms and color correction
    // the red pixels in the image are clamped between 0-50,green between 50-100 and blue between
    // 100-150
    // the image is created in such a way that the histogram will have clear boundaries
    int[][][] boundedPixels = {
      {{28, 75, 134}, {8, 75, 117}, {37, 55, 103}, {25, 89, 109}, {16, 83, 119}},
      {{20, 97, 130}, {35, 80, 110}, {36, 80, 147}, {35, 63, 138}, {29, 79, 119}},
      {{38, 91, 112}, {47, 73, 110}, {45, 68, 113}, {36, 62, 122}, {32, 73, 126}},
      {{31, 61, 117}, {25, 72, 144}, {36, 99, 135}, {21, 72, 133}, {18, 74, 107}},
      {{26, 78, 124}, {25, 71, 105}, {24, 64, 109}, {34, 88, 135}, {18, 98, 123}}
    };

    rgbScaleSampleImage = createRGBFromPixelArray(pixels);
    boundedImage = createRGBFromPixelArray(boundedPixels);
  }

  @Test
  public void horizontalFlip() {
    IME flippedImage = rgbScaleSampleImage.horizontalFlip();
    int[][][] flippedPixels = {
      {{140, 0, 0}, {160, 0, 0}, {0, 0, 0}, {180, 0, 0}, {200, 0, 0}},
      {{100, 0, 0}, {120, 0, 0}, {0, 0, 0}, {140, 0, 0}, {160, 0, 0}},
      {{60, 0, 0}, {80, 0, 0}, {0, 0, 0}, {100, 0, 0}, {120, 0, 0}},
      {{20, 0, 0}, {40, 0, 0}, {0, 0, 0}, {60, 0, 0}, {80, 0, 0}},
      {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {20, 0, 0}, {40, 0, 0}}
    };

    IME testImage = createRGBFromPixelArray(flippedPixels);
    assertTrue(testImage.equals(flippedImage));
  }

  @Test
  public void verticalFlip() {
    IME flippedImage = rgbScaleSampleImage.verticalFlip();
    int[][][] flippedPixels = {
      {{40, 0, 0}, {20, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
      {{80, 0, 0}, {60, 0, 0}, {0, 0, 0}, {40, 0, 0}, {20, 0, 0}},
      {{120, 0, 0}, {100, 0, 0}, {0, 0, 0}, {80, 0, 0}, {60, 0, 0}},
      {{160, 0, 0}, {140, 0, 0}, {0, 0, 0}, {120, 0, 0}, {100, 0, 0}},
      {{200, 0, 0}, {180, 0, 0}, {0, 0, 0}, {160, 0, 0}, {140, 0, 0}}
    };
    IME testImage = createRGBFromPixelArray(flippedPixels);
    assertTrue(testImage.equals(flippedImage));
  }

  @Test
  public void brightenImage() {
    IME brightImage = rgbScaleSampleImage.brighten(60);
    int[][][] brightPixels = {
      {{255, 60, 60}, {240, 60, 60}, {60, 60, 60}, {220, 60, 60}, {200, 60, 60}},
      {{220, 60, 60}, {200, 60, 60}, {60, 60, 60}, {180, 60, 60}, {160, 60, 60}},
      {{180, 60, 60}, {160, 60, 60}, {60, 60, 60}, {140, 60, 60}, {120, 60, 60}},
      {{140, 60, 60}, {120, 60, 60}, {60, 60, 60}, {100, 60, 60}, {80, 60, 60}},
      {{100, 60, 60}, {80, 60, 60}, {60, 60, 60}, {60, 60, 60}, {60, 60, 60}}
    };

    IME testImage = createRGBFromPixelArray(brightPixels);
    assertTrue(testImage.equals(brightImage));
  }

  @Test
  public void darkenImage() {
    IME darkImage = rgbScaleSampleImage.brighten(-60);
    int[][][] darkPixels = {
      {{140, 0, 0}, {120, 0, 0}, {0, 0, 0}, {100, 0, 0}, {80, 0, 0}},
      {{100, 0, 0}, {80, 0, 0}, {0, 0, 0}, {60, 0, 0}, {40, 0, 0}},
      {{60, 0, 0}, {40, 0, 0}, {0, 0, 0}, {20, 0, 0}, {0, 0, 0}},
      {{20, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
      {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };

    IME testImage = createRGBFromPixelArray(darkPixels);
    assertTrue(testImage.equals(darkImage));
  }

  @Test
  public void extractRedComponent() {
    IME redImage = rgbScaleSampleImage.getRedComponent();
    int[][][] pixels = {
      {{200, 0, 0}, {180, 0, 0}, {0, 0, 0}, {160, 0, 0}, {140, 0, 0}},
      {{160, 0, 0}, {140, 0, 0}, {0, 0, 0}, {120, 0, 0}, {100, 0, 0}},
      {{120, 0, 0}, {100, 0, 0}, {0, 0, 0}, {80, 0, 0}, {60, 0, 0}},
      {{80, 0, 0}, {60, 0, 0}, {0, 0, 0}, {40, 0, 0}, {20, 0, 0}},
      {{40, 0, 0}, {20, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    IME testImage = createRGBFromPixelArray(pixels);
    assertTrue(redImage.equals(testImage));
  }

  @Test
  public void extractBlueComponent() {
    IME blueImage = rgbScaleSampleImage.getBlueComponent();
    int[][][] pixels = {
      {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
      {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
      {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
      {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
      {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };

    IME testImage = createRGBFromPixelArray(pixels);
    assertTrue(blueImage.equals(testImage));
  }

  @Test
  public void extractGreenComponent() {
    IME greenImage = rgbScaleSampleImage.getGreenComponent();
    int[][][] pixels = {
      {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
      {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
      {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
      {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
      {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };

    IME testImage = createRGBFromPixelArray(pixels);
    assertTrue(greenImage.equals(testImage));
  }

  @Test
  public void testCalculateValue() {
    IME valueImage = rgbScaleSampleImage.calculateValue();
    Integer[][] pixels = {
      {200, 180, 0, 160, 140},
      {160, 140, 0, 120, 100},
      {120, 100, 0, 80, 60},
      {80, 60, 0, 40, 20},
      {40, 20, 0, 0, 0}
    };

    IME testImage = createFromIntArray(pixels);
    assertTrue(valueImage.equals(testImage));
  }

  @Test
  public void testCalculateIntensity() {
    IME intensityImage = rgbScaleSampleImage.calculateIntensity();
    Integer[][] pixels = {
      {66, 60, 0, 53, 46},
      {53, 46, 0, 40, 33},
      {40, 33, 0, 26, 20},
      {26, 20, 0, 13, 6},
      {13, 6, 0, 0, 0}
    };

    IME testImage = createFromIntArray(pixels);
    assertTrue(intensityImage.equals(testImage));
  }

  @Test
  public void testCalculateLuma() {
    IME lumaImage = rgbScaleSampleImage.calculateLuma();
    Integer[][] pixels = {
      {42, 38, 0, 34, 29},
      {34, 29, 0, 25, 21},
      {25, 21, 0, 17, 12},
      {17, 12, 0, 8, 4},
      {8, 4, 0, 0, 0}
    };

    IME testImage = createFromIntArray(pixels);
    assertTrue(lumaImage.equals(testImage));
  }

  @Test
  public void testcombineRGBComponents() {
    IME combinedImage =
        rgbScaleSampleImage.combineRGBComponents(
            rgbScaleSampleImage.getGreenComponent(), rgbScaleSampleImage.getBlueComponent());
    assertTrue(combinedImage.equals(rgbScaleSampleImage));
  }

  @Test
  public void testSepia() {
    IME sepiaImage = rgbScaleSampleImage.sepia();
    int[][][] pixels = {
      {{78, 69, 54}, {70, 62, 48}, {0, 0, 0}, {62, 55, 43}, {55, 48, 38}},
      {{62, 55, 43}, {55, 48, 38}, {0, 0, 0}, {47, 41, 32}, {39, 34, 27}},
      {{47, 41, 32}, {39, 34, 27}, {0, 0, 0}, {31, 27, 21}, {23, 20, 16}},
      {{31, 27, 21}, {23, 20, 16}, {0, 0, 0}, {15, 13, 10}, {7, 6, 5}},
      {{15, 13, 10}, {7, 6, 5}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    IME testImage = createRGBFromPixelArray(pixels);
    assertTrue(sepiaImage.equals(testImage));
  }

  @Test
  public void testSharpen() {
    IME sharpenImage = rgbScaleSampleImage.sharpen();
    int[][][] sharpenPixels = {
      {{255, 0, 0}, {232, 0, 0}, {30, 0, 0}, {180, 0, 0}, {217, 0, 0}},
      {{255, 0, 0}, {255, 0, 0}, {72, 0, 0}, {187, 0, 0}, {232, 0, 0}},
      {{200, 0, 0}, {135, 0, 0}, {0, 0, 0}, {65, 0, 0}, {112, 0, 0}},
      {{127, 0, 0}, {82, 0, 0}, {0, 0, 0}, {12, 0, 0}, {37, 0, 0}},
      {{52, 0, 0}, {22, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    IME testImage = createRGBFromPixelArray(sharpenPixels);
    assertTrue(sharpenImage.equals(testImage));
  }

  @Test
  public void testBlur() {
    IME blurImage = rgbScaleSampleImage.blur();
    int[][][] modifiedBlurPixels = {
      {{101, 0, 0}, {97, 0, 0}, {58, 0, 0}, {78, 0, 0}, {75, 0, 0}},
      {{115, 0, 0}, {110, 0, 0}, {65, 0, 0}, {85, 0, 0}, {80, 0, 0}},
      {{85, 0, 0}, {80, 0, 0}, {45, 0, 0}, {55, 0, 0}, {50, 0, 0}},
      {{55, 0, 0}, {50, 0, 0}, {25, 0, 0}, {26, 0, 0}, {22, 0, 0}},
      {{26, 0, 0}, {22, 0, 0}, {8, 0, 0}, {6, 0, 0}, {5, 0, 0}}
    };
    IME testImage = createRGBFromPixelArray(modifiedBlurPixels);
    assertTrue(blurImage.equals(testImage));
  }

  @Test
  public void testZeroCompression() {
    IME compressedImage = rgbScaleSampleImage.compressImage(0);
    assertTrue(compressedImage.equals(rgbScaleSampleImage));
  }

  @Test
  public void testCompression() {
    IME compressedImage = rgbScaleSampleImage.compressImage(60);
    int[][][] pixels = {
      {{179, 0, 0}, {179, 0, 0}, {4, 0, 0}, {144, 0, 0}, {128, 0, 0}},
      {{159, 0, 0}, {159, 0, 0}, {0, 0, 0}, {124, 0, 0}, {108, 0, 0}},
      {{93, 0, 0}, {93, 0, 0}, {25, 0, 0}, {85, 0, 0}, {61, 0, 0}},
      {{73, 0, 0}, {73, 0, 0}, {5, 0, 0}, {65, 0, 0}, {41, 0, 0}},
      {{11, 0, 0}, {11, 0, 0}, {4, 0, 0}, {4, 0, 0}, {0, 0, 0}}
    };
    IME testImage = createRGBFromPixelArray(pixels);

    assertTrue(compressedImage.equals(testImage));
  }

  @Test
  public void testLeftPercentage() {
    IME leftImage = rgbScaleSampleImage.getLeftPercentageOfImage(60);
    int[][][] pixels = {
      {{200, 0, 0}, {180, 0, 0}, {0, 0, 0}},
      {{160, 0, 0}, {140, 0, 0}, {0, 0, 0}},
      {{120, 0, 0}, {100, 0, 0}, {0, 0, 0}},
      {{80, 0, 0}, {60, 0, 0}, {0, 0, 0}},
      {{40, 0, 0}, {20, 0, 0}, {0, 0, 0}}
    };
    IME testImage = createRGBFromPixelArray(pixels);
    assertTrue(leftImage.equals(testImage));
  }

  @Test
  public void testRightpercentage() {
    IME rightImage = rgbScaleSampleImage.getRightPercentageOfImage(60);
    int[][][] pixels = {
      {{0, 0, 0}, {160, 0, 0}, {140, 0, 0}},
      {{0, 0, 0}, {120, 0, 0}, {100, 0, 0}},
      {{0, 0, 0}, {80, 0, 0}, {60, 0, 0}},
      {{0, 0, 0}, {40, 0, 0}, {20, 0, 0}},
      {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    IME testImage = createRGBFromPixelArray(pixels);
    assertTrue(testImage.equals(rightImage));
  }

  // the test will create a histogram with clear boundaries
  // the bounded Image has redPixels in range 0-50,greenPixels in range 50-100 and bluePixels in
  // range 100-150
  // we can check visually  in histogram if all the channels are separated
  @Test
  public void testHistogram() {

    Image histogram = boundedImage.getHistogram();
    RGBPixel[][] mat = histogram.getRGBPixelMatrix();
    assertEquals(mat.length, 256);
    assertEquals(mat[0].length, 256);
    try {
      convertToImage(histogram, "test/model/testImages/testHistogram.png", "png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  // the test will create a histogram after color correction
  // the modified image will have pixels pushed in middle
  // we can check visually  in histogram if all the channels are pushed to middle
  @Test
  public void testColorCorrection() {
    Image correctedColors = boundedImage.colorCorrect();
    Image correctedHistogram = correctedColors.getHistogram();
    RGBPixel[][] mat = correctedHistogram.getRGBPixelMatrix();
    assertEquals(mat.length, 256);
    try {
      convertToImage(correctedHistogram, "test/model/testImages/correctedHistogram.png", "png");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  // the test will create histogrmas for different values of m,b and w
  // the histogram of all these images will be different and can be checked visually
  @Test
  public void testLevelAdjust() {
    for (int i = 0; i < 4; i++) {
      int temp = i * 30;
      Image levelColors = boundedImage.levelAdjust(temp, temp + 70, temp + 100);
      Image levelAdjustedHistogram = levelColors.getHistogram();
      String filename = "test/model/testImages/levelHistogram" + i + ".png";
      assertEquals(levelAdjustedHistogram.getRGBPixelMatrix().length, 256);
      try {
        convertToImage(levelAdjustedHistogram, filename, "png");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
