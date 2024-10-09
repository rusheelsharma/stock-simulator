package view;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The BetterViewImpl class implements the BetterView interface and provides the
 * methods for displaying portfolio information and gathering user inputs.
 */
public class BetterViewImpl extends ViewImpl implements BetterView {
  private final Scanner scanner;

  /**
   * Constructs a BetterViewImpl object and initializes the Scanner for user input.
   */
  public BetterViewImpl() {
    this.scanner = new Scanner(System.in);
  }

  /**
   * Displays the composition of a portfolio.
   * This method shows the quantities of each stock in the portfolio.
   *
   * @param composition a map where the keys are ticker symbols and the values are the quantities
   *                    of each stock
   */
  @Override
  public void displayPortfolioComposition(Map<String, Double> composition) {
    System.out.println("Portfolio Composition:");
    composition.forEach((stock, quantity) -> System.out.println(stock + ": " + quantity
            + " shares"));
  }

  /**
   * Displays the total value of a portfolio.
   *
   * @param value the total value of the portfolio
   */
  @Override
  public void displayPortfolioValue(double value) {
    System.out.println("Portfolio Value: $" + value);
  }

  /**
   * Displays the value distribution of a portfolio.
   * This method shows the value of each stock as a percentage of the total portfolio value.
   *
   * @param distribution a map where the keys are ticker symbols and the values are stocks values
   */
  @Override
  public void displayPortfolioValueDistribution(Map<String, Double> distribution) {
    System.out.println("Portfolio Value Distribution:");
    distribution.forEach((stock, value) -> System.out.println(stock + " :" + value + " %"));
  }

  /**
   * Displays the performance of a portfolio over a date range.
   * This method shows the portfolio values for each date within the specified range.
   *
   * @param performance   a list of portfolio values for each date in the specified range
   * @param portfolioName the name of the portfolio
   * @param startDate     the start date of the performance period
   * @param endDate       the end date of the performance period
   */
  @Override
  public void displayPortfolioPerformance(List<Double> performance, String portfolioName,
                                          LocalDate startDate, LocalDate endDate) {
    System.out.println("Performance of portfolio " + portfolioName + " from " + startDate
            + " to " + endDate);

    double maxValue = performance.stream().max(Double::compareTo).orElse(1.0);
    int maxAsterisks = 50;
    double scale = maxValue / maxAsterisks;
    if (scale == 0) {
      scale = 1;
    }

    LocalDate currentDate = startDate;
    int index = 0;

    while (!currentDate.isAfter(endDate) && index < performance.size()) {
      double value = performance.get(index);
      int numAsterisks = (int) (value / scale);
      System.out.println(currentDate + ": " + "*".repeat(numAsterisks));
      currentDate = currentDate.plusMonths(1);
      index++;
    }

    System.out.println("Scale: * = $" + scale);
  }

  /**
   * Prompts the user to enter the name of a portfolio.
   *
   * @return the name of the portfolio entered by the user
   */
  @Override
  public String getPortfolioName() {
    return getStringInput("Enter portfolio name: ");
  }

  /**
   * Prompts the user to enter a file path.
   *
   * @return the file path entered by the user
   */
  @Override
  public String getFilePath() {
    return getStringInput("Enter file name (with format, e.g., portfolio.csv): ");
  }

  /**
   * Prompts the user to enter the target weights for rebalancing a portfolio.
   *
   * @return a map where the keys are ticker symbols and the values are the target weights
   *                                                                        for each stock
   */
  @Override
  public Map<String, Double> getTargetWeights(List<String> tickers) {
    Map<String, Double> weights = new HashMap<>();
    for (String ticker : tickers) {
      double weight = getDoubleInput("Enter target weight for stock " + ticker
              + " (as a percentage): ") / 100.0;
      weights.put(ticker, weight);
    }
    return weights;
  }

  /**
   * Prompts the user to enter a date.
   *
   * @return the date entered by the user
   */
  @Override
  public LocalDate getDate() {
    System.out.println("Enter the date:");
    int year = getYear();
    int month = getMonth();
    int day = getDay(year, month);
    LocalDate date = LocalDate.of(year, month, day);
    System.out.println("Date entered: " + date);
    return date;
  }

  /**
   * Prompts the user to enter the start date for a range.
   *
   * @return the start date entered by the user
   */
  @Override
  public LocalDate getStartDate() {
    System.out.println("Enter the start date:");
    int year = getYear();
    int month = getMonth();
    int day = getDay(year, month);
    LocalDate date = LocalDate.of(year, month, day);
    System.out.println("Start date entered: " + date);
    return date;
  }

  /**
   * Prompts the user to enter the end date for a range.
   *
   * @return the end date entered by the user
   */
  @Override
  public LocalDate getEndDate() {
    System.out.println("Enter the end date:");
    int year = getYear();
    int month = getMonth();
    int day = getDay(year, month);
    LocalDate date = LocalDate.of(year, month, day);
    System.out.println("End date entered: " + date);
    return date;
  }

  /**
   * Displays the main menu and prompts the user to choose an option.
   *
   * @return the option chosen by the user
   */
  @Override
  public int mainMenuOptionChooser() {
    System.out.println("Main Menu:");
    System.out.println("1. Calculate gain/loss");
    System.out.println("2. Calculate x-day moving average");
    System.out.println("3. Calculate x-day crossovers");
    System.out.println("4. Create a new portfolio");
    System.out.println("5. Add/Purchase stock into portfolio");
    System.out.println("6. Remove/Sell stock from portfolio");
    System.out.println("7. Show portfolio composition");
    System.out.println("8. Show portfolio value");
    System.out.println("9. Show portfolio value distribution");
    System.out.println("10. Save portfolio");
    System.out.println("11. Load portfolio");
    System.out.println("12. Rebalance portfolio");
    System.out.println("13. Display portfolio performance");
    System.out.println("14. Exit");
    return getIntInput("Choose an option: ");
  }

  /**
   * Prompts the user to enter a stock ticker symbol.
   *
   * @return the stock ticker symbol entered by the user
   */
  @Override
  public String getTickerSymbol() {
    return getStringInput("Enter stock ticker symbol: ");
  }

  /**
   * Prompts the user to enter the number of shares for a stock.
   *
   * @return the number of shares entered by the user
   */
  @Override
  public int getNumberOfShares() {
    return getIntInput("Enter number of shares: ");
  }

  /**
   * Asks the user if they want to add more stocks.
   *
   * @return true if the user wants to add more stocks, false otherwise
   */
  @Override
  public boolean askToAddMoreStocks() {
    return getStringInput("Add more stocks? (y/n): ").equalsIgnoreCase("y");
  }

  /**
   * Prompts the user to enter a year.
   *
   * @return the year entered by the user
   */
  private int getYear() {
    return getIntInput("Enter year: ");
  }

  /**
   * Prompts the user to enter a month and ensures it is within the valid range (1-12).
   *
   * @return the month entered by the user
   */
  private int getMonth() {
    int month;
    do {
      month = getIntInput("Enter month (1-12): ");
      if (month < 1 || month > 12) {
        System.out.println("Invalid month. Please enter a value between 1 and 12.");
      }
    }

    while (month < 1 || month > 12);

    return month;
  }

  /**
   * Prompts the user to enter a day and ensures it is within the valid range for
   * the specified month and year.
   *
   * @param year  the year to validate the day
   * @param month the month to validate the day
   * @return the day entered by the user
   */
  private int getDay(int year, int month) {
    int day;
    int maxDay = LocalDate.of(year, month, 1).lengthOfMonth();
    do {
      day = getIntInput("Enter day (1-" + maxDay + "): ");
      if (day < 1 || day > maxDay) {
        System.out.println("Invalid day. Please enter a value between 1 and " + maxDay + ".");
      }
    }

    while (day < 1 || day > maxDay);

    return day;
  }

  /**
   * Reads and returns an integer input from the user.
   * Ensures the input is a valid positive integer.
   *
   * @param prompt the prompt to display to the user
   * @return the integer input entered by the user
   */
  private int getIntInput(String prompt) {
    System.out.print(prompt);
    while (true) {
      try {
        if (scanner.hasNextLine()) {
          int value = Integer.parseInt(scanner.nextLine().trim());
          if (value >= 0) {
            return value;
          } else {
            System.out.println("Please enter a positive number.");
          }
        } else {
          throw new NoSuchElementException("No input available.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid number.");
      }
    }
  }

  /**
   * Reads and returns a double input from the user.
   * Ensures the input is a valid positive double.
   *
   * @param prompt the prompt to display to the user
   * @return the double input entered by the user
   */
  private double getDoubleInput(String prompt) {
    System.out.print(prompt);
    while (true) {
      try {
        if (scanner.hasNextLine()) {
          double value = Double.parseDouble(scanner.nextLine().trim());
          if (value >= 0) {
            return value;
          } else {
            System.out.println("Please enter a positive number.");
          }
        } else {
          throw new NoSuchElementException("No input available.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid number.");
      }
    }
  }

  /**
   * Reads and returns a string input from the user.
   * Ensures the input is not empty.
   *
   * @param prompt the prompt to display to the user
   * @return the string input entered by the user
   */
  private String getStringInput(String prompt) {
    System.out.print(prompt);
    while (true) {
      if (scanner.hasNextLine()) {
        String input = scanner.nextLine().trim();
        if (!input.isEmpty()) {
          return input;
        } else {
          System.out.println("Input cannot be empty. Please enter a valid input.");
        }
      } else {
        throw new NoSuchElementException("No input available.");
      }
    }
  }
}
