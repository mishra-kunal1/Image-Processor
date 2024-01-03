package controller;

import java.io.PrintStream;

import view.View;

/** Mock view class for testing. */
public class MockView extends View {
  private StringBuilder log;

  /**
   * Constructor for the view. Initializes the output stream.
   *
   * @param out The output stream to display information to.
   */
  public MockView(PrintStream out) {
    super(out);
    this.log = new StringBuilder();
  }

  public StringBuilder getLog() {
    return this.log;
  }

  public String getLastLog() {
    String[] logs = log.toString().split("\n");
    return logs[logs.length - 1];
  }

  @Override
  public void showPrompt(String s) {
    // log.append(s);
  }

  @Override
  public void showString(String s) {
    // log.append(s);
  }

  @Override
  public void showDetails() {
    log.append("Show Details called!" + "\n");
  }

  @Override
  public void showOptions() {
    log.append("Show Options called!" + "\n");
  }

  @Override
  public void showStringSuccess(String s) {
    log.append("Command Success! for " + s);
  }

  @Override
  public void showStringError(String s) {
    log.append("Command Failure! for " + s);
  }

  @Override
  public void showOptionError() {
    log.append("Show Option Error called!" + "\n");
  }
}
