package controller;

/**
 * The GuiController interface extends the BetterController interface
 * to support GUI functionality. This interface provides a method to manage
 * the important execution loop of the controller.
 */
public interface GuiController extends BetterController {

  /**
   * Executes the main loop of the controller, setting up the GUI and handling
   * user interactions.
   */
  @Override
  void execute();
}
