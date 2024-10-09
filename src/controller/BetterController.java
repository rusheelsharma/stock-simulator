package controller;

/**
 * The BetterController interface extends the functionality of the Controller interface
 * by providing additional methods for managing stock portfolios. Those features include creating,
 * modifying, querying, saving, loading, rebalancing, and displaying portfolio information.
 */
public interface BetterController extends Controller {

  /**
   * Creates a new portfolio.
   * This method prompts the user to provide necessary details and initializes a new portfolio.
   */
  void createPortfolio();

  /**
   * Adds a stock to an existing portfolio.
   * This method prompts the user for the portfolio identifier and stock details,
   * and adds the specified stock to the portfolio.
   */
  void addStockToPortfolio();

  /**
   * Removes a stock from an existing portfolio.
   * This method prompts the user for the portfolio identifier and the stock to be removed,
   * and removes the specified stock from the portfolio.
   */
  void removeStockFromPortfolio();

  /**
   * Queries the composition of a portfolio.
   * This method prompts the user for the portfolio identifier and displays the list of stocks
   * and their respective quantities in the portfolio.
   */
  void queryPortfolioComposition();

  /**
   * Queries the total value of a portfolio.
   * This method prompts the user for the portfolio identifier and displays the total value
   * of the portfolio based on current stock prices.
   */
  void queryPortfolioValue();

  /**
   * Queries the value distribution of a portfolio.
   * This method prompts the user for the portfolio identifier and displays the value distribution
   * across different stocks in the portfolio.
   */
  void displayPortfolioValueDistribution();

  /**
   * Saves the current state of a portfolio to a file.
   * This method prompts the user for the portfolio identifier and a file location,
   * and saves the portfolio data to the specified file.
   */
  void savePortfolioToLocal();

  /**
   * Loads a portfolio from a file.
   * This method prompts the user for a file location and loads the portfolio data from
   * the specified file into the application.
   */
  void loadPortfolioFromLocal();

  /**
   * Rebalances a portfolio based on specified criteria.
   * This method prompts the user for the portfolio identifier and rebalancing criteria,
   * and adjusts the stock quantities in the portfolio to meet the specified criteria.
   */
  void rebalancePortfolio();

  /**
   * Displays the performance of a portfolio over time.
   * This method prompts the user for the portfolio identifier and a time range,
   * and displays the performance of the portfolio during the specified period.
   */
  void displayPortfolioPerformance();
}
