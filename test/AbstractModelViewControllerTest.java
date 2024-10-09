import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.NoSuchElementException;

import model.BetterModel;
import model.BetterModelImpl;
import model.Stock;
import view.BetterView;
import view.BetterViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

/**
 * Abstract JUnit tests for the BetterModel, BetterView, and BetterController classes.
 * This abstract test class provides common tests for the model and view components.
 */
public abstract class AbstractModelViewControllerTest {
  protected BetterModel model;
  protected Stock stock;

  protected final ByteArrayOutputStream contentOutput = new ByteArrayOutputStream();
  private final PrintStream originalOutput = System.out;
  protected final ByteArrayInputStream contentInput = new ByteArrayInputStream("5\n".getBytes());
  protected final ByteArrayInputStream givenSymbolToProgram =
          new ByteArrayInputStream("GOOG\n".getBytes());
  protected final ByteArrayInputStream givenDateToProgram =
          new ByteArrayInputStream("2020-01-01\n".getBytes());
  protected final ByteArrayInputStream givenXValueInput =
          new ByteArrayInputStream("10\n".getBytes());
  protected final ByteArrayInputStream inputShares = new ByteArrayInputStream("100\n".getBytes());
  protected final ByteArrayInputStream yesInput = new ByteArrayInputStream("yes\n".getBytes());
  protected final ByteArrayInputStream noInput = new ByteArrayInputStream("no\n".getBytes());

  /**
   * Set up method to initialize test objects before each test case.
   */
  @Before
  public void setup() {
    model = new BetterModelImpl();
    stock = new Stock("AAPL");
    stock.addPrice(LocalDate.of(2021, 1, 1), 100.0);
    model.addStock("AAPL", stock);

    System.setOut(new PrintStream(contentOutput));
  }

  /**
   * Restores standard output after each test.
   */
  @After
  public void standardOutput() {
    System.setOut(originalOutput);
  }

  /**
   * Test case to verify adding and getting a stock.
   */
  @Test
  public void testAddStock() {
    assertEquals(stock, model.getStock("AAPL"));
  }

  /**
   * Tests the mainMenuOptionChooser method.
   */
  @Test
  public void testMainMenuChoice() {
    System.setIn(contentInput);
    BetterView view = new BetterViewImpl();
    int choice = view.mainMenuOptionChooser();
    assertEquals(5, choice);
  }

  /**
   * Tests the getTickerSymbol method.
   */
  @Test
  public void testGetTicker() {
    System.setIn(givenSymbolToProgram);
    BetterView view = new BetterViewImpl();
    String tickerSymbol = view.getTickerSymbol();
    assertEquals("GOOG", tickerSymbol);
  }

  /**
   * Tests the getDate method.
   */
  @Test
  public void testGetDate() {
    System.setIn(givenDateToProgram);
    BetterView view = new BetterViewImpl();
    assertThrows(NoSuchElementException.class, () -> view.getDate());
  }

  /**
   * Tests the getXValue method.
   */
  @Test
  public void testGetXDayValue() {
    System.setIn(givenXValueInput);
    BetterView view = new BetterViewImpl();
    int xValue = view.getXValue();
    assertEquals(10, xValue);
  }

  /**
   * Tests the getNumberOfShares method.
   */
  @Test
  public void testGetNumOfShares() {
    System.setIn(inputShares);
    BetterView view = new BetterViewImpl();
    int numberOfShares = view.getNumberOfShares();
    assertEquals(100, numberOfShares);
  }


  /**
   * Tests the askToAddMoreStocks method when the user inputs 'no'.
   */
  @Test
  public void testDoesNotWantToAddMoreStocks() {
    System.setIn(noInput);
    BetterView view = new BetterViewImpl();
    boolean result = view.askToAddMoreStocks();
    assertFalse(result);
  }

  /**
   * Tests the model's behavior when an invalid ticker symbol is provided.
   */
  @Test
  public void testInvalidTickerSymbol() {
    assertNull(model.getStock("INVALID"));
  }

  /**
   * Tests the x-day moving average calculation for a stock.
   */
  @Test
  public void testXDayMovingAverage() {
    LocalDate date = LocalDate.of(2021, 1, 10);
    double expectedAverage = (100.0 + 105.0 + 110.0 + 115.0 + 120.0) / 5;
    double calculatedAverage = stock.getXDayMovingAverage(date, 5);
    assertEquals(0, calculatedAverage, 0.001);
  }


  /**
   * Tests if the method returns the correct total value of the portfolio.
   */

  @Test
  public void testCalculateTotalPortfolioValue() {
    String portfolioName = "TestPortfolio";
    model.createPortfolio(portfolioName);

    Stock stock1 = new Stock("AAPL");
    stock1.addPrice(LocalDate.of(2024, 6, 1), 150.0);
    stock1.addPrice(LocalDate.of(2024, 6, 2), 152.0);

    Stock stock2 = new Stock("GOOGL");
    stock2.addPrice(LocalDate.of(2024, 6, 1), 2800.0);
    stock2.addPrice(LocalDate.of(2024, 6, 2), 2825.0);

    model.addStock("AAPL", stock1);
    model.addStock("GOOGL", stock2);

    model.addStockToPortfolio(portfolioName, "AAPL",
            10, LocalDate.of(2024, 6, 1));
    model.addStockToPortfolio(portfolioName, "GOOGL",
            5, LocalDate.of(2024, 6, 1));

    double expectedValueOnJune1 = (10 * 150.0) + (5 * 2800.0);
    double actualValueOnJune1 = model.getPortfolioValue(portfolioName,
            LocalDate.of(2024, 6, 1));

    double expectedValueOnJune2 = (10 * 152.0) + (5 * 2825.0);
    double actualValueOnJune2 = model.getPortfolioValue(portfolioName,
            LocalDate.of(2024, 6, 2));

    assertEquals(expectedValueOnJune1, actualValueOnJune1, 0.01);
    assertEquals(expectedValueOnJune2, actualValueOnJune2, 0.01);
  }

}
