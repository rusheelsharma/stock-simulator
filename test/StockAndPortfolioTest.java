import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import model.Portfolio;
import model.Stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


/**
 * JUnit tests for the Stock and Portfolio classes.
 * These tests verify the functionality of the Stock and Portfolio classes by testing
 * various methods and scenarios related to stock prices and portfolio values.
 */
public class StockAndPortfolioTest {
  private Stock stock;
  private Portfolio portfolio;

  /**
   * Set up method to initialize test objects before each test case.
   * This method sets up a stock with historical prices and a portfolio containing the stock.
   */
  @Before
  public void setup() {
    portfolio = new Portfolio("MyPortfolio");
    stock = new Stock("AAPL");
    stock.addPrice(LocalDate.of(2021, 1, 1), 100.0);
    stock.addPrice(LocalDate.of(2021, 1, 2), 105.0);
    stock.addPrice(LocalDate.of(2021, 1, 3), 110.0);
    stock.addPrice(LocalDate.of(2021, 1, 4), 115.0);
    portfolio.addStock("AAPL", 10, stock);
  }

  // STOCK TESTS

  /**
   * Test case to verify the gain/loss calculation for a stock.
   * This method checks if the stock's gain/loss is calculated correctly between two dates.
   */
  @Test
  public void testGainLossValue() {
    assertEquals(5.0, stock.gainLossValue(LocalDate.of(2021, 1, 1),
            LocalDate.of(2021, 1, 2)), 0.001);
    assertEquals(15.0, stock.gainLossValue(LocalDate.of(2021, 1, 1),
            LocalDate.of(2021, 1, 4)), 0.001);
  }

  /**
   * Test case to verify that gain/loss calculation throws an exception for invalid dates.
   * This method ensures that the correct exception is thrown when the dates are not present in the
   * price history.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGainLossValueInvalidDates() {
    stock.gainLossValue(LocalDate.of(2021, 1, 1),
            LocalDate.of(2021, 1, 5));
  }

  /**
   * Test case to verify the x-day moving average calculation for a stock.
   * This method checks if the moving average is calculated correctly for a given date and x value.
   */
  @Test
  public void testXDayMovingAverage() {
    assertEquals(102.5, stock.getXDayMovingAverage(LocalDate.of(2021,
            1, 2), 2), 0.001);
    assertEquals(110.0, stock.getXDayMovingAverage(LocalDate.of(2021,
            1, 4), 3), 0.001);
    assertEquals(107.5, stock.getXDayMovingAverage(LocalDate.of(2021,
            1, 4), 4), 0.001);
  }

  /**
   * Test case to verify that the x-day moving average calculation handles edge cases.
   * This method ensures that the moving average is calculated correctly when x exceeds the
   * available data points.
   */
  @Test
  public void testXDayMovingAverageEdgeCase() {
    assertEquals(107.5, stock.getXDayMovingAverage(LocalDate.of(2021, 1,
            4), 10), 0.001);
  }

  /**
   * Test case to verify the crossover detection for a stock.
   * This method checks if the crossovers are identified correctly within a date range and x value.
   */
  @Test
  public void testCrossovers() {
    assertTrue(stock.getCrossovers(LocalDate.of(2021, 1, 1),
            LocalDate.of(2021, 1, 4), 2).contains(LocalDate.of(2021,
            1, 3)));
    assertTrue(stock.getCrossovers(LocalDate.of(2021, 1, 1),
            LocalDate.of(2021, 1, 4), 2).contains(LocalDate.of(2021,
            1, 4)));
  }

  /**
   * Test case to verify the retrieval of historical prices for a stock.
   * This method checks if the prices are returned correctly.
   */
  @Test
  public void testGetPrices() {
    Map<LocalDate, Double> prices = stock.getPrices();
    assertEquals(4, prices.size());
    assertEquals(100.0, prices.get(LocalDate.of(2021, 1, 1)),
            0.001);
  }

  /**
   * Test case to verify the stock constructor and initialization.
   * This method checks if the stock is initialized correctly with the given ticker symbol.
   */
  @Test
  public void testStockConstructor() {
    Stock newStock = new Stock("GOOG");
    assertEquals("GOOG", newStock.getTickerSymbol());
    assertTrue(newStock.getPrices().isEmpty());
  }

  // PORTFOLIO TESTS

  /**
   * Test case to verify the correct value of the portfolio.
   * This method checks if the portfolio's total value is calculated correctly for specific dates.
   */
  @Test
  public void testGetValue() {
    assertEquals(1000.0, portfolio.getValue(LocalDate.of(2021, 1,
            1)), 0.001);
    assertEquals(1050.0, portfolio.getValue(LocalDate.of(2021, 1,
            2)), 0.001);
  }

  /**
   * Test case to verify the addition of a stock to the portfolio.
   * This method checks if the stock is added correctly and the portfolio value is updated.
   */
  @Test
  public void testAddStock() {
    Stock newStock = new Stock("GOOG");
    newStock.addPrice(LocalDate.of(2021, 1, 1), 1500.0);
    portfolio.addStock("GOOG", 5, newStock);
    assertEquals(8500.0, portfolio.getValue(LocalDate.of(2021, 1,
            1)), 0.001);
  }

  /**
   * Test case to verify the removal of a stock from the portfolio.
   * This method checks if the stock is removed correctly and the portfolio value is updated.
   */
  @Test
  public void testRemoveStock() {
    portfolio.removeStock("AAPL", 5, LocalDate.of(2021, 1,
            2));
    assertEquals(525.0, portfolio.getValue(LocalDate.of(2021, 1,
            2)), 0.001);
  }

  /**
   * Test case to verify the update of a stock in the portfolio.
   * This method checks if the stock quantity is updated correctly in the portfolio.
   */
  @Test
  public void testUpdateStock() {
    Stock updatedStock = new Stock("AAPL");
    updatedStock.addPrice(LocalDate.of(2021, 1, 2), 105.0);
    portfolio.updateStock("AAPL", 20, updatedStock);
    assertEquals(2100.0, portfolio.getValue(LocalDate.of(2021, 1,
            2)), 0.001);
  }

  /**
   * Test case to verify the portfolio composition retrieval.
   * This method checks if the composition of the portfolio is returned correctly for a
   * specific date.
   */
  @Test
  public void testGetComposition() {
    Map<String, Double> composition = portfolio.getComposition(LocalDate.of(2021,
            1, 1));
    assertEquals(1, composition.size());
    assertEquals(10, (double) composition.get("AAPL"), 0.1);
  }

  /**
   * Test case to verify the portfolio value distribution retrieval.
   * This method checks if the value distribution of the portfolio is returned correctly for a
   * specific date.
   */
  @Test
  public void testGetValueDistribution() {
    Map<String, Double> distribution = portfolio.getValueDistribution(LocalDate.of(2021,
            1, 2));
    assertEquals(1, distribution.size());
    assertEquals(100.0, distribution.get("AAPL"), 0.001);
  }

  /**
   * Test case to verify the portfolio rebalancing.
   * This method checks if the portfolio is rebalanced correctly based on target weights.
   */
  @Test
  public void testRebalance() {
    Stock googleStock = new Stock("GOOG");
    googleStock.addPrice(LocalDate.of(2021, 1, 2), 1500.0);
    portfolio.addStock("GOOG", 2, googleStock);
    Map<String, Double> targetWeights = new HashMap<>();
    targetWeights.put("AAPL", 0.5);
    targetWeights.put("GOOG", 0.5);
    portfolio.rebalance(targetWeights, LocalDate.of(2021, 1, 2));
    assertEquals(50, portfolio.getValueDistribution(LocalDate.of(2021, 1,
            2)).get("AAPL"), 0.1);
    assertEquals(50, portfolio.getValueDistribution(LocalDate.of(2021, 1,
            2)).get("GOOG"), 0.1);
  }

  /**
   * Test case to verify the portfolio rebalancing with equal distribution.
   * This method checks if the portfolio is rebalanced correctly with an equal distribution among
   * all stocks.
   */
  @Test
  public void testRebalanceEqualDistribution() {
    Stock googleStock = new Stock("GOOG");
    googleStock.addPrice(LocalDate.of(2021, 1, 2), 1500.0);
    portfolio.addStock("GOOG", 2, googleStock);
    Map<String, Double> targetWeights = new HashMap<>();
    targetWeights.put("ALL", 1.0);
    portfolio.rebalance(targetWeights, LocalDate.of(2021, 1, 2));
    assertEquals(50.0, portfolio.getValueDistribution(LocalDate.of(2021, 1,
            2)).get("AAPL"), 0.1);
    assertEquals(50.0, portfolio.getValueDistribution(LocalDate.of(2021, 1,
            2)).get("GOOG"), 0.1);
  }

  /**
   * Test case to verify the portfolio performance retrieval.
   * This method checks if the portfolio performance is returned correctly for a specific date
   * range.
   */
  @Test
  public void testGetPerformance() {
    portfolio.addStock("AAPL", 5, stock);
    assertEquals(1500.0, portfolio.getPerformance(LocalDate.of(2021, 1,
                    1),
            LocalDate.of(2021, 1, 1)).get(0), 0.001);
    assertEquals(1575.0, portfolio.getPerformance(LocalDate.of(2021, 1,
                    1),
            LocalDate.of(2021, 1, 2)).get(1), 0.001);
  }

  /**
   * Test case to verify the constructor of Portfolio.
   * This method checks if the Portfolio object is initialized correctly with the given name.
   */
  @Test
  public void testPortfolioConstructor() {
    Portfolio newPortfolio = new Portfolio("NewPortfolio");
    assertEquals("NewPortfolio", newPortfolio.getName());
    assertTrue(newPortfolio.getComposition(LocalDate.now()).isEmpty());
  }

  /**
   * Test case to verify the exception thrown for negative shares in addStock method.
   * This method checks if an IllegalArgumentException is thrown for negative shares.
   */
  @Test
  public void testAddStockNegativeShares() {
    Stock newStock = new Stock("GOOG");
    assertThrows(IllegalArgumentException.class, () -> {
      portfolio.addStock("GOOG", -5, newStock);
    });
  }

  /**
   * Test case to verify the exception thrown for negative shares in updateStock method.
   * This method checks if an IllegalArgumentException is thrown for negative shares.
   */
  @Test
  public void testUpdateStockNegativeShares() {
    Stock newStock = new Stock("GOOG");
    assertThrows(IllegalArgumentException.class, () -> {
      portfolio.updateStock("GOOG", -5, newStock);
    });
  }

  /**
   * Test case to verify the exception thrown for negative shares in removeStock method.
   * This method checks if an IllegalArgumentException is thrown for negative shares.
   */
  @Test
  public void testRemoveStockNegativeShares() {
    assertThrows(IllegalArgumentException.class, () -> {
      portfolio.removeStock("AAPL", -5, LocalDate.of(2021, 1,
              2));
    });
  }


}
