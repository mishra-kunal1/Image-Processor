package controller.commands;

import controller.Command;
import controller.ImageUtil;
import model.IModel;
import view.IView;

import java.io.IOException;

/** This class represents a save command. */
public class SaveCommand extends Command {
  public SaveCommand(IModel model, IView view) {
    super(model, view);
  }

  @Override
  protected void execute(String[] args) throws IOException {
    String fileName = args[1];
    String name = args[2];
    String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

    if (ext.equals("ppm")) {
      ImageUtil.convertToPPMImage(model.getImage(name), fileName, ext);
    } else {
      ImageUtil.convertToImage(model.getImage(name), fileName, ext);
    }

    callViewSuccess("Image saved successfully.");
  }

  @Override
  protected boolean validateArgs(String[] args) {
    if (args.length == 3) {
      if (isValidFilePath(args[1])) {
        if (isInModel(args[2])) {
          return true;
        }
        callViewFailure("Image not in model.");
        return false;
      }
      callViewFailure("Invalid file path.");
      return false;
    }
    callViewFailure("Invalid number of arguments.");
    return false;
  }
}
