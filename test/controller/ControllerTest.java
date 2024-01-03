package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/** Test class for Controller. */
public class ControllerTest {
  private MockModel mockModel;
  private MockView mockView;
  private InputStream in;

  @Before
  public void setUp() {
    PrintStream out = new PrintStream(new ByteArrayOutputStream());
    mockModel = new MockModel();
    mockView = new MockView(out);
  }

  @Test
  public void testScript() throws Exception {
    List<String> commands = new ArrayList<>();
    commands.add("run test/controller/script.txt");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Failure! for Invalid script.Show Option Error called!", mockView.getLastLog());
  }

  @Test
  public void testScript2() throws Exception {
    List<String> commands = new ArrayList<>();
    commands.add("run test/controller/MockScript.txt");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    Controller controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Blur operation executed successfully."
            + "Command Success! for Sharpen operation executed successfully."
            + "Command Success! for Script executed successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testCascadingCommands() throws IOException {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("blur mock mock-blur");
    commands.add("sharpen mock-blur mock-sharpen");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Blur operation executed successfully."
            + "Command Success! for Sharpen operation executed successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testGo() throws Exception {
    List<String> commands = new ArrayList<>();
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals("Show Options called!", mockView.getLastLog());
  }

  @Test
  public void testGo2() throws Exception {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals("Command Success! for Image loaded successfully.", mockView.getLastLog());
  }

  @Test
  public void testInvalidCommand() throws IOException {
    List<String> commands = new ArrayList<>();
    commands.add("invalid");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();
    assertEquals("Show Option Error called!", mockView.getLastLog());
  }

  @Test
  public void testInvalidPath() throws IOException {
    List<String> commands = new ArrayList<>();
    commands.add("load src/test/controller/invalidImage.png invalid");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();
    assertEquals(
        "Command Failure! for Invalid file.Show Option Error called!", mockView.getLastLog());
  }

  @Test
  public void testInvalidImage() throws IOException {
    List<String> commands = new ArrayList<>();
    commands.add("horizontal-flip invalid invalid-flip");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());
    mockModel.isInModel = false;
    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();
    assertEquals(
        "Command Failure! for Image not in model.Show Option Error called!", mockView.getLastLog());
  }

  @Test
  public void testLoadCommand() throws Exception {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/mockImage.png mock");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals("Command Success! for Image loaded successfully.", mockView.getLastLog());
  }

  @Test
  public void testSaveCommand() throws Exception {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("save test/controller/MockImage.png mock");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Image saved successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testManCommand() throws IOException {
    List<String> commands = new ArrayList<>();
    commands.add("man");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals("Show Details called!", mockView.getLastLog());
  }

  @Test
  public void testMenuCommand() throws IOException {
    List<String> commands = new ArrayList<>();
    commands.add("menu");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals("Show Options called!", mockView.getLastLog());
  }

  @Test
  public void testBlurCommand() throws Exception {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("blur mock mock-blur");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Blur operation executed successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testBrightenCommand() throws Exception {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("brighten 10 mock mock-brighten-ten");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Brightened image by 10 successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testDarkenCommand() throws Exception {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("brighten -10 mock mock-brighten-neg-ten");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Brightened image by -10 successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testGreenComponentCommand() throws Exception {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("green-component mock mock-green");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Green component operation executed successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testRedComponentCommand() throws Exception {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("red-component mock mock-red");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Red component operation executed successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testBlueComponentCommand() throws Exception {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("blue-component mock mock-blue");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Blue component operation executed successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testHorizontalFlipCommand() throws Exception {

    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("horizontal-flip mock mock-horizontal");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Horizontal flip operation executed successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testIntensityCommand() throws Exception {

    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("intensity-component mock mock-intensity");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Intensity component operation executed successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testLumaComponentCommand() throws Exception {

    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("luma-component mock mock-luma");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Luma component operation executed successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testSepiaCommand() throws Exception {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("sepia mock mock-sepia");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Sepia operation executed successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testSharpenCommand() throws Exception {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("sharpen mock mock-sharpen");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Sharpen operation executed successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testValueComponentCommand() throws Exception {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("value-component mock mock-value");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Value component operation executed successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testVerticalFlipCommand() throws Exception {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("vertical-flip mock mock-vertical");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Vertical flip operation executed successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testRGBSplitCommand() throws Exception {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("rgb-split mock mock-red-split mock-green-split mock-blue-split");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for RGB split operation executed successfully",
        mockView.getLastLog());
  }

  @Test
  public void testRGBSplitGrayScale() throws IOException {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock-gray");
    commands.add("rgb-split mock-gray mock-red-split mock-green-split mock-blue-split");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    mockModel.isGrayScale = true;
    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();
    assertEquals(
        "Command Success! for Image loaded successfully.Command Failure! for "
            + "Image cannot be a Grayscale image.Show Option Error called!",
        mockView.getLastLog());
  }

  @Test
  public void testRGBCombineCommand() throws Exception {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("rgb-combine mock-rgb mock-red-split mock-green-split mock-blue-split");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for RGB combine operation executed successfully",
        mockView.getLastLog());
  }

  @Test
  public void testRGBCombineGrayScale() throws IOException {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock-gray");
    commands.add("rgb-combine mock-rgb mock-red-split mock-green-split mock-blue-split");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    mockModel.isGrayScale = true;
    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();
    assertEquals(
        "Command Success! for Image loaded successfully.Command Failure! for Red Image "
            + "not found in model. Or cannot be grayscale.Show Option Error called!",
        mockView.getLastLog());
  }

  @Test
  public void testRGBCombineDiffSizes() throws IOException {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock-gray");
    commands.add("rgb-combine mock-rgb size-red-split mock-green-split mock-blue-split");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    mockModel.isSameSize = false;
    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();
    assertEquals(
        "Command Success! for Image loaded successfully.Command Failure! for Red and Green "
            + "images are not of the same size.Show Option Error called!",
        mockView.getLastLog());
  }

  @Test
  public void testPreview() throws IOException {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("blur mock mock-blur split 50");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Preview Blur operation executed successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testPreviewInvalid() throws IOException {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("blur mock mock-blur split -10");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Failure! for Percent value has to be between "
            + "0-100.Show Option Error called!",
        mockView.getLastLog());
  }

  @Test
  public void testPreviewInvalid2() throws IOException {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("blur mock mock-blur split 110");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Failure! for Percent value has to be between "
            + "0-100.Show Option Error called!",
        mockView.getLastLog());
  }

  @Test
  public void testHistogram() throws IOException {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("histogram blur blur-histogram");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Histogram of image executed successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testColorCorrect() throws IOException {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("color-correct mock mock-cc");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Color Correction executed successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testLevelAdjust() throws IOException {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("level-adjust 10 20 30 mock mock-la");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Level Adjust operation executed successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testLevelAdjustInvalidAscending() throws IOException {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("level-adjust 20 10 30 mock mock-la");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Failure! for Invalid values for black, mid, and white points."
            + "Show Option Error called!",
        mockView.getLastLog());
  }

  @Test
  public void testLevelAdjustInvalidAscending2() throws IOException {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("level-adjust 10 30 20 mock mock-la");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Failure! for Invalid values for black, mid, and white points."
            + "Show Option Error called!",
        mockView.getLastLog());
  }

  @Test
  public void testCompression() throws IOException {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("compress 50 mock mock-compress");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Success! for Compressed image by 50  % successfully.",
        mockView.getLastLog());
  }

  @Test
  public void testCompressionInvalid() throws IOException {
    List<String> commands = new ArrayList<>();
    commands.add("load test/controller/MockImage.png mock");
    commands.add("compress 110 mock mock-compress");
    commands.add("exit");
    in = new ByteArrayInputStream(String.join("\n", commands).getBytes());

    IController controller = new Controller(mockModel, mockView, in);
    controller.execute();

    assertEquals(
        "Command Success! for Image loaded successfully."
            + "Command Failure! for Percent value has to be between 0-100."
            + "Show Option Error called!",
        mockView.getLastLog());
  }
}
