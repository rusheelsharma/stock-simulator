SETUP-README.txt

Setup Instructions

Steps to Run the Program

1. Download the Project and note the directory you store it in.

2. Open a terminal or command prompt.

3. Navigate to the directory containing the JAR file:
 
    cd /path/to/directory/that/contains/jar

    The path to the file would be `Stocks/out/artifacts/Stocks_jar/Stocks.jar`.

    So when you download the project, the path will be:

    Stocks > out > artifacts > Stocks_jar > Stocks.jar

    This is the file you will run in the next step.

4. Run the JAR file using one of the following commands:

    - To open the text-based interface:

      java -jar Stocks.jar -text

    - To open the graphical user interface (GUI):

      java -jar Stocks.jar

    - If you simply double-click the JAR file, it will also open the graphical user interface.

Detailed Instructions to Run the Program

1. **Start the application by running the JAR file as described above.**

2. **Create the first portfolio with 3 different stocks:**
    - Choose option `4` from the main menu to create a portfolio.
    - Enter the portfolio name, e.g., `Portfolio1`.
    - Enter the ticker symbol for the first stock, e.g., `GOOG`.
    - Enter the number of shares, e.g., `15`.
    - After being prompted to add more stocks, enter `yes`.
    - Enter the ticker symbol for the second stock, e.g., `AAPL`.
    - Enter the number of shares, e.g., `10`.
    - When asked if you want to add more stocks, enter `yes`.
    - Enter the ticker symbol for the third stock, e.g., `TSLA`.
    - Enter the number of shares, e.g., `5`.
    - When asked if you want to add more stocks, enter `no`.

3. **Create the second portfolio with stocks:**
    - Choose option `4` from the main menu to create a portfolio.
    - Enter the portfolio name, e.g., `Portfolio2`.
    - Enter the ticker symbol for the first stock, e.g., `NFLX`.
    - Enter the number of shares, e.g., `8`.
    - When asked if you want to add more stocks, enter `yes`.
    - Enter the ticker symbol for the second stock, e.g., `GOOG`.
    - Enter the number of shares, e.g., `12`.
    - When asked if you want to add more stocks, enter `no`.

4. **Query the value of the first portfolio on a specific date:**
    - Choose option `8` from the main menu to query portfolio value.
    - Enter the portfolio name, e.g., `Portfolio1`.
    - Enter the date you want to query in the correct format, e.g., `2023-06-01`.
    - The application will display the value of the portfolio on the specified date.

5. **Query the cost basis of the first portfolio on a specific date:**
    - Choose option `9` from the main menu to query portfolio cost basis.
    - Enter the portfolio name, e.g., `Portfolio1`.
    - Enter the date you want to query in the correct format, e.g., `2023-06-01`.
    - The application will display the cost basis of the portfolio on the specified date.

6. **Exit the application:**
    - Choose option `14` from the main menu to exit.

Note:
- The application will need a connection to fetch data from the API.
- The program supports stocks such as `NFLX`, `AAPL`, `GOOG`, `TSLA`.

If you follow the steps above, the program will run as intended.
