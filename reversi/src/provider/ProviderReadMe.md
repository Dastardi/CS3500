# Designing Reversi with Hexagonal Boards #
The Hex Reversi is designed to model the game of Reversi, a strategic board game involving two
players, on a hexagonal board. Read more about the [rules of the game](https://www.ludoteka.com/clasika/reversi-en.html). The project encompasses the design and
implementation of the Hex game board model, the development of a textual rendering for
visualization, and the design of a player interface to allow both human and AI players to interact
with the game.

## Quick Start ##
To get started interacting with the codebase, here are some the basic interactions the model
provides:

Begin by creating a model and starting the game, passing in the desired number of hex cell layers
for the game board.
```
HexagonalReversiModel model = new HexagonalReversiModel();
model.startGame(3);
```
Then, after understanding the cubic coordinate system for hexagonal boards (see below),
try experimenting with some of the allowed methods implemented by the model in an acceptable order.
I.e., White should move first, then Black, then White, and so on.
```
// Black's turn: here are some example moves
// model.placePiece(2, -1, -1, HexState.BLACK);
// model.pass(HexState.BLACK);

// White's turn: here are some example moves
// model.placePiece(2, -1, -1, HexState.WHITE);
// model.pass(HexState.WHITE);
```
We encourage the user to experiment with the above code in
```reversi/src/cs3500/reversi/ExamplesModel.java```
where the same above code can be found.


## Understanding the MVC Architecture ##

### The Model ###
The model, represented as a ```HexagonalReversi```, is used to represent the current state of
the game. The model's ```BoardState``` field is the key component that tracks the majority of the
game's current state. In brief, the board state tracks how many layers of hex cells the game's
board contains and the location and state of each of the hex
cells, represented as ```ReversiHex```s, by indexing their cubic\* location with a 3D array.
Additionally, each hex cell stored in the array contains a ```HexState``` attribute which serves
as a universal representation for various hex cell states (WHITE, BLACK, and EMPTY). Each hex cell
also contains a ```HexPosn``` field that is used to validate the coordinates of the cell, infer
cell equality, and perform vector-like operations on cells.

The model offers the user a selections of methods that either mutate the state of the game in some
way or retrieve some piece of information from the game without mutating anything. There are three
mutator methods that are used to effectively play the game: ```startGame```, ```placePiece```, and
```pass```. Given a single integer greater than 3, ```startGame``` initializes the state of the
game and allows for further interactions. ```placePiece```, which take three integers representing
the coordinates of a hex cell and a hex state representing the color of the piece intended to be
places is used to place pieces onto the board. Lastly, ```pass```, which takes in a hex state is
used to indicate that the given color is passing their turn. The remaining methods of the model,
```validateMove```, ```isGameOver```, ```getNextTurn``` , ```getHexState```, ```getWhiteScore``` ,
```getBlackScore```, and ```getNumLayers``` are all used to observe the current state of the game.
Their respective documentations are found in ```src/cs3500/reversi/model/HexagonalReversi.java```.


##### * Cubic Coordinate System for Representing a Hex Map #####
See [this description](https://www.redblobgames.com/grids/hexagons/#coordinates) for how to
represent coordinates for a hexagonal grid. Specifically, read and understand the Cubic Coordinates
section, as this is the coordinate system our project employs.

### The View ###

The view, represented as a ```TextualView```, is one of many ways to visualize an ongoing game which
opts to represent it as a String. ```HexagonalReversiTextualView``` implements this for the
```HexagonalReversi``` model. It is passed in a model upon construction and has one, zero-argument
method, ```toString```, that returns the current state of an ongoing (already started) Reversi game
as a String.

### The Controller ###

Once completed, players will be the primary way to control a game of Reversi. The ```Player```
interface is a rudimentary representation for how players, either users or AI, will be able to
interact with a game. See ```src/reversi/controller/Player.java``` for specific documentation of
each player-allowed method.

Note that the controller package is for the most part empty as we have yet to implement a controller
for the game.


## Navigating Through the Codebase ##
This is a short table of contents that indicates what each component is, why it *is*, and where it
is:

##### Hexagonal Reversi Interface #####
*What*: A summary of publicly available methods for a given model and their documentations.<br>
*Why*: To inform codebase users about the model how to interact with it.<br>
*Where*: ```src/cs3500/reversi/model/```

##### Hexagonal Reversi Model #####
*What*: The implementation of the Hexagonal Reversi Interface.<br>
*Why*: To keep track of the game state, allow game intractability, and enforce the rules of the game.
<br>
*Where*: ```src/cs3500/reversi/model/```

##### Board State #####
*What*: A comprehensive representation of a Hex Reversi board.<br>
*Why*: To keep track of important game state information for the model.<br>
*Where*: ```src/cs3500/reversi/model/```

##### Reversi Hex #####
*What*: A representation of a single hex cell.<br>
*Why*: To keep track of the state of each individual cell throughout a Hex Reversi game.<br>
*Where*: ```src/cs3500/reversi/model/```

##### Hex Posn #####
*What*: A representation of a single hex cell's location.<br>
*Why*: To keep track of the location of a given hex cell.<br>
*Where*: ```src/cs3500/reversi/model/```

##### Hex State #####
*What*: A representation of the state of a hex cell, either white, black, or empty. <br>
*Why*: To allow for the differentiation between various cells.<br>
*Where*: ```src/cs3500/reversi/model/```

##### Textual View Interface #####
*What*: A summary of methods available for a given textual representation of a model.<br>
*Why*: To inform the codebase users about how to interact with a model's textual representation.<br>
*Where*: ```src/cs3500/reversi/view/```

##### Hexagonal Reversi Textual View #####
*What*: A way of representing a provided model as a String.<br>
*Why*: To display the current status of an ongoing Hex Reversi game.<br>
*Where*: ```src/cs3500/reversi/view/```

##### Strategy Interface #####
*What*: Finds the best move for a player in a certain position.<br>
*Why*: To inform the codebase users about how to incorporate an AI into their design.<br>
*Where*: ```src/cs3500/reversi/strategy/```

##### Abstract Strategy Model #####
*What*: Provides a metric to evaluate moves, visible only to inheriting strategies, which also helps
determine the best move.<br>
*Why*: To give codebase users a blueprint for the logic of new strategies.<br>
*Where*: ```src/cs3500/reversi/strategy```

##### Player Interface #####
*What*: A guide for how players can interact with a Hex Reversi game.<br>
*Why*: To inform the codebase users about how to incorporate players into their design.<br>
*Where*: ```src/cs3500/reversi/controller/```

##### User Player #####
*What*: A human player.<br>
*Why*: To enable humans to interact with the game.<br>
*Where*: ```src/cs3500/reversi/controller```

##### Machine Player #####
*What*: A machine player with a specified strategy.<br>
*Why*: To enable machines to interact with the game.<br>
*Where*: ```src/cs3500/reversi/controller```

##### Model Event Listener Interface #####
*What*: Listens to events from the model.<br>
*Why*: To allow the controller to be notified by changes in the state of the model and then notify
the gui accordingly<br>
*Where*: ```src/cs3500/reversi/model```

##### Player Action Listener Interface #####
*What*: Listens to actions from the player.<br>
*Why*: To allow the controller to be notified by player actions and then update the model and gui
accordingly<br>
*Where*: ```src/cs3500/reversi/controller```

##### Hex Reversi Player Factory #####
*What*: Produces players based on player type and color.<br>
*Why*: To simplify the construction process of a player.<br>
*Where*: ```src/cs3500/reversi/controller```


## Testing ##

#### Note About Testing Design Decisions ####

Convenient method of testing the entire board:
Write a triple nested for loop that checks all coordinates from -layers + 1 to layers - 1. Write
an if statement that checks if the coordinates sum to 0. Inside the if statement, write if
statements to test that the state of a particular coordinate is White or Black. All others should
be empty. This is helpful for testing that moves don't modify other unexpected hexes on the board.

## Changes for Part 2 ##

#### List of changes we made and why we made them ####
* Removed HexCoords and abstracted over the entire project with HexPosn
* Added a read only interface to allow for non-mutational interaction with the model
* Added the function ```playableMoveExists``` to the model interface. The function takes in a color
  and is useful for determining if a given player is out of moves
* Added the option to create deep copies of the game. Can create read only deep copies
  Removed start game from the mutable model interface. Start game is called in the constructor now
* Added a Java Swing GUI that supports resizing and handles user interaction
* Abstracted getScore
* Added option for user to play on selected cell using enter key and pass using p key.
* Added a Strategy interface, which has one method, ```getBestMove```, which returns the best move
  in a given position, which allows for an AI to find the most optimal move based on what is valued
* Added an AbstractStrategy abstract class, which adds an abstract method, ```getMoveValue```, which
  returns the absolute value (between 0 and 1) generated by a move in a given position based on the
  specified strategy
* Added four unique strategies, ```GreedyStrategy``` (which prioritizes moves that flip the most
  pieces), ```AvoidAdjacentToCornersStrategy``` (which prioritizes moves that are not adjacent to
  corners), ```PrioritizeCornersStrategy``` (which prioritizes moves that take control of corners),
  ```MinimaxStrategy``` (which prioritizes moves that generate the least value for the opponent),
  as well as ```AggregateStrategy```, (which represents multiple strategies, and computes an aggregate
  value based on the ordering/priority of the strategies)
* Added two new mock models to test the newly implemented strategies. Both mocks use delegates.
  ```FindsFakeLegalMovesMockModel``` lies about which moves are legal, and the tests verify that the
  strategy employed finds the only "legal" moves. ```ConsidersAllPlayableMovesMockModel``` logs all
  legal moves that the strategy considers to ensure that all were tested.


## Changes for Part 3 ##

#### List of changes we made and why we made them ####
* Added the ```ModelEventListener``` and ```PlayerActionListener``` interfaces to enable the gui to
  be updated in accordance with model events (such as the game ending) and player actions (such as
  placing a piece).
* Added the ```HexReversiController```, which communicates with the player, model, and gui. Any
  player action via the gui notifies the controller, which notifies the model, and then the gui. Any
  change to the model is emitted to the controller, which notifies the gui.
* Added the ```Player``` interface and two implementing classes, ```UserPlayer``` and
  ```MachinePlayer```, which both notify all subscribers of player actions.
* Added a ```startGame``` method to the ```HexagonalReversi``` interface, implemented in the model
  class ```HexagonalReversiModel``` as notifying all listeners that the next player to play is white.
* Added a mock controller, ```ListensToPlayerAndModelMockController```, which uses inheritance to
  perform all the same duties as a regular controller, while also logging all notifications received,
  in order to confirm that all messages are being properly transmitted between the player, controller,
  and gui
* Added a ```HexReversiPlayerFactory```, which uses the factory pattern to manufacture various types
  of players: humans, and machines with all basic strategies (no aggregates)
* Updated the ```Reversi``` main method to parse up to three String arguments. The user can create a
  game by specifying the two types of players (human is default for both) and the number of layers of
  the board