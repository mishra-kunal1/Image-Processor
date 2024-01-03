package controller.commands;

import controller.Command;
import model.IModel;
import view.IView;

/** This class represents a RGB split command. */
public class RGBCombineCommand extends Command {
  public RGBCombineCommand(IModel model, IView view) {
    super(model, view);
  }

  @Override
  protected void execute(String[] args) {
    String destImageName = args[1];
    String redImageName = args[2];
    String greenImageName = args[3];
    String blueImageName = args[4];
    model.combineRGB(destImageName, redImageName, greenImageName, blueImageName);
    callViewSuccess("RGB combine operation executed successfully");
  }

  @Override
  protected boolean validateArgs(String[] args) {
    if (args.length == 5) {
      if (isValidName(args[1])) {
        if (isInModel(args[2]) && !isGrayScale(args[2])) {
          if (isInModel(args[3]) && !isGrayScale(args[3])) {
            if (model.isSameSize(args[2], args[3])) {
              if (isInModel(args[4])
                  && !isGrayScale(args[4])
                  && model.isSameSize(args[2], args[4])) {
                return true;
              }
              callViewFailure(
                  "Blue Image not found in model. Or cannot be grayscale. "
                      + "Or not same size as Red image.");
            } else {
              callViewFailure("Red and Green images are not of the same size.");
            }
            return false;
          }
          callViewFailure("Green Image not found in model. Or cannot be grayscale.");
          return false;
        }
        callViewFailure("Red Image not found in model. Or cannot be grayscale.");
        return false;
      }
      callViewFailure("Invalid Image name for destination image.");
      return false;
    }
    callViewFailure("Invalid number of arguments");
    return false;
  }
}
