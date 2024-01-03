package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static controller.ImageUtil.convertToImage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/** Test class for GrayScaleImage. */
public class GrayScaleImageTest {
  private IME grayScaleSampleImage;
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

    Integer[][] grayscaleValues = {
      {200, 180, 0, 160, 140},
      {160, 140, 0, 120, 100},
      {120, 100, 0, 80, 60},
      {80, 60, 0, 40, 20},
      {40, 20, 0, 0, 0}
    };
    Integer[][] boundedPixels = {
      {2, 8, 7, 25, 16},
      {2, 3, 36, 35, 29},
      {3, 4, 45, 36, 32},
      {3, 2, 36, 21, 18},
      {2, 2, 24, 34, 18}
    };

    grayScaleSampleImage = createFromIntArray(grayscaleValues);
    boundedImage = createFromIntArray(boundedPixels);
  }

  @Test
  public void horizontalFlip() {
    IME flippedImage = grayScaleSampleImage.horizontalFlip();
    Integer[][] flippedPixels = {
      {140, 160, 0, 180, 200},
      {100, 120, 0, 140, 160},
      {60, 80, 0, 100, 120},
      {20, 40, 0, 60, 80},
      {0, 0, 0, 20, 40}
    };
    IME testImage = createFromIntArray(flippedPixels);
    assertTrue(testImage.equals(flippedImage));
  }

  @Test
  public void verticalFlip() {
    Image flippedImage = grayScaleSampleImage.verticalFlip();
    Integer[][] flippedPixels = {
      {40, 20, 0, 0, 0},
      {80, 60, 0, 40, 20},
      {120, 100, 0, 80, 60},
      {160, 140, 0, 120, 100},
      {200, 180, 0, 160, 140}
    };
    IME testImage = createFromIntArray(flippedPixels);
    assertTrue(testImage.equals(flippedImage));
  }

  @Test
  public void brightenImage() {
    // Brighten the grayScaleSampleImage with a parameter of 60
    Image brightenedImage = grayScaleSampleImage.brighten(60);

    // Define the expected pixel values for the brightened image
    Integer[][] brightenPixels = {
      {255, 240, 60, 220, 200},
      {220, 200, 60, 180, 160},
      {180, 160, 60, 140, 120},
      {140, 120, 60, 100, 80},
      {100, 80, 60, 60, 60}
    };
    IME testImage = createFromIntArray(brightenPixels);
    assertTrue(testImage.equals(brightenedImage));
  }

  @Test
  public void darkenImage() {
    // Darken the grayScaleSampleImage with a parameter of 60
    Image darkenedImage = grayScaleSampleImage.brighten(-60);

    // Define the expected pixel values for the darkened image after decreasing by 60
    Integer[][] darkenedPixels = {
      {140, 120, 0, 100, 80},
      {100, 80, 0, 60, 40},
      {60, 40, 0, 20, 0},
      {20, 0, 0, 0, 0},
      {0, 0, 0, 0, 0}
    };

    IME testImage = createFromIntArray(darkenedPixels);

    // Assert that the darkened image matches the expected darkened pixels array
    assertTrue(testImage.equals(darkenedImage));
  }

  // test blur,sharpen,sepia
  @Test
  public void blurImage() {

    Image blurredImage = grayScaleSampleImage.blur();

    Integer[][] blurPixels = {
      {101, 97, 58, 78, 75},
      {115, 110, 65, 85, 80},
      {85, 80, 45, 55, 50},
      {55, 50, 25, 26, 22},
      {26, 22, 8, 6, 5}
    };

    IME testImage = createFromIntArray(blurPixels);

    assertTrue(testImage.equals(blurredImage));
  }

  @Test
  public void sharpenImage() {

    Image sharpenImage = grayScaleSampleImage.sharpen();

    Integer[][] sharpenPixels = {
      {255, 232, 30, 180, 217},
      {255, 255, 72, 187, 232},
      {200, 135, 0, 65, 112},
      {127, 82, 0, 12, 37},
      {52, 22, 0, 0, 0}
    };

    IME testImage = createFromIntArray(sharpenPixels);

    assertTrue(testImage.equals(sharpenImage));
  }

  @Test
  public void sepiaImage() {

    Image sepiaImage = grayScaleSampleImage.sepia();

    int[][][] pixels = {
      {{255, 240, 187}, {243, 216, 168}, {0, 0, 0}, {216, 192, 149}, {189, 168, 131}},
      {{216, 192, 149}, {189, 168, 131}, {0, 0, 0}, {162, 144, 112}, {135, 120, 93}},
      {{162, 144, 112}, {135, 120, 93}, {0, 0, 0}, {108, 96, 74}, {81, 72, 56}},
      {{108, 96, 74}, {81, 72, 56}, {0, 0, 0}, {54, 48, 37}, {27, 24, 18}},
      {{54, 48, 37}, {27, 24, 18}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    IME testImage = createRGBFromPixelArray(pixels);

    assertTrue(testImage.equals(sepiaImage));
  }

  @Test
  public void extractRedComponent() {
    Assert.assertNull(grayScaleSampleImage.getRedComponent());
  }

  @Test
  public void extractBlueComponent() {
    Assert.assertNull(grayScaleSampleImage.getBlueComponent());
  }

  @Test
  public void extractGreenComponent() {
    Assert.assertNull(grayScaleSampleImage.getGreenComponent());
  }

  @Test
  public void testCalculateValue() {
    Assert.assertNull(grayScaleSampleImage.calculateValue());
  }

  @Test
  public void testCalculateIntensity() {
    Assert.assertNull(grayScaleSampleImage.calculateIntensity());
  }

  @Test
  public void testCalculateLuma() {
    Assert.assertNull(grayScaleSampleImage.calculateLuma());
  }

  @Test
  public void testCombineRGBComponents() {

    int[][][] pixels = {
      {{255, 240, 187}, {243, 216, 168}, {0, 0, 0}, {216, 192, 149}, {189, 168, 131}},
      {{216, 192, 149}, {189, 168, 131}, {0, 0, 0}, {162, 144, 112}, {135, 120, 93}},
      {{162, 144, 112}, {135, 120, 93}, {0, 0, 0}, {108, 96, 74}, {81, 72, 56}},
      {{108, 96, 74}, {81, 72, 56}, {0, 0, 0}, {54, 48, 37}, {27, 24, 18}},
      {{54, 48, 37}, {27, 24, 18}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    IME testImage = createRGBFromPixelArray(pixels);
    Assert.assertNull(
        grayScaleSampleImage.combineRGBComponents(
            testImage.getGreenComponent(), testImage.getBlueComponent()));
  }

  @Test
  public void testZeroCompression() {
    IME compressedImage = grayScaleSampleImage.compressImage(0);
    assertTrue(compressedImage.equals(grayScaleSampleImage));
  }

  @Test
  public void testCompression() {
    IME compressedImage = grayScaleSampleImage.compressImage(60);
    Integer[][] pixels = {
      {179, 179, 4, 144, 128},
      {159, 159, 0, 124, 108},
      {93, 93, 25, 85, 61},
      {73, 73, 5, 65, 41},
      {11, 11, 4, 4, 0}
    };
    IME testImage = createFromIntArray(pixels);

    assertTrue(compressedImage.equals(testImage));
  }

  @Test
  public void testLeftPercentage() {
    IME leftImage = grayScaleSampleImage.getLeftPercentageOfImage(60);
    Integer[][] pixels = {
      {200, 180, 0},
      {160, 140, 0},
      {120, 100, 0},
      {80, 60, 0},
      {40, 20, 0}
    };
    IME testImage = createFromIntArray(pixels);
    assertTrue(leftImage.equals(testImage));
  }

  @Test
  public void testRightpercentage() {
    IME rightImage = grayScaleSampleImage.getRightPercentageOfImage(60);
    Integer[][] pixels = {
      {
        0, 160, 140,
      },
      {
        0, 120, 100,
      },
      {
        0, 80, 60,
      },
      {
        0, 40, 20,
      },
      {
        0, 0, 0,
      }
    };
    IME testImage = createFromIntArray(pixels);
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
      convertToImage(histogram, "test/model/testImages/grayTestHistogram.png", "png");
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
      convertToImage(
          correctedHistogram, "test/model/testImages/grayTestCorrectedHistogram.png", "png");
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
      String filename = "test/model/testImages/grayTestLevelHistogram" + i + ".png";
      assertEquals(levelAdjustedHistogram.getRGBPixelMatrix().length, 256);
      try {
        convertToImage(levelAdjustedHistogram, filename, "png");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
