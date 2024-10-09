package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ModelImpl implements the Model interface and represents the application's
 * data model and is responsible for storing and managing stocks and portfolios.
 */
public class ModelImpl implements Model {
  protected Map<String, Stock> stocks;
  protected List<Portfolio> portfolios;

  /**
   * Constructs a new ModelImpl object with empty collections for stocks and portfolios.
   */
  public ModelImpl() {
    this.stocks = new HashMap<>();
    this.portfolios = new ArrayList<>();
  }

  /**
   * Adds a stock to the model.
   * This method stores the provided stock object in the model's collection, identified by its
   * ticker symbol.
   *
   * @param tickerSymbol the ticker symbol of the stock
   * @param stock the Stock object to add
   */
  @Override
  public void addStock(String tickerSymbol, Stock stock) {
    stocks.put(tickerSymbol, stock);
  }

  /**
   * Retrieves a stock from the model by its ticker symbol.
   * This method returns the stock associated with the given ticker symbol, if it exists in the
   * model.
   *
   * @param tickerSymbol the ticker symbol of the stock
   * @return the Stock object associated with the ticker symbol, or null if not found
   */
  @Override
  public Stock getStock(String tickerSymbol) {
    return stocks.get(tickerSymbol);
  }

  /**
   * Adds a portfolio to the model.
   * This method stores the provided portfolio object in the model's collection of portfolios.
   *
   * @param portfolio the Portfolio object to add
   */
  @Override
  public void addPortfolio(Portfolio portfolio) {
    portfolios.add(portfolio);
  }

  /**
   * Retrieves all portfolios from the model.
   * This method returns all the portfolios currently stored in the model.
   *
   * @return a list of Portfolio objects
   */
  @Override
  public List<Portfolio> getPortfolios() {
    return portfolios;
  }
}
