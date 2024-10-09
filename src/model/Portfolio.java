package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Portfolio class represents a collection of stocks owned by an investor.
 * It provides methods for managing and querying the portfolio's composition, value, and
 * performance.
 */
public class Portfolio {
  private final String name;
  private Map<String, Double> stocks;
  private final Map<String, Stock> stockData;

  /**
   * Constructs a Portfolio object with the specified name.
   *
   * @param name the name of the portfolio
   */
  public Portfolio(String name) {
    this.name = name;
    this.stocks = new HashMap<>();
    this.stockData = new HashMap<>();
  }

  /**
   * Gets the name of the portfolio.
   *
   * @return the name of the portfolio
   */
  public String getName() {
    return name;
  }

  /**
   * Adds a stock to the portfolio or updates the quantity if the stock already exists.
   *
   * @param tickerSymbol the ticker symbol of the stock
   * @param shares       the number of shares to add
   * @param stock        the Stock object containing stock data
   */
  public void addStock(String tickerSymbol, double shares, Stock stock) {
    if (shares <= 0) {
      throw new IllegalArgumentException("Shares must be positive.");
    }
    stocks.put(tickerSymbol, stocks.getOrDefault(tickerSymbol, 0.0) + shares);
    stockData.put(tickerSymbol, stock);
  }

  /**
   * Updates the quantity of a stock in the portfolio.
   *
   * @param tickerSymbol the ticker symbol of the stock
   * @param shares       the new number of shares
   * @param stock        the Stock object containing stock data
   */
  public void updateStock(String tickerSymbol, double shares, Stock stock) {
    if (shares < 0) {
      throw new IllegalArgumentException("Shares cannot be negative.");
    }
    stocks.put(tickerSymbol, shares);
    stockData.put(tickerSymbol, stock);
  }

  /**
   * Removes a specified quantity of a stock from the portfolio.
   * If the resulting quantity is zero or negative, the stock is removed entirely from the
   * portfolio.
   *
   * @param tickerSymbol the ticker symbol of the stock
   * @param shares       the number of shares to remove
   * @param date         the date of the removal
   */
  public void removeStock(String tickerSymbol, double shares, LocalDate date) {
    if (shares <= 0) {
      throw new IllegalArgumentException("Shares must be positive.");
    }
    if (stocks.containsKey(tickerSymbol)) {
      stocks.put(tickerSymbol, stocks.get(tickerSymbol) - shares);
      if (stocks.get(tickerSymbol) <= 0) {
        stocks.remove(tickerSymbol);
        stockData.remove(tickerSymbol);
      }
    }
  }

  /**
   * Retrieves the composition of the portfolio as of the specified date.
   *
   * @param date the date for which to retrieve the composition
   * @return a map where the keys are ticker symbols and the values are the quantities of each stock
   */
  public Map<String, Double> getComposition(LocalDate date) {
    return new HashMap<>(stocks);
  }

  /**
   * Retrieves the total value of the portfolio as of the specified date.
   *
   * @param date the date for which to retrieve the value
   * @return the total value of the portfolio
   */
  public double getValue(LocalDate date) {
    double totalValue = 0.0;
    for (String tickerSymbol : stocks.keySet()) {
      Stock stock = stockData.get(tickerSymbol);
      if (stock != null && stock.getPrices().containsKey(date)) {
        totalValue += stocks.get(tickerSymbol) * stock.getPrices().get(date);
      }
    }
    return totalValue;
  }

  /**
   * Retrieves the value distribution of the portfolio as of the specified date.
   *
   * @param date the date for which to retrieve the value distribution
   * @return a map where the keys are ticker symbols and the values are the value of each stock
   *                                               as a percentage of the total portfolio value
   */
  public Map<String, Double> getValueDistribution(LocalDate date) {
    Map<String, Double> distribution = new HashMap<>();
    double totalValue = getValue(date);
    for (String tickerSymbol : stocks.keySet()) {
      Stock stock = stockData.get(tickerSymbol);
      if (stock != null && stock.getPrices().containsKey(date)) {
        double stockValue = stocks.get(tickerSymbol) * stock.getPrices().get(date);
        distribution.put(tickerSymbol, (stockValue / totalValue) * 100);
      }
    }
    return distribution;
  }

  /**
   * Rebalances the portfolio based on the target weights as of the specified date.
   * If the target weight for "ALL" is provided, the portfolio is equally distributed among all
   * stocks.
   *
   * @param targetWeights a map where the keys are ticker symbols and the values are the target
   *                      weights for each stock
   * @param date          the date for the rebalancing
   */
  public void rebalance(Map<String, Double> targetWeights, LocalDate date) {
    double totalValue = getValue(date);
    if (targetWeights.containsKey("ALL")) {
      int totalStocks = stocks.size();
      double targetWeight = 1.0 / totalStocks; // Equal distribution
      for (String tickerSymbol : stocks.keySet()) {
        Stock stock = stockData.get(tickerSymbol);
        if (stock != null && stock.getPrices().containsKey(date)) {
          double targetValue = totalValue * targetWeight;
          double targetShares = targetValue / stock.getPrices().get(date);
          stocks.put(tickerSymbol, targetShares);
        }
      }
    } else {
      for (Map.Entry<String, Double> entry : targetWeights.entrySet()) {
        String tickerSymbol = entry.getKey();
        double targetWeight = entry.getValue();
        Stock stock = stockData.get(tickerSymbol);
        if (stock != null && stock.getPrices().containsKey(date)) {
          double targetValue = totalValue * targetWeight;
          double targetShares = targetValue / stock.getPrices().get(date);
          stocks.put(tickerSymbol, targetShares);
        }
      }
    }
  }

  /**
   * Saves the portfolio to a file.
   *
   * @param filePath the path of the file to save the portfolio
   * @throws IOException if an I/O error occurs
   */
  public void saveToFile(String filePath) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      writer.write(name + "\n");
      for (String tickerSymbol : stocks.keySet()) {
        writer.write(tickerSymbol + "," + stocks.get(tickerSymbol) + "\n");
      }
    }
  }

  /**
   * Loads a portfolio from a file.
   *
   * @param filePath the path of the file to load the portfolio from
   * @return the loaded Portfolio object
   * @throws IOException if an I/O error occurs
   */
  public static Portfolio loadFromFile(String filePath) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String name = reader.readLine();
      Portfolio portfolio = new Portfolio(name);
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        String tickerSymbol = parts[0];
        double shares = Double.parseDouble(parts[1]);
        portfolio.stocks.put(tickerSymbol, shares);
      }
      return portfolio;
    }
  }

  /**
   * Retrieves the performance of the portfolio over a date range.
   * The performance is represented as a list of portfolio values for each date in the range.
   *
   * @param startDate the start date of the performance period
   * @param endDate   the end date of the performance period
   * @return a list of portfolio values for each date in the specified range
   * @throws IllegalArgumentException if the start date is after the end date
   */
  public List<Double> getPerformance(LocalDate startDate, LocalDate endDate) {
    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("Start date is after the end date.");
    }
    List<Double> performance = new ArrayList<>();
    LocalDate currentDate = startDate;
    while (!currentDate.isAfter(endDate)) {
      performance.add(getValue(currentDate));
      currentDate = currentDate.plusDays(1);
    }
    return performance;
  }
}
