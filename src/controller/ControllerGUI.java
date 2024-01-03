package controller;

import java.io.File;
import java.io.IOException;

import model.IModel;
import model.Image;
import view.IViewGUI;

/**
 * This class represents the controller for the GUI. It implements the GUIFeatures interface.
 * The controller is responsible for talking with the view in order to showcase image operations.
 */
public class ControllerGUI implements GUIFeaturesInterface {
  private IModel m;
  private IViewGUI v;

  public ControllerGUI(IModel model) {
    m = model;
  }

  public void setView(IViewGUI view) {
    v = view;
    view.addFeatures(this);
  }

  private void splitView(int percent) {
    m.sideBySide("currImage", "splitImage", "splitView", percent);
  }

  @Override
  public void showImage() {
    resetSplitImage();
    Image image = m.getImage("currImage");
    v.setImage(image);
    m.histogram("currImage", "histImage");
    Image histogram = m.getImage("histImage");
    v.setHistogram(histogram);
  }

  @Override
  public void showSplit() {
    Image image = m.getImage("splitView");
    v.setImage(image);
    m.histogram("splitImage", "splitHistImage");
    Image histogram = m.getImage("splitHistImage");
    v.setHistogram(histogram);
  }

  @Override
  public void saveSplitImage() {
    Image image = m.getImage("splitImage");
    m.addImage("currImage", image);
    resetSplitImage();
  }

  private void resetSplitImage() {
    Image image = m.getImage("currImage");
    m.addImage("splitImage", image);
  }

  @Override
  public void loadImage(String path) {
    if (isValidFile(path)) {
      String name = "currImage";
      String ext = path.substring(path.lastIndexOf(".") + 1);

      try {
        if (ImageUtil.loadImage(path, ext) == null) {
          return;
        }
        m.addImage(name, ImageUtil.loadImage(path, ext));
        Image currentImage = m.getImage("currImage");
        v.setImage(currentImage);

        m.histogram("currImage", "histImage");
        Image histogram = m.getImage("histImage");
        v.setHistogram(histogram);

        m.addImage("splitImage", ImageUtil.loadImage(path, ext));
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      v.showError("File Not Supported");
    }
  }

  @Override
  public void saveImage(String path) {
    if (isValidFilePath(path)) {
      String name = "currImage";
      String ext = path.substring(path.lastIndexOf(".") + 1);

      if (ext.equals("ppm")) {
        try {
          ImageUtil.convertToPPMImage(m.getImage(name), path, ext);
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        try {
          ImageUtil.convertToImage(m.getImage(name), path, ext);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } else {
      v.showError("Invalid File Path");
    }
  }

  @Override
  public void sepiaOperation() {
    if (v.getSplitStatus()) {
      m.sepiaImage("splitImage", "splitImage");
      splitView(v.getSplitPercentage());
      showSplit();
    } else {
      m.sepiaImage("currImage", "currImage");
      showImage();
    }
  }

  @Override
  public void redComponentOperation() {
    if (v.getSplitStatus()) {
      v.showError("Cannot perform operation with split view");
    } else {
      if (!isGrayScale("currImage")) {
        m.redComponent("currImage", "currImage");
        showImage();
      } else {
        v.showError("Image cannot be GrayScale");
      }
    }
  }

  @Override
  public void greenComponentOperation() {
    if (v.getSplitStatus()) {
      v.showError("Cannot perform operation with split view");
    } else {
      if (!isGrayScale("currImage")) {
        m.greenComponent("currImage", "currImage");
        showImage();
      } else {
        v.showError("Image cannot be GrayScale");
      }
    }
  }

  @Override
  public void blueComponentOperation() {
    if (v.getSplitStatus()) {
      v.showError("Cannot perform operation with split view");
    } else {
      if (!isGrayScale("currImage")) {
        m.blueComponent("currImage", "currImage");
        showImage();
      } else {
        v.showError("Image cannot be GrayScale");
      }
    }
  }

  @Override
  public void horizontalFlipOperation() {
    if (v.getSplitStatus()) {
      v.showError("Cannot perform operation with split view");
    } else {
      m.horizontalFlip("currImage", "currImage");
      showImage();
    }
  }

  @Override
  public void verticalFlipOperation() {
    if (v.getSplitStatus()) {
      v.showError("Cannot perform operation with split view");
    } else {
      m.verticalFlip("currImage", "currImage");
      showImage();
    }
  }

  @Override
  public void blurOperation() {
    if (v.getSplitStatus()) {
      m.blurImage("splitImage", "splitImage");
      splitView(v.getSplitPercentage());
      showSplit();
    } else {
      m.blurImage("currImage", "currImage");
      showImage();
    }
  }

  @Override
  public void sharpenOperation() {
    if (v.getSplitStatus()) {
      m.sharpenImage("splitImage", "splitImage");
      splitView(v.getSplitPercentage());
      showSplit();
    } else {
      m.sharpenImage("currImage", "currImage");
      showImage();
    }
  }

  @Override
  public void greyScaleOperation() {
    if (!isGrayScale("currImage")) {
      if (v.getSplitStatus()) {
        m.calculateLuma("splitImage", "splitImage");
        splitView(v.getSplitPercentage());
        showSplit();
      } else {
        m.calculateLuma("currImage", "currImage");
        showImage();
      }
    } else {
      v.showError("Image cannot already be GrayScale");
    }
  }

  @Override
  public void compressionOperation(int compressionFactor) {
    if (v.getSplitStatus()) {
      v.showError("Cannot perform operation with split view");
    } else {
      m.compressImage("currImage", "currImage", compressionFactor);
      showImage();
    }
  }

  @Override
  public void colorCorrectionOperation() {
    if (v.getSplitStatus()) {
      m.colorCorrect("splitImage", "splitImage");
      splitView(v.getSplitPercentage());
      showSplit();
    } else {
      m.colorCorrect("currImage", "currImage");
      showImage();
    }
  }

  @Override
  public void adjustLevelsOperation(int b, int mid, int w) {
    if (v.getSplitStatus()) {
      m.levelAdjust(b, mid, w, "splitImage", "splitImage");
      splitView(v.getSplitPercentage());
      showSplit();
    } else {
      m.levelAdjust(b, mid, w, "currImage", "currImage");
      showImage();
    }
  }

  @Override
  public void brightnessOperation(int value) {
    if (v.getSplitStatus()) {
      m.brightenImage(value, "splitImage", "splitImage");
      splitView(v.getSplitPercentage());
      showSplit();
    } else {
      m.brightenImage(value, "currImage", "currImage");
      showImage();
    }
  }

  @Override
  public void ditherOperation() {
    if (v.getSplitStatus()) {
      m.ditherImage("splitImage", "splitImage");
      splitView(v.getSplitPercentage());
      showSplit();
    } else {
      m.ditherImage("currImage", "currImage");
      showImage();
    }
  }

  /**
   * Check if the image file is valid and exists. Additionally, checks for the right extensions.
   *
   * @param s the file.
   * @return true if the file is valid, false otherwise.
   */
  protected boolean isValidFile(String s) {
    File file = new File(s);

    if (!s.contains(".")) {
      return false;
    }

    if (!file.exists()) {
      return false;
    }

    String[] validExtensions = {".jpg", ".png", ".bmp", ".ppm"};
    String fileExtension = s.substring(s.lastIndexOf('.'));

    for (String ext : validExtensions) {
      if (ext.equals(fileExtension)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Check if the directory is valid and exists. Valid directories are directories that exist and
   * can have files placed in them.
   *
   * @param s the directory.
   * @return true if the directory is valid, false otherwise.
   */
  protected boolean isValidFilePath(String s) {
    File file = new File(s);
    File parentDirectory = file.getParentFile();

    if (!s.contains(".")) {
      return false;
    }

    if (parentDirectory == null) {
      parentDirectory = new File(System.getProperty("user.dir"));
    }

    if (parentDirectory.exists() && parentDirectory.isDirectory()) {
      String[] validExtensions = {".jpg", ".png", ".bmp", ".ppm"};
      String fileExtension = s.substring(s.lastIndexOf('.'));
      for (String ext : validExtensions) {
        if (ext.equals(fileExtension)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Check if the image is a gray scale image.
   *
   * @param s the name of the image.
   * @return true if the image is gray scale, false otherwise.
   */
  private boolean isGrayScale(String s) {
    return m.isGrayScale(s);
  }
}
