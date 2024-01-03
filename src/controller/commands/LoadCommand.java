package controller.commands;

import controller.Command;
import controller.ImageUtil;
import model.IModel;
import view.IView;

import java.io.IOException;

/** This class represents a load command. */
public class LoadCommand extends Command {
  public LoadCommand(IModel model, IView view) {
    super(model, view);
  }

  @Override
  protected void execute(String[] args) throws IOException {
    String fileName = args[1];
    String name = args[2];
    String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

    if (ImageUtil.loadImage(fileName, ext) == null) {
      callViewFailure("Invalid image.");
      return;
    }

    model.addImage(name, ImageUtil.loadImage(fileName, ext));
    callViewSuccess("Image loaded successfully.");
  }

  @Override
  protected boolean validateArgs(String[] args) {
    if (args.length == 3) {
      if (isValidFile(args[1])) {
        if (isValidName(args[2])) {
          return true;
        }
        callViewFailure("Invalid name for image.");
        return false;
      }
      callViewFailure("Invalid file.");
      return false;
    }
    callViewFailure("Invalid number of arguments.");
    return false;
  }
}
