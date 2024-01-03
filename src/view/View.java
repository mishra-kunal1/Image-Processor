package view;

import java.io.PrintStream;

/** This class is used to display information to the user. */
public class View implements IView {
  /** The output stream to display information to. */
  private PrintStream out;

  /**
   * Constructor for the view. Initializes the output stream.
   *
   * @param out The output stream to display information to.
   */
  public View(PrintStream out) {
    this.out = out;
  }

  /**
   * Displays a prompt to the user. Using print.
   *
   * @param s The prompt to display.
   */
  public void showPrompt(String s) {
    out.print(s);
  }

  /**
   * Displays a string to the user. Using println.
   *
   * @param s The string to display.
   */
  public void showString(String s) {
    out.println(s);
  }

  /** Displays details (manual) about the program commands to the user. */
  public void showDetails() {
    out.println("rgb-split: split an image into three separate images");
    out.println("rgb-combine: combine three images into one");
    out.println(
        "brighten: increase/decrease the brightness of an image, increment"
            + " may be positive (brightening) or negative (darkening)");
    out.println("red-component: create an image with the red-component of the image");
    out.println("green-component: create an image with the green-component of the image");
    out.println("blue-component: create an image with the blue-component of the image");
    out.println(
        "value-component: create an image with the maximum value of the three "
            + "components for each pixel");
    out.println(
        "luma-component: create an image with the luma of the three components for each "
            + "pixel, useful for converting image to grayscale");
    out.println(
        "intensity-component: create an image with the average of the three "
            + "components for each pixel");
    out.println("horizontal-flip: flip an image horizontally");
    out.println("vertical-flip: flip an image vertically");
    out.println("blur: blur an image");
    out.println("sharpen: sharpen an image");
    out.println("sepia: apply a sepia filter to an image");
    out.println("histogram: create a histogram of color values of an image");
    out.println("color-correct: color correct an image by aligning the peaks of the histogram");
    out.println("level-adjust: adjust the levels of an image, by the given b m w values");
    out.println("compress: compresses an image by the given percentage");
    out.println("run: runs a script of commands from a file");
  }

  /** Displays the options for the program to the user. */
  public void showOptions() {
    out.println("Command Menu: ");
    out.println("load <fileName> <name>: load an image from a file");
    out.println("save <destFileName> <name>: save an image to a file");
    out.println(
        "rgb-split <name> <nameRed> <nameGreen> <nameBlue>: split an image into three "
            + "separate images");
    out.println(
        "rgb-combine <name> <destFileNameRed> <destFileNameGreen> <destFileNameBlue>: "
            + "combine three images into one");
    out.println(
        "brighten <increment> <name> <destImageName> | action = brighten: "
            + "increase/decrease the brightness of an image");
    out.println(
        "compress <percentage> <name> <destImageName> | action = compress: "
            + "compress the image by a given percentage");
    out.println(
        "level-adjust <value> <value> <value> <name> <destImageName> | action = level-adjust: "
            + "black value, mid value, white value respectively to adjust the levels of image");
    out.println("<action> <name> <destImageName>: apply an action to an image");
    out.println(
        "<action> = red-component | green-component | blue-component | value-component "
            + "| luma-component | intensity-component | horizontal-flip | "
            + "vertical-flip | blur | sharpen | sepia | histogram | color-correct");
    out.println("run <scriptFileName>: run a script of commands from a file");
    out.println("man: show the manual with action descriptions");
    out.println("exit: exit the program");
  }

  /**
   * Displays a success message to the user. Simply using println.
   *
   * @param s The success message to display.
   */
  public void showStringSuccess(String s) {
    out.println(s);
  }

  /**
   * Displays an error message to the user. Simply using println.
   *
   * @param s The error message to display.
   */
  public void showStringError(String s) {
    out.println(s);
  }

  /** Displays an error message to the user that command entered is invalid. */
  public void showOptionError() {
    out.println("\nInvalid option. Please try again.");
    out.println("menu: show the command menu");
  }
}
