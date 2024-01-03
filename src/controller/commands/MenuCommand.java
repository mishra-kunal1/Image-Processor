package controller.commands;

import controller.Command;
import model.IModel;
import view.IView;

/** This class represents a menu command. */
public class MenuCommand extends Command {
  public MenuCommand(IModel model, IView view) {
    super(model, view);
  }

  @Override
  protected void execute(String[] args) {
    callViewOptions();
  }

  @Override
  protected boolean validateArgs(String[] args) {
    if (args.length == 1) {
      return true;
    }
    callViewFailure("Invalid number of arguments.");
    return false;
  }
}
