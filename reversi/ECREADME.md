# CS3500 Reversi
## EXTRA CREDIT CHANGES
For the extra credit assignment, we implemented all features.
* Hints: players are able to toggle hints on or off using the 'h' key. Each player can turn hints on or off separately, though they will always see the hints for the current player. Hints display the move score for the currently selected tile, or if the move is illegal shows nothing.
  * Files changed: ReversiPanel, ViewableTile
### SQUARE REVERSI
* Model: we have a model for a game of square Reversi that works with any positive, even-side length (not specified, but the side length must be at least 4 in order to place starting tiles). It implements the same interfaces as our hexagonal model. We also have a textual rendering for this model.
  * Files changed: BasicReversi, ReversiTextualView
* GUI: we have a GUI view for square Reversi that displays properly and can be interacted with through clicks and key presses. It implements the same interfaces as our hexagonal view.
  * Files changed: ReversiPanel, ViewableTile
* Controller: our controller from all previous assignments works with both square and hexagonal Reversi.
  * Files changed: none
* Strategies: our strategy implementations work with both square and hexagonal Reversi.
  * Files changed: MaxCaptureStrategy, CornerStrategy, AvoidCornerAdjacentStrategy
* Other files changed: most testing files