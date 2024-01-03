package view;

import controller.GUIFeaturesInterface;
import model.Image;

/**
 * Interface for the GUI of the view.
 */
public interface IViewGUI {
  void setImage(Image image);

  void setHistogram(Image image);

  void addFeatures(GUIFeaturesInterface guiFeatures);

  void showError(String message);

  boolean getSplitStatus();

  int getSplitPercentage();
}
