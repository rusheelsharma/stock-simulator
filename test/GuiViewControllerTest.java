import controller.GuiControllerImpl;
import model.BetterModel;
import model.BetterModelImpl;
import model.Stock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

/**
 * Unit tests for the GUI controller implementation using a test GUI view and model.
 */
public class GuiViewControllerTest {

  private MockGuiImpl view;
  private BetterModel model;
  private GuiControllerImpl controller;
  private StringBuilder output;

  /**
   * Sets up the test environment before each test.
   */
  @Before
  public void setup() {
    output = new StringBuilder();
    model = new BetterModelImpl() {
      private final Map<String, Map<LocalDate, Map<String, Integer>>> portfolios = new HashMap<>();

      @Override
      public void createPortfolio(String portfolioName) {
        portfolios.put(portfolioName, new HashMap<>());
      }

      @Override
      public void addStock(String tickerSymbol, Stock stock) {
        // No implementation needed for testing
      }

      @Override
      public Stock getStock(String tickerSymbol) {
        return null;
      }

      @Override
      public void addStockToPortfolio(String portfolioName, String tickerSymbol, int shares,
                                      LocalDate date) {
        portfolios.get(portfolioName).putIfAbsent(date, new HashMap<>());
        portfolios.get(portfolioName).get(date).put(tickerSymbol, shares);
      }

      @Override
      public void removeStockFromPortfolio(String portfolioName, String tickerSymbol, int shares,
                                           LocalDate date) {
        portfolios.get(portfolioName).get(date).remove(tickerSymbol);
      }

      @Override
      public Map<String, Double> getPortfolioComposition(String portfolioName, LocalDate date) {
        return null;
      }

      @Override
      public double getPortfolioValue(String portfolioName, LocalDate date) {
        return 0;
      }

      @Override
      public Map<String, Double> getPortfolioValueDistribution(String portfolioName,
                                                               LocalDate date) {
        return null;
      }

      @Override
      public void savePortfolioToLocal(String portfolioName, String filePath) {
        try {
          FileWriter writer = new FileWriter(filePath);
          writer.write("Sample portfolio data");
          writer.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      @Override
      public void loadPortfolioFromLocal(String filePath) {
        if (!new File(filePath).exists()) {
          throw new RuntimeException("File not found: " + filePath);
        }
      }

      @Override
      public void rebalancePortfolio(String portfolioName, Map<String, Double> targetWeights,
                                     LocalDate date) {
        // No implementation needed for testing
      }

      @Override
      public java.util.List<Double> getPortfolioPerformance(String portfolioName,
                                                            LocalDate startDate,
                                                            LocalDate endDate) {
        return null;
      }
    };
  }

  /**
   * Cleans up after each test.
   * This method is run after each test method.
   */
  @After
  public void testcleanup() {
    // No implementation needed for testing
  }

  /**
   * Tests the creation of a portfolio with a valid name.
   */
  @Test
  public void testCreatePortfolioValidName() {
    Scanner input = new Scanner("testPortfolio\n");
    view = new MockGuiImpl(input, output);
    controller = new GuiControllerImpl(model, view);
    controller.createPortfolio();
    assertTrue(output.toString().contains("Portfolio created: testPortfolio"));
  }


  /**
   * Tests adding a stock to an invalid portfolio.
   */
  @Test
  public void testAddStockToInvalidPortfolio() {
    Scanner input = new Scanner("invalidPortfolio\nAAPL\n10\n2022\n1\n1\n");
    view = new MockGuiImpl(input, output);
    view.updatePortfolioMenu(java.util.Arrays.asList("testPortfolio"));
    controller = new GuiControllerImpl(model, view);
    controller.addStockToPortfolio();
    assertTrue(output.toString().contains("Please select a valid portfolio."));
  }

  /**
   * Tests loading a portfolio from an invalid file.
   */
  @Test
  public void testNewPortfolioLocalInvalidFile() {
    Scanner input = new Scanner("nonexistent.csv\n");
    view = new MockGuiImpl(input, output);
    controller = new GuiControllerImpl(model, view);
    controller.loadPortfolioFromLocal();
    assertTrue(output.toString()
            .contains("Error loading portfolio: File not found: nonexistent.csv"));
  }

  /**
   * Tests loading a portfolio from a valid file.
   * @throws IOException if an I/O error occurs
   */
  @Test
  public void testNewPortfolioLocalValidFile() throws IOException {
    String filePath = "validPortfolio.csv";
    File file = new File(filePath);
    file.createNewFile();
    Scanner input = new Scanner(filePath + "\n");
    view = new MockGuiImpl(input, output);
    controller = new GuiControllerImpl(model, view);
    controller.loadPortfolioFromLocal();
    assertTrue(output.toString().contains("Portfolio loaded from validPortfolio.csv"));
    file.delete();
  }
}
