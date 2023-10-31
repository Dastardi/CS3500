# CS3500 Reversi 

### OVERVIEW
This is a codebase for playing a game of Reversi (or Othello) on a hexagonal board rather than the traditional square. 
The code is designed to support both human vs human and human vs computer play. 

It is built to be playable both in textual form in the terminal and through clicking via a GUI. 
The code is designed to be modular and extendable for custom functionalities and rules alterations, and provides options such as variable board size for a more interesting experience as well as ease of testing and implementation. 

The game should be playable by someone with minimal technical experience, and provide a fun and challenging diversion for anyone who likes a little strategy!

### QUICK START 
The codebase currently contains only a model and a view and is therefore not accessible by the user. 
There is no code that acts as an access point for a user to interact with the model other than internal testing.

### KEY COMPONENTS
**Model** is where the majority of the current code is held, and contains the implementation and documentation of the model. 
The model holds the logic for running a game of Reversi - making the board, players taking turns, and performing operations on the board such as flipping tiles. 
It's organized into two interfaces - PlayerActions and ReversiModel -  which are both implemented by a model class, BasicReversi.
PlayerActions is extended by ReversiModel in order to ensure that a ReversiModel always has access to the moves required to play the game.

**View** contains the code for a textual representation of Reversi. 
Given any game state, it is able to produce a rudimentary image of the board as a string OR as an appendable. 

### KEY SUBCOMPONENTS
Within the model, the most important subcomponents are the board and Tile, Coordinate, and PlayerColor classes.

The PlayerColor enum represents the players involved in the game and is the basis for how much of the game is run.
It contains two values, BLACK and WHITE, and is used to track turn-taking using its ordinal index values and determines the contents of tiles.

Coordinates are positional representations of Tiles on a hexagonal board, using the axial coordinate method. 
They contain a q and r value, which together can be used to conceptually represent the third value s required to have an effective hexagonal mapping system.

Tiles are the spaces on the board, which can be empty or hold discs in them. 
There is no actual disc object - instead, the Tile tracks the state of the disc within it in order to reduce code complication. 
These Tiles also each hold an axial coordinate to track their position on the board.

Tiles all share some public methods which are used by the model to manipulate the board.<br>
The placeDisc(PlayerColor) method changes the contents of the Tile to reflect a Tile being put down.<br>
The Flip() method changes the contents of the Tile from white to black, and vice versa.<br>
The isEmpty() method returns true if the Tile has no disc in it, and false if it has a disc. <br>
The getContents() method returns the color of the disc currently held in the Tile.<br>
Finally, the getCoordinate() method returns the Coordinate location of the Tile.<br>

The board is a 2D array of Tiles, with null placeholder values in the top left and bottom right corners to create a hexagonal structure. 
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
Src has three parts: controller, model, and view. 

Controller is as of now empty, but will be implemented in the coming weeks. 

Model contains the PlayerActions and ReversiModel interface, both of which dictate the methods in the BasicReversi class. 
Additionally, interfaces and implementations of Tile, Coordinate, and PlayerColor are held in this directory. 

View contains the TextualView interface and ReversiTextualView, the implementation of the view. 

The test directory contains separate test classes for each class in the src folder.