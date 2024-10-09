package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Stock class represents a single stock and its historical prices.
 * It provides methods for managing the stock's prices, calculating gain or loss,
 * calculating moving averages, and identifying crossovers.
 */
public class Stock {
  private String tickerSymbol;
  private Map<LocalDate, Double> prices;

  /**
   * Constructs a Stock object with the specified ticker symbol.
   *
   * @param tickerSymbol the ticker symbol of the stock
   */
  public Stock(String tickerSymbol) {
    this.tickerSymbol = tickerSymbol;
    this.prices = new HashMap<>();
  }

  /**
   * Adds a closing price for a specific date.
   *
   * @param date  the date of the closing price
   * @param price the closing price of the stock
   */
  public void addPrice(LocalDate date, double price) {
    if (date == null || price < 0) {
      throw new IllegalArgumentException("Invalid date or price.");
    }
    prices.put(date, price);
  }

  /**
   * Gets and returns the ticker symbol of the stock.
   *
   * @return the ticker symbol of the stock
   */
  public String getTickerSymbol() {
    return tickerSymbol;
  }

  /**
   * Calculates the gain or loss of the stock between two dates.
   * This method assumes both dates are valid and present in the price history.
   *
   * @param startDate the start date
   * @param endDate   the end date
   * @return the gain or loss of the stock between the two dates
   * @throws IllegalArgumentException if the start or end date is not in the price history
   *                                  or if start date is after end date
   */
  public double gainLossValue(LocalDate startDate, LocalDate endDate) {
    validateDateRange(startDate, endDate);
    if (!prices.containsKey(startDate) || !prices.containsKey(endDate)) {
      throw new IllegalArgumentException("Start date or end date not found in price history.");
    }
    return prices.get(endDate) - prices.get(startDate);
  }

  /**
   * Calculates the x-day moving average for a specific date.
   * This method averages the closing prices for the specified number of days up to the given date.
   *
   * @param date the date for which to calculate the moving average
   * @param x    the number of days in the moving average
   * @return the x-day moving average for the given date
   */
  public double getXDayMovingAverage(LocalDate date, int x) {
    if (x <= 0) {
      throw new IllegalArgumentException("X must be greater than 0.");
    }
    double sum = 0.0;
    int count = 0;

    for (int i = 0; i < x; i++) {
      LocalDate d = date.minusDays(i);
      if (prices.containsKey(d)) {
        sum += prices.get(d);
        count++;
      }
    }
    return count == 0 ? 0 : sum / count;
  }

  /**
   * Identifies the x-day crossovers within a date range.
   * A crossover is defined as a date where the closing price exceeds the x-day moving average.
   *
   * @param startDate the start date of the range
   * @param endDate   the end date of the range
   * @param x         the number of days in the moving average
   * @return a list of dates that are x-day crossovers
   */
  public List<LocalDate> getCrossovers(LocalDate startDate, LocalDate endDate, int x) {
    validateDateRange(startDate, endDate);
    List<LocalDate> crossovers = new ArrayList<>();

    for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
      if (prices.containsKey(date)) {
        double movingAverage = getXDayMovingAverage(date, x);
        if (prices.get(date) > movingAverage) {
          crossovers.add(date);
        }
      }
    }
    return crossovers;
  }

  /**
   * Retrieves the historical prices of the stock.
   *
   * @return a map of dates to their corresponding prices
   */
  public Map<LocalDate, Double> getPrices() {
    return prices;
  }

  /**
   * Validates that the start date is before or equal to the end date.
   *
   * @param startDate the start date
   * @param endDate   the end date
   * @throws IllegalArgumentException if the start date is after the end date
   */
  private void validateDateRange(LocalDate startDate, LocalDate endDate) {
    if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("Start date must be before or equal to the end date.");
    }
  }
}
