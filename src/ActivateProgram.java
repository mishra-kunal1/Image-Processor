import controller.IController;
import controller.Controller;
import controller.GUIFeaturesInterface;
import controller.ControllerGUI;
import controller.GUIFeaturesInterface;
import model.IModel;
import model.Model;
import view.IView;
import view.IViewGUI;
import view.JFrameView;
import view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class is used to activate the program. The program is responsible for manipulating images,
 * and converting them using many image formats.
 */
public class ActivateProgram {
  /**
   * Main method to activate the program. Accepts an argument to a script file or uses CLI input.
   *
   * @param args The arguments passed into the program.
   * @throws IOException If the file cannot be found.
   */
  public static void main(String[] args) throws IOException {
    String path="view";
    if(path=="nview"){



      IModel model = new Model();
      IView view = new View(System.out);
      IController controller;
      if (args.length == 1) {
        String command = args[0];
        if (command.equals("-text")) {
          controller = new Controller(model, view, System.in);
          controller.execute();
        }
        view.showStringError("Invalid command.");
        System.exit(0);
      } else if (args.length == 2) {
        String command = args[0];
        String filePath = args[1];
        if (command.equals("-file")) {
          if (isValidScript(filePath)) {
            InputStream fileInputStream = new FileInputStream(filePath);
            controller = new Controller(model, view, fileInputStream);
            controller.execute();
            System.exit(0);
          }
          view.showStringError("Invalid file or file path.");
          System.exit(0);
        }
        view.showStringError("Invalid command.");
        System.exit(0);
      }
      view.showStringError("Invalid arguments.");
      System.exit(0);
    } else {
      IModel model = new Model();
      IViewGUI view = new JFrameView();
      GUIFeaturesInterface controller = new ControllerGUI(model);
      controller.setView(view);
    }
  }

  private static boolean isValidScript(String s) {
    File file = new File(s);

    if (!file.exists()) {
      return false;
    }

    String[] validExtensions = {".txt"};
    String fileExtension = s.substring(s.lastIndexOf('.'));

    for (String ext : validExtensions) {
      if (ext.equals(fileExtension)) {
        return true;
      }
    }

    return false;
  }
}
