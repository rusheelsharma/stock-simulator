package view;

import javax.swing.JButton;
import java.awt.Color;
import java.util.List;

/**
 * The GuiView interface extends the BetterView interface to provide additional
 * methods specific to GUI's for managing stock portfolios. This interface defines methods for
 * interacting with GUI components and updating the view based on user inputs and model changes.
 */
public interface GuiView extends BetterView {

  /**
   * Returns the button for creating a new portfolio.
   *
   * @return the create portfolio button
   */
  JButton getCreatePortfolioButton();

  /**
   * Returns the button for buying or selling stocks.
   *
   * @return the buy/sell stock button
   */
  JButton getBuySellStockButton();

  /**
   * Returns the button for querying the value of a portfolio.
   *
   * @return the query portfolio button
   */
  JButton getQueryPortfolioButton();

  /**
   * Returns the button for saving a portfolio to a local file.
   *
   * @return the save portfolio button
   */
  JButton getSavePortfolioButton();

  /**
   * Returns the button for loading a portfolio from a local file.
   *
   * @return the load portfolio button
   */
  JButton getLoadPortfolioButton();

  /**
   * Sets the visibility of the GUI.
   *
   * @param visible true to make the GUI visible, false to hide it
   */
  void setVisible(boolean visible);

  /**
   * Displays a message to the user in the GUI.
   *
   * @param message the message to be displayed
   * @param color the color of the message text
   */
  void displayMessage(String message, Color color);

  /**
   * Updates the portfolio menu in the GUI with the given list of portfolio names.
   *
   * @param portfolioNames the list of portfolio names to be displayed in the menu
   */
  void updatePortfolioMenu(List<String> portfolioNames);

  /**
   * Returns the name of the selected portfolio from the portfolio menu.
   *
   * @return the name of the selected portfolio
   */
  String getSelectedPortfolio();
}
