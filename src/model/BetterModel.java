package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * The BetterModel interface extends the Model interface and provides additional methods for
 * managing stock portfolios. It includes functionalities for creating, modifying, querying,
 * saving, loading, rebalancing, and displaying portfolio information.
 */
public interface BetterModel extends Model {

  /**
   * Creates a new portfolio with the specified name.
   *
   * @param portfolioName the name of the new portfolio
   */
  void createPortfolio(String portfolioName);

  /**
   * Adds a stock to the specified portfolio.
   *
   * @param portfolioName the name of the portfolio
   * @param tickerSymbol  the ticker symbol of the stock
   * @param quantity      the number of shares to add
   * @param date          the date of the addition
   */
  void addStockToPortfolio(String portfolioName, String tickerSymbol, int quantity, LocalDate date);

  /**
   * Removes a stock from the specified portfolio.
   *
   * @param portfolioName the name of the portfolio
   * @param tickerSymbol  the ticker symbol of the stock
   * @param quantity      the number of shares to remove
   * @param date          the date of the removal
   */
  void removeStockFromPortfolio(String portfolioName, String tickerSymbol, int quantity,
                                LocalDate date);

  /**
   * Retrieves the composition of the specified portfolio as of the specified date.
   *
   * @param portfolioName the name of the portfolio
   * @param date          the date for which to retrieve the composition
   * @return a map where the keys are ticker symbols and the values are the quantities of each stock
   */
  Map<String, Double> getPortfolioComposition(String portfolioName, LocalDate date);

  /**
   * Retrieves the total value of the specified portfolio as of the specified date.
   *
   * @param portfolioName the name of the portfolio
   * @param date          the date for which to retrieve the value
   * @return the total value of the portfolio
   */
  double getPortfolioValue(String portfolioName, LocalDate date);

  /**
   * Retrieves the value distribution of the specified portfolio as of the specified date.
   *
   * @param portfolioName the name of the portfolio
   * @param date          the date for which to retrieve the value distribution
   * @return a map where the keys are ticker symbols and the values are the value of each stock
   *                                              as a percentage of the total portfolio value
   */
  Map<String, Double> getPortfolioValueDistribution(String portfolioName, LocalDate date);

  /**
   * Saves the specified portfolio to a file.
   *
   * @param portfolioName the name of the portfolio
   * @param filePath      the path of the file to save the portfolio
   */
  void savePortfolioToLocal(String portfolioName, String filePath);

  /**
   * Loads a portfolio from a file.
   *
   * @param filePath the path of the file to load the portfolio from
   */
  void loadPortfolioFromLocal(String filePath);

  /**
   * Rebalances the specified portfolio based on the target weights as of the specified date.
   *
   * @param portfolioName the name of the portfolio
   * @param targetWeights a map where the keys are ticker symbols and the values are the target
   *                      weights for each stock
   * @param date          the date for the rebalancing
   */
  void rebalancePortfolio(String portfolioName, Map<String, Double> targetWeights, LocalDate date);

  /**
   * Retrieves the performance of the specified portfolio over a date range.
   *
   * @param portfolioName the name of the portfolio
   * @param startDate     the start date of the performance period
   * @param endDate       the end date of the performance period
   * @return a list of portfolio values for each date in the specified range
   */
  List<Double> getPortfolioPerformance(String portfolioName, LocalDate startDate,
                                       LocalDate endDate);

  /**
   * Checks if the model contains a portfolio with the specified name.
   *
   * @param portfolioName the name of the portfolio
   * @return true if the portfolio exists, false otherwise
   */
  boolean containsPortfolio(String portfolioName);
}
