package controller;

import api.AlphaVantageAPI;
import model.BetterModel;
import model.Model;
import model.Stock;
import view.GuiView;
import view.View;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The GuiControllerImpl class implements the GuiController interface and provides
 * the functionality for managing stock portfolios using a GUI.
 */
public class GuiControllerImpl extends BetterControllerImpl implements GuiController {
  private final BetterModel model;
  private final GuiView view;
  private final List<String> portfolioNames;

  /**
   * Constructs a GuiControllerImpl with the specified model and view.
   *
   * @param model the model to be used by this controller, must be an instance of BetterModel.
   * @param view the view to be used by this controller, must be an instance of GuiView.
   * @throws IllegalArgumentException if the model is not an instance of BetterModel
   *                                  or the view is not an instance of GuiView
   */
  public GuiControllerImpl(Model model, View view) {
    super(model, view);
    if (model instanceof BetterModel && view instanceof GuiView) {
      this.model = (BetterModel) model;
      this.view = (GuiView) view;
      this.portfolioNames = new ArrayList<>();
    } else {
      throw new IllegalArgumentException("Model must be BetterModel and View must be GuiView");
    }
  }

  /**
   * Executes the main loop of the controller, setting up the GUI and handling user interactions.
   */
  @Override
  public void execute() {
    SwingUtilities.invokeLater(() -> view.setVisible(true));
    addEventHandlers();
    updatePortfolioMenu();
  }

  /**
   * Adds event handlers to the GUI components to handle user interactions.
   */
  private void addEventHandlers() {
    view.getCreatePortfolioButton().addActionListener(e -> createPortfolio());
    view.getBuySellStockButton().addActionListener(e -> buySellStock());
    view.getQueryPortfolioButton().addActionListener(e -> queryPortfolioValue());
    view.getSavePortfolioButton().addActionListener(e -> savePortfolioToLocal());
    view.getLoadPortfolioButton().addActionListener(e -> loadPortfolioFromLocal());
  }

  /**
   * Handles the Buy/Sell stock action by displaying a dialog to choose between buying and
   * selling stocks and calls appropriate methods based on the user's choice.
   */
  private void buySellStock() {
    String[] options = {"Buy", "Sell"};
    int choice = JOptionPane.showOptionDialog(null, "Choose an action",
            "Buy/Sell Stocks", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
            null, options, options[0]);
    if (choice == 0) {
      addStockToPortfolio();
    } else if (choice == 1) {
      removeStockFromPortfolio();
    }
  }

  /**
   * Creates a new portfolio with the name provided by the user.
   */
  @Override
  public void createPortfolio() {
    try {
      String portfolioName = view.getPortfolioName();
      model.createPortfolio(portfolioName);
      portfolioNames.add(portfolioName);
      view.displayMessage("Portfolio created: " + portfolioName, Color.BLACK);
      updatePortfolioMenu();
    } catch (Exception e) {
      view.displayMessage("Error creating portfolio: " + e.getMessage(), Color.RED);
    }
  }

  /**
   * Adds a stock to the selected portfolio based on user input.
   */
  @Override
  public void addStockToPortfolio() {
    try {
      String portfolioName = view.getSelectedPortfolio();
      if (portfolioName == null || portfolioName.equals("Select Portfolio")) {
        view.displayMessage("Please select a valid portfolio.", Color.RED);
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
      view.displayMessage("Stock added: " + tickerSymbol + ", shares: " + shares
              + ", date: " + date, Color.BLACK);
    } catch (Exception e) {
      view.displayMessage("Error adding stock to portfolio: " + e.getMessage(), Color.RED);
    }
  }

  /**
   * Removes a stock from the selected portfolio based on user input.
   */
  @Override
  public void removeStockFromPortfolio() {
    try {
      String portfolioName = view.getSelectedPortfolio();
      if (portfolioName == null || portfolioName.equals("Select Portfolio")) {
        view.displayMessage("Please select a valid portfolio.", Color.RED);
        return;
      }
      String tickerSymbol = view.getTickerSymbol();
      int shares = view.getNumberOfShares();
      LocalDate date = view.getDate();

      model.removeStockFromPortfolio(portfolioName, tickerSymbol, shares, date);
      view.displayMessage("Stock removed: " + tickerSymbol + ", shares: " + shares
              + ", date: " + date, Color.BLACK);
    } catch (Exception e) {
      view.displayMessage("Error removing stock: " + e.getMessage(), Color.RED);
    }
  }

  /**
   * Queries the composition of the selected portfolio and updates the view with the composition
   * details.
   */
  @Override
  public void queryPortfolioComposition() {
    try {
      String portfolioName = view.getSelectedPortfolio();
      if (portfolioName == null || portfolioName.equals("Select Portfolio")) {
        view.displayMessage("Please select a valid portfolio.", Color.RED);
        return;
      }
      LocalDate date = view.getDate();
      Map<String, Double> composition = model.getPortfolioComposition(portfolioName, date);
      view.displayPortfolioComposition(composition);
    } catch (Exception e) {
      view.displayMessage("Error querying composition: " + e.getMessage(), Color.RED);
    }
  }

  /**
   * Queries the value of the selected portfolio on a given date and updates the view with the
   * value.
   */
  @Override
  public void queryPortfolioValue() {
    try {
      String portfolioName = view.getSelectedPortfolio();
      if (portfolioName == null || portfolioName.equals("Select Portfolio")) {
        view.displayMessage("Please select a valid portfolio.", Color.RED);
        return;
      }
      LocalDate date = view.getDate();
      double value = model.getPortfolioValue(portfolioName, date);
      view.displayPortfolioValue(value);
    } catch (Exception e) {
      view.displayMessage("Error querying value: " + e.getMessage(), Color.RED);
    }
  }

  /**
   * Displays the value distribution of the selected portfolio and updates the view with the
   * distribution details.
   */
  @Override
  public void displayPortfolioValueDistribution() {
    try {
      String portfolioName = view.getSelectedPortfolio();
      if (portfolioName == null || portfolioName.equals("Select Portfolio")) {
        view.displayMessage("Please select a valid portfolio.", Color.RED);
        return;
      }
      LocalDate date = view.getDate();
      Map<String, Double> distribution = model.getPortfolioValueDistribution(portfolioName, date);
      view.displayPortfolioValueDistribution(distribution);
    } catch (Exception e) {
      view.displayMessage("Error querying value distribution: " + e.getMessage(),
              Color.RED);
    }
  }

  /**
   * Saves the selected portfolio to a local file based on user input.
   */
  @Override
  public void savePortfolioToLocal() {
    try {
      String portfolioName = view.getSelectedPortfolio();
      if (portfolioName == null || portfolioName.equals("Select Portfolio")) {
        view.displayMessage("Please select a valid portfolio.", Color.RED);
        return;
      }
      String filePath = view.getFilePath();
      model.savePortfolioToLocal(portfolioName, filePath);
      view.displayMessage("Portfolio saved to " + filePath, Color.BLACK);
    } catch (Exception e) {
      view.displayMessage("Error saving portfolio: " + e.getMessage(), Color.RED);
    }
  }

  /**
   * Loads a portfolio from a local file based on user input and updates the portfolio list.
   */
  @Override
  public void loadPortfolioFromLocal() {
    try {
      String filePath = view.getFilePath();
      File file = new File(filePath);
      if (!file.exists()) {
        throw new Exception("File not found: " + filePath);
      }
      model.loadPortfolioFromLocal(filePath);
      portfolioNames.add(filePath); // Add to local list
      updatePortfolioMenu();
      view.displayMessage("Portfolio loaded from " + filePath, Color.BLACK);
    } catch (Exception e) {
      view.displayMessage("Error loading portfolio: " + e.getMessage(), Color.RED);
      System.err.println("Error loading portfolio: " + e.getMessage()); // Print to terminal
    }
  }

  /**
   * Rebalances the selected portfolio to the target weights provided by the user.
   */
  @Override
  public void rebalancePortfolio() {
    try {
      String portfolioName = view.getSelectedPortfolio();
      if (portfolioName == null || portfolioName.equals("Select Portfolio")) {
        view.displayMessage("Please select a valid portfolio.", Color.RED);
        return;
      }
      LocalDate date = view.getDate();
      List<String> tickers = List.copyOf(model.getPortfolioComposition(portfolioName, date)
              .keySet());
      Map<String, Double> targetWeights = view.getTargetWeights(tickers);
      model.rebalancePortfolio(portfolioName, targetWeights, date);
      view.displayMessage("Portfolio rebalanced: " + portfolioName, Color.BLACK);
    } catch (Exception e) {
      view.displayMessage("Error rebalancing portfolio: " + e.getMessage(), Color.RED);
    }
  }

  /**
   * Displays the performance of the selected portfolio over a date range specified by the user.
   */
  @Override
  public void displayPortfolioPerformance() {
    try {
      String portfolioName = view.getSelectedPortfolio();
      if (portfolioName == null || portfolioName.equals("Select Portfolio")) {
        view.displayMessage("Please select a valid portfolio.", Color.RED);
        return;
      }
      LocalDate startDate = view.getStartDate();
      LocalDate endDate = view.getEndDate();
      List<Double> performance = model.getPortfolioPerformance(portfolioName, startDate, endDate);
      view.displayPortfolioPerformance(performance, portfolioName, startDate, endDate);
    } catch (Exception e) {
      view.displayMessage("Error displaying portfolio performance: " + e.getMessage(),
              Color.RED);
    }
  }

  /**
   * Updates the portfolio menu in the view with the list of portfolio names.
   */
  private void updatePortfolioMenu() {
    view.updatePortfolioMenu(portfolioNames);
  }
}
