# Player Interface Design Doc

### Structure: 
The interface ReversiPlayer is implemented by classes HumanPlayer and AIPlayer.

### Methods:
* public PlayerColor getColor()
  * Each player, human or AI, will have their own color, which they may need to know!
  * Our player implementation will have a field for the player's color, and this method returns that color
* public Pair<PlayStatus, Coordinate> play()
  * On each turn, a player must play. Their choices for playing are either move or pass.
  * If they move, the play method will return PlayStatus MOVE and the coordinate to move to
  * If they pass, the play method will return PlayStatus PASS and a null coordinate
  * The controller will be able to check the status of the play and take appropriate action