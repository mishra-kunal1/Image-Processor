package model;

import org.junit.Test;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import controller.ImageUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/** Test class for ImageLoader. */
public class ImageUtilTest {

  @Test
  public void testLoadImageWithInvalidExt() {
    try {
      ImageUtil.loadImage("testResources/testImage.jpg", "invalidExt");
      fail("Expected IOException to be thrown");
    } catch (IOException e) {
      // Expected exception
    }
  }

  @Test
  public void testSaveAndLoadImage() {
    BufferedImage testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
    testImage.setRGB(0, 0, new Color(100, 100, 100).getRGB());
    testImage.setRGB(1, 0, new Color(100, 100, 150).getRGB());
    testImage.setRGB(0, 1, new Color(150, 100, 100).getRGB());
    testImage.setRGB(1, 1, new Color(100, 150, 100).getRGB());

    String fileName = "test/model/tempTestImage.jpg";

    try {
      ImageIO.write(testImage, "jpg", new File(fileName));
      Image loadedImage = ImageUtil.loadImage(fileName, "jpg");
      assertNotNull(loadedImage);

      // Delete the test file after testing
      new File(fileName).delete();
    } catch (IOException e) {
      fail("Unexpected IOException");
    }
  }

  @Test
  public void testLoadPPM() throws IOException {
    String fileName = "test/model/tempTestPPM.ppm";
    FileWriter writer = new FileWriter(fileName);
    writer.write(
        "P3\n"
            + "# Created by GIMP version 2.10.20 PNM plug-in\n"
            + "2 2\n"
            + "255\n"
            + "101\n"
            + "90\n"
            + "58\n"
            + "103\n"
            + "92\n"
            + "62\n"
            + "110\n"
            + "255\n"
            + "66\n"
            + "104\n"
            + "91\n"
            + "90\n");

    writer.close();
    Image img = ImageUtil.loadImage(fileName, "ppm");

    new File(fileName).delete();

    assert img != null;
    assertEquals(2, img.getWidth());
    assertEquals(2, img.getHeight());

    assertEquals(101, img.getPixelRGB(0, 0).getRedComponent());
    assertEquals(90, img.getPixelRGB(0, 0).getGreenComponent());
    assertEquals(58, img.getPixelRGB(0, 0).getBlueComponent());

    assertEquals(104, img.getPixelRGB(1, 1).getRedComponent());
    assertEquals(91, img.getPixelRGB(1, 1).getGreenComponent());
    assertEquals(90, img.getPixelRGB(1, 1).getBlueComponent());
  }

  @Test
  public void testSavePPM() throws IOException {
    Image rgb = new RGBImage(1, 1);
    rgb.setPixelRGB(0, 0, new RGBPixel(100, 100, 100));
    String fileName = "test/model/tempTestPPM.ppm";
    ImageUtil.convertToPPMImage(rgb, fileName, "ppm");

    File file = new File(fileName);

    assertTrue(file.exists());
    assertEquals("[P3, 1 1, 255, 100, 100, 100]", Files.readAllLines(Path.of(fileName)).toString());
    // Delete the test file after testing
    new File(fileName).delete();
  }
}
