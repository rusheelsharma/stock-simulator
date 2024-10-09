package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The BetterModelImpl class implements the BetterModel interface, providing functionalities
 * for managing stock portfolios. This includes creating, modifying, querying, saving, loading,
 * rebalancing, and displaying portfolio information.
 */
public class BetterModelImpl extends ModelImpl implements BetterModel {
  private Map<String, Stock> stocks;
  private Map<String, Portfolio> portfolios;

  /**
   * Constructs a BetterModelImpl object, initializing the stocks and portfolios maps.
   */
  public BetterModelImpl() {
    this.stocks = new HashMap<>();
    this.portfolios = new HashMap<>();
  }

  /**
   * Retrieves a stock by its ticker symbol.
   *
   * @param tickerSymbol the ticker symbol of the stock
   * @return the stock corresponding to the ticker symbol, or null if not found
   */
  @Override
  public Stock getStock(String tickerSymbol) {
    return stocks.get(tickerSymbol);
  }

  /**
   * Adds a stock to the model.
   *
   * @param tickerSymbol the ticker symbol of the stock
   * @param stock        the stock to be added
   */
  @Override
  public void addStock(String tickerSymbol, Stock stock) {
    if (stock != null) {
      stocks.put(tickerSymbol, stock);
      System.out.println("Stock added: " + tickerSymbol);
    } else {
      System.out.println("Stock is null: " + tickerSymbol);
    }
  }

  /**
   * Creates a new portfolio with the specified name.
   *
   * @param portfolioName the name of the new portfolio
   */
  @Override
  public void createPortfolio(String portfolioName) {
    portfolios.put(portfolioName, new Portfolio(portfolioName));
    System.out.println("Portfolio created: " + portfolioName);
  }

  /**
   * Adds a stock to the specified portfolio.
   *
   * @param portfolioName the name of the portfolio
   * @param tickerSymbol  the ticker symbol of the stock
   * @param quantity      the number of shares to add
   * @param date          the date of the addition
   */
  @Override
  public void addStockToPortfolio(String portfolioName, String tickerSymbol, int quantity,
                                  LocalDate date) {
    Portfolio portfolio = portfolios.get(portfolioName);
    Stock stock = getStock(tickerSymbol);
    if (portfolio != null && stock != null) {
      portfolio.addStock(tickerSymbol, quantity, stock);
      System.out.println("Added " + quantity + " shares of " + tickerSymbol + " to portfolio "
              + portfolioName);
    } else {
      System.out.println("Error adding stock to portfolio. Portfolio or stock is null.");
    }
  }

  /**
   * Removes a stock from the specified portfolio.
   *
   * @param portfolioName the name of the portfolio
   * @param tickerSymbol  the ticker symbol of the stock
   * @param quantity      the number of shares to remove
   * @param date          the date of the removal
   */
  @Override
  public void removeStockFromPortfolio(String portfolioName, String tickerSymbol, int quantity,
                                       LocalDate date) {
    Portfolio portfolio = portfolios.get(portfolioName);
    Stock stock = getStock(tickerSymbol);
    if (portfolio != null && stock != null) {
      portfolio.removeStock(tickerSymbol, quantity, date);
      System.out.println("Removed " + quantity + " shares of " + tickerSymbol + " from portfolio "
              + portfolioName);
    } else {
      System.out.println("Error removing stock from portfolio. Portfolio or stock is null.");
    }
  }

  /**
   * Retrieves the composition of the specified portfolio as of the specified date.
   *
   * @param portfolioName the name of the portfolio
   * @param date          the date for which to retrieve the composition
   * @return a map where the keys are ticker symbols and the values are the quantities of each stock
   */
  @Override
  public Map<String, Double> getPortfolioComposition(String portfolioName, LocalDate date) {
    Portfolio portfolio = portfolios.get(portfolioName);
    if (portfolio != null) {
      return portfolio.getComposition(date);
    }
    return new HashMap<>();
  }

  /**
   * Retrieves the total value of the specified portfolio as of the specified date.
   *
   * @param portfolioName the name of the portfolio
   * @param date          the date for which to retrieve the value
   * @return the total value of the portfolio
   */
  @Override
  public double getPortfolioValue(String portfolioName, LocalDate date) {
    Portfolio portfolio = portfolios.get(portfolioName);
    if (portfolio != null) {
      return portfolio.getValue(date);
    }
    return 0;
  }

  /**
   * Retrieves the value distribution of the specified portfolio as of the specified date.
   *
   * @param portfolioName the name of the portfolio
   * @param date          the date for which to retrieve the value distribution
   * @return a map where the keys are ticker symbols and the values are the value of each stock
   *                                               as a percentage of the total portfolio value
   */
  @Override
  public Map<String, Double> getPortfolioValueDistribution(String portfolioName, LocalDate date) {
    Portfolio portfolio = portfolios.get(portfolioName);
    if (portfolio != null) {
      return portfolio.getValueDistribution(date);
    }
    return new HashMap<>();
  }

  /**
   * Saves the specified portfolio to a file.
   *
   * @param portfolioName the name of the portfolio
   * @param filePath      the path of the file to save the portfolio
   */
  @Override
  public void savePortfolioToLocal(String portfolioName, String filePath) {
    Portfolio portfolio = portfolios.get(portfolioName);
    if (portfolio != null) {
      try {
        portfolio.saveToFile(filePath);
        System.out.println("Portfolio saved: " + portfolioName + " to " + filePath);
      } catch (IOException e) {
        System.out.println("Error saving portfolio: " + e.getMessage());
      }
    } else {
      System.out.println("Error saving portfolio. Portfolio is null.");
    }
  }

  /**
   * Loads a portfolio from a file.
   *
   * @param filePath the path of the file to load the portfolio from
   */
  @Override
  public void loadPortfolioFromLocal(String filePath) {
    try {
      Portfolio portfolio = Portfolio.loadFromFile(filePath);
      if (portfolio != null) {
        portfolios.put(portfolio.getName(), portfolio);
        System.out.println("Portfolio loaded from: " + filePath);
      } else {
        System.out.println("Error loading portfolio. Portfolio is null.");
      }
    } catch (IOException e) {
      System.out.println("Error loading portfolio: " + e.getMessage());
    }
  }

  /**
   * Rebalances the specified portfolio based on the target weights as of the specified date.
   *
   * @param portfolioName the name of the portfolio
   * @param targetWeights a map where the keys are ticker symbols and the values are the target
   *                      weights for each stock
   * @param date          the date for the rebalancing
   */
  @Override
  public void rebalancePortfolio(String portfolioName, Map<String, Double> targetWeights,
                                 LocalDate date) {
    Portfolio portfolio = portfolios.get(portfolioName);
    if (portfolio != null) {
      portfolio.rebalance(targetWeights, date);
      System.out.println("Portfolio rebalanced: " + portfolioName);
    } else {
      System.out.println("Error rebalancing portfolio. Portfolio is null.");
    }
  }

  /**
   * Retrieves the performance of the specified portfolio over a date range.
   *
   * @param portfolioName the name of the portfolio
   * @param startDate     the start date of the performance period
   * @param endDate       the end date of the performance period
   * @return a list of portfolio values for each date in the specified range
   */
  @Override
  public List<Double> getPortfolioPerformance(String portfolioName, LocalDate startDate,
                                              LocalDate endDate) {
    Portfolio portfolio = portfolios.get(portfolioName);
    if (portfolio != null) {
      return portfolio.getPerformance(startDate, endDate);
    }
    return new ArrayList<>();
  }

  /**
   * Checks if the model contains a portfolio with the specified name.
   *
   * @param portfolioName the name of the portfolio
   * @return true if the portfolio exists, false otherwise
   */
  @Override
  public boolean containsPortfolio(String portfolioName) {
    return portfolios.containsKey(portfolioName);
  }
}
