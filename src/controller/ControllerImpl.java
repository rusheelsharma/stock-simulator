package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import api.AlphaVantageAPI;
import model.Model;
import model.Portfolio;
import model.Stock;
import view.View;

/**
 * The ControllerImpl class represents the controller that interacts with the user,
 * processes input, and directs the models to perform operations based on the user's choices.
 */
public class ControllerImpl implements Controller {
  protected Model model;
  protected View view;

  /**
   * Constructs a ControllerImpl object with the specified model and view.
   * This constructor sets up the controller to use the given model and view for
   * performing operations and outputting results.
   *
   * @param model the model that the controller will use
   * @param view  the view that the controller will use to display information
   */
  public ControllerImpl(Model model, View view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Starts the main loop of the application, displaying the menu and handling user choices.
   * This method always presents the user with a menu of options, processes their input,
   * and invokes the appropriate helper methods to perform the selected operations.
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
            handleQueryPortfolioValue();
            break;
          case 6:
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
   * Handles the user choice for gain or loss calculation.
   * This method prompts the user for a stock ticker symbol and a date range, calculates the
   * gain or loss for the stock over that period, and displays the result.
   */
  protected void gainOrLossHandler() {
    try {
      String tickerSymbol = view.getTickerSymbol();
      LocalDate startDate = view.getStartDate();
      LocalDate endDate = view.getEndDate();
      Stock stock = model.getStock(tickerSymbol);
      if (stock != null) {
        double gainOrLoss = stock.gainLossValue(startDate, endDate);
        view.displayMessage("Gain/Loss: $" + gainOrLoss);
      } else {
        view.displayMessage("Stock not found.");
      }
    } catch (Exception e) {
      view.displayMessage("Error calculating gain/loss: " + e.getMessage());
    }
  }

  /**
   * Handles the user choice for x-day moving average calculation.
   * This method prompts the user for a stock ticker symbol, a date, and the number of days (x),
   * calculates the x-day moving average for the stock as of the specified date, and displays
   * the result.
   */
  protected void handleXDayMovingAverage() {
    try {
      String tickerSymbol = view.getTickerSymbol();
      LocalDate date = view.getDate();
      int x = view.getXValue();
      Stock stock = model.getStock(tickerSymbol);
      if (stock != null) {
        double movingAverage = stock.getXDayMovingAverage(date, x);
        view.displayMessage("Moving Average: $" + movingAverage);
      } else {
        view.displayMessage("Stock not found.");
      }
    } catch (Exception e) {
      view.displayMessage("Error calculating moving average: " + e.getMessage());
    }
  }

  /**
   * Handles the user choice for x-day crossover detection.
   * This method prompts the user for a stock ticker symbol, a date range, and the number of days,
   * detects crossovers within that period, and displays the crossover dates.
   */
  protected void handleXDayCrossovers() {
    try {
      String tickerSymbol = view.getTickerSymbol();
      LocalDate startDate = view.getStartDate();
      LocalDate endDate = view.getEndDate();
      int x = view.getXValue();
      Stock stock = model.getStock(tickerSymbol);
      if (stock != null) {
        List<LocalDate> crossovers = stock.getCrossovers(startDate, endDate, x);
        view.displayMessage("Crossovers: " + crossovers);
      } else {
        view.displayMessage("Stock not found.");
      }
    } catch (Exception e) {
      view.displayMessage("Error calculating crossovers: " + e.getMessage());
    }
  }

  /**
   * Handles the user choice for creating a portfolio.
   * This method prompts the user to provide a name for the new portfolio and allows them to add
   * stocks with their respective share quantities.
   */
  protected void createPortfolio() {
    try {
      Portfolio portfolio = new Portfolio("MyPortfolio");
      do {
        String tickerSymbol = view.getTickerSymbol();
        int shares = view.getNumberOfShares();
        try {
          Stock stock = AlphaVantageAPI.getStockData(tickerSymbol);
          model.addStock(tickerSymbol, stock);
          portfolio.addStock(tickerSymbol, shares, stock);
          view.displayMessage("Stock added to portfolio.");
        } catch (IOException e) {
          view.displayMessage("Error fetching stock data.");
        }
      }
      while (view.askToAddMoreStocks());
      model.addPortfolio(portfolio);
      view.displayMessage("Portfolio created.");
    } catch (Exception e) {
      view.displayMessage("Error creating portfolio: " + e.getMessage());
    }
  }

  /**
   * Handles the user choice for querying the value of all portfolios on a specific date.
   * This method prompts the user for a date and displays the total value of each portfolio
   * on that specific date.
   */
  protected void handlePortfolioQuery() {
    try {
      LocalDate date = view.getDate();
      for (Portfolio portfolio : model.getPortfolios()) {
        double totalValue = portfolio.getValue(date);
        view.displayMessage("Portfolio Value on " + date + ": $" + totalValue);
      }
    } catch (Exception e) {
      view.displayMessage("Error querying portfolio value: " + e.getMessage());
    }
  }

  /**
   * Handles the user choice for querying the value of a specific portfolio on a specific date.
   * This method prompts the user to select a portfolio and a date, then displays the value of
   * the selected portfolio on the specified date.
   */
  protected void handleQueryPortfolioValue() {
    try {
      List<Portfolio> portfolios = model.getPortfolios();
      if (portfolios.isEmpty()) {
        view.displayMessage("No portfolios available. Please create a portfolio first.");
        return;
      }

      view.displayMessage("Available Portfolios:");
      for (int i = 0; i < portfolios.size(); i++) {
        view.displayMessage((i + 1) + ". Portfolio " + (i + 1));
      }

      int portfolioChoice = view.getPortfolioChoice(portfolios.size());
      Portfolio selectedPortfolio = portfolios.get(portfolioChoice - 1);

      LocalDate queryDate = view.getDate();
      double portfolioValue = selectedPortfolio.getValue(queryDate);
      view.displayMessage("Value of Portfolio on " + queryDate + ": $" + portfolioValue);
    } catch (Exception e) {
      view.displayMessage("Error querying portfolio value: " + e.getMessage());
    }
  }
}
