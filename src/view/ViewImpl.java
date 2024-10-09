package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The ViewImpl class handles user interaction by providing methods to get user input
 * and output messages. It implements the View interface.
 */
public class ViewImpl implements View {
  protected Scanner scanner;
  protected DateTimeFormatter dateFormatter;

  /**
   * Constructs a ViewImpl object and initializes the Scanner and DateTimeFormatter.
   */
  public ViewImpl() {
    scanner = new Scanner(System.in);
    dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  }

  /**
   * Displays the main menu and returns the user's choice.
   *
   * @return the user's choice as an integer
   */
  @Override
  public int mainMenuOptionChooser() {
    System.out.println("1. Examine and view gain/loss of a stock");
    System.out.println("2. Compute x-day moving average of a stock");
    System.out.println("3. Compute x-day crossovers of a stock");
    System.out.println("4. Create a new portfolio and find its value");
    System.out.println("5. Query portfolio value on a given date");
    System.out.println("6. Exit the program");
    System.out.print("Welcome to Stock Simulator | Please Enter Your Choice: ");
    return scanner.nextInt();
  }

  /**
   * Prompts the user to enter a ticker symbol and returns it.
   *
   * @return the ticker symbol entered by the user
   */
  @Override
  public String getTickerSymbol() {
    System.out.print("Enter the ticker symbol: ");
    return scanner.next();
  }

  /**
   * Prompts the user to enter a date and returns it as a LocalDate object.
   *
   * @return the date entered by the user as a LocalDate
   */
  @Override
  public LocalDate getDate() {
    System.out.print("Enter the date in this format - (YYYY-MM-DD): ");
    while (true) {
      try {
        return LocalDate.parse(scanner.next(), dateFormatter);
      } catch (DateTimeParseException e) {
        System.out.print("Invalid date format. Please enter the date (YYYY-MM-DD): ");
      }
    }
  }

  /**
   * Prompts the user to enter a start date and returns it as a LocalDate object.
   *
   * @return the start date entered by the user as a LocalDate
   */
  @Override
  public LocalDate getStartDate() {
    System.out.print("Enter the start date (YYYY-MM-DD): ");
    while (true) {
      try {
        return LocalDate.parse(scanner.next(), dateFormatter);
      } catch (DateTimeParseException e) {
        System.out.print("Invalid date format. Please enter the start date (YYYY-MM-DD): ");
      }
    }
  }

  /**
   * Prompts the user to enter an end date and returns it as a LocalDate object.
   *
   * @return the end date entered by the user as a LocalDate
   */
  @Override
  public LocalDate getEndDate() {
    System.out.print("Enter the end date (YYYY-MM-DD): ");
    while (true) {
      try {
        return LocalDate.parse(scanner.next(), dateFormatter);
      } catch (DateTimeParseException e) {
        System.out.print("Invalid date format. Please enter the end date (YYYY-MM-DD): ");
      }
    }
  }

  /**
   * Prompts the user to enter the x value for the moving average or crossovers
   * and returns it.
   *
   * @return the x value entered by the user
   */
  @Override
  public int getXValue() {
    System.out.print("Enter the x value: ");
    while (true) {
      try {
        return scanner.nextInt();
      } catch (InputMismatchException e) {
        System.out.print("Invalid input. Please enter a number for x value: ");
        scanner.next();
      }
    }
  }

  /**
   * Prompts the user to enter the number of shares and returns it.
   *
   * @return the number of shares entered by the user
   */
  @Override
  public int getNumberOfShares() {
    System.out.print("Enter the number of shares: ");
    while (true) {
      try {
        return scanner.nextInt();
      } catch (InputMismatchException e) {
        System.out.print("Invalid input. Please enter a number for shares: ");
        scanner.next();
      }
    }
  }

  /**
   * Asks the user to indicate whether they want to add more stocks to the portfolio.
   *
   * @return true if the user wants to add more stocks, false otherwise
   */
  @Override
  public boolean askToAddMoreStocks() {
    System.out.print("Would you like to add more stocks to this portfolio? Enter (yes/no): ");
    while (true) {
      String input = scanner.next();
      if (input.equalsIgnoreCase("yes")) {
        return true;
      } else if (input.equalsIgnoreCase("no")) {
        return false;
      } else {
        System.out.print("Invalid input. Please enter 'yes' or 'no': ");
      }
    }
  }

  /**
   * Displays a message to the user.
   *
   * @param message the message to be displayed
   */
  @Override
  public void displayMessage(String message) {
    System.out.println(message);
  }

  /**
   * Prompts the user to choose a portfolio from the available options.
   *
   * @param maxChoice the maximum valid choice number
   * @return the user's choice as an integer
   */
  @Override
  public int getPortfolioChoice(int maxChoice) {
    int choice;
    do {
      System.out.print("Enter the portfolio number (1-" + maxChoice + "): ");
      while (!scanner.hasNextInt()) {
        System.out.print("Invalid input. Please enter a number between 1 and " + maxChoice + ": ");
        scanner.next();
      }
      choice = scanner.nextInt();
    }
    while (choice < 1 || choice > maxChoice);
    return choice;
  }
}
