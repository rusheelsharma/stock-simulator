package api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;


import model.Stock;

/**
 * The AlphaVantageAPI class provides methods to fetch stock data from the Alpha Vantage API.
 */
public class AlphaVantageAPI {
  private static final String API_KEY = "ZBD3QIPITMQNSPPF";
  private static final String BASE_URL
          = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&outputsize=full&apikey=";

  /**
   * Gets the stock data for the given ticker symbol from the Alpha Vantage API.
   *
   * @param tickerSymbol The stock ticker symbol to fetch data for.
   * @return A Stock object containing the fetched stock data.
   * @throws IOException If an input/output error occurs
   */
  public static Stock getStockData(String tickerSymbol) throws IOException {
    String requestUrl = BASE_URL + API_KEY + "&symbol=" + tickerSymbol + "&datatype=csv";
    URL url = new URL(requestUrl);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");

    if (connection.getResponseCode() != 200) {
      throw new IOException("Failed to fetch data: HTTP error code "
              + connection.getResponseCode());
    }

    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    String inputLine;
    Stock stock = new Stock(tickerSymbol);

    // Skip the header line
    in.readLine();

    while ((inputLine = in.readLine()) != null) {
      String[] data = inputLine.split(",");

      if (data.length < 5) {
        continue;
      }
      LocalDate date = LocalDate.parse(data[0]);
      double closePrice = Double.parseDouble(data[4]);
      stock.addPrice(date, closePrice);
    }

    in.close();
    connection.disconnect();
    return stock;
  }

}
