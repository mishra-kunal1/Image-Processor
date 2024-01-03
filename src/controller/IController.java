package controller;

import java.io.IOException;
import java.io.InputStream;

public interface IController {
  /**
   * Run a script from an input stream. This method is called when the program is run with a script file.
   *
   * @param script The script to run.
   * @throws IOException If the script cannot be found.
   */
  void runScript(InputStream script) throws IOException;

  /**
   * Run the prompter. This method is called when the program is run without a script file. It
   * prompts the user for commands and runs them.
   *
   * @throws IOException If the input cannot be read.
   */
  void runPrompter() throws IOException;

  /**
   * Run the program. If the input stream is a script, run the script. Otherwise, run the prompter.
   *
   * @throws IOException If the input cannot be read.
   */
  void execute() throws IOException;
}
