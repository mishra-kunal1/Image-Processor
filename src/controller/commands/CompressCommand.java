package controller.commands;

import controller.Command;
import model.IModel;
import view.IView;

/** This class represents a Compress command. */
public class CompressCommand extends Command {
  public CompressCommand(IModel model, IView view) {
    super(model, view);
  }

  @Override
  protected void execute(String[] args) {
    int factor = Integer.parseInt(args[1]);
    String name = args[2];
    String destImageName = args[3];

    model.compressImage(name, destImageName, factor);
    view.showStringSuccess("Compressed image by " + factor + "  % successfully.");
  }

  @Override
  protected boolean validateArgs(String[] args) {
    if (args.length == 4) {
      if (Integer.parseInt(args[1]) != -1) {
        if (Integer.parseInt(args[1]) >= 0 && Integer.parseInt(args[1]) <= 100) {
          if (isInModel(args[2])) {
            if (isValidName(args[3])) {
              return true;
            }
            callViewFailure("Invalid name for image.");
            return false;
          }
          callViewFailure("Image not in model.");
          return false;
        }
        callViewFailure("Percent value has to be between 0-100.");
        return false;
      }
      callViewFailure("Percent Value needs to be an integer");
      return false;
    }
    callViewFailure("Invalid number of arguments.");
    return false;
  }
}
