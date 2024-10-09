package view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The GuiViewImpl class implements the GuiView interface and provides a GUI for interacting with
 * the application.
 */
public class GuiViewImpl extends JFrame implements GuiView {
  private final JButton createPortfolioButton;
  private final JButton buySellStockButton;
  private final JButton queryPortfolioButton;
  private final JButton savePortfolioButton;
  private final JButton loadPortfolioButton;
  private final JTextArea displayArea;
  private final JComboBox<String> portfolioMenu;
  private final JTextField yearField;
  private final JTextField monthField;
  private final JTextField dayField;

  /**
   * Constructs a GuiViewImpl and sets up the initial layout and components of the GUI.
   */
  public GuiViewImpl() {
    setTitle("Stock Portfolio Manager");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    getContentPane().setBackground(Color.LIGHT_GRAY);

    JLabel titleLabel = new JLabel("Stock Analysis Simulator", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
    titleLabel.setOpaque(true);
    titleLabel.setBackground(Color.DARK_GRAY);
    titleLabel.setForeground(Color.WHITE);
    add(titleLabel, BorderLayout.NORTH);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(6, 1, 10, 10));
    buttonPanel.setBackground(Color.LIGHT_GRAY);

    createPortfolioButton = new JButton("Create New Portfolio");
    buySellStockButton = new JButton("Buy/Sell Stocks");
    queryPortfolioButton = new JButton("Query Portfolio");
    savePortfolioButton = new JButton("Save Portfolio");
    loadPortfolioButton = new JButton("Load Portfolio");

    styleButton(createPortfolioButton);
    styleButton(buySellStockButton);
    styleButton(queryPortfolioButton);
    styleButton(savePortfolioButton);
    styleButton(loadPortfolioButton);

    buttonPanel.add(createPortfolioButton);
    buttonPanel.add(buySellStockButton);
    buttonPanel.add(queryPortfolioButton);
    buttonPanel.add(savePortfolioButton);
    buttonPanel.add(loadPortfolioButton);

    portfolioMenu = new JComboBox<>();
    portfolioMenu.addItem("Select Portfolio");

    JPanel portfolioPanel = new JPanel();
    portfolioPanel.setLayout(new FlowLayout());
    portfolioPanel.setBackground(Color.LIGHT_GRAY);
    portfolioPanel.add(new JLabel("Select Portfolio:"));
    portfolioPanel.add(portfolioMenu);

    add(buttonPanel, BorderLayout.WEST);
    add(portfolioPanel, BorderLayout.SOUTH);

    displayArea = new JTextArea();
    displayArea.setEditable(false);
    displayArea.setBackground(Color.WHITE);
    displayArea.setForeground(Color.BLACK);
    JScrollPane scrollPane = new JScrollPane(displayArea);
    add(scrollPane, BorderLayout.CENTER);

    yearField = new JTextField(4);
    monthField = new JTextField(2);
    dayField = new JTextField(2);
  }

  /**
   * Styles a JButton with some basic designs.
   *
   * @param button the JButton to style
   */
  private void styleButton(JButton button) {
    button.setFont(new Font("Serif", Font.PLAIN, 16));
    button.setBackground(Color.DARK_GRAY);
    button.setForeground(Color.BLACK);
  }

  /**
   * Displays a message to the user in the black color.
   *
   * @param message the message to be displayed
   */
  @Override
  public void displayMessage(String message) {
    displayMessage(message, Color.BLACK);
  }

  /**
   * Displays a message to the user in the specified color.
   *
   * @param message the message to be displayed
   * @param color the color of the message text
   */
  @Override
  public void displayMessage(String message, Color color) {
    displayMessageWithColor(message, color);
  }

  /**
   * Displays a message to the user in the specified color.
   *
   * @param message the message to be displayed
   * @param color the color of the message text
   */
  private void displayMessageWithColor(String message, Color color) {
    displayArea.setForeground(color);
    displayArea.append(message + "\n");
  }

  /**
   * Returns the button component for creating a new portfolio.
   *
   * @return the create portfolio button
   */
  @Override
  public JButton getCreatePortfolioButton() {
    return createPortfolioButton;
  }

  /**
   * Returns the button component for buying or selling stocks.
   *
   * @return the buy/sell stock button
   */
  @Override
  public JButton getBuySellStockButton() {
    return buySellStockButton;
  }

  /**
   * Returns the button component for querying the value of a portfolio.
   *
   * @return the query portfolio button
   */
  @Override
  public JButton getQueryPortfolioButton() {
    return queryPortfolioButton;
  }

  /**
   * Returns the button component for saving a portfolio to a local file.
   *
   * @return the save portfolio button
   */
  @Override
  public JButton getSavePortfolioButton() {
    return savePortfolioButton;
  }

  /**
   * Returns the button component for loading a portfolio from a local file.
   *
   * @return the load portfolio button
   */
  @Override
  public JButton getLoadPortfolioButton() {
    return loadPortfolioButton;
  }

  /**
   * Sets the visibility of the GUI.
   *
   * @param visible true to make the GUI visible, false to hide it
   */
  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);
  }

  /**
   * Displays the main menu option chooser. This implementation always returns 0.
   *
   * @return the selected menu option
   */
  @Override
  public int mainMenuOptionChooser() {
    return 0;
  }

  /**
   * Prompts the user to enter a stock ticker symbol.
   *
   * @return the entered stock ticker symbol
   */
  @Override
  public String getTickerSymbol() {
    return promptInput("Enter stock ticker symbol:",
            "[A-Za-z]+");
  }

  /**
   * Prompts the user to enter a date in the format YYYY-MM-DD.
   *
   * @return the entered date as a LocalDate object, or null if the input is invalid
   */
  @Override
  public LocalDate getDate() {
    JPanel inputPanel = new JPanel(new FlowLayout());
    inputPanel.add(new JLabel("Year:"));
    inputPanel.add(yearField);
    inputPanel.add(new JLabel("Month:"));
    inputPanel.add(monthField);
    inputPanel.add(new JLabel("Day:"));
    inputPanel.add(dayField);

    int result = JOptionPane.showConfirmDialog(this, inputPanel
            , "Enter Date (YYYY-MM-DD)", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      try {
        int year = Integer.parseInt(yearField.getText());
        int month = Integer.parseInt(monthField.getText());
        int day = Integer.parseInt(dayField.getText());
        return LocalDate.of(year, month, day);
      } catch (NumberFormatException | DateTimeException e) {
        displayMessage("Invalid date input. Please try again.", Color.RED);
      }
    }
    return null;
  }

  /**
   * Prompts the user to enter an X value.
   *
   * @return the entered X value
   */
  @Override
  public int getXValue() {
    return Integer.parseInt(promptInput("Enter X value:",
            "\\d+"));
  }

  /**
   * Prompts the user to enter the number of shares.
   *
   * @return the entered number of shares
   */
  @Override
  public int getNumberOfShares() {
    return Integer.parseInt(promptInput("Enter number of shares:", "\\d+"));
  }

  /**
   * Asks the user if they want to add more stocks.
   *
   * @return true if the user wants to add more stocks, false otherwise
   */
  @Override
  public boolean askToAddMoreStocks() {
    int result = JOptionPane.showConfirmDialog(this, "Add more stocks?"
            , "Confirmation", JOptionPane.YES_NO_OPTION);
    return result == JOptionPane.YES_OPTION;
  }

  /**
   * Prompts the user to enter a start date.
   *
   * @return the entered start date as a LocalDate object
   */
  @Override
  public LocalDate getStartDate() {
    return getDate();
  }

  /**
   * Prompts the user to enter an end date.
   *
   * @return the entered end date as a LocalDate object
   */
  @Override
  public LocalDate getEndDate() {
    return getDate();
  }

  /**
   * Prompts the user to select a portfolio number within the specified range.
   *
   * @param maxChoice the maximum portfolio number that can be selected
   * @return the selected portfolio number
   */
  @Override
  public int getPortfolioChoice(int maxChoice) {
    return Integer.parseInt(promptInput("Enter portfolio number (1-" + maxChoice
            + "):", "\\d+"));
  }

  /**
   * Displays the composition of the selected portfolio.
   *
   * @param composition a map containing the stock symbols and their respective quantities
   *                    in the portfolio
   */
  @Override
  public void displayPortfolioComposition(Map<String, Double> composition) {
    displayMessage("Portfolio Composition:", Color.BLACK);
    composition.forEach((stock, quantity) -> displayMessage(stock + ": " + quantity
            + " shares", Color.BLACK));
  }

  /**
   * Displays the value of the selected portfolio.
   *
   * @param value the value of the portfolio
   */
  @Override
  public void displayPortfolioValue(double value) {
    displayMessage("Portfolio Value: $" + value, Color.BLACK);
  }

  /**
   * Displays the value distribution of the selected portfolio.
   *
   * @param distribution a map containing the stock symbols and their respective value percentages
   *                     in the portfolio
   */
  @Override
  public void displayPortfolioValueDistribution(Map<String, Double> distribution) {
    displayMessage("Portfolio Value Distribution:", Color.BLACK);
    distribution.forEach((stock, value) -> displayMessage(stock + ": " + value
            + " %", Color.BLACK));
  }

  /**
   * Displays the performance of the selected portfolio over the specified date range.
   *
   * @param performance a list of portfolio values over the specified date range
   * @param portfolioName the name of the portfolio
   * @param startDate the start date of the performance period
   * @param endDate the end date of the performance period
   */
  @Override
  public void displayPortfolioPerformance(List<Double> performance, String portfolioName,
                                          LocalDate startDate, LocalDate endDate) {
    displayMessage("Performance of portfolio " + portfolioName + " from " + startDate
            + " to " + endDate + ":", Color.BLACK);

    double maxValue = performance.stream().max(Double::compareTo).orElse(1.0);
    int maxAsterisks = 50;
    double scale = maxValue / maxAsterisks;
    if (scale == 0) {
      scale = 1;
    }

    LocalDate currentDate = startDate;
    int index = 0;

    while (!currentDate.isAfter(endDate) && index < performance.size()) {
      double value = performance.get(index);
      int numAsterisks = (int) (value / scale);
      displayMessage(currentDate + ": " + "*".repeat(numAsterisks), Color.BLACK);
      currentDate = currentDate.plusMonths(1);
      index++;
    }

    displayMessage("Scale: * = $" + scale, Color.BLACK);
  }

  /**
   * Prompts the user to enter a portfolio name.
   *
   * @return the entered portfolio name
   */
  @Override
  public String getPortfolioName() {
    return promptInput("Enter portfolio name:",
            "[A-Za-z0-9_]+");
  }

  /**
   * Prompts the user to enter a file path for saving or loading a portfolio.
   *
   * @return the entered file path
   * @throws IllegalArgumentException if the file path does not end with ".csv"
   */
  @Override
  public String getFilePath() {
    String filePath = promptInput("Enter file name (with format, e.g., portfolio.csv):");
    if (!filePath.endsWith(".csv")) {
      throw new IllegalArgumentException("Invalid file format. Please enter a .csv file.");
    }
    return filePath;
  }

  /**
   * Prompts the user to enter target weights for the specified stocks.
   *
   * @param tickers a list of stock symbols
   * @return a map containing the stock symbols and their respective target weights
   */
  @Override
  public Map<String, Double> getTargetWeights(List<String> tickers) {
    Map<String, Double> weights = new HashMap<>();
    for (String ticker : tickers) {
      double weight = Double.parseDouble(promptInput("Enter target weight for stock "
              + ticker + " (as a percentage):", "\\d+")) / 100.0;
      weights.put(ticker, weight);
    }
    return weights;
  }

  /**
   * Prompts the user for input with a given message and optional pattern for validation.
   *
   * @param message the message to display in the input dialog
   * @return the user's input
   */
  private String promptInput(String message) {
    return promptInput(message, null);
  }

  /**
   * Prompts the user for input with a given message and optional pattern for validation.
   *
   * @param message the message to display in the input dialog
   * @param pattern the pattern to validate the input, or null for no validation
   * @return the user's input
   */
  private String promptInput(String message, String pattern) {
    while (true) {
      String input = JOptionPane.showInputDialog(this, message);
      if (input == null || input.trim().isEmpty()) {
        throw new IllegalArgumentException("Input cancelled or empty");
      }
      if (pattern == null || input.matches(pattern)) {
        return input;
      }
      displayMessage("Invalid input. Please try again.", Color.RED);
    }
  }

  /**
   * Updates the portfolio menu in the GUI with the given list of portfolio names.
   *
   * @param portfolios the list of portfolio names to be displayed in the menu
   */
  @Override
  public void updatePortfolioMenu(List<String> portfolios) {
    portfolioMenu.removeAllItems();
    for (String portfolio : portfolios) {
      portfolioMenu.addItem(portfolio);
    }
  }

  /**
   * Returns the name of the selected portfolio from the portfolio menu.
   *
   * @return the name of the selected portfolio
   */
  @Override
  public String getSelectedPortfolio() {
    return (String) portfolioMenu.getSelectedItem();
  }
}
