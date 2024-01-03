package view;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/** Test class for the View class. */
public class ViewTest {

  private ByteArrayOutputStream outContent;
  private View view;

  private String normalizeNewLines(String s) {
    return s.replace("\r\n", "\n");
  }

  @Before
  public void setUp() {
    outContent = new ByteArrayOutputStream();
    view = new View(new PrintStream(outContent));
  }

  @Test
  public void testShowPrompt() {
    view.showPrompt("Enter your name: ");
    assertEquals("Enter your name: ", normalizeNewLines(outContent.toString()));
  }

  @Test
  public void testShowString() {
    view.showString("Hello, World!");
    assertEquals("Hello, World!\n", normalizeNewLines(outContent.toString()));
  }

  @Test
  public void testShowDetails() {
    view.showDetails();
    String expectedDetails =
        "rgb-split: split an image into three separate images\n"
            + "rgb-combine: combine three images into one\n"
            + "brighten: increase/decrease the brightness of an image, increment may be "
            + "positive (brightening) or negative (darkening)\n"
            + "red-component: create an image with the red-component of the image\n"
            + "green-component: create an image with the green-component of the image\n"
            + "blue-component: create an image with the blue-component of the image\n"
            + "value-component: create an image with the maximum value of the three "
            + "components for each pixel\n"
            + "luma-component: create an image with the luma of the three components for "
            + "each pixel, useful for converting image to grayscale\n"
            + "intensity-component: create an image with the average of the three components "
            + "for each pixel\n"
            + "horizontal-flip: flip an image horizontally\n"
            + "vertical-flip: flip an image vertically\n"
            + "blur: blur an image\n"
            + "sharpen: sharpen an image\n"
            + "sepia: apply a sepia filter to an image\n"
            + "histogram: create a histogram of color values of an image\n"
            + "color-correct: color correct an image by aligning the peaks of the histogram\n"
            + "level-adjust: adjust the levels of an image, by the given b m w values\n"
            + "compress: compresses an image by the given percentage\n"
            + "run: runs a script of commands from a file\n";

    assertEquals(expectedDetails, normalizeNewLines(outContent.toString()));
  }

  @Test
  public void testShowOptions() {
    view.showOptions();
    String expectedOptions =
        "Command Menu: \n"
            + "load <fileName> <name>: load an image from a file\n"
            + "save <destFileName> <name>: save an image to a file\n"
            + "rgb-split <name> <nameRed> <nameGreen> <nameBlue>: split an image into three"
            + " separate images\n"
            + "rgb-combine <name> <destFileNameRed> <destFileNameGreen> <destFileNameBlue>: "
            + "combine three images into one\n"
            + "brighten <increment> <name> <destImageName> | action = brighten: increase/"
            + "decrease the brightness of an image\n"
            + "compress <percentage> <name> <destImageName> | action = compress: "
            + "compress the image by a given percentage\n"
            + "level-adjust <value> <value> <value> <name> <destImageName> |"
            + " action = level-adjust: "
            + "black value, mid value, white value respectively to adjust the levels of image\n"
            + "<action> <name> <destImageName>: apply an action to an image\n"
            + "<action> = red-component | green-component | blue-component | value-component "
            + "| luma-component | intensity-component | horizontal-flip | vertical-flip "
            + "| blur | sharpen | sepia | histogram | color-correct\n"
            + "run <scriptFileName>: run a script of commands from a file\n"
            + "man: show the manual with action descriptions\n"
            + "exit: exit the program\n";
    assertEquals(expectedOptions, normalizeNewLines(outContent.toString()));
  }

  @Test
  public void testShowStringSuccess() {
    view.showStringSuccess("Operation was successful!");
    assertEquals("Operation was successful!\n", normalizeNewLines(outContent.toString()));
  }

  @Test
  public void testShowStringError() {
    view.showStringError("An error occurred!");
    assertEquals("An error occurred!\n", normalizeNewLines(outContent.toString()));
  }

  @Test
  public void testShowOptionError() {
    view.showOptionError();
    String expectedError = "\nInvalid option. Please try again.\nmenu: show the command menu\n";
    assertEquals(expectedError, normalizeNewLines(outContent.toString()));
  }
}
