package view;

import java.time.LocalDate;

/**
 * The View interface defines methods for user interaction in the application.
 * It provides functionalities for displaying menus, gathering user inputs,
 * and displaying messages to the user.
 */
public interface View {

  /**
   * Displays the main menu and prompts the user to choose an option.
   *
   * @return the user's choice as an integer
   */
  int mainMenuOptionChooser();

  /**
   * Prompts the user to enter a ticker symbol and returns it.
   *
   * @return the ticker symbol entered by the user
   */
  String getTickerSymbol();

  /**
   * Prompts the user to enter a date and returns it as a LocalDate object.
   *
   * @return the date entered by the user
   */
  LocalDate getDate();

  /**
   * Prompts the user to enter an X value and returns it.
   * This value can be used for calculations such as moving averages.
   *
   * @return the X value entered by the user
   */
  int getXValue();

  /**
   * Prompts the user to enter the number of shares and returns it.
   *
   * @return the number of shares entered by the user
   */
  int getNumberOfShares();

  /**
   * Asks the user whether they want to add more stocks to the portfolio.
   *
   * @return true if the user wants to add more stocks, false otherwise
   */
  boolean askToAddMoreStocks();

  /**
   * Displays a message to the user.
   *
   * @param message the message to be displayed
   */
  void displayMessage(String message);

  /**
   * Prompts the user to enter a start date for a range and returns it as a LocalDate object.
   *
   * @return the start date entered by the user
   */
  LocalDate getStartDate();

  /**
   * Prompts the user to enter an end date for a range and returns it as a LocalDate object.
   *
   * @return the end date entered by the user
   */
  LocalDate getEndDate();

  /**
   * Asks the user to choose a portfolio from the available options.
   * This method should display the available portfolios and prompt the user to select one.
   *
   * @param maxChoice the maximum valid choice number
   * @return the user's choice as an integer
   */
  int getPortfolioChoice(int maxChoice);
}
