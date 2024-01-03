package controller.commands;

import controller.Command;
import model.IModel;
import view.IView;

import java.io.IOException;

/** This class represents a level adjust command. */
public class LevelAdjustCommand extends Command {
  public LevelAdjustCommand(IModel model, IView view) {
    super(model, view);
  }

  @Override
  protected void execute(String[] args) throws IOException {
    if (args.length == 8) {
      String operation = args[0];
      int b = Integer.parseInt(args[1]);
      int m = Integer.parseInt(args[2]);
      int w = Integer.parseInt(args[3]);
      String name = args[4];
      String destImageName = args[5];
      int percent = Integer.parseInt(args[7]);

      model.previewImage(name, destImageName, percent, operation, b, m, w);
    }
    if (args.length == 6) {
      int b = Integer.parseInt(args[1]);
      int m = Integer.parseInt(args[2]);
      int w = Integer.parseInt(args[3]);
      String name = args[4];
      String destImageName = args[5];

      model.levelAdjust(b, m, w, name, destImageName);
      callViewSuccess("Level Adjust operation executed successfully.");
    }
  }

  @Override
  protected boolean validateArgs(String[] args) {
    if (args.length == 8) {
      if (Integer.parseInt(args[1]) != -1) {
        if (Integer.parseInt(args[1]) >= 0 && Integer.parseInt(args[1]) < 256) {
          if (Integer.parseInt(args[2]) != -1) {
            if (Integer.parseInt(args[2]) >= 0
                && Integer.parseInt(args[2]) < 256
                && Integer.parseInt(args[2]) > Integer.parseInt(args[1])) {
              if (Integer.parseInt(args[3]) != -1) {
                if (Integer.parseInt(args[3]) >= 0
                    && Integer.parseInt(args[3]) < 256
                    && Integer.parseInt(args[3]) > Integer.parseInt(args[2])) {
                  if (isInModel(args[4])) {
                    if (isValidName(args[5])) {
                      if (args[6].equals("split")) {
                        if (Integer.parseInt(args[7]) != -1) {
                          if (Integer.parseInt(args[7]) > 0 && Integer.parseInt(args[7]) < 100) {
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
                callViewFailure("Value has to be between 0-255");
                return false;
              }
              callViewFailure("Value needs to be an integer");
              return false;
            }
            callViewFailure("Value has to be between 0-255");
            return false;
          }
          callViewFailure("Value has to be between 0-255");
          return false;
        }
        callViewFailure("Value has to be between 0-255");
        return false;
      }
      callViewFailure("Value has to be between 0-255");
      return false;
    }
    if (args.length == 6) {
      if (Integer.parseInt(args[1]) != -1) {
        if (Integer.parseInt(args[1]) >= 0 && Integer.parseInt(args[1]) < 256) {
          if (Integer.parseInt(args[2]) != -1) {
            if (Integer.parseInt(args[2]) >= 0 && Integer.parseInt(args[2]) < 256) {
              if (Integer.parseInt(args[3]) != -1) {
                if (Integer.parseInt(args[3]) >= 0 && Integer.parseInt(args[3]) < 256) {
                  if (isInModel(args[4])) {
                    if (isValidName(args[5])) {
                      if (Integer.parseInt(args[1]) < Integer.parseInt(args[2])
                          && Integer.parseInt(args[2]) < Integer.parseInt(args[3])) {
                        return true;
                      }
                      callViewFailure("Invalid values for black, mid, and white points.");
                      return false;
                    }
                    callViewFailure("Invalid name for image.");
                    return false;
                  }
                  callViewFailure("Image not in model.");
                  return false;
                }
                callViewFailure("Value has to be between 0-255");
                return false;
              }
              callViewFailure("Value needs to be an integer");
              return false;
            }
            callViewFailure("Value has to be between 0-255");
            return false;
          }
          callViewFailure("Value has to be between 0-255");
          return false;
        }
        callViewFailure("Value has to be between 0-255");
        return false;
      }
      callViewFailure("Value has to be between 0-255");
      return false;
    }
    callViewFailure("Invalid number of arguments.");
    return false;
  }
}
