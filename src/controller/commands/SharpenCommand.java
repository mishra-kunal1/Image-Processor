package controller.commands;

import controller.Command;
import model.IModel;
import view.IView;

/** This class represents a sharpen command. */
public class SharpenCommand extends Command {
  public SharpenCommand(IModel model, IView view) {
    super(model, view);
  }

  @Override
  protected void execute(String[] args) {
    if (args.length == 5) {
      String operation = args[0];
      String name = args[1];
      String destImageName = args[2];
      int percent = Integer.parseInt(args[4]);

      model.previewImage(name, destImageName, percent, operation);
      callViewSuccess("Preview Sharpen operation executed successfully.");
    }
    if (args.length == 3) {
      String name = args[1];
      String destImageName = args[2];

      model.sharpenImage(name, destImageName);
      callViewSuccess("Sharpen operation executed successfully.");
    }
  }

  @Override
  protected boolean validateArgs(String[] args) {
    if (args.length == 5) {
      if (isInModel(args[1])) {
        if (isValidName(args[2])) {
          if (args[3].equals("split")) {
            if (Integer.parseInt(args[4]) != -1) {
              if (Integer.parseInt(args[4]) > 0 && Integer.parseInt(args[4]) < 100) {
                return true;
              }
              callViewFailure("Percent value has to be between 0-100");
              return false;
            }
            callViewFailure("Percent Value needs to be an integer");
            return false;
          }
          callViewFailure("Invalid additional command.");
          return false;
        }
        callViewFailure("Invalid name for image.");
        return false;
      }
      callViewFailure("Image not in model.");
      return false;
    }
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
