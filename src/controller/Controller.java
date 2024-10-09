package controller;

/**
 * The Controller interface defines the important main method for a controller in the application.
 * It is responsible for starting the main execution loop and coordinating between the model
 * and the view.
 */
public interface Controller {

  /**
   * Starts the main execution of the controller.
   * This method is responsible for coordinating between the model and the view,
   * handling user inputs, and managing the overall control of the application.
   */
  void execute();

}
