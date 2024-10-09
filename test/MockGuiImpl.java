import view.GuiView;

import javax.swing.JButton;
import java.awt.Color;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The TestGuiViewImpl class implements the GuiView interface and provides
 * a way to test the GUI functionalities using input streams. It is used as a Mock
 */
public class MockGuiImpl implements GuiView {
  private final JButton portfolioButton = new JButton("Create New Portfolio");
  private final JButton buySellButton = new JButton("Buy/Sell Stocks");
  private final JButton queryPortButton = new JButton("Query Portfolio");
  private final JButton savePortButton = new JButton("Save Portfolio");
  private final JButton loadPortButton = new JButton("Load Portfolio");

  private final Scanner input;
  private final StringBuilder output;

  private String selectedPortfolio;

  /**
   * Constructs a TestGuiViewImpl with the specified input and output streams.
   *
   * @param input the input stream to use for user input
   * @param output the output stream to use for displaying messages
   */
  public MockGuiImpl(Scanner input, StringBuilder output) {
    this.input = input;
    this.output = output;
  }

  /**
   * Displays a message to the output stream.
   *
   * @param message the message to display
   */
  @Override
  public void displayMessage(String message) {
    output.append(message).append("\n");
  }

  /**
   * Displays a message to the output stream with a specified color.
   *
   * @param message the message to display
   * @param color the color of the message (not used in this implementation)
   */
  @Override
  public void displayMessage(String message, Color color) {
    output.append(message).append("\n");
  }

  /**
   * Returns the button for creating a new portfolio.
   *
   * @return the portfolio creation button
   */
  @Override
  public JButton getCreatePortfolioButton() {
    return portfolioButton;
  }

  /**
   * Returns the button for buying or selling stocks.
   *
   * @return the buy/sell stock button
   */
  @Override
  public JButton getBuySellStockButton() {
    return buySellButton;
  }

  /**
   * Returns the button for querying a portfolio.
   *
   * @return the query portfolio button
   */
  @Override
  public JButton getQueryPortfolioButton() {
    return queryPortButton;
  }

  /**
   * Returns the button for saving a portfolio.
   *
   * @return the save portfolio button
   */
  @Override
  public JButton getSavePortfolioButton() {
    return savePortButton;
  }

  /**
   * Returns the button for loading a portfolio.
   *
   * @return the load portfolio button
   */
  @Override
  public JButton getLoadPortfolioButton() {
    return loadPortButton;
  }

  /**
   * Sets the visibility of the view.
   *
   * @param visible the visibility state
   */
  @Override
  public void setVisible(boolean visible) {
    // No implementation needed for testing
  }

  /**
   * Prompts the user to choose an option from the main menu.
   *
   * @return the chosen option (always returns 0 for testing purposes)
   */
  @Override
  public int mainMenuOptionChooser() {
    return 0;
  }

  /**
   * Prompts the user to enter a ticker symbol.
   *
   * @return the entered ticker symbol
   */
  @Override
  public String getTickerSymbol() {
    return input.nextLine();
  }

  /**
   * Prompts the user to enter a date.
   *
   * @return the entered date
   */
  @Override
  public LocalDate getDate() {
    int year = input.nextInt();
    int month = input.nextInt();
    int day = input.nextInt();
    input.nextLine();
    return LocalDate.of(year, month, day);
  }

  /**
   * Prompts the user to enter an X value.
   *
   * @return the entered X value
   */
  @Override
  public int getXValue() {
    return input.nextInt();
  }

  /**
   * Prompts the user to enter the number of shares.
   *
   * @return the entered number of shares
   */
  @Override
  public int getNumberOfShares() {
    return input.nextInt();
  }

  /**
   * Asks the user if they want to add more stocks.
   *
   * @return always returns false for testing purposes
   */
  @Override
  public boolean askToAddMoreStocks() {
    return false;
  }

  /**
   * Prompts the user to enter a start date.
   *
   * @return the entered start date
   */
  @Override
  public LocalDate getStartDate() {
    return getDate();
  }

  /**
   * Prompts the user to enter an end date.
   *
   * @return the entered end date
   */
  @Override
  public LocalDate getEndDate() {
    return getDate();
  }

  /**
   * Prompts the user to choose a portfolio.
   *
   * @param maxChoice the maximum valid choice
   * @return the chosen portfolio index
   */
  @Override
  public int getPortfolioChoice(int maxChoice) {
    return input.nextInt();
  }

  /**
   * Displays the composition of a portfolio.
   *
   * @param composition the composition of the portfolio
   */
  @Override
  public void displayPortfolioComposition(Map<String, Double> composition) {
    // No implementation needed for testing
  }

  /**
   * Displays the value of a portfolio.
   *
   * @param value the value of the portfolio
   */
  @Override
  public void displayPortfolioValue(double value) {
    // No implementation needed for testing
  }

  /**
   * Displays the value distribution of a portfolio.
   *
   * @param distribution the value distribution of the portfolio
   */
  @Override
  public void displayPortfolioValueDistribution(Map<String, Double> distribution) {
    // No implementation needed for testing
  }

  /**
   * Displays the performance of a portfolio.
   *
   * @param performance the performance values
   * @param portfolioName the name of the portfolio
   * @param startDate the start date of the performance period
   * @param endDate the end date of the performance period
   */
  @Override
  public void displayPortfolioPerformance(List<Double> performance, String portfolioName,
                                          LocalDate startDate, LocalDate endDate) {
    // No implementation needed for testing
  }

  /**
   * Prompts the user to enter a portfolio name.
   *
   * @return the entered portfolio name
   */
  @Override
  public String getPortfolioName() {
    return input.nextLine();
  }

  /**
   * Prompts the user to enter a file path.
   *
   * @return the entered file path
   */
  @Override
  public String getFilePath() {
    return input.nextLine();
  }

  /**
   * Prompts the user to enter target weights for rebalancing a portfolio.
   *
   * @param tickers the list of ticker symbols
   * @return the target weights (always returns null for testing purposes)
   */
  @Override
  public Map<String, Double> getTargetWeights(List<String> tickers) {
    return null;
  }

  /**
   * Updates the portfolio menu with the given list of portfolios.
   *
   * @param portfolios the list of portfolio names
   */
  @Override
  public void updatePortfolioMenu(List<String> portfolios) {
    List<String> portfolioList = portfolios;
  }

  /**
   * Returns the currently selected portfolio.
   *
   * @return the selected portfolio
   */
  @Override
  public String getSelectedPortfolio() {
    return selectedPortfolio;
  }

}
