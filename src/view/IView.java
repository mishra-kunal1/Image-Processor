package view;

/** This interface defines methods for displaying information to the user. */
public interface IView {

  /**
   * Displays a prompt to the user.
   *
   * @param s The prompt to display.
   */
  void showPrompt(String s);

  /**
   * Displays a string to the user.
   *
   * @param s The string to display.
   */
  void showString(String s);

  /** Displays details (manual) about the program commands to the user. */
  void showDetails();

  /** Displays the options for the program to the user. */
  void showOptions();

  /**
   * Displays a success message to the user.
   *
   * @param s The success message to display.
   */
  void showStringSuccess(String s);

  /**
   * Displays an error message to the user.
   *
   * @param s The error message to display.
   */
  void showStringError(String s);

  /** Displays an error message to the user that the entered command is invalid. */
  void showOptionError();
}
