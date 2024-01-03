package controller;

import view.IViewGUI;

/**
 * Interface for the GUI to implement features that the view can call upon.
 */
public interface GUIFeaturesInterface {
  /**
   * Sets the view of the program and maps the buttons to their respective actions.
   * inside the controller.
   * @param view the view of the program.
   */
  void setView(IViewGUI view);

  void saveSplitImage();

  void showImage();

  void loadImage(String filePath);

  void saveImage(String filePath);

  void showSplit();

  void sepiaOperation();

  void redComponentOperation();

  void greenComponentOperation();

  void blueComponentOperation();

  void horizontalFlipOperation();

  void verticalFlipOperation();

  void blurOperation();

  void sharpenOperation();

  void greyScaleOperation();

  void compressionOperation(int compressionFactor);

  void colorCorrectionOperation();

  void adjustLevelsOperation(int b, int m, int w);

  void brightnessOperation(int value);

  void ditherOperation();
}
