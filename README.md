# CS3500 Reversi

### OVERVIEW
This is a codebase for playing a game of Reversi (or Othello) on a square board.
The code is designed to support both human vs human and human vs computer play.

It is built to be playable both in textual form in the terminal and through clicking via a GUI.
The code is designed to be modular and extendable for custom functionalities and rules alterations, and provides options such as variable board size for a more interesting experience as well as ease of testing and implementation.

The game should be playable by someone with minimal technical experience, and provide a fun and challenging diversion for anyone who likes a little strategy!

### QUICK START
To display and interact with the board, navigate to the Reversi file and run the main() method.
This will create a new window in which a reversi board will be displayed.
You can use your mouse to select and deselect tiles, hit ENTER to place a disc, press SPACE to pass your turn, press H to turn hints on and off, and press Q to quit.

### KEY COMPONENTS
**Model** is where the majority of the current code is held, and contains the implementation and documentation of the model.
The model holds the logic for running a game of Reversi - making the board, players taking turns, and performing operations on the board such as flipping tiles.
It's organized into two interfaces - PlayerActions and ReversiModel -  which are both implemented by a model class, BasicReversi.
PlayerActions is extended by ReversiModel in order to ensure that a ReversiModel always has access to the moves required to play the game.

**View** contains the gui and text packages, which represent a GUI visual representation and a textual representation of a game of Reversi.

GUI View
- The GUI is the primary way for a player to interact with a game of Reversi via Java Swing.
- It consists of a Panel within a Frame, which the player can click and interact with. Tiles are arranged according to a specified board size and tile radius, and discs can be placed within tiles.
- To interact with the board, players use their mouse to select and deselect tiles and their keyboard to move in selected tiles, pass, and quit the game.

Textual View
- Given any game state, it is able to produce a rudimentary image of the board as a string OR as an appendable.
- Given the existence of a working GUI this is pretty much depreciated, but can still be interesting to get a sense of how the board looks in a simple format.

### KEY SUBCOMPONENTS
Within the model, the most important subcomponents are the board and Tile, Coordinate, and PlayerColor classes.

The PlayerColor enum represents the players involved in the game and is the basis for how much of the game is run.
It contains two values, BLACK and WHITE, and is used to track turn-taking using its ordinal index values and determines the contents of tiles.

Coordinates are positional representations of Tiles on a square board.
They contain a q and r value, which together are used to map out coordinates on a square board.

Tiles are the spaces on the board, which can be empty or hold discs in them.
There is no actual disc object - instead, the Tile tracks the state of the disc within it in order to reduce code complication.
These Tiles also each hold a coordinate to track their position on the board.

Tiles all share some public methods which are used by the model to manipulate the board.<br>
The placeDisc(PlayerColor) method changes the contents of the Tile to reflect a Tile being put down.<br>
The Flip() method changes the contents of the Tile from white to black, and vice versa.<br>
The isEmpty() method returns true if the Tile has no disc in it, and false if it has a disc. <br>
The getContents() method returns the color of the disc currently held in the Tile.<br>
Finally, the getCoordinate() method returns the Coordinate location of the Tile.<br>

The board is a 2D array of Tiles.
The Tiles' r value is the row it occupies in the array, and the q value is its position in its respective inner array.
This system of holding coordinate values of Tiles allows for easy and efficient access to the board's fields, simplifying the operations required to run a game of Reversi.

The model accesses and changes the board with a number of public methods. <br>
From the PlayerActions interface, move(Coordinate) and pass() represent the player's options when playing the game. <br>
Move places a tile on the board and handles the consequences of that move (Tiles flipping), while pass simply skips the current player's turn.<br>
GetCurrentPlayer() returns the color of the current player. <br>
IsGameOver() returns a boolean based on the current state of the game - if it's ongoing, false, if it's over, true.<br>
GetCurrentWinner() returns the player with the highest score. <br>
GetPlayerScore(PlayerColor) returns the score (number of tiles on the board) of the given color.<br>
GetTileAt(Coordinate) returns the board space of the given coordinate. <br>
GetBoardSize is a getter for the private final boardSize variable which we use for a number of our calculations when looking at the Tile array.

### SOURCE ORGANIZATION
The code is held in the cs3500.reversi folder, which is split into src (source code of the model) and test (a test directory for validating classes and methods).
Src has four parts: controller, model, strategy, and view.

Controller currently contains only the ViewEventListener, which is not implemented,
but will be implemented when a controller is added in (presumably) the next assignment.

Model contains the PlayerActions and ReversiModel interface, both of which dictate the methods in the BasicReversi class.
Additionally, interfaces and implementations of Tile, Coordinate, and PlayerColor are held in this directory.

Strategy contains the implementation of our AI strategies, as well as the HumanPlayer class, which will pass information from the view to the controller.

View contains two packages: gui and text.
<br>GUI contains the ViewPanel, ViewableReversiTile, and PanelEventListener interface.
There are some supporting classes with no interfaces, such as Circle2D, which is used to draw shapes in the panel, and then implementations of the aforementioned interfaces: ViewableTile, ReversiFrame, and ReversiPanel.
<br>Text contains the TextualView interface and ReversiTextualView, the implementation of the view.

The test directory contains separate test classes for each class in the src folder.

## CHANGES FOR PART 2:
Task: explain what functionality was missing, and why, and how you chose to add it.

BasicReversi
- Added the isMoveLegal and currentPlayerHasLegalMoves methods to allow the view and controller to be able to externally check move legality.
  Initially we were worried about using public methods in other public methods, and didn't want to duplicate code, so we didn't make public legality checks - but the view needed them, so here we are.
  The currentPlayerHasLegalMoves method is as of right now unused, but as it may be useful down the line we're keeping it implemented but inactive.
- Added an isEmpty check to getValidRows for edge cases - a testing case for the view made us realized that we didn't check that the contents of a tile were null when finding valid rows, only if the tile itself was null. We have since remedied that.
- Added board copying functionality and a third constructor that takes in a board. Right now we're not using it for anything, but there are a number of cases in which it may prove useful to have.
- Added the getMoveScore method to easily check the score of a potential move, allowing for far easier implementation of our strategies and opening up pathways to interesting enhancements later down the line.

ReversiModel
- We renamed our two interfaces and changed which one implemented the other.
  Initially we had the read-only model implement the move interface, because we felt that the getters were "closer" to the model.
  Now, though, since we want to be able to make a read-only and an active model, we switched the order.
- Improved documentation.

Coordinate
- Added the toString method for testing purposes.
- Added getters for q and r, which are now private fields. They were public before for ease of checking, and since they were final we assumed it was fine to have them be public, but in all honestly it was just an oversight.
- Explained coordinate system in more detail in the javadoc.

Other
- Deleted packagePrivateTests class, as we were not using it.
- Added ReadOnlyReversiModel and changed textual view to take a read-only model instead of a normal ReversiModel.
- Deleted PlayerActions interface, because it was replaced by ReversiModel once ReversiModel was replaced by ReadOnlyReversiModel.

ReversiTile
- Improved documentation.

Tile
- Improved documentation.

### EXTRA CREDIT:
- We implemented two of the three extra credit strategies, AvoidCornerAdjacentStrategy and CornerCaptureStrategy. You can check them out in the strategy package and see the tests showing their difference from our base maximum capture strategy in test.StrategyTests.

### GUI DESIGN CHOICES
<b>How the board is created:</b>
We use similar logic to our system for hexagonal reversi, but simplified due to the lack of null values in the corners of the array.

<b>How we interact with the board as users:</b>
- Clicking a tile selects it. When a tile is selected, you can deselect it by clicking it again, clicking another tile (which then selects that tile instead), or clicking outside of the board on the background.
- When you have a tile selected, pressing RETURN places a tile there. At the moment only black tiles can be placed using this functionality, but a controller will be able to pass in colors to ensure the discs are the correct color.
- At any time, pressing SPACE passes the turn.
- At any time, pressing Q quits the game.
- Additionally, for testing purposes (these will be removed once we have rules implementations), W and B place white and black tiles respectively in order to easily visually set up interesting board scenarios.

<b>Other:</b>
- We hard code a specific radius value right now, but the goal is that a user will be able to pass in a desired radius size through command line arguments in order to customize the size of the board.
  This means that the board size will be highly customizable, and makes our calculations easier!

## CHANGES FOR PART 3

