# CS3500 Reversi 

### OVERVIEW
This is a codebase for playing a game of Reversi (or Othello) on a hexagonal board rather than the traditional square. The code is designed to support both human vs human and human vs computer play. 

It is built to be playable both in textual form in the terminal and through clicking via a GUI. The code is designed to be modular and extendable for custom functionalities and rules alterations, and provides options such as variable board size for a more interesting experience, as well as ease of testing and implementation. 

The game should be playable by someone with minimal technical experience, and provide a fun and challenging diversion for anyone who likes a little strategy!

### QUICK START
- Give a short snippet of code (rather like an Examplar example) showing how a user might get started using this codebase

### KEY COMPONENTS
**Model** is where the majority of the current code is held, and contains the implementation and documentation of the model. The model hold the logic for running a game of Reversi - making the board, players taking turns, and performing operations on the board such as flipping tiles. 

**View** contains the code for a textual representation of Reversi. Given any game state, it is able to produce a rudimentary image of the board. 

### KEY SUBCOMPONENTS
Within the model, the most important subcomponents are the board and Tile, Coordinate, and PlayerColor classes.

The PlayerColor enum is the basis for how much of the game is run. It contains two values, BLACK and WHITE, and is used to track turn-taking using its ordinal index values, determines the contents of tiles, and on a high level represents the players involved in the game.

Coordinates are positional representations of tiles on a hexagonal board, using the axial coordinate method. They contain a q and r value, which together can be used to conceptually represent the third value s required to have an effective hexagonal mapping system.

Tiles are the spaces on the board, which can be empty or hold discs in them. There is no actual disc representation - instead, the tile tracks the state of the disc within it in order to reduce code complication. These tiles also each hold an axial coordinate to track their position on the board.

The board is a 2D array of Tiles, with null placeholder values in the top left and bottom right corners to create a hexagonal structure. The Tiles' r value is the row it occupies in the array, and the q value is its position in its respective inner array. This system of holding coordinate values of tiles allows for easy and efficient access to the board's fields, simplifying the operations required to run a game of Reversi.

### SOURCE ORGANIZATION
The code is held in the cs3500.reversi folder, which is split into src (source code of the model) and test (a test directory for validating classes and methods). Src has three parts: controller, model, and view. 

Controller is as of now empty, but will be implemented in the coming weeks. 

Model contains the PlayerActions and ReversiModel interface, both of which dictate the methods in the BasicReversi class. 
Additionally, interfaces and implementations of Tile, Coordinate, and PlayerColor are held in this directory. 

View contains the TextualView interface and ReversiTextualView, the implementation of the view. 

The test directory contains separate test classes for each class in the src folder.