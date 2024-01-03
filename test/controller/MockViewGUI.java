package controller;

import model.Image;
import view.IViewGUI;

/**
 * Mocking the view to allow for testing.
 */
public class MockViewGUI implements IViewGUI {
  @Override
  public void setImage(Image image) {
    // this is a mock view, so do nothing
  }

  @Override
  public void setHistogram(Image image) {
    // this is a mock view, so do nothing
  }

  @Override
  public void addFeatures(GUIFeaturesInterface guiFeatures) {
    // this is a mock view, so do noth
  }

  @Override
  public void showError(String message) {
    // this is a mock view, so do nothing
  }

  @Override
  public boolean getSplitStatus() {
    return false;
  }

  @Override
  public int getSplitPercentage() {
    return 0;
  }
}
