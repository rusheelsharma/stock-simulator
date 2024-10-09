package view;

import java.time.LocalDate;
import java.util.Map;
import java.util.List;

/**
 * The BetterView interface extends the View interface and provides additional methods for
 * displaying and interacting with portfolio information. It includes methods for displaying
 * portfolio compositions, values, value distributions, performances, and gathering user inputs.
 */
public interface BetterView extends View {

  /**
   * Displays the composition of a portfolio.
   * This method shows the quantities of each stock in the portfolio.
   *
   * @param composition a map where the keys are ticker symbols and the values are the quantities
   *                    of each stock
   */
  void displayPortfolioComposition(Map<String, Double> composition);

  /**
   * Displays the total value of a portfolio.
   *
   * @param value the total value of the portfolio
   */
  void displayPortfolioValue(double value);

  /**
   * Displays the value distribution of a portfolio.
   * This method shows the value of each stock as a percentage of the total portfolio value.
   *
   * @param distribution a map where the keys are ticker symbols and the values are the value
   *                     of each stock as a percentage of the total portfolio value
   */
  void displayPortfolioValueDistribution(Map<String, Double> distribution);

  /**
   * Displays the performance of a portfolio over a date range.
   * This method shows the portfolio values for each date within the specified range.
   *
   * @param performance   a list of portfolio values for each date in the specified range
   * @param portfolioName the name of the portfolio
   * @param startDate     the start date of the performance period
   * @param endDate       the end date of the performance period
   */
  void displayPortfolioPerformance(List<Double> performance, String portfolioName,
                                   LocalDate startDate, LocalDate endDate);

  /**
   * Prompts the user to enter the name of a portfolio.
   *
   * @return the name of the portfolio entered by the user
   */
  String getPortfolioName();

  /**
   * Prompts the user to enter the number of shares for a stock.
   *
   * @return the number of shares entered by the user
   */
  int getNumberOfShares();

  /**
   * Prompts the user to enter a file path.
   *
   * @return the file path entered by the user
   */
  String getFilePath();

  /**
   * Prompts the user to enter the target weights for rebalancing a portfolio.
   *
   * @return a map where the keys are ticker symbols and the values are the target weights for
   *                                                                                each stock
   */
  Map<String, Double> getTargetWeights(List<String> tickers);

  /**
   * Prompts the user to enter a date.
   *
   * @return the date entered by the user
   */
  LocalDate getDate();
}
