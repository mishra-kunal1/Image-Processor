package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.IModel;
import view.IView;

import controller.commands.BlueComponentCommand;
import controller.commands.BlurCommand;
import controller.commands.BrightenCommand;
import controller.commands.CompressCommand;
import controller.commands.GreenComponentCommand;
import controller.commands.HorizontalFlipCommand;
import controller.commands.IntensityComponentCommand;
import controller.commands.LumaComponentCommand;
import controller.commands.ColorCorrectCommand;
import controller.commands.HistogramCommand;
import controller.commands.LevelAdjustCommand;
import controller.commands.LoadCommand;
import controller.commands.ManCommand;
import controller.commands.MenuCommand;
import controller.commands.RedComponentCommand;
import controller.commands.RGBCombineCommand;
import controller.commands.RGBSplitCommand;
import controller.commands.SaveCommand;
import controller.commands.SepiaCommand;
import controller.commands.SharpenCommand;
import controller.commands.ValueComponentCommand;
import controller.commands.VerticalFlipCommand;
import controller.commands.RunCommand;

/**
 * This class is responsible for controlling the program. It takes in a model and a view. It has the
 * capability to parse a script and run those commands or run commands from the user.
 */
public class Controller implements IController{
  /** The model and view for the program. */
  private IModel model;

  private IView view;
  private InputStream inputStream;
  private boolean isScript;

  /**
   * Store commands in a map for easy access to add additional commands, by creating additional
   * command classes.
   */
  private Map<String, Command> commandMap;

  /**
   * Constructor for the controller. Takes in a model and view. Also takes in an input stream to
   * determine if the input is from a script or not. Also initializes the command map with
   * appropriate commands.
   *
   * @param model The model for the program.
   * @param view The view for the program.
   * @param in The input stream for the program.
   */
  public Controller(IModel model, IView view, InputStream in) {
    this.model = model;
    this.view = view;
    this.inputStream = in;
    this.commandMap = new HashMap<>();
    this.isScript = in instanceof FileInputStream;
    initializeCommands();
  }

  /**
   * Insert commands into the command map. This allows for easy addition of commands by creating
   * additional command classes.
   */
  private void initializeCommands() {
    this.commandMap.put("load", new LoadCommand(model, view));
    this.commandMap.put("save", new SaveCommand(model, view));
    this.commandMap.put("menu", new MenuCommand(model, view));
    this.commandMap.put("man", new ManCommand(model, view));
    this.commandMap.put("rgb-split", new RGBSplitCommand(model, view));
    this.commandMap.put("rgb-combine", new RGBCombineCommand(model, view));
    this.commandMap.put("brighten", new BrightenCommand(model, view));
    this.commandMap.put("red-component", new RedComponentCommand(model, view));
    this.commandMap.put("green-component", new GreenComponentCommand(model, view));
    this.commandMap.put("blue-component", new BlueComponentCommand(model, view));
    this.commandMap.put("value-component", new ValueComponentCommand(model, view));
    this.commandMap.put("luma-component", new LumaComponentCommand(model, view));
    this.commandMap.put("intensity-component", new IntensityComponentCommand(model, view));
    this.commandMap.put("horizontal-flip", new HorizontalFlipCommand(model, view));
    this.commandMap.put("vertical-flip", new VerticalFlipCommand(model, view));
    this.commandMap.put("blur", new BlurCommand(model, view));
    this.commandMap.put("sharpen", new SharpenCommand(model, view));
    this.commandMap.put("sepia", new SepiaCommand(model, view));
    this.commandMap.put("run", new RunCommand(model, view, this));
    this.commandMap.put("compress", new CompressCommand(model, view));
    this.commandMap.put("histogram", new HistogramCommand(model, view));
    this.commandMap.put("color-correct", new ColorCorrectCommand(model, view));
    this.commandMap.put("level-adjust", new LevelAdjustCommand(model, view));
  }

  /**
   * Run a script from an input stream. This method is called when the program is run with a script
   * file.
   *
   * @param script The script to run.
   * @throws IOException If the script cannot be found.
   */
  public void runScript(InputStream script) throws IOException {
    Scanner in = new Scanner(script);

    boolean quit = false;

    while (!quit && in.hasNextLine()) {

      String[] commandArgs = in.nextLine().split(" ");
      String commandKey = commandArgs[0];
      if (commandMap.containsKey(commandKey)) {
        Command command = commandMap.get(commandKey);

        if (command.validateArgs(commandArgs)) {
          command.execute(commandArgs);
        } else if (commandKey.equals("exit")) {
          quit = true;
        }
      }
    }
  }

  /**
   * Run the prompter. This method is called when the program is run without a script file. It
   * prompts the user for commands and runs them.
   *
   * @throws IOException If the input cannot be read.
   */
  public void runPrompter() throws IOException {
    Scanner in = new Scanner(this.inputStream);

    boolean quit = false;

    view.showOptions();

    while (!quit) {
      view.showPrompt("\nEnter your command: ");

      String[] commandArgs = in.nextLine().split(" ");
      String commandKey = commandArgs[0];

      if (commandMap.containsKey(commandKey)) {
        Command command = commandMap.get(commandKey);

        if (command.validateArgs(commandArgs)) {
          command.execute(commandArgs);
        } else {
          view.showOptionError();
        }

      } else if (commandKey.equals("exit")) {
        quit = true;
      } else {
        view.showOptionError();
      }
    }
  }

  /**
   * Run the program. If the input stream is a script, run the script. Otherwise, run the prompter.
   *
   * @throws IOException If the input cannot be read.
   */
  public void execute() throws IOException {
    if (isScript) {
      runScript(this.inputStream);
    } else {
      runPrompter();
    }
  }
}
