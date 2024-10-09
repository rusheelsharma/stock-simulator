package model;

import java.util.List;

/**
 * The Model interface defines the important main methods for managing stocks and portfolios
 * within the application. It provides functionalities for adding stocks and portfolios,
 * retrieving stocks and portfolios, and other related operations.
 */
public interface Model {

  /**
   * Adds a stock to the model.
   * This method stores the provided stock object in the model's collection, identified by its
   * ticker symbol.
   *
   * @param tickerSymbol the ticker symbol of the stock
   * @param stock the Stock object to be added
   */
  void addStock(String tickerSymbol, Stock stock);

  /**
   * Adds a portfolio to the model.
   * This method stores the provided portfolio object in the model's collection of portfolios.
   *
   * @param portfolio the Portfolio object to be added
   */
  void addPortfolio(Portfolio portfolio);

  /**
   * Retrieves the list of portfolios from the model.
   * This method returns all the portfolios currently stored in the model.
   *
   * @return a list of Portfolio objects
   */
  List<Portfolio> getPortfolios();

  /**
   * Retrieves a stock from the model based on its ticker symbol.
   * This method returns the stock associated with the given ticker symbol, if it exists in the
   * model.
   *
   * @param tickerSymbol the ticker symbol of the stock
   * @return the Stock object associated with the ticker symbol, or null if not found
   */
  Stock getStock(String tickerSymbol);
}
