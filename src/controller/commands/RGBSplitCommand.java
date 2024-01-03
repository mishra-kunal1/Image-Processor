package controller.commands;

import controller.Command;
import model.IModel;
import view.IView;

/** This class represents a RGB split command. */
public class RGBSplitCommand extends Command {
  public RGBSplitCommand(IModel model, IView view) {
    super(model, view);
  }

  @Override
  protected void execute(String[] args) {
    String name = args[1];
    String redDestImageName = args[2];
    String greenDestImageName = args[3];
    String blueDestImageName = args[4];
    model.splitRGB(name, redDestImageName, greenDestImageName, blueDestImageName);
    callViewSuccess("RGB split operation executed successfully");
  }

  @Override
  protected boolean validateArgs(String[] args) {
    if (args.length == 5) {
      if (isInModel(args[1])) {
        if (isGrayScale(args[1])) {
          callViewFailure("Image cannot be a Grayscale image.");
          return false;
        }
        if (isValidName(args[2])) {
          if (isValidName(args[3])) {
            if (isValidName(args[4])) {
              return true;
            }
            callViewFailure("Invalid Image name for blue image.");
            return false;
          }
          callViewFailure("Invalid Image name for green image.");
          return false;
        }
        callViewFailure("Invalid Image name for red image.");
        return false;
      }
      callViewFailure("Image not found in model");
      return false;
    }
    callViewFailure("Invalid number of arguments");
    return false;
  }
}
