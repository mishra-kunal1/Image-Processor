package controller;

import java.io.File;
import java.io.IOException;

import model.IModel;
import view.IView;

/**
 * Abstract class for all commands. Commands are used to call the model to perform operations on
 * images or the program.
 */
public abstract class Command {
  /** The model and view for the program. */
  protected IModel model;

  protected IView view;

  /**
   * Super Constructor for the command. Takes in a model and view.
   *
   * @param model model for the program.
   * @param view view for the program.
   */
  protected Command(IModel model, IView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Call the view to display the success message.
   *
   * @param s the success message.
   */
  protected void callViewSuccess(String s) {
    view.showStringSuccess(s);
  }

  /**
   * Call the view to display the failure message.
   *
   * @param s the failure message.
   */
  protected void callViewFailure(String s) {
    view.showStringError(s);
  }

  /** Call the view to display the menu. */
  protected void callViewOptions() {
    view.showOptions();
  }

  /** Call the view to display the commands. */
  protected void callViewDetails() {
    view.showDetails();
  }

  /**
   * Execute the command. Takes in an array of arguments which are commands passed in by user.
   *
   * @param args arguments for the command.
   * @throws IOException if the file cannot be found.
   */
  protected abstract void execute(String[] args) throws IOException;

  /**
   * Validate the arguments passed in by the user.
   *
   * @param args arguments for the command.
   * @return true if the arguments are valid, false otherwise.
   */
  protected abstract boolean validateArgs(String[] args);

  /**
   * Check if the script file is valid and exists.
   *
   * @param s the script file.
   * @return true if the script file is valid, false otherwise.
   */
  protected boolean isValidScript(String s) {
    File file = new File(s);

    if (!file.exists()) {
      return false;
    }

    String[] validExtensions = {".txt"};
    String fileExtension = s.substring(s.lastIndexOf('.'));

    for (String ext : validExtensions) {
      if (ext.equals(fileExtension)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Check if the image file is valid and exists. Additionally, checks for the right extensions.
   *
   * @param s the file.
   * @return true if the file is valid, false otherwise.
   */
  protected boolean isValidFile(String s) {
    File file = new File(s);

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

    if (parentDirectory == null) {
      parentDirectory = new File(System.getProperty("user.dir"));
    }

    return parentDirectory.exists() && parentDirectory.isDirectory();
  }

  /**
   * Check if the name is valid. Valid names are any string.
   *
   * @param s the name of an image.
   * @return true if the name is valid, false otherwise.
   */
  protected boolean isValidName(String s) {
    return true;
  }

  /**
   * Check if the image is in the model.
   *
   * @param s the name of the image.
   * @return true if the image is in the model, false otherwise.
   */
  protected boolean isInModel(String s) {
    return model.isInModel(s);
  }

  /**
   * Check if the image is a gray scale image.
   *
   * @param s the name of the image.
   * @return true if the image is gray scale, false otherwise.
   */
  protected boolean isGrayScale(String s) {
    return model.isGrayScale(s);
  }
}
