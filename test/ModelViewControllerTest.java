import org.junit.Test;
import model.Portfolio;


import static org.junit.Assert.assertEquals;


/**
 * Concrete JUnit tests for the BetterModel, BetterView, and BetterController classes.
 */
public class ModelViewControllerTest extends AbstractModelViewControllerTest {


  /**
   * Test case to verify adding and getting a portfolio.
   * This method checks if a portfolio can be added to the model and retrieved correctly.
   */
  @Test
  public void testAddPortfolio() {
    Portfolio portfolio = new Portfolio("MyPortfolio");
    portfolio.addStock("AAPL", 10, stock);
    model.addPortfolio(portfolio);
    assertEquals(1, model.getPortfolios().size());
    assertEquals(portfolio, model.getPortfolios().get(0));
  }
}
