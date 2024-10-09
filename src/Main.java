import controller.Controller;
import controller.ControllerImpl;
import controller.GuiController;
import controller.GuiControllerImpl;
import model.BetterModel;
import model.BetterModelImpl;
import view.BetterView;
import view.BetterViewImpl;
import view.GuiView;
import view.GuiViewImpl;

/**
 * The Main class serves as the main point for the application.
 * Permits a user to select between the text-based interface or graphical interface
 * based on the command-line arguments provided.
 */
public class Main {

  /**
   * Main method of the program to actually run it.
   *
   * @param args the arguments passed to the CLI.
   */
  public static void main(String[] args) {

    BetterModel model = new BetterModelImpl();

    if (args.length == 0) {
      GuiView guiView = new GuiViewImpl();
      GuiController guiController = new GuiControllerImpl(model, guiView);
      guiController.execute();
    } else if (args.length == 1 && args[0].equals("-text")) {
      BetterView textView = new BetterViewImpl();
      Controller textController = new ControllerImpl(model, textView);
      textController.execute();
    } else {
      System.err.println("Invalid CLI arguments. Use one of:");
      System.err.println("java -jar Program.jar -text : run this for text-based interface");
      System.err.println("java -jar Program.jar : run this for GUI");
      System.exit(1);
    }
  }
}
