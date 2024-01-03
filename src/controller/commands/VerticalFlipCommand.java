package controller.commands;

import controller.Command;
import model.IModel;
import view.IView;

/** This class represents a vertical flip command. */
public class VerticalFlipCommand extends Command {
  public VerticalFlipCommand(IModel model, IView view) {
    super(model, view);
  }

  @Override
  protected void execute(String[] args) {
    String name = args[1];
    String destImageName = args[2];

    model.verticalFlip(name, destImageName);
    callViewSuccess("Vertical flip operation executed successfully.");
  }

  @Override
  protected boolean validateArgs(String[] args) {
    if (args.length == 3) {
      if (isInModel(args[1])) {
        if (isValidName(args[2])) {
          return true;
        }
        callViewFailure("Invalid name for image.");
        return false;
      }
      callViewFailure("Image not in model.");
      return false;
    }
    callViewFailure("Invalid number of arguments.");
    return false;
  }
}
