package controller.commands;

import controller.Command;
import model.IModel;
import view.IView;

/** This class represents a brighten command. */
public class BrightenCommand extends Command {
  public BrightenCommand(IModel model, IView view) {
    super(model, view);
  }

  @Override
  protected void execute(String[] args) {
    int amount = Integer.parseInt(args[1]);
    String name = args[2];
    String destImageName = args[3];

    model.brightenImage(amount, name, destImageName);
    view.showStringSuccess("Brightened image by " + amount + " successfully.");
  }

  @Override
  protected boolean validateArgs(String[] args) {
    if (args.length == 4) {
      return Integer.parseInt(args[1]) != -1 && isValidName(args[2]) && isValidFilePath(args[3]);
    }
    return false;
  }
}
