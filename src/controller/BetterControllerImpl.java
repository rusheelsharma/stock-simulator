package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import api.AlphaVantageAPI;
import model.BetterModel;
import model.Model;
import view.BetterView;
import view.View;
import model.Stock;

/**
 * The BetterControllerImpl class implements the BetterController interface and provides
 * the functionality for managing stock portfolios. Those features include creating, modifying,
 * querying, saving, loading, rebalancing, and displaying portfolio information.
 */
public class BetterControllerImpl extends ControllerImpl implements BetterController {
  private static final String BASE_PATH = System.getProperty("user.dir") + "/";
  private final BetterModel model;
  private final BetterView view;

  /**
   * Constructs a BetterControllerImpl object with the specified model and view.
   *
   * @param model the model to be used by this controller
   * @param view  the view to be used by this controller
   * @throws IllegalArgumentException if the model is not an instance of BetterModel
   *                                  or the view is not an instance of BetterView
   */
  public BetterControllerImpl(Model model, View view) {
    super(model, view);
    if (model instanceof BetterModel && view instanceof BetterView) {
      this.model = (BetterModel) model;
      this.view = (BetterView) view;
    } else {
      throw new IllegalArgumentException("Model must be BetterModel and View must be BetterView");
    }

    createBasePath();
  }

  /**
   * Creates the base directory for storing portfolio files if it does not already exist.
   */
  private void createBasePath() {
    Path path = Paths.get(BASE_PATH);
    if (!Files.exists(path)) {
      try {
        Files.createDirectories(path);
        System.out.println("Created base directory: " + BASE_PATH);
      } catch (IOException e) {
        view.displayMessage("Error creating base directory: " + e.getMessage());
      }
    }
  }

  /**
   * Executes the main loop of the controller, presenting the main menu and handling user choices.
   */
  @Override
  public void execute() {
    boolean running = true;
    while (running) {
      try {
        int choice = view.mainMenuOptionChooser();
        switch (choice) {
          case 1:
            gainOrLossHandler();
            break;
          case 2:
            handleXDayMovingAverage();
            break;
          case 3:
            handleXDayCrossovers();
            break;
          case 4:
            createPortfolio();
            break;
          case 5:
            addStockToPortfolio();
            break;
          case 6:
            removeStockFromPortfolio();
            break;
          case 7:
            queryPortfolioComposition();
            break;
          case 8:
            queryPortfolioValue();
            break;
          case 9:
            displayPortfolioValueDistribution();
            break;
          case 10:
            savePortfolioToLocal();
            break;
          case 11:
            loadPortfolioFromLocal();
            break;
          case 12:
            rebalancePortfolio();
            break;
          case 13:
            displayPortfolioPerformance();
            break;
          case 14:
            running = false;
            break;
          default:
            view.displayMessage("Invalid choice. Please try again.");
        }
      } catch (Exception e) {
        view.displayMessage("An error occurred: " + e.getMessage());
      }
    }
  }

  /**
   * Creates a new portfolio.
   * This method prompts the user to provide necessary details and initializes a new portfolio.
   */
  @Override
  public void createPortfolio() {
    try {
      String portfolioName = view.getPortfolioName();
      model.createPortfolio(portfolioName);
      System.out.println("Creating portfolio: " + portfolioName);
      do {
        String tickerSymbol = view.getTickerSymbol();
        int shares = view.getNumberOfShares();
        try {
          Stock stock = AlphaVantageAPI.getStockData(tickerSymbol);
          model.addStock(tickerSymbol, stock);
          model.addStockToPortfolio(portfolioName, tickerSymbol, shares, LocalDate.now());
          view.displayMessage("Stock added to portfolio: " + tickerSymbol + " with " + shares
                  + " shares.");
        } catch (IOException e) {
          view.displayMessage("Error fetching stock data: " + e.getMessage());
        }
      }
      while (view.askToAddMoreStocks());

      view.displayMessage("Portfolio created.");
    } catch (Exception e) {
      view.displayMessage("Error creating portfolio: " + e.getMessage());
    }
  }

  /**
   * Adds a stock to an existing portfolio.
   * This method prompts the user for the portfolio identifier and stock details,
   * and adds the specified stock to the portfolio.
   */
  @Override
  public void addStockToPortfolio() {
    try {
      String portfolioName = view.getPortfolioName();
      if (!model.containsPortfolio(portfolioName)) {
        view.displayMessage("Portfolio not found: " + portfolioName);
        return;
      }
      String tickerSymbol = view.getTickerSymbol();
      int shares = view.getNumberOfShares();
      LocalDate date = view.getDate();
      Stock stock = model.getStock(tickerSymbol);
      if (stock == null) {
        stock = AlphaVantageAPI.getStockData(tickerSymbol);
        model.addStock(tickerSymbol, stock);
      }
      model.addStockToPortfolio(portfolioName, tickerSymbol, shares, date);
      view.displayMessage(shares + " shares of " + tickerSymbol + " added to portfolio "
              + portfolioName + ".");
    } catch (Exception e) {
      view.displayMessage("Error adding stock to portfolio: " + e.getMessage());
    }
  }

  /**
   * Removes a stock from an existing portfolio.
   * This method prompts the user for the portfolio identifier and the stock to be removed,
   * and removes the specified stock from the portfolio.
   */
  @Override
  public void removeStockFromPortfolio() {
    try {
      String portfolioName = view.getPortfolioName();
      if (!model.containsPortfolio(portfolioName)) {
        view.displayMessage("Portfolio not found: " + portfolioName);
        return;
      }
      String tickerSymbol = view.getTickerSymbol();
      int shares = view.getNumberOfShares();
      LocalDate date = view.getDate();
      Stock stock = model.getStock(tickerSymbol);
      if (stock == null) {
        stock = AlphaVantageAPI.getStockData(tickerSymbol);
        model.addStock(tickerSymbol, stock);
      }
      model.removeStockFromPortfolio(portfolioName, tickerSymbol, shares, date);
      view.displayMessage(shares + " shares of " + tickerSymbol + " removed from portfolio "
              + portfolioName + ".");
    } catch (Exception e) {
      view.displayMessage("Error removing stock from portfolio: " + e.getMessage());
    }
  }

  /**
   * Queries the composition of a portfolio.
   * This method prompts the user for the portfolio identifier and displays the list of stocks
   * and their respective quantities in the portfolio.
   */
  @Override
  public void queryPortfolioComposition() {
    try {
      String portfolioName = view.getPortfolioName();
      if (!model.containsPortfolio(portfolioName)) {
        view.displayMessage("Portfolio not found: " + portfolioName);
        return;
      }
      LocalDate date = view.getDate();
      Map<String, Double> composition = model.getPortfolioComposition(portfolioName, date);
      view.displayPortfolioComposition(composition);
    } catch (Exception e) {
      view.displayMessage("Error querying portfolio composition: " + e.getMessage());
    }
  }

  /**
   * Queries the total value of a portfolio.
   * This method prompts the user for the portfolio identifier and displays the total value
   * of the portfolio based on current stock prices.
   */
  @Override
  public void queryPortfolioValue() {
    try {
      String portfolioName = view.getPortfolioName();
      if (!model.containsPortfolio(portfolioName)) {
        view.displayMessage("Portfolio not found: " + portfolioName);
        return;
      }
      LocalDate date = view.getDate();
      double value = model.getPortfolioValue(portfolioName, date);
      view.displayPortfolioValue(value);
    } catch (Exception e) {
      view.displayMessage("Error querying portfolio value: " + e.getMessage());
    }
  }

  /**
   * Queries the value distribution of a portfolio.
   * This method prompts the user for the portfolio identifier and displays the value distribution
   * across different stocks in the portfolio.
   */
  @Override
  public void displayPortfolioValueDistribution() {
    try {
      String portfolioName = view.getPortfolioName();
      if (!model.containsPortfolio(portfolioName)) {
        view.displayMessage("Portfolio not found: " + portfolioName);
        return;
      }
      LocalDate date = view.getDate();
      Map<String, Double> distribution = model.getPortfolioValueDistribution(portfolioName, date);
      view.displayPortfolioValueDistribution(distribution);
    } catch (Exception e) {
      view.displayMessage("Error querying portfolio value distribution: " + e.getMessage());
    }
  }

  /**
   * Saves the current state of a portfolio to a file.
   * This method prompts the user for the portfolio identifier and a file location,
   * and saves the portfolio data to the specified file.
   */
  @Override
  public void savePortfolioToLocal() {
    try {
      String portfolioName = view.getPortfolioName();
      if (!model.containsPortfolio(portfolioName)) {
        view.displayMessage("Portfolio not found: " + portfolioName);
        return;
      }
      String fileName = view.getFilePath();
      if (!fileName.endsWith(".csv")) {
        view.displayMessage("Error: File name must end with .csv");
        return;
      }
      String fullPath = BASE_PATH + fileName;
      System.out.println("Saving portfolio to: " + fullPath);
      model.savePortfolioToLocal(portfolioName, fullPath);
      view.displayMessage("Portfolio " + portfolioName + " saved to " + fullPath + ".");
    } catch (Exception e) {
      view.displayMessage("Error saving portfolio: " + e.getMessage());
    }
  }

  /**
   * Loads a portfolio from a file.
   * This method prompts the user for a file location and loads the portfolio data from
   * the specified file into the application.
   */
  @Override
  public void loadPortfolioFromLocal() {
    try {
      String fileName = view.getFilePath();
      if (!fileName.endsWith(".csv")) {
        view.displayMessage("Error: File name must end with .csv");
        return;
      }
      String fullPath = BASE_PATH + fileName;
      System.out.println("Loading portfolio from: " + fullPath);
      model.loadPortfolioFromLocal(fullPath);
      view.displayMessage("Portfolio loaded from " + fullPath + ".");
    } catch (Exception e) {
      view.displayMessage("Error loading portfolio: " + e.getMessage());
    }
  }

  /**
   * Rebalances a portfolio based on specified criteria.
   * This method prompts the user for the portfolio identifier and rebalancing criteria,
   * and adjusts the stock quantities in the portfolio to meet the specified criteria.
   */
  @Override
  public void rebalancePortfolio() {
    try {
      String portfolioName = view.getPortfolioName();
      if (!model.containsPortfolio(portfolioName)) {
        view.displayMessage("Portfolio not found: " + portfolioName);
        return;
      }
      Map<String, Double> composition = model.getPortfolioComposition(portfolioName,
              view.getDate());
      List<String> tickers = new ArrayList<>(composition.keySet());
      Map<String, Double> targetWeights = view.getTargetWeights(tickers);
      LocalDate date = view.getDate();
      model.rebalancePortfolio(portfolioName, targetWeights, date);
      view.displayMessage("Portfolio " + portfolioName + " rebalanced successfully.");
    } catch (Exception e) {
      view.displayMessage("Error rebalancing portfolio: " + e.getMessage());
    }
  }

  /**
   * Displays the performance of a portfolio over time.
   * This method prompts the user for the portfolio identifier and a time range,
   * and displays the performance of the portfolio during the specified period.
   */
  @Override
  public void displayPortfolioPerformance() {
    try {
      String portfolioName = view.getPortfolioName();
      if (!model.containsPortfolio(portfolioName)) {
        view.displayMessage("Portfolio not found: " + portfolioName);
        return;
      }
      LocalDate startDate = view.getStartDate();
      LocalDate endDate = view.getEndDate();
      if (startDate.isAfter(endDate)) {
        view.displayMessage("Start date was after the end date. Please try again.");
        return;
      }
      List<Double> performance = model.getPortfolioPerformance(portfolioName, startDate, endDate);
      view.displayPortfolioPerformance(performance, portfolioName, startDate, endDate);
    } catch (Exception e) {
      view.displayMessage("Error displaying portfolio performance: " + e.getMessage());
    }
  }
}
