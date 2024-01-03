package controller.commands;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import controller.Command;
import controller.IController;
import model.IModel;
import view.IView;

/** This class represents a run command. */
public class RunCommand extends Command {
  private IController controller;

  public RunCommand(IModel model, IView view, IController controller) {
    super(model, view);
    this.controller = controller;
  }

  @Override
  protected void execute(String[] args) throws IOException {
    String filePath = args[1];
    InputStream fileInputStream = new FileInputStream(filePath);

    controller.runScript(fileInputStream);
    callViewSuccess("Script executed successfully.");
  }

  @Override
  protected boolean validateArgs(String[] args) {
    if (args.length == 2) {
      if (isValidScript(args[1])) {
        return true;
      }
      callViewFailure("Invalid script.");
      return false;
    }
    callViewFailure("Invalid number of arguments.");
    return false;
  }
}
