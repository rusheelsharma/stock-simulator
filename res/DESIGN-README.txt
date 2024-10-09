Stock Market Application Design Overview

------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------
Architecture:

Model-View-Controller (MVC) Pattern
The application is designed using the MVC design architecture, which divides the application into three components:

------------------------------------------------------------------------------------------------

1.) Model: Handles data operations, such as retrieving stock data from the API and managing portfolio information.

model.Model.java: Interface defining the methods for handling data.

model.ModelImpl.java: Implements the model.Model interface. Handles data retrieval, processing, and storage.

model.Stock.java: Represents a stock with attributes such as symbol, price, and historical data.

model.Portfolio.java: Manages a collection of stocks that are in a user's portfolio.

------------------------------------------------------------------------------------------------

2.) View: The user interacts with the application through the view.

view.View.java: Interface defining the methods for user interaction.

view.ViewImpl.java: Implements the view.View interface. Provides methods to display stock data, portfolio information, and take user input.

------------------------------------------------------------------------------------------------

3.) Controller: The controller processes the inputs and updates the model.

controller.Controller.java: Interface defining the methods for controlling the application.

controller.ControllerImpl.java: Implements the controller.Controller interface. Handles user commands, updates the model, and constantly updates the view.

------------------------------------------------------------------------------------------------

4.) Additional Components:

api.AlphaVantageAPI.java: Handles interaction with the Alpha Vantage API for retrieving stock data.

Main.java: Starting point of the application. Initializes the model, view, and controller.

------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------

CHANGES MADE TO THE DESIGN -> Design Patterns Used to make a better implementation following SOLID Principles:

Adapter Design Pattern:

Where Used: In the BetterControllerImpl constructor.

How Used: It adapts the generic Model and View interfaces to the more specific BetterModel and BetterView implementations, enabling extended functionality while maintaining compatibility with the original interfaces.

Command Design Pattern:

Where Used: In the execute method of BetterControllerImpl.

How Used: Each case in the switch statement represents a command that executes a specific action based on user input, decoupling method invocation from command execution and allowing for easier maintenance and extensibility.

Other changes made:

We worked on all the errors from our previous assignments:

- Added exception handling everywhere.
- Added descriptive names for tests.
- Added dollar signs for currency representation.
- Date is easier to get from the user: asks for date details one by one.
- Model can now take in a fractional number of shares.

Our program also enforces the user to save/load files in a .csv format.

------------------------------------------------------------------------------------------------

Better Classes and Interfaces:

1.) BetterModel.java: Extends the functionality of the Model interface by adding more specific methods for handling advanced portfolio operations such as rebalancing and querying detailed portfolio information.

2.) BetterModelImpl.java: Implements the BetterModel interface. Provides enhanced methods for creating, modifying, querying, saving, loading, and rebalancing portfolios.

3.) BetterView.java: Extends the View interface by adding methods for displaying more detailed and specific portfolio information.

4.) BetterViewImpl.java: Implements the BetterView interface. Offers enhanced interaction capabilities, making it easier for users to input complex data and view detailed portfolio information.

5.) BetterController.java: Extends the Controller interface by defining additional methods for managing advanced portfolio operations.

6.) BetterControllerImpl.java: Implements the BetterController interface. Manages user inputs and commands for advanced portfolio operations, leveraging the extended functionalities of BetterModel and BetterView.


------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------

CHANGES MADE TO THE DESIGN:

Inclusion of Graphical User Interface (GUI) Using Java Swing:
- Added a GUI layer using Java Swing to coexist with the text-based view.
- The GUI improves user interaction, making the application more user-friendly and visually appealing, while adhering to the MVC architecture and SOLID principles.

Gui Classes and Interfaces:

1.) GuiController.java: Interface that extends the BetterController to support GUI-specific functionality.

2.) GuiControllerImpl.java: Implements the GuiController interface and provides the functionality for managing stock portfolios through a graphical user interface.

3.) GuiView.java: Interface extends the BetterView interface to provide additional methods specific to graphical user interfaces (GUIs) for managing stock portfolios.

4.) GuiViewImpl.java: Implements the GuiView interface and provides a graphical user interface for interacting with the application.

------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------
